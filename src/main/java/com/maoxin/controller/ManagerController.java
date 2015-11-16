package com.maoxin.controller;

import com.maoxin.model.Manager;
import com.maoxin.model.ResultResponse;
import com.maoxin.model.Staff;
import com.maoxin.service.ManagerService;
import com.maoxin.service.StaffService;
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
@RequestMapping("/manage")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private StaffService staffService;

    @ResponseBody
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ModelAndView queryAll(HttpSession session){
        ModelAndView mv = new ModelAndView();
        //仅超级管理员可以管理管理员
        if (session.getAttribute("username").equals(Constant.SUPER_MANAGER_NAME)){
            mv.setViewName("manager_list");
            List<Manager> managerList = this.managerService.queryAllManager();
            mv.addObject("managerList", managerList);
        } else {
            mv.setViewName("unauthorized");
        }
        return mv;
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView toAdd(HttpSession session){
        ModelAndView mv = new ModelAndView();
        if (session.getAttribute("username").equals(Constant.SUPER_MANAGER_NAME)){
            List<Staff> staffList = this.staffService.queryAllStaff();
            mv.addObject("staffList",staffList);
            mv.setViewName("manager_add");
        } else {
            mv.setViewName("unauthorized");
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultResponse<Object> addManager(@RequestBody Manager manager,HttpSession session){
        ResultResponse<Object> result = new ResultResponse<Object>();
        if (!session.getAttribute("username").equals(Constant.SUPER_MANAGER_NAME)){
            result.setIsok(false);
            result.setMessage("您无权限访问");
            return result;
        }
        boolean success = this.managerService.saveManager(manager);
        if (success){
            result.setIsok(true);
            result.setMessage("成功新增管理员:" + manager.getUsername());
        } else {
            result.setIsok(false);
            result.setMessage("用户名已存在！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResultResponse<Object> deleteManager(@PathVariable int id,HttpSession session){
        ResultResponse<Object> result = new ResultResponse<Object>();
        if (!session.getAttribute("username").equals(Constant.SUPER_MANAGER_NAME)){
            result.setIsok(false);
            result.setMessage("您无权限访问");
            return result;
        }
        boolean success = this.managerService.deleteManager(id);
        if (success){
            result.setIsok(true);
            result.setMessage("成功删除管理员！");
        } else {
            result.setIsok(false);
            result.setMessage("删除失败！");
        }
        return result;
    }
}
