package com.fantasy.framework.util.web;

import com.fantasy.framework.util.common.ClassUtil;
import com.fantasy.framework.util.common.ObjectUtil;
import com.fantasy.framework.util.common.PathUtil;
import com.fantasy.framework.util.common.StringUtil;
import com.fantasy.framework.util.ognl.OgnlUtil;
import com.fantasy.framework.util.regexp.RegexpUtil;
import com.fantasy.framework.util.web.context.ActionContext;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * web 工具类
 *
 * @author 李茂峰
 * @version 1.0
 * @功能描述 web开发中经常使用的方法
 * @since 2013-9-10 上午9:16:01
 */
public class WebUtil {

    private static final Logger logger = Logger.getLogger(WebUtil.class);

    private static HttpServletRequest getRequest() {
        HttpServletRequest request;
        try {
            request = ServletActionContext.getRequest();
        } catch (NullPointerException e) {
            request = ActionContext.getContext().getHttpRequest();
            if (request == null) {
                throw e;
            }
        }
        return request;
    }

    /**
     * 获取请求URL的后缀名
     *
     * @param request HttpServletRequest
     * @return {String}
     * @功能描述
     */
    public static String getExtension(HttpServletRequest request) {
        return getExtension(request.getRequestURI());
    }

    /**
     * 获取请求URL的后缀名
     *
     * @param requestUri 请求路径
     * @return {String}
     * @功能描述
     */
    public static String getExtension(String requestUri) {
        if (RegexpUtil.isMatch(requestUri, "[^/]{1,}[.]([^./]{1,})$")) {
            return RegexpUtil.parseGroup(requestUri, "[^/]{1,}[.]([^./]{1,})$", 1);
        }
        return "";
    }

    public static String getRequestUrl(HttpServletRequest request) {
        return getRequestUrl(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath());
    }

    public static String getRequestUrl(HttpServletRequest request, String contextPath) {
        return getRequestUrl(request.getScheme(), request.getServerName(), request.getServerPort(), contextPath);
    }

    public static String getRequestUrl(String scheme, String serverName, int serverPort, String contextPath) {
        scheme = scheme.toLowerCase();
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        if ("http".equals(scheme)) {
            if (serverPort != 80) {
                url.append(":").append(serverPort);
            }
        } else if ("https".equals(scheme)) {
            if (serverPort != 443) {
                url.append(":").append(serverPort);
            }
        }
        url.append(contextPath);
        return url.toString();
    }

    /**
     * 作用不大 不推荐使用
     *
     * @param request HttpServletRequest
     * @return {String}
     * @功能描述
     */
    public static String getServerName(HttpServletRequest request) {
        return getServerName(request.getRequestURL().toString());
    }

    /**
     * 作用不大 不推荐使用
     *
     * @param url 路径
     * @return {String}
     */
    public static String getServerName(String url) {
        url = RegexpUtil.replaceFirst(url, "^" + getScheme(url) + "://", "");
        String serverName = RegexpUtil.parseFirst(url, "[^/]+");
        return RegexpUtil.replaceFirst(serverName, ":" + getPort(serverName), "");
    }

    /**
     * 获取协议名称
     *
     * @param url 路径
     * @return {String}
     */
    public static String getScheme(String url) {
        return RegexpUtil.parseFirst(url, "^(https|http)");
    }

    /**
     * 获取请求的端口号
     *
     * @param url 路径
     * @return {String}
     */
    public static int getPort(String url) {
        String scheme = getScheme(url);
        String port = RegexpUtil.parseGroup(url, ":([0-9]+)", 1);
        return Integer.valueOf(StringUtil.isNull(port) ? ("http".equals(scheme) ? "80" : "443") : port);
    }

    /**
     * 获取请求的端口号
     *
     * @param request 路径
     * @return {String}
     */
    public static String getPort(HttpServletRequest request) {
        return String.valueOf(request.getLocalPort());
    }

    @Deprecated
    public static String getResponsePath(String filePath) {
        return filePath.replaceAll("\\\\", "/").replaceFirst(PathUtil.root().replaceAll("\\\\", "/"), "").replaceAll("\\\\", "/");
    }

    @Deprecated
    public static boolean acceptEncoding(HttpServletRequest request) {
        String encoding = request.getHeader("Accept-Encoding");
        return (encoding != null) && (encoding.contains("gzip"));
    }

    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("Referer");
    }

    private static Cookie[] getCookies(HttpServletRequest request) {
        return request.getCookies();
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = getCookies(request);
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name))
                    return cookies[i];
            }
        }
        return null;
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = getCookie(request, name);
        if (!ObjectUtil.isNull(cookie)) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public static String getRealIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static boolean isSelfIp(String ip) {
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress ia = ips.nextElement();
                    if (ia instanceof Inet4Address && (ia.isSiteLocalAddress() || ia.isMCGlobal())) {
                        // 只获取IPV4的局域网和广域网地址，忽略本地回环和本地链路地址
                        // System.out.println("IP:"
                        // + ia.getHostAddress());
                        // System.out.println("--------------------------------------------");
                        if (ia.getHostAddress().equals(ip.trim())) {
                            return true;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取服务器的本地 ip
     *
     * @return {String[]}
     */
    public static String[] getServerIps() {
        String[] serverIps = new String[0];
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress ia = ips.nextElement();
                    if (ia instanceof Inet4Address && (ia.isSiteLocalAddress() || ia.isMCGlobal())) {// 只获取IPV4的局域网和广域网地址，忽略本地回环和本地链路地址
                        serverIps = ObjectUtil.join(serverIps, ia.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            logger.error(e.getMessage(), e);
        }
        return serverIps;
    }

    public static Browser browser(HttpServletRequest request) {
        return Browser.getBrowser(request.getHeader("User-Agent"));
    }

    public static String getBrowserVersion(Browser browser, HttpServletRequest request) {
        return browser.getVersion(request.getHeader("User-Agent").toLowerCase());
    }

    public static String getOsVersion(HttpServletRequest request) {
        String useros = request.getHeader("User-Agent").toLowerCase();
        String osVersion = "unknown";
        if (useros.indexOf("nt 6.1") > 0)
            osVersion = "Windows 7";
        else if (useros.indexOf("nt 6.0") > 0)
            osVersion = "Windows Vista/Server 2008";
        else if (useros.indexOf("nt 5.2") > 0)
            osVersion = "Windows Server 2003";
        else if (useros.indexOf("nt 5.1") > 0)
            osVersion = "Windows XP";
        else if (useros.indexOf("nt 5") > 0)
            osVersion = "Windows 2000";
        else if (useros.indexOf("nt 4") > 0)
            osVersion = "Windows nt4";
        else if (useros.indexOf("me") > 0)
            osVersion = "Windows Me";
        else if (useros.indexOf("98") > 0)
            osVersion = "Windows 98";
        else if (useros.indexOf("95") > 0)
            osVersion = "Windows 95";
        else if (useros.indexOf("ipad") > 0)
            osVersion = "iPad";
        else if (useros.indexOf("macintosh") > 0)
            osVersion = "Mac";
        else if (useros.indexOf("unix") > 0)
            osVersion = "UNIX";
        else if (useros.indexOf("linux") > 0)
            osVersion = "Linux";
        else if (useros.indexOf("sunos") > 0) {
            osVersion = "SunOS";
        }else if(useros.indexOf("iPhone")>0){
            osVersion = "iPhone";
        }else if(useros.indexOf("Android")>0){
            osVersion = "Android";
        }
        return osVersion;
    }

    /**
     * 将请求参数转换为Map
     *
     * @param query 请求参数字符串
     * @return {Map<String,String[]>}
     */
    public static Map<String, String[]> parseQuery(String query) {
        Map<String, String[]> params = new LinkedHashMap<String, String[]>();
        if (StringUtil.isBlank(query)) {
            return params;
        }
        for (String pair : query.split("[;&]")) {
            String[] vs = pair.split("=");
            String key = vs[0];
            String val = vs.length == 1 ? "" : vs[1];
            if (StringUtil.isNotBlank(val)) {
                try {
                    val = URLDecoder.decode(val, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    val = pair.split("=")[1];
                }
            }
            if (!params.containsKey(key))
                params.put(key, new String[]{val});
            else {
                params.put(key, ObjectUtil.join(params.get(key), val));
            }
        }
        return params;
    }

    /**
     * 将请求参数转为指定的对象
     *
     * @param query   请求参数字符串
     * @param classes 要转换成的FormBean
     * @return T 对象
     */
    public static <T> T parseQuery(String query, Class<T> classes) {
        T t = ClassUtil.newInstance(classes);
        if (ObjectUtil.isNull(t)) {
            return null;
        }
        for (Map.Entry<String, String[]> entry : parseQuery(query).entrySet()) {
            if (entry.getValue().length == 1)
                OgnlUtil.getInstance().setValue(entry.getKey(), t, (entry.getValue())[0]);
            else
                OgnlUtil.getInstance().setValue(entry.getKey(), t, entry.getValue());
        }
        return t;
    }

    public static String getQueryString(Map<String, String[]> params) {
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            for (String value : entry.getValue()) {
                queryString.append(entry.getKey()).append("=").append(value).append("&");
            }
        }
        return queryString.toString().replaceAll("&$", "");
    }

    public static String sort(String queryString, String orderBy) {
        Map<String, String[]> params = parseQuery(queryString);
        if (params.containsKey("sort")) {
            String value = (params.get("sort"))[0];
            if (value.startsWith(orderBy)) {
                return queryString.replace("sort=" + value, "sort=" + orderBy + (value.split("-")[1].equals("asc") ? "-desc" : "-asc"));
            }
            return queryString.replace("sort=" + value, "sort=" + orderBy + "-asc");
        }

        return queryString.replace("&$", "").concat("&sort=" + orderBy + "-asc");
    }

    public static class UserAgent{

    }

    public static enum Browser {
        Opera("Opera", "version/\\d+\\W\\d+"),
        chrome("Chrome", "Chrome/\\d+\\W\\d+"),
        Firefox("Firefox", "Firefox/\\d+\\W\\d+"),
        safari("Safari", "version/\\d+\\W\\d+\\W\\d+"),
        _360se("360SE", "360SE/\\d+\\W\\d+"),
        green("GreenBrowser", "GreenBrowser/\\d+\\W\\d+"),
        qq("QQBrowser", "QQBrowser/\\d+\\W\\d+"),
        maxthon("Maxthon", "Maxthon \\d+\\W\\d+"),
        msie("MSIE", "msie\\s\\d+\\W\\d+"),
        mozilla("Mozilla", "firefox/\\d+\\W\\d+"),
        mqqbrowser("MQQBrowser","MQQBrowser/\\d+\\W\\d+"),
        ucbrowser("UCBrowser","UCBrowser/\\d+\\W\\d+"),
        baidubrowser("baidubrowser","baidubrowser/\\d+\\W\\d+"),
        unknown("unknown", "version/\\d+\\W\\d+");

        private String browser;
        private String version;

        private Browser(String browser, String version) {
            this.browser = browser;
            this.version = version;
        }

        public String getVersion(String agent) {
            if ("unknown".equals(this.version)) {
                return null;
            }
            return RegexpUtil.parseFirst(agent, this.version);
        }

        public static Browser getBrowser(String userAgent) {
            userAgent = ObjectUtil.defaultValue(userAgent, "").toLowerCase();
            for (Browser browser : Browser.values()) {
                if (RegexpUtil.isMatch(userAgent, browser.browser)) {
                    return browser;
                }
            }
            return unknown;
        }

        @Override
        public String toString() {
            return this.browser;
        }

    }

    public static String filename(String name) {
        try {
            return Browser.mozilla == browser(getRequest()) ? new String(name.getBytes("UTF-8"), "iso8859-1") : URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return name;
        }
    }

    public static String getValue(byte[] bytes, String charset) {
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return new String(bytes);
        }
    }

    public static String transformCoding(String str, String oldCharset, String charset) {
        try {
            return new String(str.getBytes(oldCharset), charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
            return str;
        }
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    public static boolean isAjax() {
        return isAjax(getRequest());
    }

    public static String getSessionId() {
        return getSessionId(getRequest());
    }

    public static String getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null ? session.getId() : null);
    }

    public static String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

}