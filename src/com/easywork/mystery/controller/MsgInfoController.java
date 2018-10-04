package com.easywork.mystery.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easycore.utils.BaseController;
import com.easywork.mystery.entity.MsgComm;
import com.easywork.mystery.entity.MsgInfo;
import com.easywork.mystery.entity.SysUser;
import com.easywork.mystery.service.MsgCommService;
import com.easywork.mystery.service.MsgInfoService;
import com.easywork.mystery.service.SysUserService;

@Controller
@RequestMapping("/mystery/MsgInfo")
public class MsgInfoController extends BaseController {
	@Autowired
	private MsgInfoService msginfoService;
	@Autowired
	private MsgCommService msgcommService;
	
	@Autowired
	private SysUserService sysuserService;
	
	@RequestMapping("/addMsgDetil")
	@ResponseBody
	public String addMsgDetil(String title,String msg){
		MsgInfo info = new MsgInfo(title,1,msg);
		int result = msginfoService.addMsgDetil(info);
		System.out.println("回显的主键为="+result);
		return "success";
	} 
	
	@RequestMapping("/selectMsgDetails/{msgid}")
	public String selectMsgDetails(@PathVariable("msgid") String msgid,Model model){
		List<MsgInfo> list = msginfoService.selectMsgDetail(msgid);
		model.addAttribute("content", list.get(0).getContent());
		/*改变状态*/
		msgcommService.changeReadFlag(msgid);
		return "/demo/detils";
	}
	
	@RequestMapping("deleteMsgDetailsById")
	@ResponseBody
	public String deleteMsgDetailsById(String msgid){
		long msg_id = Long.parseLong(msgid);
		int result = msginfoService.deleteMsgById(msg_id);
		if(result!=0){
			int value = msgcommService.deleteMsgByMsgId(msg_id);
			return "ok";
		}
		return "error";
	}
	  
	@RequestMapping("insertMsg")
	@ResponseBody
	public String insertMsg(String msg,String title,String receiver,HttpServletRequest req){
		if(receiver.equals("伟哥牛逼")){
			return "error";
		}
		String result = msginfoService.insertMsg(msg,title);
		/*如果是error直接return掉*/
		if(result.equals("error"))
		return result;
		/*拿到主键*/
		long msgId = Long.parseLong(result);
		SysUser user = (SysUser) req.getSession().getAttribute("user");
		Long senderid = user.getId();
		String senderAccount = user.getAccount();
		String msgTitle = title;
		SysUser u = sysuserService.getUser(receiver);
		Long receiverid = u.getId();
		String receiverAccount = u.getAccount();
		MsgComm msgc = new MsgComm(receiverid, receiverAccount, senderid, senderAccount, msgId, msgTitle, 0,new Date(), 1);
		msgcommService.addcomm(msgc);
		return "success";
	}
	
	
	
	
	
	
}