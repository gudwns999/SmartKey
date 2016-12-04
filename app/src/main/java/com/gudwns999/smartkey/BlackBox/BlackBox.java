package com.gudwns999.smartkey.BlackBox;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gudwns999.smartkey.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kim on 2016-11-19.
 */

public class BlackBox extends Activity {
    private WebView mWebView;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blackbox);
        ButterKnife.bind(this);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);      // 웹뷰에서 자바 스크립트 사용
        mWebView.loadUrl( "http://192.168.0.252:8181/javascript_simple.html" );            // 웹뷰에서 불러올 URL 입력
        mWebView.setWebViewClient(new WishWebViewClient());
    }
    private class WishWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

    @OnClick({R.id.callBtn1, R.id.callBtn2})
    void onClicked(View view) {
        switch (view.getId()) {
            case R.id.callBtn1:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-4742-0181")));
                break;
            case R.id.callBtn2:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112")));
                break;
        }
    }
/*
    private static final String MOVIE_URL = "http://192.168.0.252:8181/?action=stream";

    VideoView videoView;
    Button callBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blackbox);

        videoView = (VideoView) findViewById(R.id.VideoView);
        callBtn = (Button)findViewById(R.id.callBtn);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        // Set video link (mp4 format )
        Uri video = Uri.parse(MOVIE_URL);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(video);
        videoView.requestFocus();
        videoView.start();
    }
     */
}