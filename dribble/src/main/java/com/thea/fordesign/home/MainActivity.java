package com.thea.fordesign.home;

import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseActivity;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fg_home);
        new HomePresenter(homeFragment);
    }

    /*public void test(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DribbleConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<DribbbleShot> call = retrofit.create(DribbbleService.class).getShot(DribbleConstant.AUTH_TYPE + DribbleConstant
                .CLIENT_ACCESS_TOKEN, 2222);
        call.enqueue(new Callback<DribbbleShot>() {
            @Override
            public void onResponse(Call<DribbbleShot> call, Response<DribbbleShot> response) {
                LogUtil.i(TAG, "code: " + response.code() + ", message: " + response.message());
            }

            @Override
            public void onFailure(Call<DribbbleShot> call, Throwable t) {
                LogUtil.i(TAG, "call executed: " + call.isExecuted() + ", url: " + call.request().url());
//                LogUtil.i(TAG, "fail message: " + t.getMessage());
//                t.printStackTrace();
            }
        });
    }*/
}
