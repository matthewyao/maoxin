package com.maoxin.controller;

import com.maoxin.model.Company;
import com.maoxin.model.Deduct;
import com.maoxin.model.ResultResponse;
import com.maoxin.model.Staff;
import com.maoxin.service.CompanyService;
import com.maoxin.service.DeductService;
import com.maoxin.service.StaffService;
import com.maoxin.util.Constant;
import com.maoxin.util.SuperDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created by matthewyao on 2015/9/19.
 */
@Controller
@RequestMapping("/deduct")
public class DeductController {
    @Autowired
    private DeductService deductService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ModelAndView allDeduct(){
        ModelAndView mv = new ModelAndView("deduct_list");
        List<Deduct> deductList = this.deductService.queryAllDeduct();
        List<Company> companyList = this.companyService.queryAllCompany();

        mv.addObject("companyList",companyList);
        mv.addObject("deductList",deductList);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView("deduct_add");
        List<Staff> staffList = this.staffService.queryAllStaff();
        String curDate = SuperDate.formatDate(new Date());

        mv.addObject("staffList",staffList);
        mv.addObject("curDate",curDate);
        mv.addObject("act", Constant.ACT_NEW);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add/{id}",method = RequestMethod.GET)
    public ModelAndView modifyDeduct(@PathVariable int id){
        ModelAndView mv = new ModelAndView("deduct_add");
        Deduct deduct = this.deductService.queryDeduct(id);
        List<Staff> staffList = this.staffService.queryAllStaff();

        mv.addObject("deduct",deduct);
        mv.addObject("staffList",staffList);
        mv.addObject("act", Constant.ACT_MODIFY);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultResponse<Object> addStaff(@RequestBody Deduct deduct,HttpSession session) {
        ResultResponse<Object> result = new ResultResponse<Object>();
        deduct.setManagerStaffId(Integer.parseInt(session.getAttribute("staffId").toString()));
        double oMoney = deduct.getMoney();
        String staffName = this.staffService.queryStaff(deduct.getStaffId()).getStaffName();
        deduct.setStaffName(staffName);
        deduct.setDeduct_level(Constant.DEFAULT_DEDUCT_LEVEL);
        int osid = this.deductService.addDeduct(deduct);
        //为第一级推荐人设置奖励
        int fsid = this.deductService.getRecommendId(osid, Constant.RECOMMEND_LEVEL_FIRST);

        if (fsid != 0){
            deduct.setMoney(oMoney * Constant.RECOMMEND_FIRST_RATIO);
            deduct.setComment(Constant.CHINESE_FIRST + deduct.getStaffName() + Constant.CHINESE_AT + deduct.getCreateTime() + Constant.CHINESE_AWARD);
            deduct.setStaffId(fsid);
            deduct.setOriginId(osid);
            deduct.setDeduct_level(Constant.FIRST_DEDUCT_LEVEL);
            this.deductService.addDeduct(deduct);
        }

        //为第二级推荐人设置奖励
        int ssid = this.deductService.getRecommendId(osid, Constant.RECOMMEND_LEVEL_SECOND);
        if (ssid != 0){
            deduct.setMoney(oMoney * Constant.RECOMMEND_SECOND_RATIO);
            deduct.setComment(Constant.CHINESE_SECOND + deduct.getStaffName() + Constant.CHINESE_AT + deduct.getCreateTime() + Constant.CHINESE_AWARD);
            deduct.setStaffId(ssid);
            deduct.setOriginId(osid);
            deduct.setDeduct_level(Constant.SECOND_DEDUCT_LEVEL);
            this.deductService.addDeduct(deduct);
        }

        //为第三级推荐人设置奖励
        int tsid = this.deductService.getRecommendId(osid, Constant.RECOMMEND_LEVEL_THIRD);
        if (tsid != 0){
            deduct.setMoney(oMoney * Constant.RECOMMEND_SECOND_RATIO);
            deduct.setComment(Constant.CHINESE_THIRD + deduct.getStaffName() + Constant.CHINESE_AT + deduct.getCreateTime() + Constant.CHINESE_AWARD);
            deduct.setStaffId(tsid);
            deduct.setOriginId(osid);
            deduct.setDeduct_level(Constant.SECOND_DEDUCT_LEVEL);
            this.deductService.addDeduct(deduct);
        }

        result.setIsok(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/modify",method = RequestMethod.PUT)
    public ResultResponse<Object> modifyDeduct(@RequestBody Deduct deduct,HttpSession session){
        ResultResponse<Object> result = new ResultResponse<Object>();
        deduct.setManagerStaffId(Integer.parseInt(session.getAttribute("staffId").toString()));
        boolean success = this.deductService.modifyDeduct(deduct);
        //修改1级提成需要同时修改对应提成
//        if (deduct.getDeduct_level() == Constant.DEFAULT_DEDUCT_LEVEL){
            this.deductService.modifyOtherDeduct(deduct.getId(),deduct.getMoney());
//        }
        result.setIsok(success);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResultResponse<Object> deleteManager(@PathVariable int id,HttpSession session){
        ResultResponse<Object> result = new ResultResponse<Object>();
        int staffId = Integer.parseInt(session.getAttribute("staffId").toString());
        boolean success = this.deductService.deleteDeduct(id, staffId);
        if (success){
            result.setIsok(true);
            result.setMessage("成功删除提成纪录！");
        } else {
            result.setIsok(false);
            result.setMessage("删除失败！");
        }
        return result;
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public ModelAndView searchStaff(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("deduct_list");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        int companyId = Integer.parseInt(request.getParameter("companyId"));
        String staffName = request.getParameter("staffName");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        mv.addObject("year",year);
        mv.addObject("month",month);

        List<Deduct> deductList = this.deductService.searchDeduct(companyId,year,month,staffName);
        List<Company> companyList = this.companyService.queryAllCompany();

        mv.addObject("deductList",deductList);
        mv.addObject("companyList",companyList);
        mv.addObject("companyId",companyId);
        mv.addObject("staffName",staffName);

        return mv;
    }
}
