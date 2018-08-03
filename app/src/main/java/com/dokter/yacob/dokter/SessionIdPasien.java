package com.dokter.yacob.dokter;

/**
 * Created by yacob on 12/11/2017.
 */


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionIdPasien {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "YacobPref";
    public static final String KEY_ID_PASIEN = "id_pasien";

    public SessionIdPasien(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createIdPasienSession(String id){
        editor.putString(KEY_ID_PASIEN, id);
        editor.commit();
    }

    public HashMap<String, String> getIdPasienDetails(){
        HashMap<String, String> idpasien = new HashMap<String, String>();
        idpasien.put(KEY_ID_PASIEN, pref.getString(KEY_ID_PASIEN, null));
        return idpasien;
    }

}
