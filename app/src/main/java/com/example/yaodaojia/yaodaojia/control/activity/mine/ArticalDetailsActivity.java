package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.yaodaojia.yaodaojia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticalDetailsActivity extends AppCompatActivity {
    @BindView(R.id.artical_back)
    ImageView articalBack;
    @BindView(R.id.artical_webview)
    WebView articalWebview;
    private WebView webview;
    private WebSettings settings;
    private Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artical_details);
        ButterKnife.bind(this);
        webview = (WebView) findViewById(R.id.artical_webview);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.i("Artical", url);
        settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.loadUrl("http://" + url);
    }

    @OnClick(R.id.artical_back)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.artical_back:
                ArticalDetailsActivity.this.finish();
                break;
        }
    }
}
