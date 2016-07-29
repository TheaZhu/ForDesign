package com.thea.fordesign.home;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.DribbbleConstant;
import com.thea.fordesign.bean.DribbbleShot;
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
                .baseUrl(DribbbleConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(DribbbleService.class);
    }

    @Override
    public void test() {
        LogUtil.i(TAG, "test");
        Call<DribbbleShot> call = mService.getShot(DribbbleConstant.AUTH_TYPE + DribbbleConstant
        .CLIENT_ACCESS_TOKEN, 2222);
        call.enqueue(new Callback<DribbbleShot>() {
            @Override
            public void onResponse(Call<DribbbleShot> call, Response<DribbbleShot> response) {
                LogUtil.i(TAG, "code: " + response.code() + ", message: " + response.message());
            }

            @Override
            public void onFailure(Call<DribbbleShot> call, Throwable t) {
                LogUtil.i(TAG, "call executed: " + call.isExecuted() + ", url: " + call.request().url());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void start() {
    }
}
