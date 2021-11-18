package com.example.demo;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.dfcloud.PublicUtil;
import com.example.demo.util.DocxUtil;
import com.example.demo.util.WordToPDF;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlgraphics.util.ClasspathResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.*;

/**
 * @author LJY
 * @version 1.0
 * @description
 * @date 2021/11/12 8:56
 */
public class WordToPdf {
    public static void main(String[] args) throws Exception {
        File filestr = new File("G:\\简历test.pdf");
        filestr.delete();


        Map<String, String> params = new LinkedHashMap<>();
        params.put("${qualification_number}", "2342389423788429789");
        params.put("${agente_name}", "俞婷");
        params.put("${name}", "百年人寿");
        params.put("${company}", "百年人寿浙分");//供应商名称



        String path = "G:\\工作\\蜂险无忧\\客户告知书模板\\国华人寿.docx";
        InputStream in = null;

//        ClasspathResource resource = new ClassPathResource("static/大蜂保险销售有限公司客户告知书（百年）.docx");
        try {
            in = new FileInputStream(path);
        } catch (
                FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        XWPFDocument document = null;
        try {
            document = new XWPFDocument(in);
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }

        List<XWPFParagraph> paragraphs = document.getParagraphs();
        WordToPDF.paragraphReplace(paragraphs, params);
        // fileName
        String fileName = params.get("${name}"+".pdf");
        //poi实现讲替换后的docx生成到某个路径下面
//        DocxUtil.exportDocx(document, "G:/资格证书/", fileName + ".docx");
        DocxUtil.exportDocx(document, "G:/MyPDF/", fileName + ".docx");
        PdfOptions options = PdfOptions.create();
        //word转pdf
        wordToPDF("G:/MyPDF/" + fileName + ".docx", "G:/MyPDF/" + fileName + ".pdf");
        File file = new File("G:/MyPDF/" + fileName + ".docx");
        file.delete();
//        拼接pdf签名部分
        printlnPdf("G:/MyPDF/" + fileName + ".pdf","G:/MyPDF/" + fileName + ".pdf");
    }


    private static void wordToPDF(String inPath, String outPath) {
        /*****
         * 需要引入jar包：aspose-words-15.8.0-jdk16.jar
         * @param args*/

        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File(outPath); // 新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(inPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = new FileInputStream(new File("G:/MyPDF/license.xml")); // license.xml找个路径放即可。
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void printlnPdf(String inpath, String outputPath) throws Exception {
        BaseFont baseFont = BaseFont.createFont();
        InputStream input = new FileInputStream(new File(inpath));
        PdfReader reader = new PdfReader(input);
        OutputStream output = new FileOutputStream(new File(outputPath));
        PdfStamper stamper = new PdfStamper(reader, output);
//            PdfReader reader = new PdfReader(in);
//            PdfStamper stamper = new PdfStamper(reader, outputStream);
            PdfContentByte page = stamper.getOverContent(1);

//        //将文字贴入pdf
//        page.beginText();
//        page.setFontAndSize(baseFont,12);
//        BaseColor coler = new BaseColor(0, 0, 0);
//        page.setColorFill(coler);
//        page.setTextMatrix(100,500); //设置文字在页面中的坐标
//        page.showText("测试");
//        page.endText();


            //将图片贴入pdf
            Image image = Image.getInstance("http://fengxianwuyou.oss-cn-hangzhou.aliyuncs.com/newAStack/BDD497B56EC54C2CB219F6C431732405.png");
            image.setAbsolutePosition(-1, 0); //设置图片在页面中的坐标
            image.scalePercent(20);
            page.addImage(image);
            stamper.close();
            reader.close();
    }

}
