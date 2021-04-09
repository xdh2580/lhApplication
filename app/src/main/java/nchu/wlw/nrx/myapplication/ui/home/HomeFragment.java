package nchu.wlw.nrx.myapplication.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import nchu.wlw.nrx.myapplication.R;
import nchu.wlw.nrx.myapplication.adapter.ShopAdapter;
import nchu.wlw.nrx.myapplication.bean.ShopBean;
import nchu.wlw.nrx.myapplication.utils.Constant;
import nchu.wlw.nrx.myapplication.utils.JsonParse;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RelativeLayout rlTitleBar;
    private TextView tvTitle;
    private ListView lvShopList;
    private ShopAdapter shopAdapter;
    private String json;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


      /*  final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        initView(root);
        initData();
        return root;
    }
    private void initView(View root){
        rlTitleBar = root.findViewById(R.id.rl_title_bar);
        rlTitleBar.setBackgroundColor(getResources().getColor(R.color.blue_color));
        tvTitle = root.findViewById(R.id.tv_title);
        tvTitle.setText("店铺");
        lvShopList = root.findViewById(R.id.lv_shop_list);

        shopAdapter = new ShopAdapter(getContext());

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
                json = response.body().string();
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