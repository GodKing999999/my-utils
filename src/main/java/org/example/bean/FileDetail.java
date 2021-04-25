package org.example.bean;

import lombok.Data;

import java.io.File;

/**
 * @author admin
 * @date 2021-04-25 19:35 星期日
 */
@Data
public class FileDetail {
    private File file;

    /**
     * 文件总行数
     */
    private int totalLine;

    /**
     * 空行数
     */
    private int spaceLine;

    /**
     * 注释行数
     */
    private int commentLine;

    /**
     * 代码行数
     */
    private int codeLine;

    public int getCodeLine() {
        setCodeLine(totalLine - spaceLine - commentLine);
        return codeLine;
    }

    @Override
    public String toString() {
        return String.format("{文件名:%s,总行数=%s行,空行数=%s行,注释行数=%s行,代码行数=%s行}", getFile().getAbsolutePath(),
                getTotalLine(), getSpaceLine(), getCommentLine(), getCodeLine());
    }
}
