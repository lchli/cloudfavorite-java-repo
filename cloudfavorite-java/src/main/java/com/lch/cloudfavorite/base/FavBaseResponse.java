package com.lch.cloudfavorite.base;

public class FavBaseResponse {
    public static final String CODE_SUCCESS = "1";
    public static final String CODE_ERROR = "-1";

    public String code = CODE_SUCCESS;
    public String errmsg;

    public void markErrorCode() {
        code = CODE_ERROR;
    }
}