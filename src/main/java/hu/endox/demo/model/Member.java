package hu.endox.demo.model;

public class Member implements Model{

    private Long id;
    private String fullName;
    private String email;
    private Boolean isActive;

    public Member() {
    }

    public Member(Long id, String fullName, String email, Boolean isActive) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

}
