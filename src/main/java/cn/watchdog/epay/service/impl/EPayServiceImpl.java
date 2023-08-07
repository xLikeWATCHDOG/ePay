package cn.watchdog.epay.service.impl;

import cn.watchdog.epay.common.ErrorCode;
import cn.watchdog.epay.exception.BusinessException;
import cn.watchdog.epay.mapper.CommoditiesMapper;
import cn.watchdog.epay.mapper.OrdersMapper;
import cn.watchdog.epay.model.dto.EPayCreateRequest;
import cn.watchdog.epay.model.entity.Commodities;
import cn.watchdog.epay.model.entity.Orders;
import cn.watchdog.epay.service.CommoditiesService;
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
    @Resource
    private CommoditiesService commoditiesService;
    @Resource
    private CommoditiesMapper commoditiesMapper;

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
    public String createPayment(EPayCreateRequest ePayCreateRequest) {
        Long commodityNumber = ePayCreateRequest.getCommodityNumber();
        String userName = ePayCreateRequest.getUserName();
        String userEmail = ePayCreateRequest.getUserEmail();
        QueryWrapper<Commodities> commoditiesQueryWrapper = new QueryWrapper<>();
        commoditiesQueryWrapper.eq("id", commodityNumber);
        Commodities commodities = commoditiesMapper.selectOne(commoditiesQueryWrapper);
        if (commodities == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品不存在");
        }
        String no = PaymentUtils.generateOrderNumber();
        String name = commodities.getName();
        String money = commodities.getMoney();
        PaymentUtils.PaymentInformation paymentInformation = getPaymentInformation(no, name, money);
        Orders orders = new Orders();
        orders.setOrderNumber(no);
        orders.setCommodityNumber(commodityNumber);
        orders.setMoney(money);
        orders.setSign(paymentInformation.getParams().get("sign"));
        orders.setUserName(userName);
        orders.setUserEmail(userEmail);
        ordersService.save(orders);
        return paymentInformation.getUrl();
    }
}
