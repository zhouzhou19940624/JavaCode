package com.easywork.mystery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.easycore.utils.BaseController;
import com.easywork.mystery.entity.Relation;
import com.easywork.mystery.service.RelationService;

@Controller
@RequestMapping("/mystery/Relation")
public class RelationController extends BaseController {
	@Autowired
	private RelationService relationService;

}