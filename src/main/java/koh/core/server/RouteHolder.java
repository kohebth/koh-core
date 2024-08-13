package koh.core.server;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import koh.core.base.*;
import koh.core.helper.EnvelopeTools;
import koh.core.helper.RequestTools;
import koh.core.helper.ResponseTools;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.EnumMap;

@Slf4j
class RouteHolder extends GenericServlet {
    private final EnumMap<HttpMethod, Class<? extends SimpleService>> servicesClass;
    private final transient EnumMap<HttpMethod, SimpleService> services;

    public RouteHolder() {
        this.servicesClass = new EnumMap<>(HttpMethod.class);
        this.services = new EnumMap<>(HttpMethod.class);
    }

    void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws IOException {

        HttpParameter reqParameter;
        HttpHeader reqHeader;
        HttpCookie reqCookie;
        HttpMethod reqMethod;
        HttpBody reqBody;

        try {
            reqMethod = HttpMethod.valueOf(httpRequest.getMethod());
            SimpleService service = servicesClass.get(reqMethod).getDeclaredConstructor().newInstance();

            reqBody = RequestTools.acceptBody(httpRequest, service.getRequestBodyClass());
            reqHeader = RequestTools.acceptHeaders(httpRequest, service.getRequestHeaderClass());
            reqCookie = RequestTools.acceptCookie(httpRequest, service.getRequestCookieClass());
            reqParameter = RequestTools.acceptParameter(httpRequest, service.getRequestParameterClass());

            SimpleEnvelope resEnvelope = service.handle(
                    reqParameter,
                    EnvelopeTools.make(reqBody, reqHeader, reqCookie)
            );

            ResponseTools.returnBody(httpResponse, resEnvelope.getBody());
            ResponseTools.returnCookie(httpResponse, resEnvelope.getCookie());
            ResponseTools.returnHeader(httpResponse, resEnvelope.getHeader());
        } catch (Exception e) {
            ResponseTools.returnEmpty(httpResponse);
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            log.error("", e);
        } finally {
            httpResponse.flushBuffer();
            httpResponse.getWriter().close();
            httpRequest.getReader().close();
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res)
            throws IOException {
        service((HttpServletRequest) req, (HttpServletResponse) res);
    }

    public void addService(HttpMethod httpMethod, Class<? extends SimpleService> serviceClass) {
        this.servicesClass.compute(httpMethod, (method, sClass) -> {
            if (sClass != null) {
                throw new RuntimeException(String.format("Method [%s] is existed", method));
            }
            return serviceClass;
        });
    }

    public static class ControllerServletException extends RuntimeException {
        public ControllerServletException(Exception e) {
            super(e);
        }
    }
}
