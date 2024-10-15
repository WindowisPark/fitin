package com.fitin.auth.dto;

import com.fitin.auth.entity.MemberRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이름은 필수항목입니다.")
    private String name;

    @NotNull(message = "키는 필수항목입니다.")
    private Double height;

    @NotNull(message = "체중은 필수항목입니다.")
    private Double weight;

    @NotEmpty(message = "전화번호는 필수항목입니다.")
    @Size(min = 10, max = 11, message = "전화번호는 10-11자리여야 합니다.")
    private String phoneNumber;

    @NotNull(message = "권한은 필수항목입니다.")
    private MemberRole role;
}