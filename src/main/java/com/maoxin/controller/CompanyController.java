package com.maoxin.controller;

import com.maoxin.model.Company;
import com.maoxin.model.Manager;
import com.maoxin.model.ResultResponse;
import com.maoxin.service.CompanyService;
import com.maoxin.service.ManagerService;
import com.maoxin.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ManagerService managerService;

    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ModelAndView queryAll(HttpSession session){
        ModelAndView mv = new ModelAndView("company_list");
        List<Company> companyList = this.companyService.queryAllCompany();
        mv.addObject("companyList",companyList);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView("company_add");
        List<Manager> managerList = this.managerService.queryAllManager();
        mv.addObject("managerList",managerList);
        mv.addObject("act",Constant.ACT_NEW);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add/{id}",method = RequestMethod.GET)
    public ModelAndView modifyCompany(@PathVariable int id){
        ModelAndView mv = new ModelAndView("company_add");
        Company company = this.companyService.queryCompany(id);
        mv.addObject("company",company);
        List<Manager> managerList = this.managerService.queryAllManager();
        mv.addObject("managerList",managerList);
        mv.addObject("act",Constant.ACT_MODIFY);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultResponse<Object> addCompany(@RequestBody Company company){
        ResultResponse<Object> result = new ResultResponse<Object>();
        boolean success = this.companyService.addCompany(company);
        result.setIsok(success);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/modify",method = RequestMethod.PUT)
    public ResultResponse<Object> modifyCompany(@RequestBody Company company){
        ResultResponse<Object> result = new ResultResponse<Object>();
        boolean success = this.companyService.updateCompany(company);
        result.setIsok(success);
        return result;
    }
}
