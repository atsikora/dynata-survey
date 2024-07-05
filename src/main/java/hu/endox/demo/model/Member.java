package hu.endox.demo.model;

public class Member implements Model{

    private Long memberId;
    private String fullName;
    private String email;
    private Boolean isActive;

    public Member() {
    }

    public Member(Long memberId, String fullName, String email, Boolean isActive) {
        super();
        this.memberId = memberId;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Member [memberId=" + memberId + ", fullName=" + fullName + ", email=" + email + ", isActive=" + isActive + "]";
    }

}
