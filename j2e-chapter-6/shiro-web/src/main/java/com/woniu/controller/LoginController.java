package com.woniu.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class LoginController {
	@RequestMapping("toLogin")
	public String login() {
		return "login.jsp";
	}
	@RequestMapping("login")
	public String doLogin(String username , String password , HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		String error = null;
		try {
//			Md5Hash
			subject.login(token);
		} catch (UnknownAccountException e) {  
	        error = "�û���/�������";  
	    } catch (IncorrectCredentialsException e) {  
	        error = "�û���/�������";  
	    } catch (AuthenticationException e) {  
	        error = "��������" + e.getMessage();  
	    }
		//��¼û�гɹ�,��ת����¼ҳ�棬���󶨴�����Ϣ
		if(error!=null) {
			session.setAttribute("error", error);
			return "login.jsp";
		}
		return "redirect:index";
	}
	//�˳���¼
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login.jsp";
	}
	//��Ҫ�����֤���ܽ��з���
	@RequestMapping("index")
	public String index() {
		return "index.jsp";
	}
	//��Ҫuser:viewȨ��
	@RequestMapping("list")
	public String list() {
		return "index.jsp";
	}
	//��Ҫsystem��ɫ���ܷ���
	@RequestMapping("roles")
	public String roles() {
		return "index.jsp";
	}
}
