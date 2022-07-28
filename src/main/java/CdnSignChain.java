import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * cdn回源鉴权签名算法java版
 */
public class CdnSignChain {

    static Logger logger = LoggerFactory.getLogger(CdnSignChain.class);
    /**
     * 签名密钥
     */
    private final static String CDN_SIGN_KEY = "xxxxxxxxxxxx";

    /**
     * 按秒设置有效期
     *
     * @param url
     * @param seconds 秒
     * @return
     */
    public static String signQiNiuChain(String url, int seconds) {
        if (url == null) {
            return null;
        }
        Long curTime = System.currentTimeMillis();
        Long expire = curTime + seconds * 1000;
        String expireTime = Long.toHexString(expire / 1000);

        String baseUrl = CommUtils.getBaseUrl(url);
        String path = url.substring(baseUrl.length());
        try {
            path = URLEncoder.encode(path, "UTF-8");
            //斜线 / 不编码
            path = path.replaceAll(URLEncoder.encode("/", "UTF-8"), "/");
        } catch (UnsupportedEncodingException e) {
            logger.warn("URL编码失败{}", path);
        }
        String signKey = CDN_SIGN_KEY;
        String signPath = signKey + path + expireTime;
        return getSginUrl(url, signPath, expireTime);
    }

    /**
     * 获取签名后的url
     *
     * @param url
     * @param signPath
     * @param expireTime
     * @return
     */
    private static String getSginUrl(String url, String signPath, String expireTime) {
        try {
            signPath = MD5Util.md5Encode(signPath, "utf-8").toLowerCase();
            StringBuilder queryString = new StringBuilder(url);
            if (StringUtils.indexOf(url, "?") >= 0) {
                queryString.append("&sign=");
            } else {
                queryString.append("?sign=");
            }
            queryString.append(signPath).append("&t=").append(expireTime);
            return queryString.toString();
        } catch (Exception e) {
            logger.warn("sianFail--signPath={}", signPath, e);
            return url;
        }
    }


    /**
     * 签名验证
     *
     * @param path
     * @param sign
     * @param t
     * @return
     */
    public static boolean checkSign(String path, String sign, String t) {
        Long expireTime = Long.parseLong(t, 16);
        if (expireTime * 1000 < System.currentTimeMillis()) {
            return false;
        }

        try {
            path = URLEncoder.encode(path, "UTF-8");
            //斜线 / 不编码
            path = path.replaceAll(URLEncoder.encode("/", "UTF-8"), "/");
        } catch (UnsupportedEncodingException e) {
            logger.warn("URL编码失败:{},error:{}", path, e.getMessage());
        }
        String signKey = CDN_SIGN_KEY;
        String signPath = signKey + path + t;
        signPath = MD5Util.md5Encode(signPath, "utf-8").toLowerCase();
        if (signPath.equals(sign)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://cdn.opendfl.org.cn/test2.jpg";
        String urlSign = CdnSignChain.signQiNiuChain(url, 86400 * 365 * 10);
        System.out.println("urlSign=" + urlSign);

        String paramStr = urlSign.substring(urlSign.indexOf("?") + 1);
        String[] params = paramStr.split("&");
        String sign = null;
        String t = null;
        for (String param : params) {
            if (param.startsWith("sign=")) {
                sign = param.substring("sign=".length());
            }
            if (param.startsWith("t=")) {
                t = param.substring("t=".length());
            }
        }
        boolean isValid = checkSign("/test2.jpg", sign, t);
        System.out.println("sign=" + sign + " t=" + t + " isValid=" + isValid);
    }
}
