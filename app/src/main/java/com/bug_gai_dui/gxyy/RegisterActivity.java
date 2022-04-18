package com.bug_gai_dui.gxyy;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.*;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    boolean isUserExist = true;
    boolean isPasswdCheck = false;
    boolean isPasswdConfirm = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText userName = findViewById(R.id.account_box);
        EditText password = findViewById(R.id.password_box);
        EditText confirmPassword = findViewById(R.id.password_check_box);

        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if(!focus) {
                    new Thread() {
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient();

                            FormBody.Builder formBuilder = new FormBody.Builder();
                            formBuilder.add("name", userName.getText().toString().trim());

                            Request request = new Request.Builder().url("http://127.0.0.1:5000/check_name") //检查有无重名
                                    .post(formBuilder.build())
                                    .build();

                            Call call = okHttpClient.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(RegisterActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    final String res = response.body().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (res.equals("Already exists!")) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        isUserExist = true;
                                                        Toast.makeText(RegisterActivity.this, "此用户名已存在", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            } else {
                                                isUserExist = false;
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }.start();
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if(!focus) {
                    String pwd = password.getText().toString().trim();
                    if(pwd.length() < 8 || pwd.length() > 16) {
                        Toast.makeText(RegisterActivity.this, "密码长度应为8~16位", Toast.LENGTH_SHORT).show();
                        isPasswdCheck = false;
                        isPasswdConfirm = false;
                        return;
                    }
                    int hasNum = 0;
                    int hasLow = 0;
                    int hasUpper = 0;
                    int hasSpecial = 0;
                    for(int i = 0; i < pwd.length(); i++) {
                        if(pwd.charAt(i) >= '0' && pwd.charAt(i) <= '9') {
                            hasNum = 1;
                        } else if(pwd.charAt(i) >= 'a' && pwd.charAt(i) <= 'z') {
                            hasLow = 1;
                        } else if(pwd.charAt(i)>='A' && pwd.charAt(i) <= 'Z') {
                            hasUpper = 1;
                        } else
                            hasSpecial = 1;
                    }
                    if(hasNum + hasLow + hasUpper + hasSpecial < 3) {
                        Toast.makeText(RegisterActivity.this, "密码请至少包含大写字母、小写字母、数字、特殊符号中的三种", Toast.LENGTH_SHORT).show();
                        isPasswdCheck = false;
                        isPasswdConfirm = false;
                        return;
                    }
                    isPasswdCheck = true;
                    if(confirmPassword.getText().toString().trim().length() != 0 && password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                        isPasswdConfirm = true;
                    } else {
                        isPasswdConfirm = false;
                    }
                }
            }
        });

        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if(!focus) {
                    if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                        isPasswdConfirm = true;
                    } else {
                        isPasswdConfirm = false;
                        Toast.makeText(RegisterActivity.this, "两次密码不同", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                    isPasswdConfirm = true;
                } else {
                    isPasswdConfirm = false;
                    Toast.makeText(RegisterActivity.this, "两次密码不同", Toast.LENGTH_SHORT).show();
                }
                if(!isUserExist && isPasswdCheck && isPasswdConfirm) {
                    new Thread() {
                        @Override
                        public void run() {
                            OkHttpClient okHttpClient = new OkHttpClient();

                            FormBody.Builder formBuilder = new FormBody.Builder();
                            formBuilder.add("name", userName.getText().toString().trim());
                            formBuilder.add("password", password.getText().toString().trim());

                            Request request = new Request.Builder().url("http://127.0.0.1:5000/create_account") //建立账户
                                    .post(formBuilder.build())
                                    .build();

                            Call call = okHttpClient.newCall(request);
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(RegisterActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, final Response response) throws IOException {
                                    final String res = response.body().string();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (res.equals("Done!")) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        isUserExist = true;
                                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            } else {
                                                isUserExist = false;
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }.start();
                } else {
                    Toast.makeText(RegisterActivity.this, "请检查输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}