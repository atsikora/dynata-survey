package hu.endox.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.endox.demo.dto.MemberDTO;
import hu.endox.demo.dto.PointDTO;
import hu.endox.demo.dto.SurveyDTO;
import hu.endox.demo.dto.SurveyStatisticsDTO;
import hu.endox.demo.model.Member;
import hu.endox.demo.model.Participation;
import hu.endox.demo.model.Survey;
import hu.endox.demo.model.SurveyStatus;
import hu.endox.demo.repository.MemberRepository;
import hu.endox.demo.repository.ParticipationRepository;
import hu.endox.demo.repository.SurveyRepository;
import hu.endox.demo.statistics.StatisticCollector;

@Service
public class SurveyService implements ISurveyService {

	private static final List<Long> POINTS_AWARDING_STATUSES = Arrays.asList(SurveyStatus.FILTERED.getCode(), SurveyStatus.COMPLETED.getCode());

	@Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StatisticCollector statisticCollector;


    @Override
    public List<MemberDTO> getMembersBySurveyIdAndStatus(Long surveyId, Long status) {
        List<Participation> participations = participationRepository.findBySurveyId(surveyId);
        List<Long> memberIds = participations.stream().filter(p -> p.getStatus().equals(status)).map(Participation::getMemberId).toList();
        List<Member> members = memberRepository.findAllById(memberIds);
		return members.stream().map(m -> asMemberDTO(m)).toList();
    }

    @Override
    public List<SurveyDTO> getSurveyByMemberIdAndStatus(Long memberId, Long status) {
        List<Participation> participations = participationRepository.findByMemberId(memberId);
        List<Long> surveyIds = participations.stream().filter(p -> p.getStatus().equals(status)).map(Participation::getSurveyId).toList();
        List<Survey> surveys = surveyRepository.findAllById(surveyIds);
		return surveys.stream().map(s -> asSurveyDTO(s)).toList();
    }

    @Override
    public List<PointDTO> getPointsByMemberId(Long memberId) {
        List<Participation> participations = participationRepository.findByMemberId(memberId);
        List<Participation> eligibleParticipations = participations.stream().filter(p -> POINTS_AWARDING_STATUSES.contains(p.getStatus())).toList();
        List<Long> eligibleSurveyIds = eligibleParticipations.stream().map(Participation::getSurveyId).toList();
        List<Survey> surveys = surveyRepository.findAllById(eligibleSurveyIds);
        Map<Long, Survey> surveyMap = surveys.stream().collect(Collectors.toMap(Survey::getId, Function.identity()));
        return eligibleParticipations.stream().map(p -> new PointDTO(p.getSurveyId(), getPointsFromParticipation(p.getStatus(), surveyMap.get(p
                        .getSurveyId())))).toList();
    }

    @Override
    public List<MemberDTO> getInvitableMembersBySurveyId(Long surveyId) {
        List<Participation> participations = participationRepository.findBySurveyId(surveyId);
        //maybe not necessary, because there are no NOT_ASKED records in Participations. 
        List<Long> alreadyAskedMemberIds = participations.stream().filter(p -> !SurveyStatus.NOT_ASKED.getCode().equals(p.getStatus())).map(
                        Participation::getMemberId).toList();
        Collection<Member> allMembers = memberRepository.findAllActive();
        return allMembers.stream().filter(m -> !alreadyAskedMemberIds.contains(m.getMemberId())).map(m -> asMemberDTO(m)).toList();
    }

    @Override
    public List<SurveyStatisticsDTO> collectSurveyStatistics() {
        Collection<Survey> allSurvey = surveyRepository.findAll();
        List<SurveyStatisticsDTO> response = new ArrayList<>(allSurvey.size());
        for (Survey survey : allSurvey) {
            List<Participation> participations = participationRepository.findBySurveyId(survey.getId());
            response.add(statisticCollector.collectStatistic(survey, participations));
        }
        return response;
    }

    private Long getPointsFromParticipation(Long surveyStatus, Survey survey) {
        Long point = 0L;
        if (SurveyStatus.FILTERED.getCode().equals(surveyStatus)) {
            point = survey.getFilteredPoint();
        } else if (SurveyStatus.COMPLETED.getCode().equals(surveyStatus)) {
            point = survey.getCompletionPoint();
        }
        return point;
    }

    private MemberDTO asMemberDTO(Member m) {
    	return new MemberDTO(m.getMemberId(), m.getFullName(), m.getEmail(), m.getIsActive());
    }
    
    private SurveyDTO asSurveyDTO(Survey s) {
    	return new SurveyDTO(s.getId(), s.getName(), s.getExpectedComplete(), s.getCompletionPoint(), s.getFilteredPoint());
    }

}
