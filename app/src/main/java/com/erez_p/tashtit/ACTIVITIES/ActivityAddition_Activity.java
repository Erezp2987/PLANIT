package com.erez_p.tashtit.ACTIVITIES;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.erez_p.model.Activity;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.ActivitiesViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityAddition_Activity extends BaseActivity {
    private String tripID;
    private ActivitiesViewModel viewModel;
    private EditText AName,Aprice,Adate,Atime,Aduration;
    private Button returnButton, confirmButton;
    private String activityID;
    private Activity currentActivity;
    private static long aLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addition);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setViewModel();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        tripID = getIntent().getStringExtra("tripId");
        activityID = getIntent().getStringExtra("activityId");
        AName = findViewById(R.id.editTextActivityName);
        Aprice = findViewById(R.id.editTextActivityPrice);
        Adate = findViewById(R.id.editTextActivityDate);
        Adate.setFocusable(false);
        Atime = findViewById(R.id.editTextActivityTime);
        Atime.setFocusable(false);
        Aduration = findViewById(R.id.editTextActivityDuration);
        confirmButton = findViewById(R.id.buttonConfirm);
        returnButton = findViewById(R.id.buttonReturn);
    }

    @Override
    protected void setListeners() {
        confirmButton.setOnClickListener(v -> {
            if(!AName.getText().toString().equals("")&&
                    !Aprice.getText().toString().equals("")&&
                    !Adate.getText().toString().equals("")&&
                    !Atime.getText().toString().equals("")&&
                    !Aduration.getText().toString().equals("")) {
                String name = AName.getText().toString();
                double price = Double.parseDouble(Aprice.getText().toString());
                String date = Adate.getText().toString();
                long duration = Long.parseLong(Aduration.getText().toString());
                if (activityID == null) {
                    viewModel.add(new Activity(tripID, name, price, date, aLong, duration));
                    Intent intent = new Intent(ActivityAddition_Activity.this, Trip_Plan_Activity.class);
                    intent.putExtra("Activity", name);
                    setResult(300, intent);
                }
                else {
                    currentActivity.setActivityDate(date);
                    currentActivity.setActivityDuration(duration);
                    currentActivity.setActivityName(name);
                    currentActivity.setActivityTime(aLong);
                    currentActivity.setActivityPrice(price);
                    viewModel.update(currentActivity);
                }
                finish();
            }
            else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        returnButton.setOnClickListener(v -> finish());
        Adate.setOnClickListener(v -> showDatePickerDialog(Adate));
        Atime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(ActivityAddition_Activity.this);
                Atime.setText(longToTimeString(aLong));
            }
        });
    }

    @Override
    protected void setViewModel() {
        viewModel = new ViewModelProvider(this).get(ActivitiesViewModel.class);
        if(activityID!=null)
        {
            viewModel.getActivityByActivityId(activityID);
            viewModel.getLiveDataEntity().observe(this, new Observer<Activity>() {
                @Override
                public void onChanged(Activity activity) {
                    currentActivity = activity;
                    AName.setText(currentActivity.getActivityName());
                    Adate.setText(currentActivity.getActivityDate());
                    Aduration.setText(currentActivity.getActivityDuration()+"");
                    Aprice.setText(currentActivity.getActivityPrice()+"");
                    Atime.setText(longToTimeString(currentActivity.getActivityTime()));
                }
            });
        }
    }

    private void showDatePickerDialog(EditText targetEditText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = selectedYear + "-" +
                            String.format("%02d", selectedMonth + 1) + "-" +
                            String.format("%02d", selectedDay);
                    targetEditText.setText(formattedDate);
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public static String longToTimeString(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date(timeInMillis);
        return sdf.format(date);
    }
    public static void showTimePickerDialog(Context context) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, selectedHour, selectedMinute) -> {
                    // Create calendar with selected time
                    Calendar selectedTime = Calendar.getInstance();
                    selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    selectedTime.set(Calendar.MINUTE, selectedMinute);
                    selectedTime.set(Calendar.SECOND, 0);
                    selectedTime.set(Calendar.MILLISECOND, 0);

                    // Return time in milliseconds through callback
                    aLong = selectedTime.getTimeInMillis();
                }, hour, minute, true); // true for 24-hour format

        timePickerDialog.show();
    }

    /**
     * Callback interface for time selection
     */

}