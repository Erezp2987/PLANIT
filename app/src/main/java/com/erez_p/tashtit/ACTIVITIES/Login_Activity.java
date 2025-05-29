package com.erez_p.tashtit.ACTIVITIES;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.erez_p.helper.LoginPreference;
import com.erez_p.model.User;
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

public class Login_Activity extends BaseActivity {
    private TextInputEditText email,password;
    private Button logIn, register, childLogin;
    private User userEntered;
    private UsersViewModel viewModel;
    private CheckBox rememberMe;
    private boolean isChecked1;
    private ProgressDialog progressDialog;
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
        setViewModel();
        initializeViews();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        logIn = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerviewChange);
        rememberMe = findViewById(R.id.rememberMeCheckBox);
        isChecked1 = false;
        LoginPreference loginPreference = new LoginPreference(Login_Activity.this);
        if(loginPreference.getIdfs() != null)
        {
            Intent intent = new Intent(Login_Activity.this, Home_Screen.class);
            intent.putExtra("idUser", loginPreference.getIdfs());
            startActivity(intent);
        }
        else
        {
            email.setText("");
            password.setText("");
            rememberMe.setChecked(false);
        }
    }

    @Override
    protected void setListeners() {

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim(); // Get user input
                String passwordInput = password.getText().toString().trim(); // Get user input

                progressDialog = new ProgressDialog(Login_Activity.this);
                progressDialog.setMessage("Logging in...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                viewModel.getUserByEmail(emailInput);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login_Activity.this,Register_Activity.class);
                startActivity(intent);
            }
        });
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //save email and password
                    //String emailInput = email.getText().toString().trim(); // Get user input
                    //String passwordInput = password.getText().toString().trim(); // Get user input
                    //LoginPreference loginPreference = new LoginPreference(Login_Activity.this);
                    //loginPreference.saveLoginCredentials(emailInput, passwordInput);
                    isChecked1 =true;
                }
                else
                {
                    //clear email and password
                    //LoginPreference loginPreference = new LoginPreference(Login_Activity.this);
                    //loginPreference.clearLoginCredentials();
                    isChecked1 = false;
                }
            }
        });

    }

    @Override
    protected void setViewModel() {
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        viewModel.getLiveDataEntity().observe(Login_Activity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if(user!=null)
                        {
                            userEntered=user;
                            if(userEntered.getUserPassword().equals(password.getText().toString().trim()))
                            {
                                if(isChecked1)
                                {
                                    LoginPreference loginPreference = new LoginPreference(Login_Activity.this);
                                    loginPreference.saveLoginCredentials(userEntered.getIdFs());
                                }
                                Intent intent = new Intent(Login_Activity.this, Home_Screen.class);
                                intent.putExtra("idUser", userEntered.getIdFs());
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Login_Activity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}