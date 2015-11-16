package com.maoxin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by matthewyao on 2015/9/19.
 */
public class BaseJdbcDao {
    protected JdbcTemplate jdbcTemplate;
    protected NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSourceDefault") DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
