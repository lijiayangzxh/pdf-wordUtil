package com.example.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author LJY
 * @version 1.0
 * @description
 * @date 2021/11/17 11:02
 */
public class TrimImage {
    private static int WHITE = new Color(255, 255, 255).getRGB();
    private static int BLACK = new Color(0, 0, 0).getRGB();

    public static int[] bufferedImageToIntArray(BufferedImage image, int width, int height) {
        try {
            int rgb = 0;
            int x1 = width;
            int y1 = height;
            int x2 = 0;
            int y2 = 0;
            int temp1 = 0;
            int temp2 = 0;
            // 方式一：通过getRGB()方式获得像素数组
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    rgb = image.getRGB(i, j);
                    if (rgb == -16777216) {
                        temp1 = i;
                        temp2 = j;
                        // 计算最左侧
                        if (x1 >= temp1) {
                            x1 = temp1;
                        }
                        // 计算最右侧
                        if (x2 <= temp1) {
                            x2 = temp1;
                        }
                        // 计算最下方
                        if (y2 <= temp2) {
                            y2 = temp2;
                        }
                        // 计算最上方
                        if (y1 >= temp2) {
                            y1 = temp2;
                        }
                    }
                }
            }
            System.out.println("BLACK: " + BLACK);
            System.out.println("x1: " + x1);
            System.out.println("x2: " + x2);
            System.out.println("y1: " + y1);
            System.out.println("y2: " + y2);
            System.out.println("宽度: " + String.valueOf(x2 - x1));
            System.out.println("高度: " + String.valueOf(y2 - y1));
            return new int[] {x1, y1, x2 - x1, y2 - y1};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File("G:\\ImageDemo1.png"));
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        System.out.println("原图片宽度" + width);
        System.out.println("原图片高度" + height);
        int[] arr = bufferedImageToIntArray(bufferedImage, width, height);
        // blank是作为四周边距留白
        int blank = 40;
        BufferedImage newBufferedImage = bufferedImage.getSubimage(arr[0] - blank, arr[1] - blank, arr[2] + blank * 2, arr[3] + blank * 2);
        ImageIO.write(newBufferedImage, "png", new File("G:\\ImageDemo.png"));
    }
}
