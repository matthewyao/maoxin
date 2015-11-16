package com.maoxin.service;

import com.maoxin.dao.ManagerDao;
import com.maoxin.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Service("managerService")
public class ManagerService {
    @Autowired
    private ManagerDao managerDao;

    public List<Manager> queryAllManager() {
        return this.managerDao.getAllManager();
    }

    public boolean saveManager(Manager manager){
        boolean isManagerExist = this.managerDao.isManagerExist(manager.getUsername());
        if (isManagerExist) return false;
        int affectRows = this.managerDao.addManager(manager);
        if (affectRows < 1) return false;
        return true;
    }

    public boolean deleteManager(int id){
        int affectRows = this.managerDao.deleteManager(id);
        if (affectRows < 1) return false;
        return true;
    }

    public int login(Manager manager){
        return this.managerDao.loginValid(manager);
    }

    public int getStaffId(int managerId){
        return this.managerDao.queryStaffId(managerId);
    }
}
