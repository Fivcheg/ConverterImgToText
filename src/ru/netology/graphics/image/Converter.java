package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    private int maxWidth;
    private int maxHeight;
    private int newWidth;
    private int newHeight;
    private double maxRatio;
    private TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        checkRatio(img);
        checkNewWidthAndHeight(img);
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        StringBuilder sb = new StringBuilder();
        for (int h = 0; h < img.getHeight(); h++) {
            for (int w = 0; w < img.getWidth(); w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                sb.append(schema.convert(color));
                sb.append(schema.convert(color));
            }
            sb.append("\n ");
        }
        return sb.toString();
    }

    private void checkNewWidthAndHeight(BufferedImage img) {
        if (maxWidth != 0 || maxHeight != 0) {
            double newRatio;
            if (img.getHeight() > maxHeight && img.getWidth() < maxWidth) {
                newRatio = (img.getHeight() / maxHeight);
            } else if (img.getWidth() > maxWidth && img.getHeight() < maxHeight) {
                newRatio = img.getWidth() / maxWidth;
            } else if (img.getHeight() > maxHeight && img.getWidth() > maxWidth) {
                newRatio = Math.max(img.getHeight() / maxHeight, img.getWidth() / maxWidth);
            } else {
                newRatio = 1;
            }
            newHeight = (int) (img.getHeight()/ newRatio);
            newWidth = (int) (img.getWidth()/ newRatio);
        }
    }

    private void checkRatio(BufferedImage img) throws BadImageSizeException {
        if (maxRatio != 0){
            if (maxRatio > 0) {
                double imgRatio = img.getWidth() / img.getHeight();
                if (maxRatio - imgRatio < 0){
                    throw new BadImageSizeException(imgRatio, maxRatio);
                }
            }
        }
    }

    @Override
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}
