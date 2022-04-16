package com.bug_gai_dui.gxyy;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 点击“登录”按钮
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        // 点击“注册”按钮跳转到注册界面
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void login(){
        EditText account_box = findViewById(R.id.account_box);
        EditText password_box = findViewById(R.id.password_box);
        String username = account_box.getText().toString().trim();
        String passwd = password_box.getText().toString().trim();
        // 检查是否为空
        if(username.length() == 0)
        {
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwd.length() == 0)
        {
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                    //手机发的json内容
                    JSONObject json = new JSONObject();
                    json.put("username", username);
                    json.put("passwd", passwd);

                    OkHttpClient okHttpClient = new OkHttpClient();
                    //创建一个RequestBody
                    RequestBody requestBody = RequestBody.create(String.valueOf(json), JSON);
                    //创建一个请求对象
                    Request request = new Request.Builder()
                            .url("http://www.baidu.com") //后端接口
                            .post(requestBody)
                            .build();

                    Response response = null;
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        // 暂时假设服务器的回复是一个json，有个result值表示密码正确or错误
                        json = new JSONObject(response.body().string());
                        if(json.getBoolean("result")) {
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}