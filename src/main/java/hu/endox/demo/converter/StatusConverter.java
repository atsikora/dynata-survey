package hu.endox.demo.converter;

import de.siegmar.fastcsv.reader.CsvRecord;
import hu.endox.demo.model.Status;

public class StatusConverter implements Converter<Status> {

    @Override
    public Status convert(CsvRecord myRecord) {
        //Long id, String name
        return new Status(stringToLong(myRecord.getField(0)), myRecord.getField(1));
    }

    @Override
    public boolean support(Class<?> clazz) {
        return Status.class.equals(clazz);
    }

}
