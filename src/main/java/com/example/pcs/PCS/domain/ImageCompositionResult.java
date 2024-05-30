package com.example.pcs.PCS.domain;

import lombok.Data;

@Data
public class ImageCompositionResult {
    private String base64Image;
    private String hash;
    private Block block;

    public ImageCompositionResult(String base64Image, String hash, Block block) {
        this.base64Image = base64Image;
        this.hash = hash;
        this.block = block;
    }
}
