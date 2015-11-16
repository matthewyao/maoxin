package com.maoxin.service;

import com.maoxin.dao.DeductDao;
import com.maoxin.dao.ReportDao;
import com.maoxin.model.Deduct;
import com.maoxin.model.Report;
import com.maoxin.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Service("reportService")
public class ReportService {
    @Autowired
    private ReportDao reportDao;

    public List<Report> queryAllReport(){
        return this.reportDao.getAllReport();
    }

    public List<Report> queryReport(List<String> companyIds,List<String> yearIds,List<String> monthIds,String staffName){
        boolean isAllCompany=false,isAllYear=false,isAllMonth=false;
        String companyStr="",yearStr="",monthStr="";
        if (companyIds.size() == 0) {
            isAllCompany = true;
        } else {
            for (String id : companyIds){
                companyStr += id + ",";
            }
            companyStr = companyStr.substring(0,companyStr.length()-1);
        }
        if (yearIds.size() == 0) {
            isAllYear = true;
        } else {
            for (String id : yearIds){
                yearStr += id + ",";
            }
            yearStr = yearStr.substring(0,yearStr.length()-1);
        }
        if (monthIds.size() == 0) {
            isAllMonth = true;
        } else {
            for (String id : monthIds){
                monthStr += id + ",";
            }
            monthStr = monthStr.substring(0,monthStr.length()-1);
        }
        return this.reportDao.queryAllReport(isAllCompany,isAllYear,isAllMonth,companyStr,yearStr,monthStr,staffName);
    }

}
