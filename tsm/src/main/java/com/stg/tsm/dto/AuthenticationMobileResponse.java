package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationMobileResponse {
    private final String jwt;
    private MobileAuthenticationResponseDTO authenticationResponseDTO;
}
