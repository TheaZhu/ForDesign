package com.thea.fordesign;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface FileDownloadService {

    @Streaming
    @GET
    Observable<ResponseBody> downloadImageFile(@Url String fileUrl);

    class Builder {
        private Retrofit.Builder mRetrofitBuilder;

        public Builder() {
            mRetrofitBuilder = new Retrofit.Builder().baseUrl(DribbbleConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        }

        public Builder baseUrl(String baseUrl) {
            mRetrofitBuilder.baseUrl(baseUrl);
            return this;
        }

        public FileDownloadService create() {
            return mRetrofitBuilder.build().create(FileDownloadService.class);
        }
    }
}
