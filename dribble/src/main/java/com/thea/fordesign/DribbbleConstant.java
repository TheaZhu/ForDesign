package com.thea.fordesign;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleConstant {
    public static final String CLIENT_ID =
            "454e08950789bb4c0e77cc0399e312c201b55553d3de228a058cc5930992c404";
    public static final String CLIENT_SECRET =
            "f8ae2b3fa24a3e0dadf3be214789ca0318ad252998a253e68e7f62cd7c1f485c";
    public static final String CLIENT_ACCESS_TOKEN =
            "f591acdfce64a06349b6b70354d21722c9753f290a0e8a90590e9cac54424d29";
    public static final String USER_ACCESS_TOKEN =
            "05589f9e8cb9d7e8809c40be35d9f984c4ded4b9fd602f4843d05fc583609aa7";
    public static final String AUTH_TYPE = "Bearer ";
    public static final String USER_SCOPE = "public+write+comment+upload";

    public static final String BASE_URL = "https://api.dribbble.com/v1/";
    public static final String OAUTH = "https://dribbble.com/oauth/";
    public static final String REDIRECT_URI = "http://thea.com";
    public static final String HEADER_LINK = "Link: " +
            "<https://api.dribbble.com/v1/user/followers?page=1&per_page=100>; rel=\"prev\", " +
            "<https://api.dribbble.com/v1/user/followers?page=3&per_page=100>; rel=\"next\"";

    public static final String DEFAULT_SHOT_IMAGE_URL = "https://d13yacurqjgara.cloudfront.net" +
            "/assets/logo-bw-0200c7483844c355752e89efaa4ba89b83c9c591d70254ba10f4b25d901359d0.gif";

    public static final String KEY_ACCESS_TOKEN = "access_token";

    public static final String SCOPE_PUBLIC = "public";
    public static final String SCOPE_WRITE = "write";
    public static final String SCOPE_COMMENT = "comment";
    public static final String SCOPE_UPLOAD = "upload";

    // default: any type
    public static final String SHOT_LIST_ANY = "any";
    public static final String SHOT_LIST_ANIMATED = "animated";
    public static final String SHOT_LIST_ATTACHMENT = "attachments";
    public static final String SHOT_LIST_DEBUT = "debuts";
    public static final String SHOT_LIST_PLAYOFF = "playoffs";
    public static final String SHOT_LIST_REBOUND = "rebounds";
    public static final String SHOT_LIST_TEAM = "teams";
    public static final String SHOT_LIST_DEFAULT = SHOT_LIST_ANY;

    // default: recent, from now
    public static final String SHOT_TIME_FRAME_RECENT = "recent";
    public static final String SHOT_TIME_FRAME_WEEK = "week";
    public static final String SHOT_TIME_FRAME_MONTH = "month";
    public static final String SHOT_TIME_FRAME_YEAR = "year";
    public static final String SHOT_TIME_FRAME_EVER = "ever";
    public static final String SHOT_TIME_FRAME_DEFAULT = SHOT_TIME_FRAME_RECENT;

    // default: sorted by popularity
    public static final String SHOT_SORT_POPULARITY = "popularity";
    public static final String SHOT_SORT_COMMENTS = "comments";
    public static final String SHOT_SORT_RECENT = "recent";
    public static final String SHOT_SORT_VIEWS = "views";
    public static final String SHOT_SORT_DEFAULT = SHOT_SORT_POPULARITY;

    public static final int PER_PAGE_DEFAULT = 12;


    public static final int CODE_ERROR = 199;
    public static final int CODE_OK = 200;
    public static final int CODE_NO_CONTENT = 204;
    public static final int CODE_NOT_MODIFIED = 304;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_UNPROCESSABLE_ENTITY = 422;
    public static final int CODE_REQUESTS_EXCEEDED = 429;

}
