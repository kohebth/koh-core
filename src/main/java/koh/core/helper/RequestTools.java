package koh.core.helper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import koh.core.base.HttpBody;
import koh.core.base.HttpCookie;
import koh.core.base.HttpHeader;
import koh.core.base.HttpParameter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestTools {
    static final String KEY_VALUE_FORMAT = "\"%s\":\"%s\"";

    public static HttpHeader acceptHeaders(HttpServletRequest request, Class<? extends HttpHeader> cls)
            throws IOException {
        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            String headerValue = request.getHeader(headerName);
            stringBuilder.append(String.format(KEY_VALUE_FORMAT, headerName, headerValue));
            stringBuilder.append(",");
        }
        stringBuilder.append("\"__ignored__\":\"__ignored__\"}");

        return JsonTools.fromJson(stringBuilder.toString(), cls);
    }

    public static HttpCookie acceptCookie(HttpServletRequest request, Class<? extends HttpCookie> cls)
            throws IOException {
        Map<String, Cookie> cookieMap = new HashMap<>();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }

        return JsonTools.fromJson(JsonTools.toJson(cookieMap), cls);
    }

    public static HttpBody acceptBody(HttpServletRequest request, Class<? extends HttpBody> cls)
            throws IOException {
        return JsonTools.fromJson(request.getReader(), cls);
    }

    public static HttpParameter acceptParameter(HttpServletRequest request, Class<? extends HttpParameter> cls)
            throws IOException {
        Map<String, Cookie> parameterMap = new HashMap<>();
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                parameterMap.put(cookie.getName(), cookie);
            }
        }
        return JsonTools.fromJson(JsonTools.toJson(parameterMap), cls);
    }
}
