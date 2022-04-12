package com.bug_gai_dui.gxyy;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {
    EditText regName;
    EditText regPassword;
    EditText regConfrimPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regName = findViewById(R.id.account_box);
        regPassword = findViewById(R.id.password_box);
        regConfrimPassword = findViewById(R.id.password_check_box);
    }

    public void reg(View v)
    {
        String user = regName.getText().toString().trim();
        String pwd = regPassword.getText().toString().trim();
        String cfmPwd = regConfrimPassword.getText().toString().trim();
        boolean userNeed=false;
        //密码符不符合要求
        boolean isRight=false;

        int numAppear=0;
        int upChar=0;
        int lowChar=0;
        int speChar=0;
        //用户名存不存在
        if(user.length()==0)
        {
            userNeed=false;
            Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
        }else{
            userNeed=true;
        }
        if(pwd.equals(cfmPwd)& userNeed){
            for(int i=0;i<pwd.length();i++){
                if(pwd.charAt(i)>='0'& pwd.charAt(i)<='9'){
                    numAppear=1;
                } else if(pwd.charAt(i)>='a'&pwd.charAt(i)<='z'){
                    lowChar=1;
                } else if(pwd.charAt(i)>='A'& pwd.charAt(i)<='Z'){
                    upChar=1;
                } else
                    speChar=1;
            }
            isRight=true;

        }else{
            Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();
        }

        if(isRight) {
            if((numAppear+lowChar+upChar+speChar)>=3) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
                //此处发送报文
            }else{
                Toast.makeText(this,"密码不符合要求",Toast.LENGTH_SHORT).show();
            }
        }

    }
}