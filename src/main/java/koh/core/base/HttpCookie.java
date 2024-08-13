package koh.core.base;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public abstract class HttpCookie {
    public static class Cookie {
        jakarta.servlet.http.Cookie instance;

        public String getName() {
            return instance.getName();
        }

        public String getValue() {
            return instance.getValue();
        }

        public String getDomain() {
            return instance.getDomain();
        }

        public String getPath() {
            return instance.getPath();
        }

        public boolean isHttpOnly() {
            return instance.isHttpOnly();
        }

        public boolean isSecured() {
            return instance.getSecure();
        }

        public LocalDateTime getExpireTime() {
            Instant instantDateTime = Instant.ofEpochSecond(instance.getMaxAge());
            return LocalDateTime.ofInstant(instantDateTime, ZoneId.systemDefault());
        }
    }
}
