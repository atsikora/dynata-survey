package hu.endox.demo.service;

import java.util.List;

import hu.endox.demo.model.Member;
import hu.endox.demo.model.Point;
import hu.endox.demo.model.Survey;
import hu.endox.demo.model.SurveyStatistics;

public interface ISurveyService {

	List<Member> getMembersBySurveyIdAndStatus(Long surveyId, Long status);
	
	List<Survey> getSurveyByMemberIdAndStatus(Long memberId, Long status);
	
	List<Point> getPointsByMemberId(Long memberId);
	
	List<Member> getInvitableMembersBySurveyId(Long surveyId);
	
	List<SurveyStatistics> collectSurveyStatistics();
}
