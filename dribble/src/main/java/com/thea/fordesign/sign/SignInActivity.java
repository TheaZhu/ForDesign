package com.thea.fordesign.sign;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseActivity;
import com.thea.fordesign.bean.AccessToken;
import com.thea.fordesign.config.DribbbleConstant;
import com.thea.fordesign.util.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends BaseActivity {
    public static final String TAG = SignInActivity.class.getSimpleName();

    private WebView mWebView;
    private DribbbleService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mService = new DribbbleService.Builder().create();

        mWebView = (WebView) findViewById(R.id.webview);
        init();

//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(DribbleConstant.OAUTH +
// "/authorize"
//                + "?client_id=" + DribbleConstant.CLIENT_ID)));
    }

    public void init() {
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        //webView.requestFocus();
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
//                    hideWaitingDialog();
                }
            }
        });
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                LogUtil.i(TAG, "url: " + url);
                if (url != null && url.startsWith(DribbbleConstant.REDIRECT_URI)) {
                    // use the parameter your API exposes for the code (mostly it's "code")
                    Uri uri = Uri.parse(url);
                    String code = uri.getQueryParameter("code");
                    if (code != null) {
                        getAccessToken(code);
                    } else if (uri.getQueryParameter("error") != null) {
                        LogUtil.i(TAG, "get access token error");
                    }
                }
                super.onPageStarted(view, url, favicon);
//                showWaitingDialog("加载中");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                hideWaitingDialog();
            }
        });
        mWebView.loadUrl(DribbbleConstant.OAUTH + "/authorize?client_id=" + DribbbleConstant
                .CLIENT_ID + "&scope=" + DribbbleConstant.USER_SCOPE);
    }

    private void getAccessToken(String code) {
        Call<AccessToken> call = mService.postToken(code);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                LogUtil.i(TAG, "get access token code: " + response.code() +  ", message: " +
                        response.message());
                AccessToken accessToken = response.body();
                saveAccessToken(accessToken);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                LogUtil.i(TAG, "get access token call executed: " + call.isExecuted() + ", url: " +
                        call.request().url());
            }
        });
    }

    private void saveAccessToken(AccessToken accessToken) {
        UserModel userModel = new UserModel(SignInActivity.this);
        userModel.setDribbbleUserAccessToken(accessToken.getTokenType() + " " +
                accessToken.getAccessToken());
        userModel.setUserSignIn(true);
        LogUtil.i(TAG, "scope: " + accessToken.getScope());
    }
}
