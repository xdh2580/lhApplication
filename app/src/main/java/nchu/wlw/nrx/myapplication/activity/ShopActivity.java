package nchu.wlw.nrx.myapplication.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import lombok.SneakyThrows;

import nchu.wlw.nrx.myapplication.R;
import nchu.wlw.nrx.myapplication.adapter.ShopAdapter;
import nchu.wlw.nrx.myapplication.bean.ShopBean;
import nchu.wlw.nrx.myapplication.bean.UserBean;
import nchu.wlw.nrx.myapplication.utils.Constant;
import nchu.wlw.nrx.myapplication.utils.JsonParse;
import nchu.wlw.nrx.myapplication.utils.OkHttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopActivity extends AppCompatActivity {
    private RelativeLayout rlTitleBar;
    private TextView tvTitle;
    private ListView lvShopList;
    private ShopAdapter shopAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String json = (String) msg.obj;
                    List<ShopBean> shopList = JsonParse.getInstance().getShopList(json);
                    shopAdapter.setData(shopList);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initView();
        initData();
    }
    private void initView(){
        rlTitleBar = findViewById(R.id.rl_title_bar);
        rlTitleBar.setBackgroundColor(getResources().getColor(R.color.blue_color));
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("店铺");
        lvShopList = findViewById(R.id.lv_shop_list);

        shopAdapter = new ShopAdapter(this);

        lvShopList.setAdapter(shopAdapter);

    }
    private void initData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Constant.WEB_SITE+Constant.REQUEST_SHOP_DATA).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //失败
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Log.d("ggg","json:"+json);
                // final List<ShopBean> shopList = JsonParse.getInstance().getShopList(json);
                Message message = new Message();
                message.what=1;
                message.obj=json;
                handler.sendMessage(message);

               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shopAdapter.setData(shopList);
                    }
                });*/
            }
        });
    }

}