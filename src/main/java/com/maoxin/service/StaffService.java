package com.maoxin.service;

import com.maoxin.dao.StaffDao;
import com.maoxin.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Service("staffService")
public class StaffService {
    @Autowired
    private StaffDao staffDao;

    public List<Staff> queryAllStaff(){
        return this.staffDao.getAllStaff();
    }

    public Staff queryStaff(int id){
        return this.staffDao.queryStaff(id);
    }

    public List<Staff> searchStaff(int companyId,String staffName,boolean showDelete){
        return this.staffDao.searchStaff(companyId, staffName, showDelete);
    }

    public boolean addStaff(Staff staff){
        int affectRows = this.staffDao.saveStaff(staff);
        if (affectRows < 1) return false;
        return true;
    }

    public boolean changeStatus(int id,int status){
        int affectRows = this.staffDao.changeStatus(id, status);
        if (affectRows < 1) return false;
        return true;
    }

    public boolean modifyStaff(Staff staff){
        int affectRows = this.staffDao.updateStaff(staff);
        if (affectRows < 1) return false;
        return true;
    }

    public boolean queryIdNo(String idCardNo){
        return this.staffDao.queryIdNo(idCardNo);
    }
}
