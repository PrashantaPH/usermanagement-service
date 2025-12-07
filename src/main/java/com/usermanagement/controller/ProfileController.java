package com.usermanagement.controller;

import com.common.dto.CommonUserDetails;
import com.usermanagement.dto.ProfileRequest;
import com.usermanagement.dto.ProfileResponse;
import com.usermanagement.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.common.utils.Constants.API_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ProfileResponse register(@RequestHeader(API_KEY) String apiKey,
                                    @RequestBody @Valid ProfileRequest profileRequest) {
        var profileResponse = profileService.createProfile(profileRequest);
        return profileResponse;
    }

    @GetMapping("/by-email")
    public CommonUserDetails getUserByEmail(@RequestHeader(API_KEY) String apiKey,
                                            @RequestParam String email) {
        return profileService.getUserByEmail(email);
    }

}
