package com.example.handlingformsubmission.generateQRcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

public class QRCodeGenerator {
    public void qrCodeGenerator(String url) {
//    public static void main(String[] args) {
        // URL to be encoded into QR Code
        //  String url = AuthorizationAndCapture.acceptanceURL; // Replace with your URL

        // Path where QR Code image will be saved
        String filePath = "QRCode.png";

        // QR code generation
        try {
            generateQRCodeImage(url, 350, 350, filePath);
            System.out.println("QR Code generated successfully: " + filePath);
        } catch (Exception e) {
            System.err.println("Error while generating QR Code: " + e.getMessage());
        }
    }

    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws Exception {
        // Create hints for encoding
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.MARGIN, 1); // Optional margin

        // Generate the QR code as a bit matrix
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // Convert the BitMatrix into a BufferedImage
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        // Render QR code on BufferedImage
        Graphics2D graphics = (Graphics2D) image.getGraphics();
//        graphics.setRenderingHint(RenderingHints.KEY_ARTIFACTS, RenderingHints.VALUE_DITHER_DISABLE);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(i, j)) {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                } else {
                    image.setRGB(i, j, Color.WHITE.getRGB());
                }
            }
        }

        // Save the BufferedImage as PNG file
        ImageIO.write(image, "PNG", new File(filePath));
    }
}
