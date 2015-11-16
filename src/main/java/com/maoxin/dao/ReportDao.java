package com.maoxin.dao;

import com.maoxin.model.Deduct;
import com.maoxin.model.Report;
import com.maoxin.util.BeanFactory;
import com.maoxin.util.Constant;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Repository("reportDao")
public class ReportDao extends BaseJdbcDao {
    public List<Report> getAllReport(){
        List<Report> reports = new ArrayList<Report>();
        String sql = "SELECT s.`staff_name`,s.`idcard_no`,c.`company_name`,YEAR(d.create_time) AS dyear,MONTH(d.create_time) dmonth,SUM(money) AS totalDeduct FROM deduct d " +
                "LEFT JOIN staff s ON d.`staffId` = s.`id` LEFT JOIN company c ON s.`companyId`=c.`id` " +
                "WHERE d.`isdelete`=0 AND s.`isdelete`=0 GROUP BY YEAR(d.create_time),MONTH(d.create_time),staffId";
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        while (rs.next()){
            Report report = BeanFactory.buildReport(rs);
            reports.add(report);
        }
        return reports;
    }

    public List<Report> queryAllReport(boolean isAllCompany,boolean isAllYear,boolean isAllMonth,String companyStr,String yearStr,String monthStr,String staffName){
        List<Report> reports = new ArrayList<Report>();
        String sql = "SELECT s.`staff_name`,s.`idcard_no`,c.`company_name`,YEAR(d.create_time) AS dyear,MONTH(d.create_time) dmonth,SUM(money) AS totalDeduct FROM deduct d " +
                "LEFT JOIN staff s ON d.`staffId` = s.`id` " +
                "LEFT JOIN company c ON s.`companyId`=c.`id` " +
                "WHERE s.isdelete=0 and d.isdelete=0 ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (! isAllCompany){
            sql += "AND c.`id` IN (" + companyStr + ") ";
        }
        if (! isAllYear){
            sql += "AND YEAR(d.create_time) IN (" + yearStr + ") ";
        }
        if (! isAllMonth){
            sql += "AND MONTH(d.create_time) IN (" + monthStr + ") ";
        }
        if (!StringUtils.isEmpty(staffName)){
            sql += " AND s.`staff_name` LIKE :staffName ";
        }
        sql += "GROUP BY YEAR(d.create_time),MONTH(d.create_time),staffId";

        params.addValue("staffName","%"+staffName+"%");
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        while (rs.next()){
            Report report = BeanFactory.buildReport(rs);
            reports.add(report);
        }
        return reports;
    }
}
