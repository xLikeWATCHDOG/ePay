package cn.watchdog.epay.utils;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @author xLikeWATCHDOG
 */
public class PaymentUtils {
	// 生成订单号的函数
	public static String generateOrderNumber() {
		// 获取当前日期时间
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = dateFormat.format(now);

		// 生成随机数，保证订单号的唯一性
		Random random = new Random();
		int randomNumber = random.nextInt(10000); // 在0~9999之间生成随机数

		// 组合订单号
		return timestamp + String.format("%04d", randomNumber);
	}

	@Data
	public static class PaymentInformation {
		private String url;
		private Map<String, String> params;
	}
}
