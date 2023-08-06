package cn.watchdog.epay.service;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author xLikeWATCHDOG
 */
public interface EPayService {
    @NotNull String getUrl();

    @NotNull String getPid();

    @NotNull String getKey();


    @NotNull String getSign(Map<String, String> param);

    String getPayUrl(String no, String notifyUrl, String returnUrl, String name, String money);
}
