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

    @NotNull String getDomainName();


    @NotNull String getSign(Map<String, String> param);


    @NotNull String getPayUrl(String no, String name, String money);
}
