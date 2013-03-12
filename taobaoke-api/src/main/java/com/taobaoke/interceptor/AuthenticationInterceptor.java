package com.taobaoke.interceptor;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Intercepted;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component
@Order(1000)
@Intercepted()
public class AuthenticationInterceptor extends ControllerInterceptorAdapter {
	
	@Override

    public Object before(Invocation inv) throws Exception {
		System.out.println("=========hhhh========");
		System.out.println(inv);
		
		System.out.println(inv.getRequest().getScheme());
    	return true;
    }
	
	@Override
	public Object after(Invocation inv, Object instruction){
		if( instruction instanceof String ){
			String result=(String) instruction;
			if( result.startsWith("@") ){
				TextInstruction textInstruction =new TextInstruction(result.substring(1));
				return textInstruction;
			}
			
			return result;
		}
		return instruction;
	}
	
	
}
