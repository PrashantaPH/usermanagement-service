package com.usermanagement.controller;

import com.common.dto.CommonUserDetails;
import com.usermanagement.dto.ProfileRequest;
import com.usermanagement.dto.ProfileResponse;
import com.usermanagement.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ProfileResponse register(@RequestBody @Valid ProfileRequest profileRequest) {
        var profileResponse = profileService.createProfile(profileRequest);
        return profileResponse;
    }

    @GetMapping("/by-email")
    public CommonUserDetails getUserByEmail(@RequestParam String email) {
        return profileService.getUserByEmail(email);
    }

}
