package hu.endox.demo.converter;

import de.siegmar.fastcsv.reader.CsvRecord;
import hu.endox.demo.util.StringUtil;

public interface Converter<T> {

    T convert(CsvRecord myRecord);

    boolean support(Class<?> clazz);

    default Long stringToLong(String value) {
        Long response = null;
        if (StringUtil.notEmpty(value)) {
            try {
                response = Long.valueOf(value);
            } catch (NumberFormatException e) {
                response = null;
            }
        }
        return response;
    }
}
