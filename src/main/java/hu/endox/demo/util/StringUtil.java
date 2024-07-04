package hu.endox.demo.util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean notEmpty(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean empty(String value) {
        return value == null || value.isBlank();
    }
}
