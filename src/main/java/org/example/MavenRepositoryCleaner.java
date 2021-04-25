package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * maven 本地仓库清理类工具类
 * <p>
 * 删除本地maven仓库中的空文件夹
 * 只保留包含{".pom", ".jar", ".war", ".asc", ".md5", ".sha1"}的文件
 * <p>
 * Copyright admin
 *
 * @author admin
 * @date 2021-01-19 20:05 星期二
 */
public class MavenRepositoryCleaner {
    /**
     * 注意：MAVEN_REPOSITORY_PATH 的值需要替换成你自己的maven本地仓库地址
     */
    private static final String MAVEN_REPOSITORY_PATH = "D:\\maven_repository";
    private static final String[] SUFFIX = {".pom", ".jar", ".war", ".asc", ".md5", ".sha1"};
    private static List<File> willDeleteFiles = new ArrayList<>();
    private static int deletedEmptyDirectoryCount = 1;
    private static int deletedFileCount = 1;

    public static void main(String[] args) {
        File mavenRep = new File(MAVEN_REPOSITORY_PATH);
        if (!mavenRep.exists()) {
            System.out.println("Maven repos is not exist.");
            return;
        }
        File[] files = mavenRep.listFiles();
        traverse(files);
        System.out.println("将删除" + willDeleteFiles.size() + "个文件");
        checkAndDeleteFiles();
        System.out.println("清理完成~~~");
    }

    private static void traverse(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                //如果file是目录
                if (file.listFiles().length == 0) {
                    // file是空文件夹
                    System.out.println("删除第" + deletedEmptyDirectoryCount++ + "个空目录>>>" + file.getAbsolutePath());
                    file.delete();
                } else {
                    // file是非空文件夹
                    traverse(file.listFiles());
                }
            } else {
                //如果file是文件
                //文件名中不包含SUFFIX中的任意一个就添加到willDeleteFiles
                if (Stream.of(SUFFIX).noneMatch(file.getName()::contains)) {
                    willDeleteFiles.add(file);
                }
            }
        }
    }

    private static void checkAndDeleteFiles() {
        for (File file : willDeleteFiles) {
            System.out.println("删除第" + deletedFileCount++ + "个文件>>>" + file.getAbsolutePath());
            file.delete();
        }
    }
}

