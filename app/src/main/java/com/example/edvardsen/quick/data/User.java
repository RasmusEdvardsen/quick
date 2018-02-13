package com.example.edvardsen.quick.data;

import java.util.ArrayList;

/**
 * Created by buller on 17/01/2018.
 */

public class User {
    private static User instance = null;
    private String userID = "";
    private String userName = "";
    private String email = "";
    private ArrayList<String> listRooms = new ArrayList<String>();
    protected User(){

    }
    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }
    public static String getUserID(){
        return instance.userID;
    }
    public static String getUserName(){
        return instance.userName;
    }
    public static String getEmail(){
        return instance.email;
    }
    public static ArrayList<String> getListRooms(){
        return instance.listRooms;
    }
    public static void setUserID(String uID){
        instance.userID = uID;
    }
    public static void setUserName(String userName) { instance.userName = userName; }
    public static void setEmail(String email){
        instance.email = email;
    }
    //Probably need way more here -- like add/delete/deleteall, etc.
    public static void setListRooms(ArrayList<String> roomList){
        instance.listRooms = roomList;
    }
}