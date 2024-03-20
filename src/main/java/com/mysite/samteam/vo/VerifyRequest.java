package com.mysite.samteam.vo;

import lombok.Data;

@Data
public class VerifyRequest {
    private String userId;
    private String token;

    // getters and setters
}

