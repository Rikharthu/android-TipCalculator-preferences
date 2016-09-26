package com.murach.tipcalculator;

import android.os.Bundle;
import android.preference.PreferenceFragment;


public class SettingsFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load preferences from an XML resources
        addPreferencesFromResource(R.xml.preferences);

    }

}
