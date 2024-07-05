package hu.endox.demo.service;

import java.util.List;

import hu.endox.demo.dto.MemberDTO;
import hu.endox.demo.dto.PointDTO;
import hu.endox.demo.dto.SurveyDTO;
import hu.endox.demo.dto.SurveyStatisticsDTO;

public interface ISurveyService {

	List<MemberDTO> getMembersBySurveyIdAndStatus(Long surveyId, Long status);
	
	List<SurveyDTO> getSurveyByMemberIdAndStatus(Long memberId, Long status);
	
	List<PointDTO> getPointsByMemberId(Long memberId);
	
	List<MemberDTO> getInvitableMembersBySurveyId(Long surveyId);
	
	List<SurveyStatisticsDTO> collectSurveyStatistics();
	
}
