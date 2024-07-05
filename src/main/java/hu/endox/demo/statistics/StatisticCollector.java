package hu.endox.demo.statistics;

import java.util.Collection;

import hu.endox.demo.dto.SurveyStatisticsDTO;
import hu.endox.demo.model.Participation;
import hu.endox.demo.model.Survey;

public interface StatisticCollector {

    SurveyStatisticsDTO collectStatistic(Survey survey, Collection<Participation> participations);
}
