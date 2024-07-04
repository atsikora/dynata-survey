package hu.endox.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.endox.demo.model.Point;
import hu.endox.demo.model.Survey;
import hu.endox.demo.model.SurveyStatus;
import hu.endox.demo.service.SurveyService;
import hu.endox.demo.util.Preconditions;

@RestController
@RequestMapping(value = "/api/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    @Autowired
    private SurveyService surveyService;

    @GetMapping(value = "/{id}/survey/completed")
    public ResponseEntity<List<Survey>> getCompletedSurveyByMemberId(@PathVariable("id") Long id) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        return ResponseEntity.ok(surveyService.getSurveyByMemberIdAndStatus(id, SurveyStatus.COMPLETED.getCode()));
    }

    @GetMapping(value = "/{id}/survey/{status}")
    public ResponseEntity<List<Survey>> getSurveyByMemberId(@PathVariable("id") Long id, @PathVariable("status") Long status) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        Preconditions.checkPresence(status, "status cannot be null!");
        return ResponseEntity.ok(surveyService.getSurveyByMemberIdAndStatus(id, status));
    }

    @GetMapping(value = "/{id}/points")
    public ResponseEntity<List<Point>> getPointsByMemberId(@PathVariable("id") Long id) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        return ResponseEntity.ok(surveyService.getPointsByMemberId(id));
    }
}
