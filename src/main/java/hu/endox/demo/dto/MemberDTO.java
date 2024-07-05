package hu.endox.demo.dto;

public record MemberDTO(Long id, String fullName, String email, Boolean isActive) implements DTO{

}
