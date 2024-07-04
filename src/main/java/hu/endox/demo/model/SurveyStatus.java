package hu.endox.demo.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum SurveyStatus {

    NOT_ASKED(1L, "Not asked"), REJECTED(2L, "Rejected"), FILTERED(3L, "Filtered"), COMPLETED(4L, "Completed");

    private Long code;
    private String display;

    private SurveyStatus(Long code, String display) {
        this.code = code;
        this.display = display;
    }

    public static SurveyStatus fromCode(Long code) {
        Optional<SurveyStatus> value = Stream.of(SurveyStatus.values()).filter(ss -> ss.getCode().equals(code)).findFirst();
        if (value.isPresent()) {
            return value.get();
        } else {
            //TODO proper message
            throw new IllegalArgumentException();
        }
    }

    public Long getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }

}
