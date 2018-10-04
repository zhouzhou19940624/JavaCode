package com.easywork.mystery.controller;

import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.easycore.utils.BaseController;
import com.easycore.utils.MailUtils;
import com.easycore.utils.RandomCode;
import com.easywork.mystery.entity.SysUser;
import com.easywork.mystery.service.RelationService;
import com.easywork.mystery.service.SysUserService;

@Controller
@RequestMapping("/mystery/SysUser")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService sysuserService;
    @Autowired
	private RelationService relationservice;
	/* 验证码发往邮箱 */
	@RequestMapping("/test")
	@ResponseBody
	public String test(String email, HttpServletResponse resp,HttpServletRequest req) {
		System.out.println("进入邮箱验证======>验证码发送账户为" + email);
		try {
			RandomCode rc = new RandomCode();
			String code = rc.achieveCode();
			req.getSession().setAttribute(email,code);
			MailUtils.sendTxtMail(email, "验证码", code);
		} catch (MessagingException e) {
			return "fail";
		}
		return "success";
	}

	/* 注册账户 */
	@RequestMapping("/add")
	public String addUser(SysUser sysuser) {
		sysuser.setState(1);
		int result = sysuserService.addUser(sysuser);
		if (result == 1) {
			System.out.println("插入用户成功");
		} else {
			System.out.println("插入用户失败");
			
		}
		return "/demo/login";
		
	}
     @RequestMapping("/login")
     public String login(SysUser sysuser,HttpServletRequest req){
    	 List<SysUser> list = sysuserService.getUser();
    	 for (SysUser s : list) {
			if(s.getAccount().equals(sysuser.getAccount())&&s.getPassword().equals(sysuser.getPassword())){
				req.getSession().setAttribute("user", s);
				return "/demo/view";
			}
		}
		return "/demo/error";	 
     }
     
     @RequestMapping("/checkuser")
     @ResponseBody
     public String checkuser(String account){
    	System.out.println("我接收到的account为"+account);
    	if(account.equals(""))
    		 return "error";
    	 List<SysUser> list = sysuserService.getUser();
    	 boolean b = false;
    	 for (SysUser s : list) {
 			if(s.getAccount().equals(account)){
 				b=true;
 			}
    	 }
		if(b){
			return "repeated";
		}
		return "ojbk";
     }
     
     @RequestMapping("/checkphone")
     @ResponseBody
     public String checkphone(String phone){
    	 System.out.println("电话号码+++++" +phone);
    	 List<SysUser> list = sysuserService.getUser();
    	 boolean b = false;
    	 for (SysUser s : list) {
 			if(s.getMobile().equals(phone)){
 				b=true;
 			}
    	 }
		if(b){
			return "repeated";
		}
		return "ojbk";
     }
     @RequestMapping("/checkcode")
     @ResponseBody
     public String checkcode(String email,String code,HttpServletRequest req){
    	 System.out.println("email========"+email+"code======"+code);
    	  String c = (String)req.getSession().getAttribute(email);
    	  if(c.equals(code)){
    		  return "success";
    	  }else{
    		  return "wrong";
    	  }
     }
     
     @RequestMapping("/logout")
     public String logout(HttpServletRequest req,String account){
    	req.getSession().removeAttribute("user"); 
		return "/demo/login";
     }
     
     
     
     @RequestMapping("/selectaccount")
     @ResponseBody
     public List<String> selectaccount(){
    	 List<String> list = sysuserService.getaccount();
		 return list;
     }
     
     @RequestMapping("/checknickname")
     @ResponseBody
     public String checknickname(String nickname){
    	 List<SysUser> list = sysuserService.getUser();
    	 boolean b = false;
    	 for (SysUser s : list) {
 			if(s.getNickname().equals(nickname)){
 				b=true;
 			}
    	 }
		if(b){
			return "repeated";
		}
		return "ojbk";
     } 
     @RequestMapping("/checkFriend")
     @ResponseBody
     public String checkFriend(String friend,HttpServletRequest req){

    	 SysUser user = (SysUser)req.getSession().getAttribute("user");
    	 String account = user.getAccount();
    	 String result = relationservice.checkRelation(account,friend);
    	 if(result!=null)
    		 return "error";
    	 else{	 
    		 return "success";
    	 }
     }
     
     @RequestMapping("/addFriend")
     @ResponseBody
     public String addFriend(String friend,HttpServletRequest req){
         if(friend.equals("")){
        	 return "error";
         }
    	 SysUser user = (SysUser)req.getSession().getAttribute("user");
    	 String account = user.getAccount();
    	 int result = relationservice.addRelation(account,friend);
    	 if(result!=0)
    		 return "success";
    	 else{	 
    		 return "error";
    	 }
     }
     
     @RequestMapping("/selectFriends")
     @ResponseBody
     public List<String> selectFriends(HttpServletRequest req){
    	 SysUser user = (SysUser)req.getSession().getAttribute("user");
    	 String account = user.getAccount();
    	 List<String> list = relationservice.selectFriends(account);
    	 return list;
     }
     
     @RequestMapping("/deleteFriend")
     @ResponseBody
     public String deleteFriend(String friend_account,HttpServletRequest req){
    	 SysUser user = (SysUser)req.getSession().getAttribute("user");
    	 String account = user.getAccount();
    	 int result = relationservice.deleteFriend(account,friend_account);
    	 if(result!=0)
    		 return "success";
    	 else
    	 return "error";
     }   
}