package com.example.pcs.PCS.domain;

import lombok.Data;

@Data
public class VerificationResponse {
    private boolean match;

    public VerificationResponse(boolean match) {
        this.match = match;
    }
}
