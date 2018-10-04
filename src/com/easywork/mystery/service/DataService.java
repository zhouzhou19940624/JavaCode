package com.easywork.mystery.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.easywork.mystery.entity.Data;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.easywork.mystery.dao.DataMapper;

@Transactional
@Service
public class DataService {
	@Autowired
	private DataMapper dataMapper;

	public DataMapper getDataMapper() {
		return dataMapper;
	}

	/* 数据库插入软件 */
	public void addData(Data data) {
		/* 开始做判断 */
		Example exam = new Example(Data.class);
		Criteria createCriteria = exam.createCriteria();
		createCriteria.andCondition("url_a=", data.getUrlA());
		List<Data> list = dataMapper.selectByExample(exam);
		if (list.isEmpty()) {
			dataMapper.insert(data);
			System.out.println("插入" + data.getFilename() + "成功");
		} else {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createdate = sdf.format(date);
			String url = data.getUrlA();
			dataMapper.update(createdate, url);
		}
	}

	/* 通过父路径内容 */
	public List<Data> getDataByFatherUrl(String fatherUrl) {
		Example exm = new Example(Data.class);
		Criteria cta = exm.createCriteria();
		cta.andCondition("father_url=", fatherUrl);
		List<Data> list = dataMapper.selectByExample(exm);
		return list;
	}

	/* 向数据库中加入文件夹 */
	public String addFile(Data data) {
		Example exm = new Example(Data.class);
		Criteria ctr = exm.createCriteria();
		ctr.andCondition("url_a=", data.getUrlA());
		List<Data> list = dataMapper.selectByExample(exm);
		if (list.isEmpty()) {
			dataMapper.insert(data);
			return "success";
		} else {
			return "error";
		}
	}

	/* 删除文件 */
	public String deleteFile(int type, String url) {
		System.out.println("我拿到url路径为" + url);
		/* 0是文件夹 */
		if (type == 0) {
			/* 模糊查询 */
			List<Data> list = dataMapper.selectFileByUrl(url);
			for (Data data : list) {
				if (url.contains("/" + data.getFilename())) {
					dataMapper.deleteByPrimaryKey(data.getId());
				}
			}
			return "success";
		}
		if (type == 1) {
			/* 1是压缩文件 */
			Example exm = new Example(Data.class);
			Criteria ctr = exm.createCriteria();
			ctr.andCondition("url_a=", url);
			ctr.andCondition("type=", type);
			int result = dataMapper.deleteByExample(exm);
			return "success";
		}
		return "error";
	}

	public String renameFile(String rename, long id) {
		/* 0为文件夹 1是压缩文件 */
		Data data = dataMapper.selectByPrimaryKey(id);
		String oldurl = data.getUrlA();
		Long userid = data.getUserid();
		int type = data.getType();
		if (type == 0) {
			/* 模糊查询replace文件夹 */
			int l = oldurl.lastIndexOf("/");
			String first = oldurl.substring(0, l);
			String newUrl = first + "/" + rename;
			dataMapper.updateFF(oldurl, newUrl, userid);
			dataMapper.updateFilename(id, rename);
			/* 修改父路径 */
			List<Data> list = dataMapper.getUrlAbyid(userid);
			for (Data d : list) {
				Long i = d.getId();
				String url = d.getUrlA();
				int lastIndexOf = url.lastIndexOf("/");
				String fatherurl = url.substring(0, lastIndexOf);
				dataMapper.updateFatherUrlById(i, fatherurl);				
			}
			return "success";
		}
		if (type == 1) {
			int l = oldurl.lastIndexOf("/");
			int ll = oldurl.lastIndexOf(".");
			String first = oldurl.substring(0, l);
			String last = oldurl.substring(ll);
			String newUrl = first + "/" + rename + last;
			dataMapper.updateDocument(id, newUrl);
			dataMapper.updateFilename(id, rename);
			return "success";
		}
		return "error";
	}

	/* 检查重命名是否重复 */
	public String checkRename(String rename, long id) {
		Data data = dataMapper.selectByPrimaryKey(id);
		/* 0为文件夹 1是压缩文件 */
		int type = data.getType();
		String fatherUrl = data.getFatherUrl();
		String filename = data.getFilename();
		Example exm = new Example(Data.class);
		Criteria ctr = exm.createCriteria();
		ctr.andCondition("father_url=", fatherUrl);
		/* ctr.andCondition("type=", type); */
		List<Data> list = dataMapper.selectByExample(exm);
		for (Data d : list) {
			if (d.getFilename().equals(rename))
				return "error";
		}
		return "success";
	}

}