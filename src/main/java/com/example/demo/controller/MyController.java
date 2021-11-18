package com.example.demo.controller;

import com.dfcloud.PublicUtil;
import com.dfcloud.wrapper.WrapMapper;
import com.dfcloud.wrapper.Wrapper;
import com.example.demo.modle.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author LJY
 * @version 1.0
 * @description
 * @date 2021/11/8 17:59
 */
@RestController
public class MyController {
    @RequestMapping(path = {"/helloSpringBoot/{id}"})
    public String HelloSpring(@PathVariable(value = "id") String id) {
        System.out.println("hello spring boot");
        return "hello spring boot" + id;
    }

    @GetMapping(value = "/checkUser/{mobile}")
    public Wrapper checkUser(@PathVariable(value = "mobile") String mobile) {
        boolean b = false;
        return WrapMapper.ok(b);
    }

    public static void main(String[] args) {
        String file = "F:/";
        try {
            selectVideo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void selectVideo(String file) throws IOException {
        String path = null;
        OutputStream out =null;
        InputStream in = null;
        File files = new File(file);
        File[] files1 = files.listFiles();
        if (null != files1) {
            for (File f : files1) {
                if (f.isDirectory()) {
                    System.out.println(f.getAbsolutePath() + "这是一个文件夹");
                    selectVideo(f.getAbsolutePath());
                } else if ("mp4".contains(".mp4")) {
                    System.out.println("视频名称:" + f.getName());
                    path = f.getAbsolutePath();
                    byte[] bytes = new byte[1024];
                    int len = -1;
                     out = new FileOutputStream("G:/myVideos/"+f.getName());
                     in = new FileInputStream(path);
                    while ((len = in.read(bytes,0,bytes.length)) != -1){
                        out.write(bytes,0,len);
                    }

                } else {
                    selectVideo(f.getAbsolutePath());
                }
            }
        }
        in.close();
        out.close();
    }
}
