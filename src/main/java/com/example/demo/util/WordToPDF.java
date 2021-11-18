package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class WordToPDF {
    /**
     * 将word文档， 转换成pdf, 中间替换掉变量
     *
     * @param source  源为word文档， 必须为docx文档
     * @param target  目标输出
     * @param options PdfOptions.create().fontEncoding( "windows-1250" ) 或者其他
     * @throws Exception
     */
    public static void wordConverterToPdf(InputStream source, FileOutputStream target,
                                          PdfOptions options) throws Exception {

        XWPFDocument doc = new XWPFDocument(source);
        PdfConverter.getInstance().convert(doc, target, options);

    }

    /**
     * 替换段落中内容
     */
    public static void paragraphReplace(List<XWPFParagraph> paragraphs, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (XWPFParagraph p : paragraphs) {
                for (XWPFRun r : p.getRuns()) {
                    String content = r.getText(r.getTextPosition());
                    if (StringUtils.isNotEmpty(content) && params.containsKey(content)) {
                        r.setText(params.get(content), 0);
                    }
                }
            }
        }
    }



}
