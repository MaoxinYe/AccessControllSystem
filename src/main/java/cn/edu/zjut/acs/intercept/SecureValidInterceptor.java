package cn.edu.zjut.acs.intercept;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.zjut.acs.model.XT_USER;
import cn.edu.zjut.acs.service.UserService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.support.LoginState;



public class SecureValidInterceptor extends HandlerInterceptorAdapter {
	
	@Resource
	private UserService userService;
	
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object handle) throws Exception {
		if (req.getRequestURI().contains("manage/")){
			String loginname = (String) req.getSession().getAttribute("session_loginname");
			if (StringUtils.isEmpty(loginname)) {
				if(req.getRequestURI().endsWith(".html")){
					resp.sendRedirect(req.getContextPath() + "/login.html");
					return false;
				} else  {
					resp.getOutputStream().print(writeValueAsString(JSONReturn.buildFailure(LoginState.UNLOGIN)).toString());
					return false;
				}
			}
//			YW_GLY gly = this.mGlyService.getGlyBydlm(loginname);
			List<XT_USER> list = this.userService.getUserByLoginName(loginname);
			if (list != null&&list.size()>0) {
				XT_USER user = list.get(0);
				if (ObjectUtils.isEmpty(user)) {
					if(req.getRequestURI().endsWith(".html")){
						resp.sendRedirect(req.getContextPath() + "/login.html");
						return false;
					} else  {
						resp.getOutputStream().print(writeValueAsString(JSONReturn.buildFailure(LoginState.UNLOGIN)).toString());
						return false;
					}
				}
							
			}
			
			HandlerMethod handlerMethod = (HandlerMethod) handle;
			//SecureValid secureValid = handlerMethod.getMethod().getAnnotation(SecureValid.class);
			//if (ObjectUtils.isEmpty(secureValid)) return true;
			
//			if (!mJscdService.secureValid(gly.getJsid(), secureValid.modulecode(), secureValid.type())) {
//				if(req.getRequestURI().endsWith(".html")){
//					resp.sendRedirect(req.getContextPath() + "/permission.html");
//					return false;
//				} else  {
//					resp.getOutputStream().print(writeValueAsString(JSONReturn.buildFailure(LoginState.PERMISSION_DENIED)).toString());
//					return false;
//				}
//			}
		}
		return true;
	}
	
	private String writeValueAsString(Object result){
		String ret = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			ret = mapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	 
}
