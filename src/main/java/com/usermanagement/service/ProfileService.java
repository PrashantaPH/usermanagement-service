package com.usermanagement.service;

import com.common.dto.CommonUserDetails;
import com.usermanagement.dto.ProfileRequest;
import com.usermanagement.dto.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest profileRequest);

    CommonUserDetails getUserByEmail(String email);
}
