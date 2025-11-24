package com.usermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "user_id")
    private String userId;

    @Column(name = "full_name")
    private String fullName;

    private String status;

    private String role;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "verify_otp")
    private String verifyOtp;

    @Column(name = "is_account_verified")
    private Boolean isAccountVerified;

    @Column(name = "verify_otp_expired_at")
    private Long verifyOtpExpiredAt;

    @Column(name = "reset_otp")
    private String resetOtp;

    @Column(name = "reset_otp_expired_at")
    private Long resetOtpExpiredAt;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
