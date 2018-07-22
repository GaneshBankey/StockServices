package org.stock.api.security.config;


import java.lang.reflect.Method;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.stock.api.validation.ValidateParam;
import org.stock.api.validation.ValidateParams;

@Component
public class RequestValidateInterceptor extends HandlerInterceptorAdapter {

	 @Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		 
		 if (handler instanceof HandlerMethod) {
			 HandlerMethod hm=(HandlerMethod)handler; 
			 Method method=hm.getMethod(); 
			 if(method.isAnnotationPresent(ValidateParams.class))
			 { 
				 ValidateParam[] validateParams = method.getAnnotation(ValidateParams.class).value();
				 for(ValidateParam validateParam : validateParams) {
					 String value = request.getParameter(validateParam.name());
					 if(value != null) {
						 //Normalize Value
						 value = Normalizer.normalize(value, Form.NFKC);
						 Pattern pattern = Pattern.compile(validateParam.sanitize().getPattern());
						 Matcher matcher = pattern.matcher(value);
						 //Sanitize value
						 if(matcher.find()) {
							 // Found blacklisted tag
							 throw new IllegalStateException("Bad content!!");
						 }
						 
					 }else {
						 if(validateParam.isRequired()) {
							 throw new Exception("Required Parameter ["+validateParam.name()+"]is missing from request!!!");
						 }
					 }
				 }
				  System.out.println(method.getAnnotation(ValidateParams.class).value()); 
			 } 
		 }
		 
		 return true;
	 }
}
