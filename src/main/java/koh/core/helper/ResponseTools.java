package koh.core.helper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import koh.core.base.HttpBody;
import koh.core.base.HttpCookie;
import koh.core.base.HttpHeader;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class ResponseTools {

    private static final HttpBody EMPTY_BODY = new HttpBody() {
    };

    private ResponseTools() {
    }

    public static void returnBody(HttpServletResponse resp, HttpBody body)
            throws IOException {
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(JsonTools.toJson(body));
    }

    public static void returnToNonExistedPath(HttpServletResponse resp)
            throws IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.getWriter().write("{ \"status\": \"Not Found\"}");
    }

    public static void returnEmpty(HttpServletResponse resp)
            throws IOException {
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.getWriter().write(JsonTools.toJson(EMPTY_BODY));
    }

    public static void returnHeader(HttpServletResponse resp, HttpHeader header)
            throws IOException {
        Map<String, String> headers = JsonTools.fromJson(header);
        headers.forEach(resp::setHeader);
    }

    public static void returnCookie(HttpServletResponse resp, HttpCookie cookie)
            throws IOException {
        Map<String, Cookie> cookies = JsonTools.fromJson(cookie);
        cookies.values().forEach(resp::addCookie);
    }
}
