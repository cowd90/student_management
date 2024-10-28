package com.unit.studentmgmt.controller;

import com.nimbusds.jose.JOSEException;
import com.unit.studentmgmt.dto.request.AuthenticationRequest;
import com.unit.studentmgmt.dto.request.IntrospectRequest;
import com.unit.studentmgmt.dto.request.LogoutRequest;
import com.unit.studentmgmt.dto.request.RefreshRequest;
import com.unit.studentmgmt.dto.response.BaseRes;
import com.unit.studentmgmt.dto.response.AuthenticationResponse;
import com.unit.studentmgmt.dto.response.IntrospectResponse;
import com.unit.studentmgmt.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    BaseRes<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return BaseRes.<AuthenticationResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/introspect")
    BaseRes<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        var result = authenticationService.introspect(request);
        return BaseRes.<IntrospectResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/refresh")
    BaseRes<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        return BaseRes.<AuthenticationResponse>builder()
                .data(authenticationService.refreshToken(request))
                .build();
    }

    @PostMapping("/logout")
    BaseRes<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return BaseRes.<Void>builder().build();
    }

}
