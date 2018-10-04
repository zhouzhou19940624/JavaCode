package com.easywork.mystery.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.easycore.utils.BaseController;
import com.easycore.utils.MyPage;
import com.easywork.mystery.entity.MsgComm;
import com.easywork.mystery.entity.SysUser;
import com.easywork.mystery.service.MsgCommService;
import com.easywork.mystery.service.SysUserService;

@Controller
@RequestMapping("/mystery/MsgComm")
public class MsgCommController extends BaseController {
	@Autowired
	private MsgCommService msgcommService;
	@Autowired
	private SysUserService sysuserService;

	@RequestMapping("/queryMsg")
	@ResponseBody
	public MyPage<MsgComm> queryMsg(HttpServletRequest req,int pageNum,int pageSize) {
		SysUser user = (SysUser) req.getSession().getAttribute("user");
		MyPage<MsgComm> mypage = msgcommService.queryMsg(user,pageNum,pageSize);
		return mypage;
	}

	@RequestMapping("/changeReadFlag")
	@ResponseBody
	public String changeReadFlag(String msgid) {
		int result = msgcommService.changeReadFlag(msgid);
		if (result == 1) {
			return "success";
		} else {
			return "fail";
		}
	}

	/*改变邮箱的状态*/
	@RequestMapping("/changeStatus")
	@ResponseBody
	public String changeStatus(String msgid){
		long id = Long.parseLong(msgid);
		msgcommService.changeRead(id);
		return "success";
	}
	
	
	
	@RequestMapping("/sendMsg")
	@ResponseBody
	public String senMsg(String msg, String title, String receiver, HttpServletRequest req) {
		System.out.println("接收到的发送人是?+======="+receiver);
		if (msg.equals("<p><br></p>"))
			return "null";
		if (msg.equals(""))
			return "null";
		if (title.equals(""))
			return "null";
		if (!msg.equals("<p><br></p>") && !title.equals("")) {
			SysUser user = (SysUser) req.getSession().getAttribute("user");
			Long senderid = user.getId();
			String senderAccount = user.getAccount();
			String msgTitle = title;
			SysUser u = sysuserService.getUser(receiver);
			Long receiverid = u.getId();
			String receiverAccount = u.getAccount();
			MsgComm msgc = new MsgComm(receiverid, receiverAccount, senderid, senderAccount, (long) 1, msgTitle, 0, new Date(),1);
			int result = msgcommService.addcomm(msgc);
			System.out.println("插入信息结果为" + result);
		}
		return "success";
	}


	
	
}