package cn.watchdog.epay.controller;

import cn.watchdog.epay.common.BaseResponse;
import cn.watchdog.epay.common.ErrorCode;
import cn.watchdog.epay.common.ResultUtils;
import cn.watchdog.epay.exception.BusinessException;
import cn.watchdog.epay.model.dto.EPayCreateRequest;
import cn.watchdog.epay.model.dto.EPayResultRequest;
import cn.watchdog.epay.service.EPayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xLikeWATCHDOG
 */
@RestController
@RequestMapping("/epay")
@Slf4j
public class EPayController {
	@Resource
	private EPayService ePayService;

	@PostMapping("/create")
	public BaseResponse<String> create(EPayCreateRequest ePayCreateRequest, HttpServletRequest request) {
		if (ePayCreateRequest == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String name = ePayCreateRequest.getName();
		String money = ePayCreateRequest.getMoney();
		if (StringUtils.isAnyBlank(name, money)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}

		return ResultUtils.success(ePayService.createOrder(ePayCreateRequest));
	}

	@GetMapping("/notify")
	public String notify(EPayResultRequest ePayResultRequest, HttpServletRequest request) {
		if (ePayResultRequest == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String out_trade_no = ePayResultRequest.getOut_trade_no();
		String trade_status = ePayResultRequest.getTrade_status();
		String sign = ePayResultRequest.getSign();
		String sign_type = ePayResultRequest.getSign_type();
		if (StringUtils.isAnyBlank(out_trade_no, trade_status, sign, sign_type)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		if (!(trade_status.equals("TRADE_SUCCESS") && sign_type.equals("MD5"))) {
			throw new BusinessException(ErrorCode.SYSTEM_ERROR);
		}
		return ePayService.notifyOrder(ePayResultRequest);
	}
}

