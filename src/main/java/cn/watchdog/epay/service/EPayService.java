package cn.watchdog.epay.service;

import cn.watchdog.epay.model.dto.EPayCreateRequest;
import cn.watchdog.epay.model.dto.EPayResultRequest;
import cn.watchdog.epay.utils.PaymentUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author xLikeWATCHDOG
 */
public interface EPayService {
	@NotNull String getUrl();

	@NotNull String getPid();

	@NotNull String getKey();

	@NotNull String getDomainName();


	@NotNull String getSign(Map<String, String> param);


	@NotNull PaymentUtils.PaymentInformation getPaymentInformation(String no, String name, String money);

	@NotNull String createOrder(EPayCreateRequest ePayCreateRequest);

	String notifyOrder(EPayResultRequest ePayResultRequest);
}
