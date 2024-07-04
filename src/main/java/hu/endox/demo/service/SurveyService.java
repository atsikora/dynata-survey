package hu.endox.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.endox.demo.controller.EntityNotFoundException;
import hu.endox.demo.model.Member;
import hu.endox.demo.model.Participation;
import hu.endox.demo.model.Survey;
import hu.endox.demo.model.SurveyStatistics;
import hu.endox.demo.model.SurveyStatus;
import hu.endox.demo.repository.MemberRepository;
import hu.endox.demo.repository.ParticipationRepository;
import hu.endox.demo.repository.SurveyRepository;
import hu.endox.demo.statistics.StatisticCollector;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StatisticCollector statisticCollector;

    public List<Member> getMembersBySurveyIdAndStatus(Long surveyId, Long status) {
        List<Participation> participations = participationRepository.findBySurveyId(surveyId);
        List<Long> memberIds = participations.stream().filter(p -> p.getStatus().equals(status)).map(Participation::getMemberId).toList();
        return memberRepository.findAllById(memberIds);
    }

    public List<Member> getInvitableMembersBySurveyId(Long surveyId) {
        List<Participation> participations = participationRepository.findBySurveyId(surveyId);
        //maybe not necessary, because there are no NOT_ASKED records in Participations. 
        List<Long> alreadyAskedMemberIds = participations.stream().filter(p -> !SurveyStatus.NOT_ASKED.getCode().equals(p.getStatus())).map(
                        Participation::getMemberId).toList();
        Collection<Member> allMembers = memberRepository.findAllActive();
        return allMembers.stream().filter(m -> !alreadyAskedMemberIds.contains(m.getMemberId())).toList();
    }

    public Survey findById(Long id) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (survey.isEmpty()) {
            throw new EntityNotFoundException(Survey.class.getName(), id);
        }
        return survey.get();
    }

    public List<SurveyStatistics> collectSurveyStatistics() {
        Collection<Survey> allSurvey = surveyRepository.findAll();
        List<SurveyStatistics> response = new ArrayList<>(allSurvey.size());
        for (Survey survey : allSurvey) {
            List<Participation> participations = participationRepository.findBySurveyId(survey.getId());
            response.add(statisticCollector.collectStatistic(survey, participations));
        }
        return response;
    }
}
