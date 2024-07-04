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
import hu.endox.demo.service.MemberService;
import hu.endox.demo.util.Preconditions;

@RestController
@RequestMapping(value = "/api/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping(value = "/{id}/survey/completed")
    public ResponseEntity<List<Survey>> getCompletedSurveyByMemberId(@PathVariable("id") Long id) {
        Preconditions.checkPresence(id, "id cannot be null or empty!");
        return ResponseEntity.ok(memberService.getSurveyByMemberIdAndStatus(id, SurveyStatus.COMPLETED.getCode()));
    }

    @GetMapping(value = "/{id}/survey/{status}")
    public ResponseEntity<List<Survey>> getSurveyByMemberId(@PathVariable("id") Long id, @PathVariable("status") Long status) {
        Preconditions.checkPresence(id, "surveyId cannot be null or empty!");
        Preconditions.checkPresence(status, "status cannot be null!");
        return ResponseEntity.ok(memberService.getSurveyByMemberIdAndStatus(id, status));
    }

    @GetMapping(value = "/{id}/points")
    public ResponseEntity<List<Point>> getPointsByMemberId(@PathVariable("id") Long id) {
        Preconditions.checkPresence(id, "surveyId cannot be null or empty!");
        return ResponseEntity.ok(memberService.getPointsByMemberId(id));
    }
}
