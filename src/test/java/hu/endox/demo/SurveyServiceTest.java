package hu.endox.demo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hu.endox.demo.dto.MemberDTO;
import hu.endox.demo.dto.SurveyDTO;
import hu.endox.demo.dto.SurveyStatisticsDTO;
import hu.endox.demo.model.Member;
import hu.endox.demo.model.ParticipationEntity;
import hu.endox.demo.model.Status;
import hu.endox.demo.model.Survey;
import hu.endox.demo.model.SurveyStatus;
import hu.endox.demo.repository.MemberRepository;
import hu.endox.demo.repository.ParticipationRepository;
import hu.endox.demo.repository.SurveyRepository;
import hu.endox.demo.service.SurveyService;
import hu.endox.demo.statistics.BasicStatisticCollector;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {

	@InjectMocks
	SurveyService surveyService;
	
	@Mock
    private SurveyRepository surveyRepository;

	@Mock
    private ParticipationRepository participationRepository;

	@Mock
    private MemberRepository memberRepository;

	@Mock
    private BasicStatisticCollector statisticCollector;
	
	private static final Long SURVEY_ID = 1L;
	private static final Long STATUS = 4L;
	private static final Long MEMBER_ID = 1L;
	
	@Test
	public void testGetMembersBySurveyIdAndStatus() {
		
		when(participationRepository.findBySurveyId(SURVEY_ID)).thenReturn(createParticipationsForSurvey());
		
		List<MemberDTO> members = surveyService.getMembersBySurveyIdAndStatus(SURVEY_ID, STATUS);
		
		assertEquals(3, members.size());
		assertArrayEquals(new Long[] {1L, 2L, 3L} , members.stream().map(MemberDTO::id).toArray());

	}
	
	@Test
	public void testGetSurveyByMemberIdAndStatus() {
		
		when(participationRepository.findByMemberId(MEMBER_ID)).thenReturn(createParticipationsForMember());
		
		List<SurveyDTO> surveys = surveyService.getSurveyByMemberIdAndStatus(SURVEY_ID, STATUS);
		
		assertEquals(2, surveys.size());
		assertArrayEquals(new Long[] {1L, 2L} , surveys.stream().map(SurveyDTO::id).toArray());
	}
	
	@Test
	public void testGetInvitableMembersBySurveyId() {
		
		when(participationRepository.findBySurveyId(SURVEY_ID)).thenReturn(createParticipationsForSurvey());
		
		when(memberRepository.findAllActive()).thenReturn(createAllActiveMembers());
		
		List<MemberDTO> invitableMembers = surveyService.getInvitableMembersBySurveyId(SURVEY_ID);
		
		assertEquals(3, invitableMembers.size());
		assertArrayEquals(new Long[] {5L, 6L, 7L} , invitableMembers.stream().map(MemberDTO::id).toArray());
	}
	
	@Test
	public void testCollectSurveyStatistics() {
		
		when(surveyRepository.findAll()).thenReturn(createSurveysForStatistics());
		
		when(participationRepository.findBySurveyId(SURVEY_ID)).thenReturn(createParticipationsForSurvey());
		
		when(statisticCollector.collectStatistic(any(Survey.class), ArgumentMatchers.<List<ParticipationEntity>>any())).thenCallRealMethod();
		
		List<SurveyStatisticsDTO> stats = surveyService.collectSurveyStatistics();
		
		assertEquals(1, stats.size());

		SurveyStatisticsDTO stat = stats.get(0);
		assertEquals(3, stat.numberOfCompletedSurveys());
		assertEquals(1, stat.numberOfFilteredParticipants());
		assertEquals(0, stat.numberOfRejectedParticipants());
		assertEquals(20, stat.averageTimeSpent());
		
		verify(statisticCollector, times(1)).collectStatistic(any(Survey.class), ArgumentMatchers.<List<ParticipationEntity>>any());
	}
	
	private List<ParticipationEntity> createParticipationsForSurvey() {
		List<ParticipationEntity> entities = new ArrayList<>(3);
		Survey survey = createSurvey(1L, "survey1", 10L, 10L, 5L);
		Status completed = createStatus(SurveyStatus.COMPLETED);
		
		entities.add(new ParticipationEntity(createMember(1L, "test1", "test1@mail.com", true), survey, completed, 10L));
		entities.add(new ParticipationEntity(createMember(2L, "test2", "test2@mail.com", true), survey, completed, 20L));
		entities.add(new ParticipationEntity(createMember(3L, "test3", "test3@mail.com", false), survey, completed, 30L));
		entities.add(new ParticipationEntity(createMember(4L, "test4", "test4@mail.com", false), survey, createStatus(SurveyStatus.FILTERED), 0L));
		
		return entities;
	}
	
	private List<Member> createAllActiveMembers() {
		List<Member> activeMembers = new ArrayList<>(5);
		
		activeMembers.add(createMember(1L, "test1", "test1@mail.com", true));
		activeMembers.add(createMember(2L, "test2", "test2@mail.com", true));
		activeMembers.add(createMember(5L, "test5", "test5@mail.com", true));
		activeMembers.add(createMember(6L, "test6", "test6@mail.com", true));
		activeMembers.add(createMember(7L, "test7", "test7@mail.com", true));
		
		return activeMembers;
	}
	
	private List<ParticipationEntity> createParticipationsForMember() {
		List<ParticipationEntity> entities = new ArrayList<>(3);
		
		Survey survey1 = createSurvey(1L, "survey1", 10L, 10L, 5L);
		Survey survey2 = createSurvey(2L, "survey2", 10L, 10L, 5L);
		Survey survey3 = createSurvey(3L, "survey3", 10L, 10L, 5L);
		
		Status completed = createStatus(SurveyStatus.COMPLETED);
		Member member = createMember(1L, "test1", "test1@mail.com", true);

		entities.add(new ParticipationEntity(member, survey1, completed, 0L));
		entities.add(new ParticipationEntity(member, survey2, completed, 0L));
		entities.add(new ParticipationEntity(member, survey3, createStatus(SurveyStatus.FILTERED), 0L));
		
		return entities;
	}
	
	private List<Survey> createSurveysForStatistics() {
		List<Survey> surveys = new ArrayList<>(1);
		surveys.add(createSurvey(1L, "survey1", 10L, 10L, 5L));
		return surveys;
	}
	
	private Member createMember(Long id, String fullname, String email, Boolean isActive) {
		return new Member(id, fullname, email, isActive);
	}
	
	private Survey createSurvey(Long id, String name, Long expectedComplete, Long completionPoint, Long filteredPoint) {
		return new Survey(id, name, expectedComplete, completionPoint, filteredPoint);
	}
	
	private Status createStatus(SurveyStatus status) {
		return new Status(status.getCode(), status.getDisplay());
	}
}
