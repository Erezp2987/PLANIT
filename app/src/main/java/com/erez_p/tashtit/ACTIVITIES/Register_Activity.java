package com.erez_p.tashtit.ACTIVITIES;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

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

        initializeViews();
        setPasswordValidaitor();
        setViewModel();
        setListeners();
    }

    @Override
    protected void initializeViews() {
        name = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailRegEditText);
        password = findViewById(R.id.passwordRegEditText);
        register = findViewById(R.id.registerButton);
        login = findViewById(R.id.loginPromptTextView);
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
                        User NewUser = new User(name.getText().toString(),email.getText().toString(),password.getText().toString());
                        viewModel.add(NewUser);
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
        viewModel.getAll();
    }

    protected void setPasswordValidaitor(){
        Validator.add(new PasswordRule(password, RuleOperation.REQUIRED,"password format not validated"));
        Validator.add(new EmailRule(email,RuleOperation.REQUIRED,"email format not validated"));
        Validator.add(new NameRule(name,RuleOperation.REQUIRED,"name format not validated"));
    }
}