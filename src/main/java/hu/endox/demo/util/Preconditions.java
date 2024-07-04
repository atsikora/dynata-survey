package hu.endox.demo.util;

public final class Preconditions {

    public static final String ID_CANNOT_BE_NULL_OR_EMPTY = "id cannot be null or empty!";

    private Preconditions() {
    }

    public static String checkPresence(String value) {
        return checkPresence(value, null);
    }

    public static String checkPresence(String value, String errorMessage) {
        if (StringUtil.empty(value)) {
            String message = StringUtil.notEmpty(errorMessage) ? errorMessage : "value cannot be null or empty!";
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static Long checkPresence(Long value) {
        return checkPresence(value, null);
    }

    public static Long checkPresence(Long value, String errorMessage) {
        if (value == null) {
            String message = StringUtil.notEmpty(errorMessage) ? errorMessage : "value cannot be null!";
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
