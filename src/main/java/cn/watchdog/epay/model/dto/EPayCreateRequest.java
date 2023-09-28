package cn.watchdog.epay.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author xLikeWATCHDOG
 */
@Data
public class EPayCreateRequest implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private String name;
	private String money;
}
