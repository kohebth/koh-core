package koh.core.base;

import koh.core.base.impl.EmptyBody;
import koh.core.base.impl.EmptyCookie;
import koh.core.base.impl.EmptyHeader;
import koh.core.base.impl.EmptyParameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractService implements SimpleService {
    private final Class<? extends HttpParameter> requestParameterClass;
    private final Class<? extends HttpHeader> requestHeaderClass;
    private final Class<? extends HttpCookie> requestCookieClass;
    private final Class<? extends HttpBody> requestBodyClass;

    private AbstractService() {
        this(EmptyParameter.class, EmptyHeader.class, EmptyCookie.class, EmptyBody.class);
    }
}
