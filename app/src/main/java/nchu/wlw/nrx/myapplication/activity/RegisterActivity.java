package nchu.wlw.nrx.myapplication.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

import lombok.SneakyThrows;

import nchu.wlw.nrx.myapplication.R;
import nchu.wlw.nrx.myapplication.bean.UserBean;
import nchu.wlw.nrx.myapplication.utils.Constant;
import nchu.wlw.nrx.myapplication.utils.JsonParse;
import nchu.wlw.nrx.myapplication.utils.OkHttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public  class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText et_user_email,et_user_phone,et_user_name,et_user_password1,et_user_password2;
    private Button btn_register1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_user_email = findViewById(R.id.et_user_email);
        et_user_phone = findViewById(R.id.et_user_phone);
        et_user_name = findViewById(R.id.et_user_name);
        et_user_password1 = findViewById(R.id.et_user_password1);
        et_user_password2 = findViewById(R.id.et_user_password2);
        btn_register1 = findViewById(R.id.btn_register1);

        btn_register1.setOnClickListener(this);
    }

    @SneakyThrows
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register1:

                //注册判断 邮箱 和电话号码 格式 两次密码是否一次
                final String email = et_user_email.getText().toString();
                final String phone = et_user_phone.getText().toString();
                final String name = et_user_name.getText().toString();
                final String password1 = et_user_password1.getText().toString();
                final String password2 = et_user_password2.getText().toString();
                // System.out.println(password1 + "  " +password2);
                boolean bEmail = Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",email);

                boolean bPhone = Pattern.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$",phone);
                if(bEmail == true && bPhone==true){
                    //OkHttpClient client = new OkHttpClient();
                    String url = Constant.WEB_USER+ Constant.QUERY_EMAIL_EXISTS+email;
                    // String data = OkHttpUtils.run(url);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            String data = response.body().string();
                            if(Objects.equals(data,"noExists")){
                                if(Objects.equals(name,"")){
                                    Looper.prepare();
                                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }else{
                                    if (Objects.equals(password1,password2) && !Objects.equals(password1,"") && !Objects.equals(password2,"")){
                                        //插入数据库
                                        // System.out.println("pass: "+password1+"name:"+name );
                                        UserBean userBean = new UserBean();
                                        userBean.setEmail(email);
                                        userBean.setPassword(password1);
                                        userBean.setName(name);
                                        userBean.setPhone(phone);
                                        String json = JsonParse.getInstance().getJson(userBean);
                                        String url1 = Constant.WEB_USER+Constant.REGISTER+json;
                                        String data1 = OkHttpUtils.run(url1);
                                        // System.out.println("dara1"+data1);
                                        if(Objects.equals(data1,"ok")){
                                            //  System.out.println("成功了");
                                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            Looper.prepare();
                                            Toast.makeText(RegisterActivity.this,"注册成功!!!",Toast.LENGTH_SHORT).show();
                                            Looper.loop();

                                        }else if(Objects.equals(data1,"error")){
                                            Looper.prepare();
                                            System.out.println("失败了");
                                            Toast.makeText(RegisterActivity.this,"注册失败!!!",Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                    }else{
                                        Looper.prepare();
                                        Toast.makeText(RegisterActivity.this, "密码不匹配!!!",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                }
                            }else if(Objects.equals(data,"exists")){
                                //System.out.println("怎么回事呢");
                                Looper.prepare();
                                Toast.makeText(RegisterActivity.this,"该邮箱已经注册!!!",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }

                        }
                    });
                }else{
                    Toast.makeText(this,"邮箱或手机格式错误!!!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}