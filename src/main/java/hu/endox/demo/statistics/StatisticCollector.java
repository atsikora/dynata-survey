package hu.endox.demo.statistics;

import java.util.Collection;

import hu.endox.demo.model.Participation;
import hu.endox.demo.model.Survey;
import hu.endox.demo.model.SurveyStatistics;

public interface StatisticCollector {

    SurveyStatistics collectStatistic(Survey survey, Collection<Participation> participations);
}
