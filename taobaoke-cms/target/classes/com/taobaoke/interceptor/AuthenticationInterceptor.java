package com.taobaoke.interceptor;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Intercepted;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.utils.Apps;
import com.taobaoke.cms.utils.CookieUtil;

@Component
@Order(1000)
@Intercepted()
public class AuthenticationInterceptor extends ControllerInterceptorAdapter {

	@Override
	protected Object after(Invocation inv, Object instruction) throws Exception {
		
		inv.addModel("currentApp", Apps.getApp(CookieUtil.getApp_id())
				.getAppName());
		return super.after(inv, instruction);
	}
}
