package hu.endox.demo.converter;

import de.siegmar.fastcsv.reader.CsvRecord;
import hu.endox.demo.model.Survey;

public class SurveyConverter implements Converter<Survey> {

    @Override
    public Survey convert(CsvRecord myRecord) {
        //Long id, String name, Long expectedComplete, Long completionPoint, Long filteredPoint
        return new Survey(stringToLong(myRecord.getField(0)), myRecord.getField(1), stringToLong(myRecord.getField(2)), stringToLong(myRecord
                        .getField(3)), stringToLong(myRecord.getField(4)));
    }

    @Override
    public boolean support(Class<?> clazz) {
        return Survey.class.equals(clazz);
    }

}
