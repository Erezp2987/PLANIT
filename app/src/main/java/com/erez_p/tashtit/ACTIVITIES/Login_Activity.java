package com.erez_p.tashtit.ACTIVITIES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.erez_p.model.User;
import com.erez_p.model.Users;
import com.erez_p.viewmodel.UsersViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.erez_p.tashtit.R;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;

import java.lang.annotation.Documented;
import java.util.List;

public class Login_Activity extends BaseActivity {
    TextInputEditText email,password;
    Button logIn, register;
    Users user = new Users();
    private UsersViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_xml);

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
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        logIn = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerviewChange);

    }

    @Override
    protected void setListeners() {

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Home_Screen.class);
               startActivity(intent);
                String emailInput = email.getText().toString().trim(); // Get user input
                String passwordInput = password.getText().toString().trim(); // Get user input

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login_Activity.this,Register_Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void setViewModel() {
//        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
//        viewModel.getAll();
//        viewModel.getLiveDataCollection().observe(Login_Activity.this, new Observer<Users>() {
//            @Override
//            public void onChanged(Users usersList) {
//                if(usersList!=null) {
//                    user = usersList;
//                    logIn.setEnabled(true);
//                }
//            }
//        });
    }
}