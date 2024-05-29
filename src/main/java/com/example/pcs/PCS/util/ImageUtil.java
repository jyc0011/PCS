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

        Graphics g = image.getGraphics();
        g.drawImage(sig1, 100, 100, null);
        g.drawImage(sig2, 200, 100, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
