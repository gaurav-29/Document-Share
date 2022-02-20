package com.oceanmtech.documentshare.LoginModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.oceanmtech.documentshare.MainModule.MainActivity;
import com.oceanmtech.documentshare.MainModule.MainActivity2;
import com.oceanmtech.documentshare.R;
import com.oceanmtech.documentshare.Utils.Constants;
import com.oceanmtech.documentshare.Utils.PreferenceHelper;
import com.oceanmtech.documentshare.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    LoginActivity mContext = LoginActivity.this;
    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_login);

        onClickListeners();
    }
    private void onClickListeners() {

        mBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.etMemberId.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(mBinding.getRoot(),"Please enter Member Id", Snackbar.LENGTH_SHORT).show();
                else if (mBinding.etPassword.getText().toString().trim().equalsIgnoreCase("") ||
                        (mBinding.etPassword.getText().toString().trim().length()<6))
                    Toast.makeText(mContext, "The password must be at least 6 characters/numbers.", Toast.LENGTH_SHORT).show();
                else {
                    Login();
                    Intent main = new Intent(mContext, MainActivity2.class);
                    startActivity(main);
                }
            }
        });
    }

    private void Login() {

        PreferenceHelper.putString(Constants.ID, mBinding.etMemberId.getText().toString().trim());
        PreferenceHelper.putString(Constants.PASSWORD, mBinding.etPassword.getText().toString().trim());
        Snackbar.make(mBinding.getRoot(),"Login Successfull", Snackbar.LENGTH_SHORT).show();
    }
}