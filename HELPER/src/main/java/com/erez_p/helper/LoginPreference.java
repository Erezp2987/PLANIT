package com.erez_p.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class LoginPreference {
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_IDFS = "idFs";

    private SharedPreferences sharedPreferences;
    private Context context;

    public LoginPreference(Context context) {
        this.context = context;
        try {

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    PREF_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveLoginCredentials(String idfs) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IDFS, idfs);
        editor.apply();
    }

    public String getIdfs() {
        return sharedPreferences.getString(KEY_IDFS, null);
    }

    public void clearLoginCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_IDFS);
        editor.apply();
    }
}