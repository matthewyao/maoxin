package com.maoxin.dao;

import com.maoxin.model.Company;
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
@Repository("companyDao")
public class CompanyDao extends BaseJdbcDao{
    public List<Company> getAllCompany(){
        List<Company> companys = new ArrayList<Company>();
        String sql = "SELECT c.*,m.`username` FROM company c LEFT JOIN manager m ON c.`incharge_id`=m.`id`";
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        while (rs.next()){
            Company company = BeanFactory.buildCompany(rs);
            companys.add(company);
        }
        return companys;
    }

    public int saveCompany(Company company){
        String sql = "INSERT INTO company (`company_name`, `incharge_id`, `create_time`, `isdelete`) VALUES (:companyName, :inChargeId, NOW(), :isDelete)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyName",company.getCompanyName());
        params.addValue("inChargeId",company.getInChargeId());
        params.addValue("isDelete", Constant.NOT_DELETED);
        return this.namedJdbcTemplate.update(sql,params);
    }

    public Company getCompanyById(int id){
        Company company = new Company();
        String sql = "SELECT c.*,m.`username` FROM company c LEFT JOIN manager m ON c.`incharge_id`=m.`id` WHERE c.id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",id);
        SqlRowSet rs = this.namedJdbcTemplate.queryForRowSet(sql,params);
        if (rs != null && rs.next()) company = BeanFactory.buildCompany(rs);
        return company;
    }

    public int updateCompany(Company company){
        String sql = "UPDATE company SET company_name=:companyName , incharge_id=:inChargeId WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("companyName",company.getCompanyName());
        params.addValue("inChargeId",company.getInChargeId());
        params.addValue("id",company.getId());
        return this.namedJdbcTemplate.update(sql,params);
    }
}
