package com.dokter.yacob.dokter;



import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;

public class sessionToken {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "TokenPref";
    public static final String KEY_TOKEN = "token";

    public sessionToken(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createToken(String id) {
        editor.putString(KEY_TOKEN, id);
        editor.commit();
    }

    public HashMap<String, String> getToken() {
        HashMap<String, String> token = new HashMap<String, String>();
        token.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        return token;
    }
}