package com.maoxin.service;

import com.maoxin.dao.CompanyDao;
import com.maoxin.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Service("companyService")
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public List<Company> queryAllCompany() {
        return this.companyDao.getAllCompany();
    }

    public boolean addCompany(Company company){
        int affectRows = this.companyDao.saveCompany(company);
        if (affectRows < 1) return false;
        return true;
    }

    public Company queryCompany(int id){
        return this.companyDao.getCompanyById(id);
    }

    public boolean updateCompany(Company company){
        int affectRows = this.companyDao.updateCompany(company);
        if (affectRows < 1) return false;
        return true;
    }
}
