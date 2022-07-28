package org.ccs.cdnsign.utils;

public class CommUtils {
    public static String getBaseUrl(String url) {
        int idx = url.indexOf("://");
        if (idx > 0) {
            int idxEnd = idx + 3;
            String tmp = url.substring(idxEnd);
            idx = tmp.indexOf('/');
            if (idx > 0) {
                tmp = tmp.substring(0, idx);
                idxEnd += tmp.length();
            }
            String baseUrl = url.substring(0, idxEnd);
            return baseUrl;
        }
        return url;
    }

    public static String trimUrlParam(String uri) {
        if (uri == null) {
            return null;
        }
        if (uri.indexOf('?') >= 0) {
            return uri.substring(0, uri.indexOf('?'));
        }
        return uri;
    }
}
