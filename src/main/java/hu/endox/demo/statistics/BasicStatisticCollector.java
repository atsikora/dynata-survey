package hu.endox.demo.statistics;

import java.util.Collection;

import org.springframework.stereotype.Component;

import hu.endox.demo.dto.SurveyStatisticsDTO;
import hu.endox.demo.model.ParticipationEntity;
import hu.endox.demo.model.Survey;
import hu.endox.demo.model.SurveyStatus;

@Component
public class BasicStatisticCollector implements StatisticCollector {

    @Override
    public SurveyStatisticsDTO collectStatistic(Survey survey, Collection<ParticipationEntity> participations) {
        Long surveyId = survey.getId();
        String surveyName = survey.getName();
        Long numberOfCompletedSurveys = participations.stream().filter(p -> SurveyStatus.COMPLETED.getCode().equals(p.getStatus().getId())).count();
        Long numberOfFilteredParticipants = participations.stream().filter(p -> SurveyStatus.FILTERED.getCode().equals(p.getStatus().getId())).count();
        Long numberOfRejectedParticipants = participations.stream().filter(p -> SurveyStatus.REJECTED.getCode().equals(p.getStatus().getId())).count();
        Long sumOfLength = participations.stream().filter(p -> SurveyStatus.COMPLETED.getCode().equals(p.getStatus().getId())).mapToLong(p -> p.getLength())
                        .sum();
        Long averageTimeSpent = sumOfLength / numberOfCompletedSurveys;
        return new SurveyStatisticsDTO(surveyId, surveyName, numberOfCompletedSurveys, numberOfFilteredParticipants, numberOfRejectedParticipants,
                        averageTimeSpent);
    }

}
