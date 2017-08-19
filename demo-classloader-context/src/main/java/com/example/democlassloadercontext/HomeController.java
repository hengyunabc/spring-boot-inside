package com.example.democlassloadercontext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.democlassloadercontext.utils.ContextUtils;

@Controller
public class HomeController {

	@Autowired
	ApplicationContext context;

	@RequestMapping("/")
	@ResponseBody
	public String context() {
		return ContextUtils.treeHtml(context);
	}

}
