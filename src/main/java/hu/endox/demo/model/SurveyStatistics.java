package hu.endox.demo.model;

public record SurveyStatistics(Long surveyId, String surveyName, Long numberOfCompletedSurveys, Long numberOfFilteredParticipants,
                Long numberOfRejectedParticipants, Long averageTimeSpent) {

}
