package com.maoxin.controller;

import com.maoxin.model.Company;
import com.maoxin.model.ResultResponse;
import com.maoxin.model.Staff;
import com.maoxin.service.CompanyService;
import com.maoxin.service.StaffService;
import com.maoxin.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ModelAndView allStaff(){
        ModelAndView mv = new ModelAndView("staff_list");
        List<Staff> staffList = this.staffService.queryAllStaff();
        mv.addObject("staffList",staffList);
        List<Company> companyList = this.companyService.queryAllCompany();
        mv.addObject("companyList",companyList);
        return mv;
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ModelAndView searchStaff(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("staff_list");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        int companyId = Integer.parseInt(request.getParameter("companyId"));
        String staffName = request.getParameter("staffName");
        boolean showDelete;
        try {
            showDelete = request.getParameter("showDelete").equals("1");
        } catch (Exception e){
            showDelete = false;
        }
        List<Staff> staffList = this.staffService.searchStaff(companyId, staffName, showDelete);
        List<Company> companyList = this.companyService.queryAllCompany();

        mv.addObject("staffList",staffList);
        mv.addObject("companyList",companyList);
        mv.addObject("companyId",companyId);
        mv.addObject("staffName",staffName);
        mv.addObject("showDelete",showDelete);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView toAdd(HttpSession session){
        ModelAndView mv = new ModelAndView("staff_add");
        List<Staff> staffList = this.staffService.queryAllStaff();
        List<Company> companyList = this.companyService.queryAllCompany();
        int staffId = Integer.parseInt(session.getAttribute("staffId").toString());
        this.removeSelf(staffList, staffId);
        mv.addObject("staffList",staffList);
        mv.addObject("companyList",companyList);
        mv.addObject("act",Constant.ACT_NEW);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add/{id}",method = RequestMethod.GET)
    public ModelAndView modifyStaff(@PathVariable int id,HttpSession session){
        ModelAndView mv = new ModelAndView("staff_add");
        Staff staff = this.staffService.queryStaff(id);
        List<Company> companyList = this.companyService.queryAllCompany();
        List<Staff> staffList = this.staffService.queryAllStaff();
        int staffId = Integer.parseInt(session.getAttribute("staffId").toString());
        this.removeSelf(staffList, staffId);

        mv.addObject("staff", staff);
        mv.addObject("companyList",companyList);
        mv.addObject("staffList",staffList);
        mv.addObject("act", Constant.ACT_MODIFY);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultResponse<Object> addStaff(@RequestBody Staff staff) {
        ResultResponse<Object> result = new ResultResponse<Object>();
        boolean isIdCardNoExist = this.staffService.queryIdNo(staff.getIdCardNo());
        if (isIdCardNoExist){
            result.setIsok(false);
            return result;
        }
        boolean success = this.staffService.addStaff(staff);
        result.setIsok(success);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/status/{id}/{status}",method = RequestMethod.PUT)
    public ResultResponse<Object> changeStatus(@PathVariable int id,@PathVariable int status){
        ResultResponse<Object> result = new ResultResponse<Object>();
        //复职员工前要检查员工身份证是否重复
        Staff staff = this.staffService.queryStaff(id);
        if (status == Constant.NOT_DELETED){
            boolean isIdCardNoExist = this.staffService.queryIdNo(staff.getIdCardNo());
            if (isIdCardNoExist){
                result.setIsok(false);
                result.setMessage("ID card NO is already exist！");
                return result;
            }
        }
        boolean success = this.staffService.changeStatus(id, status);
        result.setIsok(success);
        result.setMessage("update success");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/modify",method = RequestMethod.PUT)
    public ResultResponse<Object> changeStatus(@RequestBody Staff staff){
        ResultResponse<Object> result = new ResultResponse<Object>();
        boolean success = this.staffService.modifyStaff(staff);
        result.setIsok(success);
        return result;
    }

    private void removeSelf(List<Staff> staffList,int sid){
        for (Staff s : staffList){
            if (s.getId() == sid){
                staffList.remove(s);
                return;
            }
        }
    }
}
