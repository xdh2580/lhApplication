package nchu.wlw.nrx.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


import nchu.wlw.nrx.myapplication.R;
import nchu.wlw.nrx.myapplication.activity.ShopDetailActivity;
import nchu.wlw.nrx.myapplication.bean.ShopBean;
import nchu.wlw.nrx.myapplication.ui.home.HomeFragment;

public class ShopAdapter extends BaseAdapter {
    private Context context;
    private List<ShopBean> data = new ArrayList<>();

    public ShopAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopBean> data) {
        this.data.clear();

        this.data.addAll(data);//我们不能改变this.data的地址，否则，会导致无法刷新数据
        //数据有变化，要让我们数据刷新。
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tvOfferPrice = convertView.findViewById(R.id.tv_offer_price);
            viewHolder.tvShopName = convertView.findViewById(R.id.tv_shop_name);
            viewHolder.tvSaleNum = convertView.findViewById(R.id.tv_sale_num);
            viewHolder.tvWelfare = convertView.findViewById(R.id.tv_welfare);
            viewHolder.tvTime = convertView.findViewById(R.id.tv_time);
            viewHolder.ivShopPic = convertView.findViewById(R.id.iv_shop_pic);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final ShopBean shopBean = data.get(position);
        //加载网络图片
        Glide.with(context).load(shopBean.getShopPic()).error(R.mipmap.ic_launcher).into(viewHolder.ivShopPic);

        viewHolder.tvShopName.setText(shopBean.getShopName());
        viewHolder.tvTime.setText(shopBean.getTime());
        viewHolder.tvSaleNum.setText("月售"+shopBean.getSaleNum());
        viewHolder.tvOfferPrice.setText("起送￥"+shopBean.getOfferPrice()+" 配送￥"+shopBean.getDistributionCost());
        viewHolder.tvWelfare.setText(shopBean.getWelfare());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context,shopBean.getShopName(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra("shop",shopBean);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView tvShopName,tvSaleNum,tvOfferPrice,tvWelfare,tvTime;
        ImageView ivShopPic;
    }
}
