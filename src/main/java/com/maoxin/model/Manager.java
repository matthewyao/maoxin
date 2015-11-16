package com.maoxin.model;

/**
 * Created by matthewyao on 2015/9/17.
 */
public class Manager {
    private int id;
    private String username;
    private String password;
    private String createTime;
    private boolean isDeleted;
    private int staffId;
    private String staffName;
    private String idCardNo;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public boolean getIsDeleted() { return isDeleted; }

    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
}
