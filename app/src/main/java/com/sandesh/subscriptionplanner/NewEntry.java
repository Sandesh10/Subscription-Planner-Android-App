package com.sandesh.subscriptionplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewEntry extends AppCompatActivity {

    public static final String RESULT_MESSAGE_KEY = "newEntry";

    private TextInputLayout textInputName;
    private TextInputLayout textInputDesc;
    private TextInputLayout textInputAmount;
    private TextInputLayout textInputDate;
    private EditText textDate;
    private Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        getSupportActionBar().setTitle("Add New Subscription");
        textInputName = findViewById(R.id.text_name);
        textInputDesc = findViewById(R.id.text_description);
        textInputAmount = findViewById(R.id.text_amount);
        textInputDate = findViewById(R.id.textInput_date);
        textDate = (EditText) findViewById(R.id.text_date);
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        textDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewEntry.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private boolean validateName() {
        String name = textInputName.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            textInputName.setError("Field can't be empty");
            return false;
        } else if (name.length() > 25) {
            textInputName.setError("Username too long");
            return false;
        }   else {
            textInputName.setError(null);
            return true;
        }
    }

    private boolean validateAmount() {
        String amount = textInputAmount.getEditText().getText().toString().trim();
        String pattern = "^([0-9]{1,4})(.)([0-9]{1,2})$|^([0-9]{1,4})$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(amount);
        if (m.matches()){
            textInputAmount.setError(null);
            return true;
        } else {
            textInputAmount.setError("Invalid amount");
            return false;
        }
    }

    private boolean validateDate() {
        String date = textDate.getText().toString().trim();
        if (date.isEmpty()) {
            textInputDate.setError("Field can't be empty");
            return false;
        } else {
            textInputDate.setError(null);
            return true;
        }

    }

    public void confirmInput(View v) {
        if (!validateName() | !validateAmount() | !validateDate())  {
            return;
        } else {
            Toast.makeText(NewEntry.this, "New Subscription Added",
                    Toast.LENGTH_SHORT).show();
            // extract data
            String name = textInputName.getEditText().getText().toString();
            String desc = textInputDesc.getEditText().getText().toString();
            String amount = textInputAmount.getEditText().getText().toString();
            String date = textDate.getText().toString();

            // pass data
            Intent intent = new Intent(NewEntry.this, MainActivity.class);
            SubscriptionClass obj1 = new SubscriptionClass(name, desc, date, amount);
            intent.putExtra(RESULT_MESSAGE_KEY, obj1);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    public static SubscriptionClass getResultMessageKey(Intent intent) {
//        return intent.getStringArrayListExtra(RESULT_MESSAGE_KEY);
        return (SubscriptionClass) intent.getSerializableExtra(RESULT_MESSAGE_KEY);
    }

    public Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        // Takes the locale of the system
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,getCurrentLocale(getBaseContext()));
        Log.i("MyApp", getCurrentLocale(getBaseContext()).toString());
        textDate.setText(sdf.format(myCalendar.getTime()));
    }
}


