package com.easywork.mystery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.easycore.utils.BaseController;
import com.easywork.mystery.service.SysEmpService;

@Controller
@RequestMapping("/mystery/SysEmp")
public class SysEmpController extends BaseController {
	@Autowired
	private SysEmpService sysempService;

}