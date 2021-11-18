package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DocxUtil {
    /*
     * @description: 获取word模板数据源
     * @param srcPath 路径
     * @return XWPFDocument
     * @author xuweiqiang
     * @date 2019/8/14
     */
    public static XWPFDocument getDocument(String srcPath) throws IOException {
        return new XWPFDocument(POIXMLDocument.openPackage(srcPath));
    }

    /*
     * @description: 获取word模板数据源
     * @param srcPath 路径
     * @return XWPFDocument
     * @author xuweiqiang
     * @date 2019/8/14
     */
    public static XWPFDocument getDocument(byte[] buf) throws IOException {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(buf);
            return new XWPFDocument(in);
        } catch (Exception e) {

        } finally {
            if (in != null) {
                in.close();
            }
        }
        return null;
    }

    /*
     * @description: 导出docx
     * @param document word模板数据源
     *        destPath 文件路径
     *        fileName 文件名
     * @return
     * @author xuweiqiang
     * @date 2019/8/14
     */
    public static byte[] exportDocx(XWPFDocument document, String wordFilepath, String fileName) {
        FileOutputStream out = null;
        try {
            File file = new File(wordFilepath);
            if (!file.exists()) {
                boolean flag = file.mkdirs();
                if (!flag) {
                    return null;
                }
            }
            File f = new File(wordFilepath + fileName);
            out = new FileOutputStream(f);
            document.write(out);
            out.flush();
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    public static byte[] localWJToIo(String pathName) {
        File file = new File(pathName);
        byte[] by = new byte[(int) file.length()];
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (IOException ex) {
            return by;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                return null;
            }
        }
        return by;
    }

    /*
     * @description: 替换段落中的指定文字
     * @param document word模板数据源
     * @param map 关键字键值对映射
     * @return
     * @author xuweiqiang
     * @date 2019/8/14
     */
    public  void replaceWord(XWPFDocument document, Map<String, String> map) {
        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
        while (itPara.hasNext()) {
            XWPFParagraph paragraph = itPara.next();
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String oneparaString = run.getText(run.getTextPosition());
                if (StringUtils.isBlank(oneparaString)) {
                    continue;
                }
                for (Map.Entry<String, String> entry :
                        map.entrySet()) {
                    oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
                }
                run.setText(oneparaString, 0);
            }
        }
    }


    /*
     * @description: 增加表格行数据
     * @param document word模板数据源
     *        list 行数据
     *        tabNum 表格顺序
     *        colNum 列数
     *        fontName 字体名
     *        fontSize 字号
     * @return
     * @author xuweiqiang
     * @date 2019/8/14
     */
    public static void addTableRow(XWPFDocument document, List<Map<String, String>> list, int tabNum, int colNum, String fontName, int fontSize) {
        // 替换表格中的指定文字
        Iterator<XWPFTable> itTable = document.getTablesIterator();//获得Word的表格
        int tableNumber = 0;
        while (itTable.hasNext()) { //遍历表格
            tableNumber++;
            XWPFTable table = itTable.next();
            int count = table.getNumberOfRows();//获得表格总行数
            List<XWPFTableCell> cells = table.getRow(0).getTableCells();//获得表格的第一行下的列数
            if (tableNumber == tabNum && colNum == cells.size()) {
                for (int i = list.size() - 1; i > 1; i--) {
                    XWPFTableRow row = table.getRow(1);
                    table.addRow(row);
                }
                XWPFParagraph xp;
                for (int t = 0; t < list.size(); t++) {
                    XWPFTableRow nrow = table.getRow(t + 1);
                    List<XWPFTableCell> celln = nrow.getTableCells();//在行元素中，获得表格的单元格

                    int numb = 0;
                    for (Map.Entry<String, String> entry : list.get(t).entrySet()) {
//                        celln.get(numb).removeParagraph(0);//删除无用的段落
                        xp = celln.get(numb).getParagraphs().get(0);
                        xp.getAlignment();
                        XWPFRun title = xp.createRun();
//                        title.setFontSize(fontSize);
//                        CTFonts font = title.getCTR().addNewRPr().addNewRFonts();
//                        font.setEastAsia(fontName);
//                        font.setAscii(fontName);
                        title.setText(entry.getValue());
                        numb++;
                    }
                }
            }
        }
    }
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
