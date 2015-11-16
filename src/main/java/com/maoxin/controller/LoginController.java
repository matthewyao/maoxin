package com.maoxin.controller;

import com.maoxin.model.Manager;
import com.maoxin.model.ResultResponse;
import com.maoxin.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.InputStream;

/**
 * Created by matthewyao on 2015/9/17.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private ManagerService managerService;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultResponse<Object> login(@RequestBody Manager manager,HttpSession session){
        ResultResponse<Object> result = new ResultResponse<Object>();
        int staffId = this.managerService.login(manager);
        if (staffId != -1){
            session.setMaxInactiveInterval(-1);
            session.setAttribute("staffId",staffId);
            session.setAttribute("username",manager.getUsername());
            result.setIsok(true);
            result.setMessage("登录成功");
        } else {
            result.setIsok(false);
            result.setMessage("登录失败");
        }
        return result;
    }
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session){
        ModelAndView mv = new ModelAndView("login");
        session.removeAttribute("staffId");
        session.removeAttribute("username");
        return mv;
    }
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public ModelAndView home(HttpSession session){
        ModelAndView mv = new ModelAndView("home");
        String username = session.getAttribute("username").toString();
        mv.addObject("username",username);
        return mv;
    }
    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public ModelAndView error(){
        ModelAndView mv = new ModelAndView("error");
        return mv;
    }
}
