package hu.endox.demo.model;

public class Participation {

    private Long memberId;
    private Long surveyId;
    private Long status;
    private Long length;

    public Participation() {
    }

    public Participation(Long memberId, Long surveyId, Long status, Long length) {
        super();
        this.memberId = memberId;
        this.surveyId = surveyId;
        this.status = status;
        this.length = length;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

}
