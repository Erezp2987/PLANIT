package com.erez_p.tashtit.ACTIVITIES;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.erez_p.model.Activity;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;
import com.erez_p.viewmodel.ActivitiesViewModel;

import java.util.Calendar;

public class ActivityAddition_Activity extends BaseActivity {
    private String tripID;
    private ActivitiesViewModel viewModel;
    private EditText AName,Aprice,Adate,Atime,Aduration;
    private Button returnButton, confirmButton;

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
        AName = findViewById(R.id.editTextActivityName);
        Aprice = findViewById(R.id.editTextActivityPrice);
        Adate = findViewById(R.id.editTextActivityDate);
        Adate.setFocusable(false);
        Atime = findViewById(R.id.editTextActivityTime);
        Aduration = findViewById(R.id.editTextActivityDuration);
        confirmButton = findViewById(R.id.buttonConfirm);
        returnButton = findViewById(R.id.buttonReturn);
    }

    @Override
    protected void setListeners() {
        confirmButton.setOnClickListener(v -> {
            if(AName.getText().toString()!=""&&
                    Aprice.getText().toString()!=""&&
                    Adate.getText().toString()!=""&&
                    Atime.getText().toString()!=""&&
                    Aduration.getText().toString()!="") {
                String name = AName.getText().toString();
                double price = Double.parseDouble(Aprice.getText().toString());
                String date = Adate.getText().toString();
                long time = Long.parseLong(Atime.getText().toString());
                long duration = Long.parseLong(Aduration.getText().toString());
                viewModel.add(new Activity(tripID, name, price, date, time, duration));
                Intent intent = new Intent(ActivityAddition_Activity.this, Trip_Plan_Activity.class);
                intent.putExtra("Activity", name);
                setResult(300,intent);
                finish();
            }
            else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        returnButton.setOnClickListener(v -> finish());
        Adate.setOnClickListener(v -> showDatePickerDialog(Adate));
    }

    @Override
    protected void setViewModel() {
        viewModel = new ViewModelProvider(this).get(ActivitiesViewModel.class);
        viewModel.getAll();
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
}