package koh.core.helper;

public class Tools {
    public static <V, R extends V> R safeCastOrThrow(V object, Class<R> toClass) {
        if (object != null && toClass.isAssignableFrom(toClass)) {
            return toClass.cast(object);
        }
        throw new ClassCastException();
    }
}
