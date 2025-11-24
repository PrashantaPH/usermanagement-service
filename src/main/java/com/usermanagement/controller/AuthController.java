package com.usermanagement.controller;

import com.common.dto.ApiResponse;
import com.common.dto.AuthRequest;
import com.common.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static com.common.utils.CommonUtil.errorObject;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authentication")
public class AuthController {

    public static final String SAME_SITE_STRICT = "Strict";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String INVALID = "INVALID";
    private final JwtUtil jwtUtil;

    @GetMapping("/login")
    public ResponseEntity<?> generateToken(@RequestHeader("username") String username,
                                                      @RequestHeader("password") String password,
                                                      @RequestHeader("x-api-key") String apiKey) {
        log.info("generateToken called");
        AuthRequest authRequest = AuthRequest.builder()
                .username(username)
                .password(password)
                .apiKey(apiKey)
                .build();
        try {
            var response = jwtUtil.generateToken(authRequest);
            ResponseCookie responseCookie = ResponseCookie.from(ACCESS_TOKEN, response.getAccessToken())
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite(SAME_SITE_STRICT)
                    .build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(response);
        } catch (BadCredentialsException ex) {
            ApiResponse<Object> errorObj = errorObject("Email or Password is incorrect", INVALID);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorObj);
        } catch (DisabledException ex) {
            ApiResponse<Object> errorObj = errorObject("Account is disabled", HttpStatus.UNAUTHORIZED.name());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorObj);
        } catch (Exception ex) {
            ApiResponse<Object> errorObj = errorObject("Authentication failed", HttpStatus.UNAUTHORIZED.name());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorObj);
        }
    }

}
