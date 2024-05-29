package com.example.pcs.PCS.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageUtil {
    public static byte[] composeImage(MultipartFile imageFile, MultipartFile signature1, MultipartFile signature2) throws Exception {
        InputStream imageStream = imageFile.getInputStream();
        InputStream signature1Stream = signature1.getInputStream();
        InputStream signature2Stream = signature2.getInputStream();

        BufferedImage image = ImageIO.read(imageStream);
        BufferedImage sig1 = ImageIO.read(signature1Stream);
        BufferedImage sig2 = ImageIO.read(signature2Stream);

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // 서명의 최대 높이를 계산합니다.
        int maxSigHeight = Math.max(sig1.getHeight(), sig2.getHeight());

        // 원본 이미지에 하단 여백을 추가합니다 (여기서는 서명 높이의 두 배를 여백으로 추가합니다).
        int totalHeight = imageHeight + maxSigHeight * 2;
        BufferedImage combinedImage = new BufferedImage(imageWidth, totalHeight, BufferedImage.TYPE_INT_RGB);

        Graphics g = combinedImage.getGraphics();
        g.setColor(Color.WHITE); // 배경색을 흰색으로 설정합니다.
        g.fillRect(0, 0, imageWidth, totalHeight); // 새 이미지의 배경을 흰색으로 채웁니다.
        g.drawImage(image, 0, 0, null); // 원본 이미지를 새 이미지 위에 그립니다.

        // 서명 이미지들을 합성합니다.
        int sig1X = (imageWidth - sig1.getWidth()) / 2;
        int sig1Y = imageHeight + (maxSigHeight - sig1.getHeight()) / 2;
        int sig2X = (imageWidth - sig2.getWidth()) / 2;
        int sig2Y = sig1Y + maxSigHeight + 10; // 첫 번째 서명 아래에 10픽셀 간격으로 두 번째 서명을 위치합니다.

        g.drawImage(sig1, sig1X, sig1Y, null);
        g.drawImage(sig2, sig2X, sig2Y, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(combinedImage, "png", baos);
        return baos.toByteArray();
    }
}
