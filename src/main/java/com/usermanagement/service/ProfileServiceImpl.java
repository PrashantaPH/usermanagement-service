package com.usermanagement.service;

import com.common.dto.CommonUserDetails;
import com.usermanagement.dto.ProfileRequest;
import com.usermanagement.dto.ProfileResponse;
import com.usermanagement.entity.User;
import com.usermanagement.exception.UserAlreadyExistsException;
import com.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ProfileResponse createProfile(ProfileRequest profileRequest) {
        if (Boolean.FALSE.equals(userRepository.existsByEmail(profileRequest.getEmail()))) {
            User newProfile = convertToUserEntity(profileRequest);
            newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }
        throw new UserAlreadyExistsException("Email already exists", HttpStatus.CONFLICT.name());
    }

    public CommonUserDetails getUserByEmail(String email) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return convertToCommonUserDetails(existingUser);
    }

    private CommonUserDetails convertToCommonUserDetails(User existingUser) {
        return CommonUserDetails.builder()
                .userId(existingUser.getUserId())
                .fullName(existingUser.getFullName())
                .role(existingUser.getRole())
                .status(existingUser.getStatus())
                .email(existingUser.getEmail())
                .password(existingUser.getPassword())
                .build();
    }

    private ProfileResponse convertToProfileResponse(User newProfile) {
        return ProfileResponse.builder()
                .userId(newProfile.getUserId())
                .fullName(newProfile.getFullName())
                .email(newProfile.getEmail())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }

    private User convertToUserEntity(ProfileRequest profileRequest) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .fullName(profileRequest.getFullName())
                .role(profileRequest.getRole())
                .status(profileRequest.getStatus())
                .email(profileRequest.getEmail())
                .password(passwordEncoder.encode(profileRequest.getPassword()))
                .resetOtp(null)
                .resetOtpExpiredAt(0L)
                .verifyOtpExpiredAt(0L)
                .isAccountVerified(false)
                .build();
    }
}
