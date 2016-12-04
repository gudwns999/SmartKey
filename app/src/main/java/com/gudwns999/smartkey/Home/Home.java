package com.gudwns999.smartkey.Home;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gudwns999.smartkey.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kim on 2016-11-19.
 */

public class Home extends Activity {
    private SoundPool soundPool;
    private int soundpoolID;
    TextView oilText,climateText,locateText;
    ImageView carImage;
    Button lockBtn,unlockBtn;

    private URL Url;
    private String strUrl,strCookie,result;
    private String code_name;

    private String server = "192.168.0.252";
    private int port = 7979;
    private Socket socket;
    private OutputStream outs;
    private Thread homeThread;
    private logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        lockText = (TextView)findViewById(R.id.lockText);
        oilText = (TextView)findViewById(R.id.oilText);
        climateText = (TextView)findViewById(R.id.climateText);
        locateText = (TextView)findViewById(R.id.locateText);
        carImage = (ImageView)findViewById(R.id.carImage);
        lockBtn = (Button)findViewById(R.id.lockBtn);
        unlockBtn = (Button)findViewById(R.id.unlockBtn);
        lockText.setText("Lock");

        //httpURLConnetion
        try{
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    strUrl = "http://192.168.0.252"; //탐색하고 싶은 URL이다.
                }
                @Override
                protected Void doInBackground(Void... voids) {
                    try{
                        Url = new URL(strUrl);  // URL화 한다.
                        HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // URL을 연결한 객체 생성.
                        conn.setRequestMethod("GET"); // get방식 통신
                        conn.setDoOutput(true);       // 쓰기모드 지정
                        conn.setDoInput(true);        // 읽기모드 지정
                        conn.setUseCaches(false);     // 캐싱데이터를 받을지 안받을지
                        conn.setDefaultUseCaches(false); // 캐싱데이터 디폴트 값 설정

                        strCookie = conn.getHeaderField("Set-Cookie"); //쿠키데이터 보관

                        InputStream is = conn.getInputStream();        //input스트림 개방

                        StringBuilder builder = new StringBuilder();   //문자열을 담기 위한 객체
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));  //문자열 셋 세팅
                        String line;

                        while ((line = reader.readLine()) != null) {
                            builder.append(line+ "\n");
                        }
                        result = builder.toString();
                    }catch(MalformedURLException | ProtocolException exception) {
                        exception.printStackTrace();
                    }catch(IOException io){
                        io.printStackTrace();
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    System.out.println(result);
                    //result printout
 //                   lockText.setText(result.toString());

                }
            }.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        //socket connection
        TextView t = (TextView)findViewById(R.id.testText);
        logger = new logger(t);

        try{
            if(socket!=null)
            {
                socket.close();
                socket = null;
            }

            socket = new Socket(server, port);
            outs = socket.getOutputStream();

            homeThread = new Thread(new HomeThread(logger, socket));
            homeThread.start();
            logger.log("Connected");
        } catch (IOException e){
            logger.log("Fail to connect");
            e.printStackTrace();
        }
/*
        try{
            if(socket!=null)
            {
                socket.close();
                socket = null;
            }
            socket = new Socket(server, port);
            outs = socket.getOutputStream();

            homeThread = new Thread(new HomeThread(logger, socket));
            homeThread.start();
            logger.log("Connected");
        } catch (IOException e){
            logger.log("Fail to connect");
            e.printStackTrace();
        }
        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sndOpkey = "LOCK";
            }
        });
        unlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sndOpkey = "UNLOCK";
            }
        });
*/
    }

    @Bind(R.id.lockText)
    TextView lockText;

    @OnClick({R.id.lockBtn, R.id.unlockBtn})
    public void lockbtnClicked(View view) {
        soundPool = new SoundPool(1, AudioManager.STREAM_ALARM,0);
        soundpoolID = soundPool.load(this, R.raw.lock, 1);
        soundPool.play(soundpoolID, 1.0F, 1.0F, 1, 0, 1.0F);

        String sndOpkey = "CMD";

        switch (view.getId()) {

            case R.id.lockBtn:
                lockText.setText("Lock");
                sndOpkey = "LOCK";
                break;
            case R.id.unlockBtn:
                lockText.setText("UnLock");
                sndOpkey = "UNLOCK";

                break;
        }
        try{
            outs.write(sndOpkey.getBytes("UTF-8"));
            outs.flush();
        } catch (IOException e){
            logger.log("Fail to send");
            e.printStackTrace();
        }
    }

    void exitFromRunLoop(){
        try {
            String sndOpkey = "[close]";
            outs.write(sndOpkey.getBytes("UTF-8"));
            outs.flush();
        } catch (IOException e) {
            logger.log("Fail to send");
            e.printStackTrace();
        }
    }
}
