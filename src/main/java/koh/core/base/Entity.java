package koh.core.base;

import java.time.LocalDateTime;

public abstract class Entity {
    boolean isDeleted;
    LocalDateTime createdTime;
    LocalDateTime modifiedTime;
}
