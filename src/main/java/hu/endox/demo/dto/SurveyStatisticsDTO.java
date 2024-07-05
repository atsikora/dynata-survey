package hu.endox.demo.dto;

public record SurveyStatisticsDTO(Long surveyId, String surveyName, Long numberOfCompletedSurveys, Long numberOfFilteredParticipants,
                Long numberOfRejectedParticipants, Long averageTimeSpent) {

}
