package com.example.edvardsen.quick.helpers;

import okhttp3.MediaType;

public class DefaultValues {
    public static final String rootUrl = "http://192.168.43.229"; //IPv4.
    public static final String sockPort = "3000";
    public static final String restPort = "27017";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String roomType = "roomType";
    public static final String roomTypeCouple = "couple";
    public static final String roomTypePrivate = "private";
    public static final String roomTypePublic = "public";
}
