package cn.watchdog.epay.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xLikeWATCHDOG
 */
@Data
public class EPayCreateRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long commodityNumber;
    private String userName;
    private String userEmail;
}
