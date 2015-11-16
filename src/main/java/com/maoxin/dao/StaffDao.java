package com.maoxin.dao;

import com.maoxin.model.Staff;
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
@Repository("staffDao")
public class StaffDao extends BaseJdbcDao {
    public List<Staff> getAllStaff(){
        List<Staff> staffs = new ArrayList<Staff>();
        String sql = "SELECT s1.*,s2.`staff_name` AS recommendName,c.`company_name` FROM staff s1 " +
                "LEFT JOIN staff s2 ON s1.`recommendId`=s2.`id` " +
                "LEFT JOIN company c ON s1.`companyId`=c.`id` where s1.isdelete="+Constant.NOT_DELETED;
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        while (rs.next()){
            Staff staff = BeanFactory.buildStaff(rs);
            staffs.add(staff);
        }
        return staffs;
    }

    public Staff queryStaff(int id){
        Staff staff = new Staff();
        String sql = "SELECT s1.*,s2.`staff_name` AS recommendName,c.`company_name` FROM staff s1 " +
                "LEFT JOIN staff s2 ON s1.`recommendId`=s2.`id` " +
                "LEFT JOIN company c ON s1.`companyId`=c.`id` where s1.id=:id ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",id);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        if (rs!=null && rs.next()) staff = BeanFactory.buildStaff(rs);
        return staff;
    }

    public List<Staff> searchStaff(int companyId,String staffName,boolean showDelete){
        List<Staff> staffs = new ArrayList<Staff>();

        String sql = "SELECT s1.*,s2.`staff_name` AS recommendName,c.`company_name` FROM staff s1 " +
                "LEFT JOIN staff s2 ON s1.`recommendId`=s2.`id` " +
                "LEFT JOIN company c ON s1.`companyId`=c.`id` where 1=1 ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (companyId != 0){
            sql += " AND c.id = :id";
            params.addValue("id",companyId);
        }
        if (!StringUtils.isEmpty(staffName)){
            staffName = '%' + staffName + "%";
            sql += " AND s1.staff_name like :staffName";
            params.addValue("staffName",staffName);
        }
        if (!showDelete){
            sql += " AND s1.isdelete=" + Constant.NOT_DELETED;
        }
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        while (rs.next()){
            Staff staff = BeanFactory.buildStaff(rs);
            staffs.add(staff);
        }
        return staffs;
    }

    public int saveStaff(Staff staff){
        String sql ="INSERT INTO staff (`staff_name`, `idcard_no`, `tel`, `recommendId`, `companyId`, `create_time`, `isdelete`) " +
                "VALUES (:staffName, :idCardNo, :tel, :recommendId, :companyId, NOW(), :isdelete)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("staffName",staff.getStaffName());
        params.addValue("idCardNo",staff.getIdCardNo());
        params.addValue("tel",staff.getTel());
        params.addValue("recommendId",staff.getReCommendId());
        params.addValue("companyId",staff.getCompanyId());
        params.addValue("isdelete", Constant.NOT_DELETED);
        return this.namedJdbcTemplate.update(sql,params);
    }

    public int changeStatus(int id,int status){
        String sql;
        if (status == Constant.IS_DELETED){
            sql = "UPDATE staff SET isdelete=:status , lefttime = NOW() WHERE id=:id";
        } else {
            sql = "UPDATE staff SET isdelete=:status , lefttime = NULL WHERE id=:id";
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status",status);
        params.addValue("id",id);
        return this.namedJdbcTemplate.update(sql,params);
    }

    public int updateStaff(Staff staff){
        String sql = "UPDATE staff SET  `staff_name` = :staffName, `idcard_no` = :idCardNo, `tel` = :tel, `recommendId` = :reCommendId, `companyId` = :companyId WHERE `id` = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("staffName",staff.getStaffName());
        params.addValue("idCardNo",staff.getIdCardNo());
        params.addValue("tel",staff.getTel());
        params.addValue("reCommendId",staff.getReCommendId());
        params.addValue("companyId",staff.getCompanyId());
        params.addValue("id",staff.getId());
        return this.namedJdbcTemplate.update(sql,params);
    }

    public boolean queryIdNo(String idCardNo){
        boolean exist = false;
        String sql = "SELECT * FROM staff WHERE idcard_no = :idCardNo AND isdelete = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idCardNo",idCardNo);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        if (rs != null && rs.next()){
            exist = true;
        }
        return  exist;
    }
}
