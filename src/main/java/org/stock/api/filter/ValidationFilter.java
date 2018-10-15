package org.stock.api.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;
import org.stock.api.validation.ValidateParam;
import org.stock.api.validation.ValidateParams;

@Component
@Order(1)
public class ValidationFilter extends OncePerRequestFilter {

	@Autowired
	ApplicationContext appContext;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        HandlerMethod handlerMethod = null;
        try {
            RequestMappingHandlerMapping req2HandlerMapping = (RequestMappingHandlerMapping) appContext.getBean("requestMappingHandlerMapping");
            // Map<RequestMappingInfo, HandlerMethod> handlerMethods = req2HandlerMapping.getHandlerMethods();
            HandlerExecutionChain handlerExeChain = req2HandlerMapping.getHandler(request);
            if (Objects.nonNull(handlerExeChain)) {
                handlerMethod = (HandlerMethod) handlerExeChain.getHandler();
                Method method=handlerMethod.getMethod(); 
	   			 if(method.isAnnotationPresent(ValidateParams.class))
	   			 { 
	   				ValidateParams validations = method.getAnnotation(ValidateParams.class);
	   				 ValidateParam[] validateParams = validations.value();
	   				 if(validations.isBodyValidation()) {
	   					
	   			        String body = getBody(wrappedRequest);
	   			        System.err.println(body);
	   				 }
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
	   						 if(validateParam.required()) {
	   							 throw new Exception("Required Parameter ["+validateParam.name()+"]is missing from request!!!");
	   						 }
	   					 }
	   				 }
	   				  System.out.println(method.getAnnotation(ValidateParams.class).value()); 
	   			 }
            }
        } catch (Exception e) {
            logger.warn("Lookup the handler method", e);
        } finally {
            logger.debug("URI = " + request.getRequestURI() + ", handlerMethod = " + handlerMethod);
        }

        filterChain.doFilter(wrappedRequest, response);
		
	}
	private String getBody(ContentCachingRequestWrapper request) {
        // wrap request to make sure we can read the body of the request (otherwise it will be consumed by the actual
        // request handler)
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException ex) {
                    payload = "[unknown]";
                }
                return payload;
            }
        }
        return null;
    }

}
