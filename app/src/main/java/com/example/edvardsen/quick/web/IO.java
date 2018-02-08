package com.example.edvardsen.quick.web;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.edvardsen.quick.data.User;
import com.example.edvardsen.quick.helpers.DefaultValues;
import com.example.edvardsen.quick.R;

import java.net.URISyntaxException;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by buller on 15/01/2018.
 */

public class IO {
    private static Socket socket = null;

    private static Activity activity;
    private static RelativeLayout relativeLayout;

    private static View v = null;

    public static Socket getSocket() {
        if (socket == null) {
            try {
                User user = User.getInstance();
                socket = io.socket.client.IO.socket(DefaultValues.rootUrl
                        + ":"
                        + DefaultValues.sockPort
                        + "/?uid="
                        + user.getUserID());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        socket.on("new message", onNewMessage);
        socket.on("private", onPrivateMessage);
        socket.connect();
        return socket;
    }

    public static void configureActivity(Activity act) {
        activity = act;
    }

    public static void configureLayout(RelativeLayout layout) {
        relativeLayout = layout;
    }

    private static Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final String receivedMsg = args[0].toString();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    Log.i("asd", relativeLayout.toString());
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
                    Log.i("private", rcvdmsg);
                    createMessage(rcvdmsg);
                }
            });
        }
    };

    public static void createMessage(String text) {

        // TODO: 10/05/2017 Handle only sending notifications when actual message from user!.
        /*if (true) { //!text.startsWith(prependedUserName)
            //HelperMethods.generateNotification(getBaseContext(), prependedUserName, text);
            HelperMethods.generateNotification(getBaseContext(), text);
        }*/

        TextView tv = new TextView(activity);
        //RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (v != null) {
            //tvParams.addRule(RelativeLayout.BELOW, v.getId());
            tv.setId(View.generateViewId());
        } else {
            tv.setId(View.generateViewId());
        }
        //DER SKAL CENTRERES!
        //tvParams.topMargin = 20;
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(20, 20, 20, 20);
        tv.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        tv.setTextColor(Color.WHITE);
        //tv.setLayoutParams(tvParams);

        tv.setText(text);

        v = tv;
        relativeLayout.addView(tv);
    }
}