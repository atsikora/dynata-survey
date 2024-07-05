package hu.endox.demo.dto;

public record MemberDTO(Long memberId, String fullName, String email, Boolean isActive) implements DTO{

}
