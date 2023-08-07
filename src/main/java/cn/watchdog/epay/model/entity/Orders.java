package cn.watchdog.epay.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xLikeWATCHDOG
 */

@TableName(value = "orders")
@Data
public class Orders implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNumber;
    private Long commodityNumber;
    private String money;
    private String paymentType;
    private Boolean paymentStatus;
    private String sign;
    private String userName;
    private String userEmail;
    private Boolean callBack;
    private Date createTime;
    private Date paymentTime;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
}
