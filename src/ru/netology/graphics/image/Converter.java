package ru.netology.graphics.image;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    protected int width;
    protected int newWidth;
    protected int newHeight;
    protected int height;
    protected double maxRatio;
    private TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        if (this.schema == null) this.schema = color -> "#$@%*+-/".charAt(color / 32);
        String picture = "";
        rotation(img.getWidth(), img.getHeight());
        if (newWidth / newHeight > maxRatio) throw new BadImageSizeException(img.getWidth() / width, maxRatio);
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();
        for (int h = 0; h < newHeight; h++) {
        for (int w = 0; w < newWidth; w++) {
                int intColor = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(intColor);
                picture += c;
            }
            picture += "\n";
        }
        return picture;
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }

    public void rotation(int x, int y) {
        int rotation = x / width;
        this.newWidth = x / rotation;
        this.newHeight = y / rotation;
    }
}
