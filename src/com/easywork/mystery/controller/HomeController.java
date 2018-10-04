package com.easywork.mystery.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.easycore.utils.BaseController;
import com.easywork.mystery.entity.SysUser;

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {
       /*抓取主页面视图*/
	@RequestMapping("/view")
	public String view() {
		return "/demo/view";
	}
	
	     /*抓取登陆视图*/
	@RequestMapping("/login")
	public String login() {
		return "/demo/login";
	}

	    /*抓取注册视图*/
	@RequestMapping("/register")
	public String register() {	
	return "/demo/register";
	}
	
	@RequestMapping("/openWork/{view}")
	public String openWork(@PathVariable("view") String view,HttpServletRequest req) {
		SysUser user = (SysUser)req.getSession().getAttribute("user");
		String account = user.getAccount();
		String url = "/"+account;
		req.getSession().setAttribute("url", url);
		return "/demo/" + view;
	}
	/*回信息*/
	@RequestMapping("/reply/{senderaccount}")
	public String replyMsg(@PathVariable("senderaccount") String senderaccount,HttpServletRequest req){
		req.getSession().setAttribute("account", senderaccount);
		return "/demo/reply";
	}
	/*上传信息*/
	@RequestMapping("/showPan")
	public String showPan(){
		return "/demo/pan";
	}
	
	/*抓取rename*/
	@RequestMapping("/rename/{id}")
	public String rename(@PathVariable("id") int id,Model model){
		model.addAttribute("id",id);
		/*跳转到renamejsp*/
		return "/demo/rename";
	}	
}