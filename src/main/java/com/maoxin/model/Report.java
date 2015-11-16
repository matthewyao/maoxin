package com.maoxin.model;

/**
 * Created by matthewyao on 2015/10/6.
 */
public class Report {
    private String companyName;
    private String staffName;
    private String year;
    private String month;
    private double deduct;
    private String idCardNo;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getDeduct() {
        return deduct;
    }

    public void setDeduct(double deduct) {
        this.deduct = deduct;
    }
}
