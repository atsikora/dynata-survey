package hu.endox.demo.model;

public enum SurveyStatus {

    NOT_ASKED(1L, "Not asked"), REJECTED(2L, "Rejected"), FILTERED(3L, "Filtered"), COMPLETED(4L, "Completed");

    private Long code;
    private String display;

    private SurveyStatus(Long code, String display) {
        this.code = code;
        this.display = display;
    }

    public Long getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }

}
