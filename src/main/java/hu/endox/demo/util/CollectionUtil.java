package hu.endox.demo.util;

import java.util.Collections;
import java.util.List;

public final class CollectionUtil {

    private CollectionUtil() {
        super();
    }

    public static <T> List<T> nullAsEmptyList(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }
}
