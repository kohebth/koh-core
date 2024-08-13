package koh.core.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.util.StringRequestContent;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.HttpCookieStore;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class SimpleClient {
    final HttpClient httpClient;
    final HttpCookieStore cookieStore;

    SimpleClient() {
        httpClient = new HttpClient();
        cookieStore = new HttpCookieStore();

        httpClient.setCookieStore(cookieStore);
    }

    Object call(String host, int port, HttpMethod method, List<HttpCookie> cookies, HttpHeader header, HttpContent body) {


        Request request = httpClient.newRequest(host, port);
        request.body(new StringRequestContent(body.toString()));
        cookies
        request.method(method);
        request.timeout(30, TimeUnit.SECONDS);
        Response response = null;

        try {
            response = request.send();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (TimeoutException | ExecutionException e) {
            log.error("Request failure {} ", request, e);
        }
        return response;
    }
}
