package com.example.demo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author LJY
 * @version 1.0
 * @description
 * @date 2021/11/16 9:42
 */
public class PDF2Image {
    public static void main(String[] args) {
        File file = new File("G:\\资格证书\\测试生成pdf.pdf");
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 130);
//                     BufferedImage image = renderer.renderImage(i, 1.0f);
                ImageIO.write(image, "PNG", new File("G:\\ImageDemo1.png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}