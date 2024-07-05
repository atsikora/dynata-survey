package hu.endox.demo.model;

public class ParticipationEntity {

	private Member member;
	private Survey survey;
	private Status status;
	private Long length;

	public ParticipationEntity() {
	}

	public ParticipationEntity(Member member, Survey survey, Status status, Long length) {
		super();
		this.member = member;
		this.survey = survey;
		this.status = status;
		this.length = length;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

}
