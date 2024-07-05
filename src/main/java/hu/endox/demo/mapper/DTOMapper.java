package hu.endox.demo.mapper;

import hu.endox.demo.dto.MemberDTO;
import hu.endox.demo.dto.SurveyDTO;
import hu.endox.demo.model.Member;
import hu.endox.demo.model.Survey;

public final class DTOMapper {

    private DTOMapper() {
        super();
    }

    public static MemberDTO asMemberDTO(Member m) {
        return new MemberDTO(m.getId(), m.getFullName(), m.getEmail(), m.getIsActive());
    }

    public static SurveyDTO asSurveyDTO(Survey s) {
        return new SurveyDTO(s.getId(), s.getName(), s.getExpectedComplete(), s.getCompletionPoint(), s.getFilteredPoint());
    }
}
