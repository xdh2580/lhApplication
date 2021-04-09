package nchu.wlw.nrx.myapplication.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import nchu.wlw.nrx.myapplication.R;
import nchu.wlw.nrx.myapplication.adapter.MenuAdapter;
import nchu.wlw.nrx.myapplication.bean.ShopBean;

import java.util.Objects;


public class ShopDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvShopNameDetail,tvTimeDetail,tvNotice,tvTitle;
    private ImageView ivShopPicDetail,ivBack;
    private ListView lvListFood;
    private ShopBean shopBean;
    private MenuAdapter menuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        shopBean = (ShopBean) getIntent().getSerializableExtra("shop");
        if(shopBean == null){
            return;
        }
        initView();
        initAdapter();
        setData();
    }

    private void initAdapter() {
        menuAdapter = new MenuAdapter(this);
        lvListFood.setAdapter(menuAdapter);
    }

    private void initView(){
        tvShopNameDetail = findViewById(R.id.tv_shop_name_detail);
        tvTimeDetail = findViewById(R.id.tv_time_detail);
        tvNotice = findViewById(R.id.tv_notice);
        tvTitle = findViewById(R.id.tv_title);
        ivShopPicDetail = findViewById(R.id.iv_shop_pic_detail);
        ivBack = findViewById(R.id.iv_back);
        lvListFood = findViewById(R.id.lv_list_food);
       /* tvAdd = findViewById(R.id.tv_add);
        tvJian = findViewById(R.id.tv_jian);
        tvNum = findViewById(R.id.tv_num);
        if(Objects.equals(tvNum.getText(),"0")){
            tvJian.setVisibility(View.GONE);
            tvNum.setVisibility(View.GONE);
        }
        tvAdd.setOnClickListener(this);
        tvJian.setOnClickListener(this);*/
        tvTitle.setText("店铺详情");
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
    }
    private void setData() {
        tvShopNameDetail.setText(shopBean.getShopName());
        tvTimeDetail.setText(shopBean.getTime());
        tvNotice.setText(shopBean.getShopNotice());
        Glide.with(this).load(shopBean.getShopPic()).error(R.mipmap.ic_launcher).into(ivShopPicDetail);

        menuAdapter.setData(shopBean.getFoodList());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                //返回上一级菜单
                finish();
                break;

        }
    }
}