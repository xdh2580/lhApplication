package nchu.wlw.nrx.myapplication.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hspcadmin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBean implements Serializable {
     //private Integer id;


    private String name;
    private String email;
    private String phone;
    private String password;
    private String sex;
    private Date birthday;
    private Integer flag;
    private Date createTime;
    private Date updateTime;
    private Integer version;
    private Integer deleted;
}
