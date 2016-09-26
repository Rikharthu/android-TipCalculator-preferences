package com.murach.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content
//        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment())
//                .commit();

    }

    @Override
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        loadHeadersFromResource(R.xml.preferences_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName);
    }
}
