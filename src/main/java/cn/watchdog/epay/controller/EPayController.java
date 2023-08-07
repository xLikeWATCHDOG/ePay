package cn.watchdog.epay.controller;

import cn.watchdog.epay.common.BaseResponse;
import cn.watchdog.epay.common.ResultUtils;
import cn.watchdog.epay.model.dto.EPayCreateRequest;
import cn.watchdog.epay.service.EPayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author xLikeWATCHDOG
 */
@RestController
@RequestMapping("/epay")
@Slf4j
public class EPayController {
    @Resource
    private EPayService ePayService;

    @GetMapping("/test")
    public RedirectView sendInfoToWeiXin() {
        return new RedirectView(ePayService.getPayUrl("1237272698296", "test", "1.00"));
    }

    @GetMapping("/create")
    public BaseResponse<String> create(EPayCreateRequest ePayCreateRequest, HttpServletRequest request) {
        return ResultUtils.success(request.getRequestURL().toString());
    }
}

