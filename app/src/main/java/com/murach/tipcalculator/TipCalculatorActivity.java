package com.murach.tipcalculator;

import java.text.NumberFormat;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class TipCalculatorActivity extends Activity 
implements OnEditorActionListener, OnClickListener {


    private SharedPreferences prefs;
    private boolean rememberTipPercent=true;
    private int rounding = 1;

    // define variables for the widgets
    private EditText billAmountEditText;
    private TextView percentTextView;   
    private Button   percentUpButton;
    private Button   percentDownButton;
    private TextView tipTextView;
    private TextView totalTextView;
    
    // define the SharedPreferences object
    private SharedPreferences savedValues;
    
    // define instance variables that should be saved
    private String billAmountString = "";
    private float tipPercent = .15f;
    
    private static final String TAG = "TipCalculatorActivity";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

        // get references to the widgets
        billAmountEditText = (EditText) findViewById(R.id.billAmountEditText);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        percentUpButton = (Button) findViewById(R.id.percentUpButton);
        percentDownButton = (Button) findViewById(R.id.percentDownButton);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        // set the listeners
        billAmountEditText.setOnEditorActionListener(this);
        percentUpButton.setOnClickListener(this);
        percentDownButton.setOnClickListener(this);

        // set default preference values.
        // will be set only once (if has never been set before) since we pass false for readAgain param
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        // get default shared preferences for this app
        prefs=PreferenceManager.getDefaultSharedPreferences(this);


        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        
        Log.d(TAG, "onCreate executed");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tip_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();

                Log.d(TAG,"rounding: "+rounding+"\tremember percent: "+rememberTipPercent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        // save the instance variables       
        Editor editor = savedValues.edit();
        editor.putString("billAmountString", billAmountString);
        editor.putFloat("tipPercent", tipPercent);
        editor.commit();

        super.onPause();
        
        Log.d(TAG, "onPause executed");
    }
    
    @Override
    public void onResume() {
        super.onResume();

        // set preferences
        rememberTipPercent=prefs.getBoolean("pref_remember_perc",false);
        Toast.makeText(this, rememberTipPercent+"", Toast.LENGTH_SHORT).show();
        rounding=Integer.parseInt(prefs.getString("pref_rounding","0"));

        if(rememberTipPercent){
            tipPercent = savedValues.getFloat("tipPercent", 0.15f);
        }else{
            int defaultPercent =Integer.parseInt(prefs.getString("pref_percent","15"));
            tipPercent=defaultPercent/100f;
        }

        // get the instance variables
//        billAmountString = savedValues.getString("billAmountString", "");
//        tipPercent = savedValues.getFloat("tipPercent", 0.15f);

        // set the bill amount on its widget
//        billAmountEditText.setText(billAmountString);
        
        // calculate and display
        calculateAndDisplay();
        
        Log.d(TAG, "onResume executed");
    }    
    
    public void calculateAndDisplay() {        

        // get the bill amount
        billAmountString = billAmountEditText.getText().toString();
        float billAmount;
        try {
            billAmount = Float.parseFloat(billAmountString);        	
        }
        catch (NumberFormatException e) {
            billAmount = 0;
        }
        
        // calculate tip and total 
        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount + tipAmount;
        
        // display the other results with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));
        
        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercent));
    }
    
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

    	Toast.makeText(this, "actionId: " + actionId, Toast.LENGTH_SHORT).show();

    	if (actionId == EditorInfo.IME_ACTION_DONE ||
    		actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }        
        return false;
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.percentDownButton:
            tipPercent = tipPercent - .01f;
            calculateAndDisplay();
            break;
        case R.id.percentUpButton:
            tipPercent = tipPercent + .01f;
            calculateAndDisplay();
            break;
        }
    }
}