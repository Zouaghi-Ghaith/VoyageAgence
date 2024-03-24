package com.voyage.voyage.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class AuthenticationRequest {
private String email;
private String password;

}
