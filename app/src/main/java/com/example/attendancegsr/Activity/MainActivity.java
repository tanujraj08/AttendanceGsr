package com.example.attendancegsr.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancegsr.Api.RetrofitClient;
import com.example.attendancegsr.Models.DefaultResponse;
import com.example.attendancegsr.R;
import com.example.attendancegsr.SharedPrefManager.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etemail, etpassword, etcompany;
    private Button reg_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etcompany = findViewById(R.id.et_name);
        etemail = findViewById(R.id.et_reg_email);
        etpassword = findViewById(R.id.et_reg_password);
        reg_but = findViewById(R.id.btn_register);

        reg_but.setOnClickListener(this);
        findViewById(R.id.tv_login).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, Attendance.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.show();

        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        String company = etcompany.getText().toString().trim();

        if (email.isEmpty()) {
            etemail.setError("Email is required");
            etemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError("Enter a valid email");
            etemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etpassword.setError("Password required");
            etpassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etpassword.setError("Password should be atleast 6 character long");
            etpassword.requestFocus();
            return;
        }

        if (company.isEmpty()) {
            etcompany.setError("Name required");
            etcompany.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .signup(email, password, company);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                progressDialog.dismiss();


                if (response.code() == 200) {
                    DefaultResponse dr = response.body();
                    Toast.makeText(MainActivity.this, dr.getSts(), Toast.LENGTH_LONG).show();
                } else{
                    try {
                        String s = response.errorBody().string();
                        JSONObject jsonObject= new JSONObject(s);
                        Toast.makeText(MainActivity.this,jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        }




            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });







    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                userSignUp();
                break;
            case R.id.tv_login:
                startActivity(new Intent(this,Login_Activity.class));
                break;
        }
    }
}

