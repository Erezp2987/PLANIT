package com.erez_p.tashtit.ACTIVITIES;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.erez_p.helper.LoginPreference;
import com.erez_p.viewmodel.UsersViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.erez_p.helper.inputValidators.EmailRule;
import com.erez_p.helper.inputValidators.NameRule;
import com.erez_p.helper.inputValidators.PasswordRule;
import com.erez_p.helper.inputValidators.RuleOperation;
import com.erez_p.helper.inputValidators.Validator;
import com.erez_p.model.User;
import com.erez_p.tashtit.ACTIVITIES.BASE.BaseActivity;
import com.erez_p.tashtit.R;

public class Register_Activity extends BaseActivity {
    private TextInputEditText name, email, password;
    private Button register, login;
    private UsersViewModel viewModel;
    private String userId;
    private User userEdit;
    private LoginPreference loginPreference;
    private TextView loginPromptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_xml);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setViewModel();
        initializeViews();
        setPasswordValidaitor();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        name = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailRegEditText);
        password = findViewById(R.id.passwordRegEditText);
        loginPromptTextView = findViewById(R.id.registerTitleTextView);
        register = findViewById(R.id.registerButton);
        login = findViewById(R.id.loginPromptTextView);
        userId = getIntent().getStringExtra("userId");
        if(userId != null) {
            viewModel.getUserById(userId);
            register.setText("Update");
            loginPromptTextView.setText("Update your details");
        }
    }

    @Override
    protected void setListeners() {

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText()!=null&&email.getText()!=null&&name.getText()!=null)
                {
                    if(Validator.validate())
                    {
                        if(userId==null) {
                            User NewUser = new User(name.getText().toString(), email.getText().toString(), password.getText().toString());
                            viewModel.add(NewUser);
                        }
                        else{
                            userEdit.setUserEmail(email.getText().toString());
                            userEdit.setUserName(name.getText().toString());
                            userEdit.setUserPassword(password.getText().toString());
                            viewModel.save(userEdit);
                            loginPreference = new LoginPreference(Register_Activity.this);
                            if(loginPreference!= null) {
                                loginPreference.saveLoginCredentials(email.getText().toString(), password.getText().toString());
                            }
                        }
                        finish();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void setViewModel() {
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        viewModel.getLiveDataEntity().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                 userEdit= user;
                if(userEdit != null) {
                    name.setText(userEdit.getUserName());
                    email.setText(userEdit.getUserEmail());
                    password.setText(userEdit.getUserPassword());
                }
            }
        });
    }

    protected void setPasswordValidaitor(){
        Validator.add(new PasswordRule(password, RuleOperation.REQUIRED,"password format not validated"));
        Validator.add(new EmailRule(email,RuleOperation.REQUIRED,"email format not validated"));
        Validator.add(new NameRule(name,RuleOperation.REQUIRED,"name format not validated"));
    }
}