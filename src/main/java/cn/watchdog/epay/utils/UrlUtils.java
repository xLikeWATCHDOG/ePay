package cn.watchdog.epay.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author xLikeWATCHDOG
 */
public class UrlUtils {
    // Function to build the URL with parameters
    public static String buildUrl(String baseUrl, Map<String, String> params) {
        Map<String, String> sortedParam = new TreeMap<>(params);
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : sortedParam.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            urlBuilder.append(key).append("=").append(value).append("&");
        }
        // Remove the trailing '&' character
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        return urlBuilder.toString();
    }
}
