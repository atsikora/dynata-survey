package hu.endox.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.endox.demo.model.Member;
import hu.endox.demo.model.SurveyStatistics;
import hu.endox.demo.model.SurveyStatus;
import hu.endox.demo.service.ISurveyService;
import hu.endox.demo.util.Preconditions;

@RestController
@RequestMapping(value = "/api/survey", produces = MediaType.APPLICATION_JSON_VALUE)
public class SurveyController {

	@Autowired
    private ISurveyService surveyService;

    @GetMapping(value = "/{id}/members/completed")
    public ResponseEntity<List<Member>> getMembersWhoCompletedBySurveyId(@PathVariable("id") Long id) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        return ResponseEntity.ok(surveyService.getMembersBySurveyIdAndStatus(id, SurveyStatus.COMPLETED.getCode()));
    }

    @GetMapping(value = "/{id}/members/{status}")
    public ResponseEntity<List<Member>> getMembersBySurveyIdAndStatus(@PathVariable("id") Long id, @PathVariable("status") Long status) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        Preconditions.checkPresence(status, Preconditions.STATUS_CANNOT_BE_NULL);
        return ResponseEntity.ok(surveyService.getMembersBySurveyIdAndStatus(id, status));
    }

    @GetMapping(value = "/{id}/inviteable")
    public ResponseEntity<List<Member>> getInviteableMembersBySurveyId(@PathVariable("id") Long id) {
        Preconditions.checkPresence(id, Preconditions.ID_CANNOT_BE_NULL_OR_EMPTY);
        return ResponseEntity.ok(surveyService.getInvitableMembersBySurveyId(id));
    }

    @GetMapping(value = "/stats")
    public ResponseEntity<List<SurveyStatistics>> getInviteableMembersBySurveyId() {
        return ResponseEntity.ok(surveyService.collectSurveyStatistics());
    }

}
