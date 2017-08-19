package com.example.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.AbstractMvcEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.democlassloadercontext.utils.ContextUtils;

@ConfigurationProperties("endpoints.contexttree")
public class ContextTreeEndPoint extends AbstractMvcEndpoint {

	@Autowired
	ApplicationContext context;

	public ContextTreeEndPoint() {
		super("/contexttree", false);
	}

	@RequestMapping
	@ResponseBody
	public String invoke() {
		return ContextUtils.treeHtml(context);
	}

}
