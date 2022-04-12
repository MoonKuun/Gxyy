package com.bug_gai_dui.gxyy;

import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.account_box);
        password = findViewById(R.id.password_box);
    }
    public void login(View v){
        //这个就是 接受数据库信息，进行判断  json文件读取 自己整
        //登陆账号的密码
        String Usename="admin";
        String Upassword="12345";
        //创建两个String类，储存从输入文本框获取到的内容
        String user = name.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if(user.equals(Usename)& pwd.equals(Upassword)){
            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Wrong",Toast.LENGTH_SHORT).show();
        }
    }
    public void reg(View v){
        //这个就是 接受数据库信息，进行判断  json文件读取 自己整
        //登陆账号的密码
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

}