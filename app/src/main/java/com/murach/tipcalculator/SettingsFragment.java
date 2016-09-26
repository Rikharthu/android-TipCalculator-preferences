package com.murach.tipcalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;


public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String settings = getArguments().getString("someKey");
        Toast.makeText(getActivity(), settings, Toast.LENGTH_SHORT).show();
        // Load preferences from an XML resources
        addPreferencesFromResource(R.xml.preferences);

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_remember_perc")) {
            Preference rememberPercPref = findPreference(key);
            // Set summary to be the user-description for the selected value
//            rememberPercPref.setSummary(sharedPreferences.getString(key, "This was just edited!"));
//            Log.d(TAG,"You")
            Toast.makeText(getActivity(), "You edited 'Remember Tip Percent'!", Toast.LENGTH_SHORT).show();
        }
    }

    // For better lifecycle management register and unregister listener during onResume and onPause()
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
