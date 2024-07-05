package hu.endox.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.endox.demo.dto.PointDTO;
import hu.endox.demo.dto.SurveyDTO;
import hu.endox.demo.model.SurveyStatus;
import hu.endox.demo.service.ISurveyService;
import hu.endox.demo.util.Preconditions;

@RestController
@RequestMapping(value = "/api/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    @Autowired
    private ISurveyService surveyService;

    @GetMapping(value = "/{id}/survey/completed")
    public ResponseEntity<List<SurveyDTO>> getCompletedSurveyByMemberId(@PathVariable("id") Long id) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        return ResponseEntity.ok(surveyService.getSurveyByMemberIdAndStatus(id, SurveyStatus.COMPLETED.getCode()));
    }

    @GetMapping(value = "/{id}/survey/{status}")
    public ResponseEntity<List<SurveyDTO>> getSurveyByMemberId(@PathVariable("id") Long id, @PathVariable("status") Long status) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        Preconditions.checkPresence(status, Preconditions.STATUS_CANNOT_BE_NULL);
        return ResponseEntity.ok(surveyService.getSurveyByMemberIdAndStatus(id, status));
    }

    @GetMapping(value = "/{id}/points")
    public ResponseEntity<List<PointDTO>> getPointsByMemberId(@PathVariable("id") Long id) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        return ResponseEntity.ok(surveyService.getPointsByMemberId(id));
    }
}
