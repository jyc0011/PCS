package com.example.pcs.PCS.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageUtil {
    public static byte[] composeImage(MultipartFile imageFile, MultipartFile signature1, MultipartFile signature2, String partyA, String partyB) throws Exception {
        InputStream imageStream = imageFile.getInputStream();
        InputStream signature1Stream = signature1.getInputStream();
        InputStream signature2Stream = signature2.getInputStream();

        BufferedImage image = ImageIO.read(imageStream);
        BufferedImage sig1 = ImageIO.read(signature1Stream);
        BufferedImage sig2 = ImageIO.read(signature2Stream);

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // 서명의 크기 조정
        int sigHeight = 100; // 서명의 높이 설정
        BufferedImage scaledSig1 = scaleImage(sig1, sigHeight);
        BufferedImage scaledSig2 = scaleImage(sig2, sigHeight);

        // 전체 이미지의 높이 계산
        int totalHeight = imageHeight + scaledSig1.getHeight() + 100; // 여유 공간 포함

        BufferedImage combinedImage = new BufferedImage(imageWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinedImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth, totalHeight);

        g.drawImage(image, 0, 0, null);

        // 한글 폰트 설정
        g.setFont(new Font("Malgun Gothic", Font.BOLD, 40));
        g.setColor(Color.BLACK); // 검정색으로 텍스트 설정

        // 텍스트와 서명 그리기
        int textY = imageHeight + 30; // 텍스트 시작 위치
        int sigY = imageHeight + 20; // 서명 시작 위치

        drawSignatureNextToText(g, "갑 : " + partyA + " (인)", scaledSig1, 50, textY, sigY);
        drawSignatureNextToText(g, "을 : " + partyB + " (인)", scaledSig2, 50, textY + 50, sigY + 50);

        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(combinedImage, "png", baos);
        return baos.toByteArray();
    }

    private static BufferedImage scaleImage(BufferedImage original, int height) {
        double ratio = (double) height / original.getHeight();
        int newWidth = (int) (original.getWidth() * ratio);
        Image scaled = original.getScaledInstance(newWidth, height, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(newWidth, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();
        return newImage;
    }

    private static void drawSignatureNextToText(Graphics2D g, String text, BufferedImage signature, int x, int textY, int sigY) {
        g.drawString(text, x, textY);
        // 서명 이미지 위치 조정
        int signatureX = x + g.getFontMetrics().stringWidth(text) + 10;
        g.drawImage(signature, signatureX, sigY - signature.getHeight() / 2, null);
    }
}
