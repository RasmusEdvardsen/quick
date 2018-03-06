package com.example.edvardsen.quick.web;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.helpers.DefaultValues;
import com.example.edvardsen.quick.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by buller on 15/01/2018.
 */

public class IO {
    private static Socket socket = null;

    //TODO: FIX THIS
    private static Activity activity;
    private static RelativeLayout relativeLayout;
    private static RelativeLayout personRelativeLayout;

    private static View v = null;
    private static View view = null;

    public static Socket getSocket() {
        if (socket == null) {
            try {
                socket = io.socket.client.IO.socket(DefaultValues.rootUrl
                        + ":"
                        + DefaultValues.sockPort
                        + "/?uid="
                        + User.getUserID());
                socket.on("newmessage", onNewMessage);
                socket.on("privatemessage", onPrivateMessage);
                socket.on("userrooms", onUserRooms);
                socket.on("newroom", onNewRoom);
                //socket.on("userRooms", onUserRooms);
                socket.connect();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return socket;

        //TODO: if user is logged in, and then signs up new account, socket should reconnect.
    }

    public static void configureActivity(Activity act) {
        activity = act;
    }

    public static void configureLayout(RelativeLayout layout) {
        relativeLayout = layout;
    }

    public static void configurePrivateRoomLayout(RelativeLayout layout) {
        personRelativeLayout = layout;
    }

    private static Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            final String receivedMsg = args[0].toString();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    Log.d("onnewmessage", receivedMsg);
                    createMessage(receivedMsg);
                }
            });
        }
    };

    private static Emitter.Listener onPrivateMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final String rcvdmsg = args[0].toString();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("onprivatemessage", rcvdmsg);
                    createMessage(rcvdmsg);
                }
            });
        }
    };

    private static Emitter.Listener onUserRooms = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final String rooms = args[0].toString();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("onuserrooms", rooms);
                    setRooms(rooms);
                }
            });
        }
    };

    private static Emitter.Listener onNewRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final String room = args[0].toString();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("onnewroom", room);
                    User.setCurrentRoom(room);
                }
            });
        }
    };

    private static void setRooms(String rooms){
        TextView tv = new TextView(activity);
        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (view != null) {
            tvParams.addRule(RelativeLayout.BELOW, view.getId());
            tv.setId(View.generateViewId());
        } else {

            tv.setId(View.generateViewId());
        }
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorWhite));
        tv.setLayoutParams(tvParams);

        tv.setText(rooms);

        view = tv;
        personRelativeLayout.addView(tv);
    }

    private static void createMessage(String text) {
        // TODO: 10/05/2017 Handle only sending notifications when actual message from user!.
        /*if (true) { //!text.startsWith(prependedUserName)
            //HelperMethods.generateNotification(getBaseContext(), prependedUserName, text);
            HelperMethods.generateNotification(getBaseContext(), text);
        }*/

        TextView tv = new TextView(activity);
        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (v != null) {
            tvParams.addRule(RelativeLayout.BELOW, v.getId());
            tv.setId(View.generateViewId());
        } else {
            tv.setId(View.generateViewId());
        }
        tvParams.topMargin = 20;
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(20, 20, 20, 20);
        tv.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        tv.setTextColor(Color.WHITE);
        tv.setLayoutParams(tvParams);

        tv.setText(text);

        v = tv;
        relativeLayout.addView(tv);
    }
}