/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.web.controller.site;

import javax.servlet.http.HttpServletRequest;

import com.google.common.eventbus.EventBus;
import mblog.base.lang.MtonsException;
import mblog.core.event.exta.GuavaEventListener;
import mblog.core.event.exta.GuavaEvent;
import mblog.core.event.exta.TestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import mblog.base.lang.Consts;
import mblog.web.controller.BaseController;

/**
 * @author langhsu
 *
 */
@Controller
public class IndexController extends BaseController{

	@Autowired
	private ApplicationContext applicationContext;

	@ExceptionHandler(value =MtonsException.class)
	public void handler(Exception e) {
		System.out.println(e.getMessage());
	}
	
	@RequestMapping(value= {"/", "/index"})
	public String root(ModelMap model, HttpServletRequest request) {
		String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
		int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
		model.put("order", order);
		model.put("pn", pn);


		//applicationEvent test
		applicationContext.publishEvent(new TestEvent(IndexController.class, true));

		//guava test
		EventBus eventBus = new EventBus();
		GuavaEventListener listener = new GuavaEventListener();
		eventBus.register(listener);

		eventBus.post(new GuavaEvent(200));
		eventBus.post(new GuavaEvent(300));

		System.out.println("LastMessage:"+listener.getLastMessage());

		return view(Views.INDEX);
	}

}
