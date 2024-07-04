package hu.endox.demo.converter;

import de.siegmar.fastcsv.reader.CsvRecord;
import hu.endox.demo.model.Member;

public class MemberConverter implements Converter<Member> {

    @Override
    public Member convert(CsvRecord myRecord) {
        //Long memberId, String fullName, String email, Boolean isActive
        return new Member(stringToLong(myRecord.getField(0)), myRecord.getField(1), myRecord.getField(2), "1".equals(myRecord.getField(3)));
    }

    @Override
    public boolean support(Class<?> clazz) {
        return Member.class.equals(clazz);
    }

}
