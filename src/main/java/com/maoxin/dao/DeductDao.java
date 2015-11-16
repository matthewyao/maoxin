package com.maoxin.dao;

import com.maoxin.model.Deduct;
import com.maoxin.model.Staff;
import com.maoxin.util.BeanFactory;
import com.maoxin.util.Constant;
import com.maoxin.util.SuperDate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Repository("deductDao")
public class DeductDao extends BaseJdbcDao {
    public List<Deduct> getAllDeduct(){
        List<Deduct> deducts = new ArrayList<Deduct>();
        String sql = "SELECT d.*,s1.`staff_name`,s1.`idcard_no`,m.`username` FROM deduct d " +
                "LEFT JOIN staff s1 ON d.`staffId`=s1.`id` " +
                "LEFT JOIN manager m ON d.`managerId`=m.`staffid` WHERE d.isdelete=0 AND s1.isdelete=0 " +
                "ORDER BY d.id DESC;";
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        while (rs.next()){
            Deduct deduct = BeanFactory.buildDeduct(rs);
            deducts.add(deduct);
        }
        return deducts;
    }

    public List<Deduct> searchDeduct(int companyId,String year,String month,String staffName){
        List<Deduct> deductList = new ArrayList<Deduct>();
        String sql = "SELECT d.*,s.`staff_name`,s.`idcard_no`,m.`username` FROM deduct d LEFT JOIN staff s ON d.`staffId`=s.`id` " +
                "LEFT JOIN manager m ON d.`managerId`=m.`staffid` where d.isdelete=0 and s.isdelete=0 ";
        if (companyId != 0){
            sql += " AND s.`companyId`=:companyId";
        }
        if (!StringUtils.isEmpty(staffName)){
            sql += " AND s.`staff_name` LIKE :staffName";
        }
        if (!year.equals("0")){
            sql += " AND YEAR(d.create_time) = " + year;
        }
        if (!month.equals("0")){
            sql += " AND MONTH(d.create_time) = " + month;
        }

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyId",companyId);
        params.addValue("staffName","%"+staffName+"%");
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        while (rs.next()){
            Deduct deduct = BeanFactory.buildDeduct(rs);
            deductList.add(deduct);
        }
        return deductList;
    }

    public int saveDeduct(Deduct deduct){
        String sql ="INSERT INTO deduct (`money`, `comment`, `staffId`, `create_time`, `isdelete`, `originId`, `deduct_level`, `managerId`) " +
                "VALUES (:money, :comment, :staffId, :createTime, :isDelete, :originId, :deductLevel, :managerId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("money",deduct.getMoney());
        params.addValue("comment",deduct.getComment());
        params.addValue("staffId",deduct.getStaffId());
        params.addValue("createTime",deduct.getCreateTime());
        params.addValue("isDelete",Constant.NOT_DELETED);
        params.addValue("originId",deduct.getOriginId());
        params.addValue("deductLevel",deduct.getDeduct_level());
        params.addValue("managerId",deduct.getManagerStaffId());
        int affectRows = this.namedJdbcTemplate.update(sql, params);
        sql = "SELECT MAX(id) mid FROM deduct";
        int did = 0;
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        if (rs != null && rs.next()) did = rs.getInt("mid");
        return did;
    }

    public Deduct queryDeduct(int id){
        Deduct deduct = new Deduct();
        String sql = "SELECT d.*,s.`staff_name`,s.`idcard_no`,m.`username` FROM deduct d " +
                "LEFT JOIN staff s ON d.`staffId`=s.`id` " +
                "LEFT JOIN manager m ON d.`managerId`=m.`staffid` where d.id=:id ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",id);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        if (rs!=null && rs.next()) deduct = BeanFactory.buildDeduct(rs);
        return deduct;
    }

    public int updateDeduct(Deduct deduct){
        String sql = "UPDATE `maoxin`.`deduct` SET `money` = :money, `comment` = :comment, `staffId` = :staffId, `create_time` = :createTime, `isdelete` = :isDelete, `managerId` = :managerId WHERE `id` = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("money",deduct.getMoney());
        params.addValue("comment",deduct.getComment());
        params.addValue("staffId",deduct.getStaffId());
        params.addValue("createTime",deduct.getCreateTime());
        params.addValue("isDelete",deduct.getIsDelete());
        params.addValue("id",deduct.getId());
        params.addValue("managerId",deduct.getManagerStaffId());
        return this.namedJdbcTemplate.update(sql,params);
    }

    public int deleteDeduct(int id,int managerId){
        String sql = "UPDATE deduct SET isdelete = :isDelete , managerId = :managerId WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("managerId",managerId);
        params.addValue("isDelete",Constant.IS_DELETED);
        params.addValue("id",id);
        return this.namedJdbcTemplate.update(sql,params);
    }

    public int deleteRelativeDeduct(int id,int managerId){
        String sql = "UPDATE deduct SET isdelete = :isDelete , managerId = :managerId WHERE originid=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("managerId",managerId);
        params.addValue("isDelete",Constant.IS_DELETED);
        params.addValue("id",id);
        return this.namedJdbcTemplate.update(sql,params);
    }

    //search first recommend id
    public int queryFirstRecommendId(int sid){
        String sql = "SELECT IFNULL(s.recommendId,0) AS rid FROM staff s " +
                "LEFT JOIN staff s1 ON s.`recommendId`=s1.`id` " +
                "WHERE s.id=(SELECT staffId FROM deduct WHERE id=:id) AND s1.isdelete = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",sid);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        int recommendId = 0;
        if (rs != null && rs.next()){
            recommendId = rs.getInt("rid");
        }
        return recommendId;
    }

    //search second recommend id
    public int querySecondRecommendId(int sid){
        String sql = "SELECT IFNULL(s2.recommendId,0) AS rid FROM staff s1 " +
                "INNER JOIN staff s2 ON s1.`recommendId`=s2.id " +
                "LEFT JOIN staff s3 ON s2.`recommendId`=s3.`id`" +
                "WHERE s1.id=(SELECT staffId FROM deduct WHERE id=:id) and s3.isdelete = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",sid);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        int recommendId = 0;
        if (rs != null && rs.next()){
            recommendId = rs.getInt("rid");
        }
        return recommendId;
    }

    //search third recommend id
    public int queryThirdRecommendId(int sid){
        String sql = "SELECT IFNULL(s3.recommendId,0) AS rid FROM staff s1 " +
                "INNER JOIN staff s2 ON s1.`recommendId`=s2.id " +
                "INNER JOIN staff s3 ON s2.`recommendId`=s3.`id` " +
                "LEFT JOIN staff s4 ON s3.`recommendId`=s4.`id`" +
                "WHERE s1.id=(SELECT staffId FROM deduct WHERE id=:id) AND s4.isdelete = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",sid);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        int recommendId = 0;
        if (rs != null && rs.next()){
            recommendId = rs.getInt("rid");
        }
        return recommendId;
    }

    public void modifyRelateDeduct(int odid,double money){
        String sql1 = "UPDATE deduct SET money = :money1 WHERE originId=:odid AND deduct_level=" + Constant.FIRST_DEDUCT_LEVEL;
        MapSqlParameterSource params1 = new MapSqlParameterSource();
        params1.addValue("money1",money * Constant.RECOMMEND_FIRST_RATIO);
        params1.addValue("odid",odid);
        this.namedJdbcTemplate.update(sql1,params1);

        String sql2 = "UPDATE deduct SET money = :money2 WHERE originId=:odid AND deduct_level=" + Constant.SECOND_DEDUCT_LEVEL;
        MapSqlParameterSource params2 = new MapSqlParameterSource();
        params2.addValue("money2",money * Constant.RECOMMEND_SECOND_RATIO);
        params2.addValue("odid", odid);
        this.namedJdbcTemplate.update(sql2,params2);
    }
}
