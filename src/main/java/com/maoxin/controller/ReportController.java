package com.maoxin.controller;

import com.maoxin.model.Company;
import com.maoxin.model.Report;
import com.maoxin.service.CompanyService;
import com.maoxin.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthewyao on 2015/10/6.
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ModelAndView allReport(){
        ModelAndView mv = new ModelAndView("report_list");
        List<Report> reportList = this.reportService.queryAllReport();
        List<Company> companyList = this.companyService.queryAllCompany();
        mv.addObject("reportList",reportList);
        mv.addObject("companyList",companyList);
        return mv;
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ModelAndView queryReport(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("report_list");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        List<String> companyIds = arrayToList(request.getParameterValues("companyId"));
        List<String> yearIds = arrayToList(request.getParameterValues("year"));
        List<String> monthIds = arrayToList(request.getParameterValues("month"));
        String staffName = request.getParameter("staffName");

        List<Report> reportList = this.reportService.queryReport(companyIds, yearIds, monthIds,staffName);
        List<Company> companyList = this.companyService.queryAllCompany();
        mv.addObject("reportList",reportList);
        mv.addObject("companyList",companyList);
        return mv;
    }

    private List<String> arrayToList(String[] ids){
        List<String> idList = new ArrayList<String>();
        if (ids == null) return idList;
        for (String id : ids){
            idList.add(id);
        }
        return idList;
    }
}
