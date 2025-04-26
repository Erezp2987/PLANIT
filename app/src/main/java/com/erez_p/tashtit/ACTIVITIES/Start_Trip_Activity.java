package com.erez_p.tashtit.ACTIVITIES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;

public class Start_Trip_Activity extends BaseActivity {
    private Button suggestions, start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_trip);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        suggestions = findViewById(R.id.btnTripSuggestions);
        start = findViewById(R.id.btnPlanTrip);
    }

    @Override
    protected void setListeners() 
    {
        //AI NIR (FOR TRIP SUGGESTIONS)
        suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                
            }
        });
        
        //NEW TRIP
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                Intent intentFromLastActivity = getIntent();
                String userId = intentFromLastActivity.getStringExtra("userId");
                Intent intent = new Intent(Start_Trip_Activity.this,Trip_Plan_Activity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setViewModel() {

    }
}