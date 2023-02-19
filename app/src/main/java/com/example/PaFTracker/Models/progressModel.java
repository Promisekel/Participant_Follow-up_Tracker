package com.example.PaFTracker.Models;

public class progressModel {
    String uid, sheetId, projectName, pid;

    public progressModel() {
    }

    public progressModel(String uid, String sheetId, String projectName, String pid) {
        this.uid = uid;
        this.sheetId = sheetId;
        this.projectName = projectName;
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
