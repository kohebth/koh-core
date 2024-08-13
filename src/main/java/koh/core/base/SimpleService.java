package koh.core.base;

public interface SimpleService {

    SimpleEnvelope handle(HttpParameter parameter, SimpleEnvelope envelope)
            throws Exception;

    Class<? extends HttpBody> getRequestBodyClass();

    Class<? extends HttpCookie> getRequestCookieClass();

    Class<? extends HttpHeader> getRequestHeaderClass();

    Class<? extends HttpParameter> getRequestParameterClass();
}
