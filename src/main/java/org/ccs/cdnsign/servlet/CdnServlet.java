package org.ccs.cdnsign.servlet;


import org.apache.commons.lang3.StringUtils;
import org.ccs.cdnsign.utils.CdnSignChain;
import org.ccs.cdnsign.utils.CommUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@WebServlet(urlPatterns = "/cdnauth")
public class CdnServlet extends HttpServlet {
    static Logger LOGGER = LoggerFactory.getLogger(CdnServlet.class);
    private static final long serialVersionUID = 1L;

    public CdnServlet() {
        super();
    }

    /**
     * 信任的来源IP，即ip白名单
     */
    private static Set<String> trustSourceIps = new HashSet<String>() {{
        add("localhost");
    }};
    private static final String SOURCE_CDN_TRUST = "cdnTrust";
    //是否ts不鉴权
    private static final boolean IS_TRUST_TS = true;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");
        String host = request.getHeader("host");
        String path = getHeaderOrParam(request, "path");
        String ip = getHeaderOrParam(request, "ip");

        String sign = getParamFirst(request, "sign", "signq");
        String t = getParamFirst(request, "t", "tq");
        String sourceCdn = request.getHeader("sourceCdn");

        //自动去除后面的参数
        path = CommUtils.trimUrlParam(path);
        boolean isValid = false;
        boolean isEmptySign = StringUtils.isEmpty(sign);
        if (path == null) {
            isValid = false;
        } else if (SOURCE_CDN_TRUST.equals(sourceCdn)) {
            isValid = true;
        } else if (isEmptySign && IS_TRUST_TS && path.endsWith(".ts") || trustSourceIps.contains(ip) || trustSourceIps.contains(host)) {
            isValid = true;
        } else if (isEmptySign || StringUtils.isEmpty(t)) {
            isValid = false;
        } else {
            if (path.startsWith("http")) {
                String baseUrl = CommUtils.getBaseUrl(path);
                path = path.substring(baseUrl.length());
            }
            isValid = CdnSignChain.checkSign(path, sign, t);
        }
        if (isValid) {
            response.getWriter().append("ok");
            return;
        } else {
            LOGGER.warn("----cdnauth--path={} ip={} host={} isValid={} sign={} t={}", path, ip, host, isValid, sign, t);
        }


        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().append("fail");
    }


    private String getHeaderOrParam(HttpServletRequest request, String paramName) {
        String value = request.getHeader(paramName);
        if (value == null) {
            value = request.getParameter(paramName);
        }
        return value;
    }

    private String getParamFirst(HttpServletRequest request, String... paramNames) {
        for (String p : paramNames) {
            String v = request.getParameter(p);
            if (v != null) {
                return v;
            }
        }
        return null;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
