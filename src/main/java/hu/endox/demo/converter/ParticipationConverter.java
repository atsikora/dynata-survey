package hu.endox.demo.converter;

import de.siegmar.fastcsv.reader.CsvRecord;
import hu.endox.demo.model.Participation;

public class ParticipationConverter implements Converter<Participation> {

    @Override
    public Participation convert(CsvRecord myRecord) {
        //Long memberId, Long surveyId, Long status, String length
        return new Participation(stringToLong(myRecord.getField(0)), stringToLong(myRecord.getField(1)), stringToLong(myRecord.getField(2)),
                        stringToLong(myRecord.getField(3)));
    }

    @Override
    public boolean support(Class<?> clazz) {
        return Participation.class.equals(clazz);
    }

}
