package com.maoxin.service;

import com.maoxin.dao.DeductDao;
import com.maoxin.model.Deduct;
import com.maoxin.model.Staff;
import com.maoxin.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Service("deductService")
public class DeductService {
    @Autowired
    private DeductDao deductDao;

    public Deduct queryDeduct(int id){
        return this.deductDao.queryDeduct(id);
    }


    public List<Deduct> queryAllDeduct(){
        return this.deductDao.getAllDeduct();
    }

    public int addDeduct(Deduct deduct){
        return this.deductDao.saveDeduct(deduct);
    }

    public boolean modifyDeduct(Deduct deduct){
        int affectRows = this.deductDao.updateDeduct(deduct);
        if (affectRows < 1) return false;
        return true;
    }

    public boolean deleteDeduct(int id,int staffId){
        int affectRows = this.deductDao.deleteDeduct(id, staffId);
        //删除由原提成而导致的提成
        this.deductDao.deleteRelativeDeduct(id,staffId);
        if (affectRows < 1) return false;
        return true;
    }

    public List<Deduct> searchDeduct(int companyId,String year,String month,String staffName){
        return this.deductDao.searchDeduct(companyId, year,month, staffName);
    }

    public int getRecommendId(int sid,int level){
        if (level == Constant.RECOMMEND_LEVEL_FIRST){
            return this.deductDao.queryFirstRecommendId(sid);
        } else if (level == Constant.RECOMMEND_LEVEL_SECOND){
            return this.deductDao.querySecondRecommendId(sid);
        } else {
            return this.deductDao.queryThirdRecommendId(sid);
        }
    }

    public void modifyOtherDeduct(int originDid,double money){
        this.deductDao.modifyRelateDeduct(originDid,money);
    }
}
