package cn.watchdog.epay.service.impl;

import cn.watchdog.epay.common.ErrorCode;
import cn.watchdog.epay.exception.BusinessException;
import cn.watchdog.epay.mapper.OrdersMapper;
import cn.watchdog.epay.model.dto.EPayCreateRequest;
import cn.watchdog.epay.model.dto.EPayResultRequest;
import cn.watchdog.epay.model.entity.Orders;
import cn.watchdog.epay.service.EPayService;
import cn.watchdog.epay.service.OrdersService;
import cn.watchdog.epay.utils.PaymentUtils;
import cn.watchdog.epay.utils.UrlUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author xLikeWATCHDOG
 */
@Service
@Slf4j
public class EPayServiceImpl implements EPayService {
	@Value("${epay.url}")
	private String url;
	@Value("${epay.pid}")
	private String pid;
	@Value("${epay.key}")
	private String key;
	@Value("${domain.name}")
	private String domainName;
	@Resource
	private OrdersService ordersService;
	@Resource
	private OrdersMapper ordersMapper;

	@NotNull
	@Override
	public String getDomainName() {
		return domainName;
	}

	@Override
	@NotNull
	public String getUrl() {
		return this.url;
	}

	@Override
	@NotNull
	public String getPid() {
		return this.pid;
	}

	@Override
	@NotNull
	public String getKey() {
		return this.key;
	}

	@Override
	@NotNull
	public String getSign(Map<String, String> param) {
		Map<String, String> sortedParam = new TreeMap<>(param);

		StringBuilder signatureBuilder = new StringBuilder();

		for (Map.Entry<String, String> entry : sortedParam.entrySet()) {
			String k = entry.getKey();
			String v = entry.getValue();

			if (v == null) {
				continue;
			}

			if (!k.equalsIgnoreCase("sign") && !k.equalsIgnoreCase("sign_type") && !v.isEmpty()) {
				signatureBuilder.append(k).append('=').append(v).append('&');
			}
		}

		String signstr = signatureBuilder.toString();
		signstr = signstr.substring(0, signstr.length() - 1);
		System.out.println(signstr);
		signstr += getKey();
		System.out.println(signstr);

		return DigestUtils.md5DigestAsHex(signstr.getBytes());
	}

	@Override
	@NotNull
	public PaymentUtils.PaymentInformation getPaymentInformation(String no, String name, String money) {
		// Prepare the request parameters
		Map<String, String> requestParams = new HashMap<>();
		requestParams.put("pid", getPid());
		requestParams.put("out_trade_no", no);
		requestParams.put("notify_url", getDomainName() + "epay/notify");
		requestParams.put("return_url", getDomainName() + "epay/return");
		requestParams.put("name", name);
		requestParams.put("money", money);
		requestParams.put("sign_type", "MD5");
		String sign = getSign(requestParams);
		requestParams.put("sign", sign);
		String baseUrl = getUrl() + "submit.php";
		PaymentUtils.PaymentInformation paymentInformation = new PaymentUtils.PaymentInformation();
		paymentInformation.setUrl(UrlUtils.buildUrl(baseUrl, requestParams));
		paymentInformation.setParams(requestParams);
		return paymentInformation;
	}

	@Override
	@NotNull
	public String createOrder(EPayCreateRequest ePayCreateRequest) {
		String no = PaymentUtils.generateOrderNumber();
		String name = ePayCreateRequest.getName();
		String money = ePayCreateRequest.getMoney();
		PaymentUtils.PaymentInformation paymentInformation = getPaymentInformation(no, name, money);
		Orders orders = new Orders();
		orders.setOrderNumber(no);
		orders.setName(name);
		orders.setMoney(money);
		ordersService.save(orders);
		return paymentInformation.getUrl();
	}

	@Override
	public String notifyOrder(EPayResultRequest ePayResultRequest) {
		String out_trade_no = ePayResultRequest.getOut_trade_no();
		String type = ePayResultRequest.getType();
		String sign = ePayResultRequest.getSign();
		QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("orderNumber", out_trade_no);
		Orders orders = ordersMapper.selectOne(queryWrapper);
		// 订单不存在
		if (orders == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "订单不存在");
		}
		// 记录已回调
		orders.setCallBack(true);
		Map<String, String> requestParams = getNotifySign(ePayResultRequest, out_trade_no, type);
		String iSign = getSign(requestParams);
		// 签名数据不一致
		if (!sign.equals(iSign)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "签名数据不一致");
		}
		orders.setPaymentType(type);
		orders.setPaymentStatus(true);
		orders.setPaymentTime(new Date());
		ordersService.updateById(orders);
		// TODO 在这里写回调完成后的执行的内容。
		return "success";
	}

	@NotNull
	private Map<String, String> getNotifySign(EPayResultRequest ePayResultRequest, String out_trade_no, String type) {
		Map<String, String> requestParams = new HashMap<>();
		requestParams.put("pid", getPid());
		requestParams.put("trade_no", ePayResultRequest.getTrade_no());
		requestParams.put("out_trade_no", out_trade_no);
		requestParams.put("type", type);
		requestParams.put("name", ePayResultRequest.getName());
		requestParams.put("money", ePayResultRequest.getMoney());
		requestParams.put("trade_status", "TRADE_SUCCESS");
		requestParams.put("param", ePayResultRequest.getParam());
		requestParams.put("sign_type", "MD5");
		return requestParams;
	}
}
