package nchu.wlw.nrx.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

import nchu.wlw.nrx.myapplication.R;
import nchu.wlw.nrx.myapplication.utils.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_user_email_f,et_user_password_f1,et_user_password_f2,et_verification_code;
    public Button btn_verification_code,btn_ok_f;
    private OkHttpClient client = new OkHttpClient();
    private Long nowTime=0L;
    private String code;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nowTime=System.currentTimeMillis()/1000;
                            TimeCount timeCount = new TimeCount(1000*60,1000);
                            timeCount.start();
                        }
                    });
                    break;
                case 2:
                    String returnOkError = (String)msg.obj;
                    if(Objects.equals(returnOkError,"ok")){
                        Toast.makeText(ForgetActivity.this,"修改成功!!!",Toast.LENGTH_SHORT).show();
                        System.out.println("lehua");
                        Intent intent = new Intent(ForgetActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();

    }

    private void initView() {
        et_user_email_f = findViewById(R.id.et_user_email_f);
        et_user_password_f1 = findViewById(R.id.et_user_password_f1);
        et_user_password_f2 = findViewById(R.id.et_user_password_f2);
        et_verification_code = findViewById(R.id.et_verification_code);
        btn_verification_code = findViewById(R.id.btn_verification_code);
        btn_ok_f = findViewById(R.id.btn_ok_f);

        btn_verification_code.setOnClickListener(this);
        btn_ok_f.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verification_code:
                //  TimeCount timeCount = new TimeCount(1000*60,1000);
                //  timeCount.start();
                String email1 = et_user_email_f.getText().toString();
                final String verification_code = Constant.WEB_USER+Constant.CODE+email1;
                String url3 = Constant.WEB_USER+Constant.QUERY_EMAIL_EXISTS+email1;
                Request request2 = new Request.Builder().url(url3).build();
                client.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String data = response.body().string();
                        if(Objects.equals(data,"noExists")){
                            Looper.prepare();
                            Toast.makeText(ForgetActivity.this,"账号错误!!!",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else if(Objects.equals(data,"exists")){
                            //倒计时
                           /* Looper.prepare();
                            final TimeCount timeCount = new TimeCount(1000*60,1000);*/
                            // Looper.loop();
                            // System.out.println(000000);
                            Request request1 = new Request.Builder().url(verification_code).build();
                            client.newCall(request1).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    code= response.body().string();
                                    Message message = new Message();
                                    message.what=1;
                                    message.obj=code;
                                    handler.sendMessage(message);
                                    System.out.println(code+"code");
                                    // timeCount.start();
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.btn_ok_f:
                final String email = et_user_email_f.getText().toString();
                final String password1 = et_user_password_f1.getText().toString();
                final String password2 = et_user_password_f2.getText().toString();
                final String verificationCode = et_verification_code.getText().toString();

                boolean bEmail = Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",email);
                if(bEmail){
                    String url = Constant.WEB_USER+Constant.QUERY_EMAIL_EXISTS+email;
                    // String data = OkHttpUtils.run(url);
                    //OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String data = response.body().string();
                            if(Objects.equals(data,"noExists")){
                                Looper.prepare();
                                Toast.makeText(ForgetActivity.this,"账号错误!!!",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }else if(Objects.equals(data,"exists")){
                                if(Objects.equals(password1,password2) && !Objects.equals(password1,"") && !Objects.equals(password2,"")){
                                    Long time60 = System.currentTimeMillis()/1000;
                                    if(time60-nowTime<60){
                                        System.out.println(verificationCode+"verificationCode"+code);
                                        if (Objects.equals(verificationCode, code)) {

                                            //修改密码
                                            String url = Constant.WEB_USER+Constant.UPDATE_PASSWORD+email+"/"+password1;
                                            // String data = OkHttpUtils.run(url);
                                            //OkHttpClient client = new OkHttpClient();
                                            Request request = new Request.Builder().url(url).build();
                                            System.out.println(url);
                                            client.newCall(request).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                                }

                                                @Override
                                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                    String returnOkError = response.body().string();
                                                    System.out.println(returnOkError+"okerror");
                                                    Message message = new Message();
                                                    message.what=2;
                                                    message.obj=returnOkError;
                                                    handler.sendMessage(message);
                                                }
                                            });
                                        }else{
                                            Looper.prepare();
                                            Toast.makeText(ForgetActivity.this, "验证码不匹配!!!",Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                    }else{
                                        Looper.prepare();
                                        Toast.makeText(ForgetActivity.this, "验证码过期!!!",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                }else{
                                    Looper.prepare();
                                    Toast.makeText(ForgetActivity.this, "密码不匹配!!!",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        }
                    });

                   /* if (Objects.equals(password1,password2)&&password1!=null&&password2!=null){
                        //插入数据库
                        Toast.makeText(this,"修改成功!!!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"密码不匹配!!!",Toast.LENGTH_SHORT).show();
                    }*/
                }else{
                    Toast.makeText(this,"邮箱格式错误!!!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_verification_code.setBackgroundColor(Color.parseColor("#B6B6D8"));
            btn_verification_code.setClickable(false);
            btn_verification_code.setText("("+millisUntilFinished / 1000 +") ");
        }

        @Override
        public void onFinish() {
            btn_verification_code.setText("重新获取");
            btn_verification_code.setClickable(true);
            btn_verification_code.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }
}

