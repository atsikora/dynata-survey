package hu.endox.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.endox.demo.controller.EntityNotFoundException;
import hu.endox.demo.model.Participation;
import hu.endox.demo.model.Point;
import hu.endox.demo.model.Survey;
import hu.endox.demo.model.SurveyStatus;
import hu.endox.demo.repository.MemberRepository;
import hu.endox.demo.repository.ParticipationRepository;
import hu.endox.demo.repository.SurveyRepository;

@Service
public class MemberService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private MemberRepository memberRepository;

    private List<Long> pointsAwardingStatuses = Arrays.asList(SurveyStatus.FILTERED.getCode(), SurveyStatus.COMPLETED.getCode());

    public List<Survey> getSurveyByMemberIdAndStatus(Long memberId, Long status) {
        List<Participation> participations = participationRepository.findByMemberId(memberId);
        List<Long> surveyIds = participations.stream().filter(p -> p.getStatus().equals(status)).map(Participation::getSurveyId).toList();
        return surveyRepository.findAllById(surveyIds);
    }

    public List<Point> getPointsByMemberId(Long memberId) {
        List<Participation> participations = participationRepository.findByMemberId(memberId);
        List<Participation> eligibleParticipations = participations.stream().filter(p -> pointsAwardingStatuses.contains(p.getStatus())).toList();
        List<Long> eligibleSurveyIds = eligibleParticipations.stream().map(Participation::getSurveyId).toList();
        List<Survey> surveys = surveyRepository.findAllById(eligibleSurveyIds);
        Map<Long, Survey> surveyMap = surveys.stream().collect(Collectors.toMap(Survey::getId, Function.identity()));
        return eligibleParticipations.stream().map(p -> new Point(p.getSurveyId(), getPointsFromParticipation(p.getStatus(), surveyMap.get(p
                        .getSurveyId())))).toList();
    }

    public Survey findById(Long id) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (survey.isEmpty()) {
            throw new EntityNotFoundException(Survey.class.getName(), id);
        }
        return survey.get();
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

}
