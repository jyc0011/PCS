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

    // Getters and Setters
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
