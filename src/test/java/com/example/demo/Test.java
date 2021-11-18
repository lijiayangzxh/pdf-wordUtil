package com.example.demo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.*;

/**
 * @author LJY
 * @version 1.0
 * @description
 * @date 2021/11/12 8:33
 */
public class Test {

    public static void main(String[] args) throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont();
        InputStream input = new FileInputStream(new File("G:\\资格证书\\测试生成PDF.pdf"));
        PdfReader reader = new PdfReader(input);
        OutputStream output = new FileOutputStream(new File("G:\\testDemo.pdf"));
        PdfStamper stamper = new PdfStamper(reader, output);
        PdfContentByte page = stamper.getOverContent(1);
//
//        //将文字贴入pdf
//        page.beginText();
//        page.setFontAndSize(baseFont,12);
//        BaseColor coler = new BaseColor(0, 0, 0);
//        page.setColorFill(coler);
//        page.setTextMatrix(100,500); //设置文字在页面中的坐标
//        page.showText("测试");
//        page.endText();


        //将图片贴入pdf
        Image image = Image.getInstance("G:\\工作\\测试图片\\sign.png");
        image.setAbsolutePosition(-1,0); //设置图片在页面中的坐标
        image.scalePercent(50);
        page.addImage(image);

        stamper.close();
        reader.close();
        input.close();
    }

}