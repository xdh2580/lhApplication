package nchu.wlw.nrx.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nchu.wlw.nrx.myapplication.R;
import nchu.wlw.nrx.myapplication.bean.FoodBean;


public class MenuAdapter extends BaseAdapter {
    private Context context;
    private List<FoodBean> foodBeanList =  new ArrayList<>();

    public MenuAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<FoodBean> foodBeanList) {
        this.foodBeanList.clear();
        this.foodBeanList.addAll(foodBeanList);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return foodBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int[] num = {0};
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tvFoodName = convertView.findViewById(R.id.tv_food_name);
            viewHolder.tvTaste = convertView.findViewById(R.id.tv_taste);
            viewHolder.tvSaleNumMenu = convertView.findViewById(R.id.tv_sale_num_menu);
            viewHolder.tvFoodPriceMenu = convertView.findViewById(R.id.tv_food_price_menu);
            viewHolder.ivFoodPic = convertView.findViewById(R.id.iv_food_pic);
            viewHolder.ibAdd = convertView.findViewById(R.id.ib_add);
            viewHolder.ibJian = convertView.findViewById(R.id.ib_jian);
            viewHolder.tvNum = convertView.findViewById(R.id.tv_num);
            if(Objects.equals(viewHolder.tvNum.getText(),"0")){
                viewHolder.ibJian.setVisibility(View.GONE);
                viewHolder.tvNum.setVisibility(View.GONE);
            }
           // viewHolder.btnAddCar = convertView.findViewById(R.id.btn_add_car);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FoodBean foodBean = foodBeanList.get(position);
        if(foodBean != null){
            viewHolder.tvFoodName.setText(foodBean.getFoodName());
            Glide.with(context).load(foodBean.getFoodPic()).error(R.mipmap.ic_launcher).into(viewHolder.ivFoodPic);
            viewHolder.tvFoodPriceMenu.setText("￥"+foodBean.getPrice());
            viewHolder.tvSaleNumMenu.setText(foodBean.getSaleNum()+"");
            viewHolder.tvTaste.setText(foodBean.getContent()+"");
        }
        viewHolder.ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num[0]++;
                viewHolder.tvNum.setText(""+ num[0] +"");
                viewHolder.ibJian.setVisibility(View.VISIBLE);
                viewHolder.tvNum.setVisibility(View.VISIBLE);

            }
        });
        viewHolder.ibJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num[0]--;
                viewHolder.tvNum.setText(""+ num[0]);
                if(num[0] == 0){
                    viewHolder.ibJian.setVisibility(View.GONE);
                    viewHolder.tvNum.setVisibility(View.GONE);
                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView tvFoodName,tvTaste,tvSaleNumMenu,tvFoodPriceMenu,tvNum;
        public ImageButton ibAdd,ibJian;
        public Button btnAddCar;
        public ImageView ivFoodPic;
    }
}
