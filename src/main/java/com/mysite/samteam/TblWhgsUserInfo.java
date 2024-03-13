package com.mysite.samteam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tbl_whgs_user_info")
@RequiredArgsConstructor
public class TblWhgsUserInfo {

    @Id
    @Column(name = "user_id", length = 32)
    private String userId;

    @Column(name = "user_pwd", length = 64, nullable = false)
    private String userPwd;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 32, nullable = false)
    private String email;

    @Column(name = "hp_no", length = 12)
    private String hpNo;

    @Column(length = 64)
    private String company;

    @Column(name = "yrfy_yn", columnDefinition = "varchar default 'N'" , length = 1, nullable = false)
    private String vrfyYn;

    @UpdateTimestamp
    @Column(name = "vrfy_dt")
    private LocalDateTime vrfyDt;

    @CreationTimestamp
    @Column(name = "rgst_dt", nullable = false)
    private LocalDateTime regDt;

    @UpdateTimestamp
    @Column(name = "upd_dt")
    private LocalDateTime updDt;
}
