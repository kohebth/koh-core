package koh.core.base;

public interface SimpleEnvelope {
    <T extends HttpBody> T getBody();

    <T extends HttpHeader> T getHeader();

    <T extends HttpCookie> T getCookie();

    default <T extends HttpParameter> T getParameter() {
        throw new IllegalStateException("Only HTTP Request envelops a parameter");
    }
}
