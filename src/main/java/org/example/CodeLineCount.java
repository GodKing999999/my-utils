package org.example;

import org.example.bean.FileDetail;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author admin
 * @date 2021-04-25 19:35 星期日
 */

public class CodeLineCount {

    private static final String DIRECTORY_NAME = "D:\\code\\mine\\demo\\my-utils\\src\\main\\java";
    private static final List<File> allFiles = new ArrayList<>();

    public static void main(String[] args) {
        File[] files = {new File(DIRECTORY_NAME)};
        traverse(files);
        List<FileDetail> fileDetails = allFiles.stream().map(file -> {
            FileDetail fileDetail = new FileDetail();
            fileDetail.setFile(file);
            AtomicInteger totalLine = new AtomicInteger(0);
            AtomicInteger spaceLine = new AtomicInteger(0);
            AtomicInteger commentLine = new AtomicInteger(0);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String lineString = null;
                while ((lineString = bufferedReader.readLine()) != null) {
                    String trimStr = lineString.trim();
                    if ("".equals(trimStr)) {
                        spaceLine.getAndIncrement();
                    }
                    if (trimStr.startsWith("/*") || trimStr.startsWith("*") || trimStr.startsWith("*/") || trimStr.startsWith("//")) {
                        commentLine.getAndIncrement();
                    }
                    totalLine.getAndIncrement();
                }
                fileDetail.setTotalLine(totalLine.get());
                fileDetail.setSpaceLine(spaceLine.get());
                fileDetail.setCommentLine(commentLine.get());
                System.out.println(fileDetail.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileDetail;
        }).collect(Collectors.toList());
        int sum = fileDetails.stream().mapToInt(FileDetail::getCodeLine).sum();
        System.out.println("项目总代码行数=" + sum);
    }

    private static void traverse(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                //如果file是目录
                if (file.listFiles().length > 0) {
                    // file是非空文件夹
                    traverse(file.listFiles());
                }
            } else {
                allFiles.add(file);
            }
        }
    }
}
