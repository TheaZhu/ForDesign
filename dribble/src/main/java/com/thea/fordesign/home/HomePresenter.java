package com.thea.fordesign.home;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.DribbleConstant;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class HomePresenter implements HomeContract.Presenter {
    public static final String TAG = HomePresenter.class.getSimpleName();

    private final HomeContract.View mView;
    private DribbbleService mService;

    public HomePresenter(HomeContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        initService();
    }

    public void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DribbleConstant.OAUTH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(DribbbleService.class);
    }

    @Override
    public void test() {
        LogUtil.i(TAG, "test");
        Call<String> call = mService.authorize();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.i(TAG, "code: " + response.code() + ", message: " + response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.i(TAG, "call executed: " + call.isExecuted());
                LogUtil.i(TAG, "fail message: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void start() {
    }
}
