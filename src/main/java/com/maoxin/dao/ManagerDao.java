package com.maoxin.dao;

import com.maoxin.model.Manager;
import com.maoxin.util.BeanFactory;
import com.maoxin.util.Constant;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Repository("managerDao")
public class ManagerDao extends BaseJdbcDao{
    public List<Manager> getAllManager(){
        List<Manager> managers = new ArrayList<Manager>();
        String sql = "SELECT m.*,s.`staff_name`,s.idcard_no FROM manager m LEFT JOIN staff s ON m.`staffid`=s.`id` WHERE m.username!=:username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username",Constant.SUPER_MANAGER_NAME);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        while (rs != null && rs.next()){
            Manager manager = BeanFactory.buildManager(rs);
            managers.add(manager);
        }
        return managers;
    }

    public int addManager(Manager manager){
        String sql = "INSERT INTO manager ( `username`, `password`, `create_time`, `isdeleted`, `staffid`) VALUES (:username, :password, NOW(), :isdelete, :staffId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", manager.getUsername());
        params.addValue("password",manager.getPassword());
        params.addValue("isdelete", Constant.NOT_DELETED);
        params.addValue("staffId",manager.getStaffId());
        return this.namedJdbcTemplate.update(sql,params);
    }

    public boolean isManagerExist(String username){
        String sql = "SELECT * FROM manager WHERE username=:username";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username",username);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        if (rs != null && rs.next())
            return true;
        else return false;
    }

    public int deleteManager(int id){
        String sql = "DELETE FROM manager WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",id);
        return this.namedJdbcTemplate.update(sql,params);
    }

    public int loginValid(Manager manager){
        int staffId = -1;
        String sql ="SELECT * FROM manager WHERE username=:username AND `password`=:password";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", manager.getUsername());
        params.addValue("password", manager.getPassword());
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql, params);
        if(rs != null && rs.next()){
            staffId = rs.getInt("staffid");
        }
        return staffId;
    }

    public int queryStaffId(int managerId){
        int staffId = 0;
        String sql = "SELECT staffid FROM manager WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",managerId);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql, params);
        if (rs != null && rs.next()) staffId = rs.getInt("staffid");
        return  staffId;
    }
}
