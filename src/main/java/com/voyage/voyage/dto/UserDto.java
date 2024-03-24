package com.voyage.voyage.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {
    private Integer id;

    private String firstname;
    private String lastname;
    private String email;
    private Integer phone;

    private String password;

    private String role;
}
