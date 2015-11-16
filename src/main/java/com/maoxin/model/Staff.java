package com.maoxin.model;

/**
 * Created by matthewyao on 2015/9/19.
 */
public class Staff {
//    "{"staffName":"d","idCardNo":"jjjjjjjjjjjjjjjjjj","tel":"jj","recommendName":"0","companyName":"1"}"
    private int id;
    private String staffName;
    private String idCardNo;
    private String tel;
    private int reCommendId;
    private String recommendName;
    private int companyId;
    private String companyName;
    private String createTime;
    private boolean isDelete;
    private String leftTime;

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getReCommendId() {
        return reCommendId;
    }

    public void setReCommendId(int reCommendId) {
        this.reCommendId = reCommendId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }
}
