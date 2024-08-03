package com.example.shopappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @JsonProperty("fullname")
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String fullName;
    @JsonProperty("phone_number")
    @NotBlank(message = "Số điện thoại là bắt buộc")
    @Size(max = 10, message = "Số điện thoại phải có 10 kí tự")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "Mật khẩu là bất buộc")
    private String password;
    @JsonProperty("retype_password")
    @NotBlank(message = "Mật khẩu điện lại là bất buộc")
    private String retypePassword;
    private Date dateOfBirth;
    @JsonProperty("facebook_account_id")
    private Long facebookAccountId;
    @JsonProperty("google_account_id")
    private Long googleAccountId;
    @JsonProperty("role_id")
    @NotNull(message = "Role là bắt buộc")
    private Long roleId;
}
