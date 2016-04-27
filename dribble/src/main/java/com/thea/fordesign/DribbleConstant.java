package com.thea.fordesign;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbleConstant {
    public static final String CLIENT_ID =
            "454e08950789bb4c0e77cc0399e312c201b55553d3de228a058cc5930992c404";
    public static final String CLIENT_SECRET =
            "f8ae2b3fa24a3e0dadf3be214789ca0318ad252998a253e68e7f62cd7c1f485c";
    public static final String CLIENT_ACCESS_TOKEN =
            "f591acdfce64a06349b6b70354d21722c9753f290a0e8a90590e9cac54424d29";

    public static final String BASE_URL = "https://api.dribbble.com/v1/";
    public static final String OAUTH = "https://dribbble.com/oauth/";
    public static final String HEADER_LINK = "Link: " +
            "<https://api.dribbble.com/v1/user/followers?page=1&per_page=100>; rel=\"prev\", " +
            "<https://api.dribbble.com/v1/user/followers?page=3&per_page=100>; rel=\"next\"";

    public static final String KEY_ACCESS_TOKEN = "access_token";

    public static final String SCOPE_PUBLIC = "public";
    public static final String SCOPE_WRITE = "write";
    public static final String SCOPE_COMMENT = "comment";
    public static final String SCOPE_UPLOAD = "upload";

    // default: any type
    public static final String SHOT_ANIMATED_LIST = "animated";
    public static final String SHOT_ATTACHMENT_LIST = "attachments";
    public static final String SHOT_DEBUT_LIST = "debuts";
    public static final String SHOT_PLAYOFF_LIST = "playoffs";
    public static final String SHOT_REBOUND_LIST = "rebounds";
    public static final String SHOT_TEAM_LIST = "teams";

    // default: recent, from now
    public static final String SHOT_TIME_FRAME_WEEK = "week";
    public static final String SHOT_TIME_FRAME_MONTH = "month";
    public static final String SHOT_TIME_FRAME_YEAR = "year";
    public static final String SHOT_TIME_FRAME_EVER = "ever";

    // default: sorted by popularity
    public static final String SHOT_SORT_COMMENTS = "comments";
    public static final String SHOT_SORT_RECENT = "recent";
    public static final String SHOT_SORT_VIEWS = "views";

    public static final int CODE_OK = 200;
    public static final int CODE_NO_CONTENT = 204;
    public static final int CODE_NOT_MODIFIED = 304;
    public static final int CODE_BAD_REQUEST = 400;
    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_UNPROCESSABLE_ENTITY = 422;
    public static final int CODE_REQUESTS_EXCEEDED = 429;

}
