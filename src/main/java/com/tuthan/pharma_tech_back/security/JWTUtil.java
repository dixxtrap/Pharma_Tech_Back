package com.tuthan.pharma_tech_back.security;

public class JWTUtil {
    public static final String SECRET ="secret";
    public static final String AUTH_HEADER="Authorization";
    public static final String PREFIX="Bearer ";
    public static final long EXPIRE_ACCESS_TOKEN =864_000_000;  //10 jours
    public static final long EXPIRE_REFRESH_TOKEN =15*60*1000;

}
