package com.easywork.mystery.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.easycore.utils.BaseController;
import com.easycore.utils.HdfsUtils;
import com.easywork.mystery.entity.Data;
import com.easywork.mystery.entity.SysUser;
import com.easywork.mystery.service.DataService;

@Controller
@RequestMapping("/mystery/disk")
public class DataController extends BaseController {
	@Autowired
	private DataService dataService;

	@RequestMapping("/upload")
	@ResponseBody
	public String view(MultipartFile myFile, HttpServletRequest req) {
		SysUser user = (SysUser) req.getSession().getAttribute("user");
		String account = user.getAccount();
		if (!myFile.isEmpty()) {
			String originalFilename = myFile.getOriginalFilename();
			System.out.println(originalFilename);
			try {
				BufferedOutputStream bops = new BufferedOutputStream(new FileOutputStream(new File(originalFilename)));
				bops.flush();
				bops.close();
				String url = (String) req.getSession().getAttribute("url");
				String dickname = url + "/" + originalFilename;
				HdfsUtils.copyLocalFileToHDFS(originalFilename, dickname);
				String url_a = dickname;
				Long userid = user.getId();
				int type = 1;
				int lastIndexOf = originalFilename.lastIndexOf(".");
				String filename = originalFilename.substring(0, lastIndexOf);
				/* 父类路径插入数据库 */
				String fatherUrl = url;
				System.out.println("我上传文件得到的父路径为" + url);
				req.getSession().setAttribute("url", fatherUrl);
				Data data = new Data(account, userid, url_a, null, type, fatherUrl, filename);
				dataService.addData(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "success";
		}
		return "error";
	}

	/* 创建文件 */
	@RequestMapping("/createFile")
	@ResponseBody
	public String newFile(String filename, HttpServletRequest req) {
		SysUser user = (SysUser) req.getSession().getAttribute("user");
		String account = user.getAccount();
		/* 获取当前路径 */
		String u = (String) req.getSession().getAttribute("url");
		Long userid = user.getId();
		/* 获取新建的路径 "/account" */
		String url = u + "/" + filename;
		/* 把新建的路径放入session中 */
		req.getSession().setAttribute("url", u);
		/* 获取到父类路径 */
		String fatherUrl = u;
		try {
			HdfsUtils.createPath(url);
			/* 定义文件格式是0,(0为文件夹) */
			int type = 0;
			Data data = new Data(account, userid, url, null, type, fatherUrl, filename);
			String result = dataService.addFile(data);
			if (result.equals("success"))
				return "success";
			if (result.equals("error"))
				return "error";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "error";
	}

	/* 查询路径 */
	@RequestMapping("/showFile")
	@ResponseBody
	public List<Data> showFile(HttpServletRequest req) {
		String url = (String) req.getSession().getAttribute("url");
		List<Data> list = dataService.getDataByFatherUrl(url);
		return list;
	}

	@RequestMapping("/input")
	@ResponseBody
	public List<Data> input(String url, HttpServletRequest req) {
		String fatherurl = url;
		req.getSession().setAttribute("url", fatherurl);
		System.out.println("我查询到的子路径为=========" + url);
		List<Data> list = dataService.getDataByFatherUrl(fatherurl);
		return list;
	}

	@RequestMapping("/showFileSon")
	@ResponseBody
	public List<Data> showFileSon(HttpServletRequest req) {
		String url = (String) req.getSession().getAttribute("url");
		List<Data> list = dataService.getDataByFatherUrl(url);
		return list;
	}

	/* 上传的查询 */
	@RequestMapping("/showSoftWare")
	@ResponseBody
	public List<Data> showSoftWare(HttpServletRequest req) {
		String url = (String) req.getSession().getAttribute("url");
		List<Data> list = dataService.getDataByFatherUrl(url);
		return list;
	}

	/* 返回 */
	@RequestMapping("/returnFile")
	@ResponseBody
	public List<Data> returnFile(HttpServletRequest req) {
		String url = (String) req.getSession().getAttribute("url");
		System.out.println("返回路径为" + url);
		String[] split = url.split("/");
		List<Data> list = null;
		if (split.length == 2) {
			list = dataService.getDataByFatherUrl(url);
		} else {
			int lastIndexOf = url.lastIndexOf("/");
			String uurl = url.substring(0, lastIndexOf);
			req.getSession().setAttribute("url", uurl);
			list = dataService.getDataByFatherUrl(uurl);
		}
		return list;
	}

	/* 删除 */
	@RequestMapping("/deleteFile")
	@ResponseBody
	public String deleteFile(int type, String url) {
		try {
			HdfsUtils.deletePath(url);
			String result = dataService.deleteFile(type, url);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "error";
	}

	/* 重命名 */
	@RequestMapping("/renameFile")
	@ResponseBody
	public String renameFile(String rename, long id) {
		/* 在hdfs中修改 */
		Data data = dataService.getDataMapper().selectByPrimaryKey(id);
		String oldPathString = data.getUrlA();
		int lastIndexOf = oldPathString.lastIndexOf("/");
		String substring = oldPathString.substring(0, lastIndexOf);
		String newPathString = substring + "/" + rename;
		String result = null;
		try {
			HdfsUtils.reName(oldPathString, newPathString);
			result = dataService.renameFile(rename, id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/* 检查你的重命名 */
	@RequestMapping("/checkRename")
	@ResponseBody
	public String checkUrl(String rename, long id, HttpServletRequest req) {
		/* 判断是否重复 */
		String result = dataService.checkRename(rename, id);
		return result;
	}

	/* 下载 */
	@RequestMapping("/download")
	@ResponseBody
	public String download(String url, int type) {
		/*0是文件夹 1是文件*/
		if (type == 1) {
             int lastIndexOf = url.lastIndexOf(".");
             url = url.substring(0,lastIndexOf);
		}
		String LocalPathString = "E:\\zhou";
		try {
			HdfsUtils.copyFileFromHDFS(url, LocalPathString);
			return "success";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "error";
	}

}
