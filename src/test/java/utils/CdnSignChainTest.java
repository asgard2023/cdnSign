package utils;

import org.ccs.cdnsign.utils.CdnSignChain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CdnSignChainTest {
    @Test
    public void signUrl() {
        String url = "http://cdn.opendfl.org.cn/test2.jpg";
        String urlSign = CdnSignChain.signUrl(url, 60);
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
        boolean isValid = CdnSignChain.checkSign("/test2.jpg", sign, t);
        System.out.println("sign=" + sign + " t=" + t + " isValid=" + isValid);
        Assertions.assertTrue(isValid, "signUrl:"+url);
    }

    @Test
    public void signUrl2() {
        String url = "http://cdn.opendfl.org.cn/test2.jpg";
        String urlSign = CdnSignChain.signUrl(url, 60);
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
        boolean isValid = CdnSignChain.checkSign("/test2.jpg", sign, t);
        System.out.println("sign=" + sign + " t=" + t + " isValid=" + isValid);
        Assertions.assertTrue(isValid, "signUrl:"+url);
    }
}
