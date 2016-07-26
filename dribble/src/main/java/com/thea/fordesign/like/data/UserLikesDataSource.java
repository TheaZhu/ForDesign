package com.thea.fordesign.like.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.bean.DribbbleUserLike;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface UserLikesDataSource {

    interface LoadLikesCallback {

        void onLikesLoaded(List<DribbbleUserLike> likes);

        void onDataNotAvailable();
    }

    interface GetLikeCallback {

        void onLikeLoaded(DribbbleUserLike like);

        void onDataNotAvailable();
    }

    void getLikes(@NonNull String authorization, int page, int perPage, LoadLikesCallback callback);

    void getLikes(@NonNull String authorization, @Nullable String url, int page,
                  LoadLikesCallback callback);

    void getLike(@NonNull String authorization, int likeId, GetLikeCallback callback);

    void saveLike(@NonNull String authorization, @NonNull DribbbleUserLike like);

    void refreshLikes();

    void deleteAllLikes();

    void deleteLike(int likeId);

}
