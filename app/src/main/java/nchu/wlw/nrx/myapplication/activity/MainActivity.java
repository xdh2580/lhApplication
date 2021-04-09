package nchu.wlw.nrx.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import nchu.wlw.nrx.myapplication.MainActivity4;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;


import nchu.wlw.nrx.myapplication.R;
import nchu.wlw.nrx.myapplication.bean.UserBean;
import nchu.wlw.nrx.myapplication.utils.Constant;
import nchu.wlw.nrx.myapplication.utils.JsonParse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etUserId,etUserPassword;
    private Button btnLogin,btnRegister,btnForget;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    //System.out.println(msg.obj.toString());
                   /* UserBean userBean = (UserBean) msg.obj;
                    if(userBean==null){
                        Toast.makeText(MainActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent1= new Intent(MainActivity.this,ShopActivity.class);
                        startActivity(intent1);
                    }
                    */

                    /*if(Objects.equals(returnOkErrorData,"ok")){

                    }*/
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initOnClick();
    }

    private void initOnClick() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnForget.setOnClickListener(this);
    }

    private void initView() {
        etUserId = findViewById(R.id.et_user_id);
        etUserPassword = findViewById(R.id.et_user_password);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnForget = findViewById(R.id.btn_forget);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_login:
                //登录操作 从数据库查找数据
                String email = etUserId.getText().toString();
                final String password = etUserPassword.getText().toString();
                boolean bEmail = Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",email);
                // System.out.println(password+"pass");
                if(Objects.equals(password,"")){
                    Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    if(bEmail==true){
                        String url = Constant.WEB_USER+Constant.LOGIN+email+"/"+password;
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(url).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                //失败后得操作
                            }
                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                String json = response.body().string();
                                UserBean user = JsonParse.getInstance().getUser(json);
                                if(user==null||!(Objects.equals(user.getPassword(), password))){
                                    Looper.prepare();
                                    Toast.makeText(MainActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }else {
                                    if (Objects.equals(user.getPassword(), password)) {
                                        Intent intent1= new Intent(MainActivity.this, MainActivity4.class);
                                        //传给 shopActivity
                                        intent1.putExtra("user",user);
                                        startActivity(intent1);
                                    }

                                }

                               /* Message message = Message.obtain();
                                message.what = 1;
                                message.obj = user;
                                handler.sendMessage(message);*/
                            }
                        });
                    }else {
                        Toast.makeText(MainActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.btn_register:

                intent= new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_forget:
                intent= new Intent(this,ForgetActivity.class);
                startActivity(intent);
                break;
        }
    }
}