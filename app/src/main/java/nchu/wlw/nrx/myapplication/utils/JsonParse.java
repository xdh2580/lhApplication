package nchu.wlw.nrx.myapplication.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import nchu.wlw.nrx.myapplication.bean.ShopBean;
import nchu.wlw.nrx.myapplication.bean.UserBean;


/**
 * 单例
 */
public class JsonParse {
    private static JsonParse jsonParse;
    //构造方法私有化
    private JsonParse(){

    }
    public synchronized static JsonParse getInstance(){
        if(jsonParse == null){
            jsonParse = new JsonParse();
        }
        return  jsonParse;
    }
    public List<ShopBean> getShopList(String json){
        Type listType = new TypeToken<List<ShopBean>>(){}.getType();
        //json 变成 List<ShopBean>>
        Gson gson = new Gson();
        return  gson.fromJson(json,listType);

    }

    /**
     * 得到User对象
     * @param json
     * @return
     */
    public UserBean getUser(String json){
        Type type = new TypeToken<UserBean>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json,type);
    }
    /**
     * 把一个对象变成一个Json数据
     */
    public String getJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
