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

public class AddNewUserActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REPLY = "com.example.anuraghole.userwithroomdbdemo.insert";
    public static final String EXTRA_UPDATE = "com.example.anuraghole.userwithroomdbdemo.update";
    private EditText name, email, mobile;
    TextInputLayout tilName, tilEmail, tilMobile;
    private Button save, update;
    User updateUser;
    int position;
    static int actionFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        initViews();
        actionFlag = getIntent().getIntExtra(MainActivity.ACTION_FLAG, 0);
        if (actionFlag == MainActivity.USER_INSERT_ACTIVITY_REQUEST_CODE) {
            prepareForInsert();
        }
        if (actionFlag == MainActivity.USER_UPDATE_ACTIVITY_REQUEST_CODE) {
            prepareForUpdate();
        }
    }

    private void prepareForInsert() {
        showHideSaveUpdateButton(save);
    }

    private void prepareForUpdate() {
        updateUser = getIntent().getParcelableExtra(EXTRA_UPDATE);
        if (updateUser != null) {
            name.setText(updateUser.getName());
            email.setText(updateUser.getEmail());
            mobile.setText(updateUser.getMobile());
            showHideSaveUpdateButton(update);
        }
    }

    private void actionUpdate() {
        Intent replyUpdateIntent = new Intent();
        if (validateUser()) {
            updateUser.setName(name.getText().toString());
            updateUser.setEmail(email.getText().toString());
            updateUser.setMobile(mobile.getText().toString());
            replyUpdateIntent.putExtra(EXTRA_UPDATE, updateUser);
            setResult(RESULT_OK, replyUpdateIntent);
        } else {
            setResult(RESULT_CANCELED, replyUpdateIntent);
        }
        finish();
    }

    private void actionInsert() {
        Intent replyInsertIntent = new Intent();
        if (validateUser()) {
            User user = new User();
            user.setName(name.getText().toString());
            user.setEmail(email.getText().toString());
            user.setMobile(mobile.getText().toString());
            replyInsertIntent.putExtra(EXTRA_REPLY, user);
            setResult(RESULT_OK, replyInsertIntent);
        } else {
            setResult(RESULT_CANCELED, replyInsertIntent);
        }
        finish();
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
        save.setVisibility(View.GONE);
        update = findViewById(R.id.button_update);
        update.setVisibility(View.GONE);

        save.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    private void showHideSaveUpdateButton(Button button) {
        if (button.getVisibility() == View.VISIBLE) {
            button.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                actionInsert();
                break;
            case R.id.button_update:
                actionUpdate();
                break;

        }
    }
}
