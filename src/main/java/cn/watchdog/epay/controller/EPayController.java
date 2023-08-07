package cn.watchdog.epay.controller;

import cn.watchdog.epay.common.BaseResponse;
import cn.watchdog.epay.common.ErrorCode;
import cn.watchdog.epay.common.ResultUtils;
import cn.watchdog.epay.exception.BusinessException;
import cn.watchdog.epay.model.dto.EPayCreateRequest;
import cn.watchdog.epay.service.EPayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        Long commodityNumber = ePayCreateRequest.getCommodityNumber();
        String userName = ePayCreateRequest.getUserName();
        String userEmail = ePayCreateRequest.getUserEmail();
        if (StringUtils.isAnyBlank(userName, userEmail) || commodityNumber == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return ResultUtils.success(ePayService.createPayment(ePayCreateRequest));
    }
}

