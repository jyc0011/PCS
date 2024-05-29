package com.example.pcs.PCS.domain;

import lombok.Data;

@Data
public class VerificationResponse {
    private boolean isMatch;

    public VerificationResponse(boolean isMatch) {
        this.isMatch = isMatch;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public void setMatch(boolean match) {
        isMatch = match;
    }
}
