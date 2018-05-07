package com.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by Keval on 08-Jun-17.
 * This class provides api to get and save particular site messages. This class follows singleton
 * pattern.
 *
 * @author {@link 'https://github.com/kevalpatel2106'}
 */

@SuppressWarnings("unused")
public class UserMessageManager {
    @SuppressWarnings("unused")
    private static final String TAG = "UserMessageManager";
    /**
     * Name of the shared preference file.
     */
    private static final String PREF_FILE = "user_messages_pref";

    /**
     * Key to indicate number of fail attempt to site messages.
     */
    private static final String PREF_KEY_FAIL_ATTEMPT = "pref_key_fail_attempt";

    private static SharedPreferences sSharedPreferences;
    private static volatile UserMessageManager sUserMessageManager;

    /**
     * Private constructor to make it singleton.
     *
     * @param context instance of the caller.
     */
    private UserMessageManager(Context context) {
        sSharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }

    /**
     * Get the sole instance of {@link UserMessageManager} singleton.
     *
     * @param context instance of caller.
     * @return Sole instance {@link UserMessageManager}
     */
    public static UserMessageManager getInstance(@NonNull Context context) {

        if (sUserMessageManager == null) {

            //Make it thread safe to make it thread safe.
            synchronized (UserMessageManager.class) {
                if (sUserMessageManager == null)    //Double check
                    sUserMessageManager = new UserMessageManager(context);
            }
        }
        return sUserMessageManager;
    }

    /**
     * This method will save all the site messages to the shared preference.
     *
     * @param responseBody json response body for the site messages.
     * @return true if results stored successfully or false if any exception occurs.
     */
    @SuppressWarnings("unchecked")
    public boolean saveUserMessages(@NonNull ResponseBody responseBody) {
        //Get the response string
        BufferedReader r = new BufferedReader((new InputStreamReader(responseBody.byteStream())));
        StringBuilder total = new StringBuilder();
        try {
            String line;
            while ((line = r.readLine()) != null)
                total.append(line).append('\n');
        } catch (IOException e) {
            Timber.e(e);
            return false;
        }

        //Parse the response,
        try {
            JSONObject obj = new JSONObject(total.toString()).getJSONObject("result");
            if (obj.has("site_messages")) { //Check if the error occurred?
                Gson gson = new Gson();
                Map<String, String> map = new HashMap<>();
                map = (Map<String, String>) gson.fromJson(obj.getJSONObject("site_messages").toString(), map.getClass());

                //Save to prefaces.
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                for (String key : map.keySet()) editor.putString(key, map.get(key));
                editor.putInt(PREF_KEY_FAIL_ATTEMPT, 0);
                editor.apply();
                return true;
            }
        } catch (JSONException e) {
            Timber.e(e);
            return false;
        }
        return false;
    }

    public void onErrorOccurred() {
        final SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt(PREF_KEY_FAIL_ATTEMPT, sSharedPreferences.getInt(PREF_KEY_FAIL_ATTEMPT, 0) + 1);
        editor.apply();
    }

    /**
     * Get the user/site messages for particular key.
     *
     * @param key Key of the message required.
     * @return Message for the particular key and if the key not found, it will return "Something went wrong."
     */
    public String getUserMessage(@NonNull String key) {
        String message = sSharedPreferences.getString(key, null);
        return message == null ? "Something went wrong." : message;
    }

    /**
     * Check if the user messages are saved?
     *
     * @return True if the messages are there.
     */
    public Boolean areUserMessagesAvailable() {
        return sSharedPreferences.getInt(PREF_KEY_FAIL_ATTEMPT, Integer.MAX_VALUE) < 3;
    }
}
