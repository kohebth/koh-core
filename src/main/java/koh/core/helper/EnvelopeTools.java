package koh.core.helper;

import koh.core.base.HttpBody;
import koh.core.base.HttpCookie;
import koh.core.base.HttpHeader;
import koh.core.base.SimpleEnvelope;
import koh.core.base.impl.EmptyBody;
import koh.core.base.impl.EmptyCookie;
import koh.core.base.impl.EmptyHeader;
import lombok.Builder;
import lombok.Data;
import org.eclipse.jetty.http.HttpStatus;

public class EnvelopeTools {
    public static final SimpleEnvelope EMPTY_ENVELOPE;
    public static final HttpCookie EMPTY_COOKIE;
    public static final HttpHeader EMPTY_HEADER;
    public static final HttpBody EMPTY_BODY;
    public static final HttpBody ACCEPTED_BODY;
    public static final HttpBody REJECTED_BODY;

    static {
        EMPTY_BODY = new EmptyBody();
        EMPTY_HEADER = new EmptyHeader();
        EMPTY_COOKIE = new EmptyCookie();
        EMPTY_ENVELOPE = make(EMPTY_BODY, EMPTY_HEADER, EMPTY_COOKIE);
        ACCEPTED_BODY = new HttpBody() {
            final String message = "Accepted";
            final int code = HttpStatus.Code.ACCEPTED.getCode();
        };
        REJECTED_BODY = new HttpBody() {
            final String message = "Rejected";
            final int code = HttpStatus.Code.NOT_ACCEPTABLE.getCode();
        };
    }

    public static SimpleEnvelope make(HttpBody body, HttpHeader header, HttpCookie cookie) {
        return EnvelopeImpl.builder().body(body).header(header).cookie(cookie).build();
    }

    @Data
    @Builder
    @SuppressWarnings("unchecked")
    private static class EnvelopeImpl implements SimpleEnvelope {
        final HttpBody body;
        final HttpHeader header;
        final HttpCookie cookie;
    }
}
