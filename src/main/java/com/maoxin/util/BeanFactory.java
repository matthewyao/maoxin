package com.maoxin.util;

import com.maoxin.model.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Created by matthewyao on 2015/9/19.
 */
public class BeanFactory {
    public static Manager buildManager(SqlRowSet rs){
        Manager manager = new Manager();
        manager.setId(rs.getInt("id"));
        manager.setUsername(rs.getString("username"));
        manager.setPassword(rs.getString("password"));
        manager.setStaffName(rs.getString("staff_name"));
        manager.setCreateTime(SuperDate.formatTimestamp(rs.getTimestamp("create_time")));
        manager.setIsDeleted(rs.getInt("isdeleted") == Constant.IS_DELETED);
        manager.setIdCardNo(rs.getString("idcard_no"));
        return manager;
    }

    public static Company buildCompany(SqlRowSet rs){
        Company company = new Company();
        company.setId(rs.getInt("id"));
        company.setCompanyName(rs.getString("company_name"));
        company.setInChargeId(rs.getInt("incharge_id"));
        company.setInChargeName(rs.getString("username"));
        company.setCreateTime(SuperDate.formatTimestamp(rs.getTimestamp("create_time")));
        company.setIsDeleted(rs.getInt("isdelete") == Constant.IS_DELETED);
        return company;
    }

    public static Staff buildStaff(SqlRowSet rs){
        Staff staff = new Staff();
        staff.setId(rs.getInt("id"));
        staff.setStaffName(rs.getString("staff_name"));
        staff.setIdCardNo(rs.getString("idcard_no"));
        staff.setTel(rs.getString("tel"));
        staff.setReCommendId(rs.getInt("recommendid"));
        staff.setRecommendName(rs.getString("recommendName"));
        staff.setCompanyId(rs.getInt("companyid"));
        staff.setCompanyName(rs.getString("company_name"));
        staff.setCreateTime(SuperDate.formatTimestamp(rs.getTimestamp("create_time")));
        staff.setIsDelete(rs.getInt("isdelete") == Constant.IS_DELETED);
        if (rs.getTimestamp("lefttime") == null){
            staff.setLeftTime("");
        } else {
            staff.setLeftTime(SuperDate.formatTimestamp(rs.getTimestamp("lefttime")));
        }
        return staff;
    }

    public static Deduct buildDeduct(SqlRowSet rs){
        Deduct deduct = new Deduct();
        deduct.setId(rs.getInt("id"));
        deduct.setMoney(rs.getDouble("money"));
        deduct.setComment(rs.getString("comment"));
        deduct.setStaffId(rs.getInt("staffid"));
        deduct.setStaffName(rs.getString("staff_name"));
        deduct.setDeduct_level(rs.getInt("deduct_level"));
        deduct.setCreateTime(SuperDate.formatDate(rs.getDate("create_time")));
        deduct.setIsDelete(rs.getInt("isdelete") == Constant.IS_DELETED);
        deduct.setManagerStaffId(rs.getInt("managerId"));
        deduct.setManagerName(rs.getString("username"));
        deduct.setIdCardNo(rs.getString("idcard_no"));
        return deduct;
    }

    public static Report buildReport(SqlRowSet rs){
        Report report = new Report();
        report.setCompanyName(rs.getString("company_name"));
        report.setStaffName(rs.getString("staff_name"));
        report.setIdCardNo(rs.getString("idcard_no"));
        report.setYear(rs.getString("dyear"));
        report.setMonth(rs.getString("dmonth"));
        report.setDeduct(rs.getDouble("totalDeduct"));
        return report;
    }
}
