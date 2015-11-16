package com.maoxin.model;

/**
 * Created by matthewyao on 2015/9/17.
 */
public class Company {
    private int id;
    private String companyName;
    private int inChargeId;
    private String inChargeName;
    private String createTime;
    private boolean isDeleted;

    public String getInChargeName() {
        return inChargeName;
    }

    public void setInChargeName(String inChargeName) {
        this.inChargeName = inChargeName;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public int getInChargeId() { return inChargeId; }

    public void setInChargeId(int inChargeId) { this.inChargeId = inChargeId; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public boolean getIsDeleted() { return isDeleted; }

    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
}
