package hu.endox.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.endox.demo.dto.MemberDTO;
import hu.endox.demo.dto.PointDTO;
import hu.endox.demo.dto.SurveyDTO;
import hu.endox.demo.dto.SurveyStatisticsDTO;
import hu.endox.demo.mapper.DTOMapper;
import hu.endox.demo.model.Member;
import hu.endox.demo.model.ParticipationEntity;
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
        List<ParticipationEntity> participations = participationRepository.findBySurveyId(surveyId);
        List<Member> members = participations.stream().filter(p -> p.getStatus().getId().equals(status)).map(ParticipationEntity::getMember).toList();
        return members.stream().map(DTOMapper::asMemberDTO).toList();
    }

    @Override
    public List<SurveyDTO> getSurveyByMemberIdAndStatus(Long memberId, Long status) {
        List<ParticipationEntity> participations = participationRepository.findByMemberId(memberId);
        List<Survey> surveys = participations.stream().filter(p -> p.getStatus().getId().equals(status)).map(ParticipationEntity::getSurvey).toList();
        return surveys.stream().map(DTOMapper::asSurveyDTO).toList();
    }

    @Override
    public List<PointDTO> getPointsByMemberId(Long memberId) {
        List<ParticipationEntity> participations = participationRepository.findByMemberId(memberId);
        Stream<ParticipationEntity> eligibleParticipations = participations.stream().filter(p -> POINTS_AWARDING_STATUSES.contains(p.getStatus()
                        .getId()));
        return eligibleParticipations.map(p -> new PointDTO(p.getSurvey().getId(), getPointsFromParticipation(p.getStatus().getId(), p.getSurvey())))
                        .toList();
    }

    @Override
    public List<MemberDTO> getInvitableMembersBySurveyId(Long surveyId) {
        List<ParticipationEntity> participations = participationRepository.findBySurveyId(surveyId);
        //maybe not necessary, because there are no NOT_ASKED records in Participations. 
        List<Long> alreadyAskedMemberIds = participations.stream().filter(p -> !SurveyStatus.NOT_ASKED.getCode().equals(p.getStatus().getId())).map(
                        p -> p.getMember().getId()).toList();
        Collection<Member> allMembers = memberRepository.findAllActive();
        return allMembers.stream().filter(m -> !alreadyAskedMemberIds.contains(m.getId())).map(DTOMapper::asMemberDTO).toList();
    }

    @Override
    public List<SurveyStatisticsDTO> collectSurveyStatistics() {
        Collection<Survey> allSurvey = surveyRepository.findAll();
        List<SurveyStatisticsDTO> response = new ArrayList<>(allSurvey.size());
        for (Survey survey : allSurvey) {
            List<ParticipationEntity> participations = participationRepository.findBySurveyId(survey.getId());
            response.add(statisticCollector.collectStatistic(survey, participations));
        }
        return response;
    }

    private Long getPointsFromParticipation(Long surveyStatus, Survey survey) {
        SurveyStatus code = SurveyStatus.fromCode(surveyStatus);
        return switch (code) {
        case FILTERED -> survey.getFilteredPoint();
        case COMPLETED -> survey.getCompletionPoint();
        default -> 0L;
        };
    }

}
