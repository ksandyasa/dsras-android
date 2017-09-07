package com.bpom.dsras.object;

/**
 * Created by apridosandyasa on 9/13/16.
 */
public class ReportFiles {

    private String fileName;
    private String filePath;
    private String fileType;

    public ReportFiles() {

    }

    public ReportFiles(String name, String path, String type) {
        this.fileName = name;
        this.filePath = path;
        this.fileType = type;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public void setFilePath(String path) {
        this.filePath = path;
    }

    public void setFileType(String type) {
        this.fileType = type;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getFileType() {
        return this.fileType;
    }

}
