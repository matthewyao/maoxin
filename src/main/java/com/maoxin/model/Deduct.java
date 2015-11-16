package com.maoxin.model;

/**
 * Created by matthewyao on 2015/9/19.
 */
public class Deduct {
    private int id;
    private double money;
    private String comment;
    private int staffId;
    private String createTime;
    private boolean isDelete;
    private String staffName;
    private int deduct_level;
    private int managerStaffId;
    private int originId;
    private String managerName;
    private String idCardNo;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public int getManagerStaffId() {
        return managerStaffId;
    }

    public void setManagerStaffId(int managerStaffId) {
        this.managerStaffId = managerStaffId;
    }

    public int getDeduct_level() {
        return deduct_level;
    }

    public void setDeduct_level(int deduct_level) {
        this.deduct_level = deduct_level;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
