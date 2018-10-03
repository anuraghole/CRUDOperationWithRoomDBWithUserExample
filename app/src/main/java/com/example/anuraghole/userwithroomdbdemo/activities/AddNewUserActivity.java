package com.example.anuraghole.userwithroomdbdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anuraghole.userwithroomdbdemo.R;
import com.example.anuraghole.userwithroomdbdemo.models.User;

public class AddNewUserActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.anuraghole.userwithroomdbdemo.insert";
    public static final String EXTRA_UPDATE = "com.example.anuraghole.userwithroomdbdemo.update";

    private EditText name, email, mobile;
    TextInputLayout tilName, tilEmail, tilMobile;
    private Button save,update;
    User updateUser;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        initViews();

         updateUser=getIntent().getParcelableExtra(EXTRA_UPDATE);
        if (updateUser != null) {
            position=getIntent().getIntExtra("pos",0);
            name.setText(updateUser.getName());
            email.setText(updateUser.getEmail());
            mobile.setText(updateUser.getMobile());
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent=new Intent();
                if (validateUser()){
                    updateUser.setName(name.getText().toString());
                    updateUser.setEmail(email.getText().toString());
                    updateUser.setMobile(mobile.getText().toString());
                    updateIntent.putExtra(EXTRA_UPDATE,updateUser);
                    setResult(RESULT_OK,updateIntent);
                }else{
                    setResult(RESULT_CANCELED, updateIntent);
                }
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (validateUser()){
                    User user = new User();
                    user.setName(name.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setMobile(mobile.getText().toString());
                    replyIntent.putExtra(EXTRA_REPLY, user);
                    setResult(RESULT_OK, replyIntent);
                }
                else {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                finish();
            }
        });

    }

    private boolean validateUser() {
        if (TextUtils.isEmpty(name.getText())) {
            tilName.setError("Enter Name");
            return false;
        }
        if (TextUtils.isEmpty(email.getText())) {
            tilEmail.setError("Enter Email");
            return false;
        }
        if (TextUtils.isEmpty(mobile.getText())) {
            tilMobile.setError("Enter Mobile");
            return false;
        }
        return true;
    }

    private void initViews() {
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mobile);

        tilName = findViewById(R.id.tilName);
        tilMobile = findViewById(R.id.tilMobile);
        tilEmail = findViewById(R.id.tilEmail);

        save = findViewById(R.id.button_save);
        update = findViewById(R.id.button_update);
    }
}
