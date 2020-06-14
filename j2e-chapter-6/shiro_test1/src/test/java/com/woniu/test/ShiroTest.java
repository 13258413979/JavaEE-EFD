package com.woniu.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class ShiroTest {
	@Test
	public void test1() {
		//��ȡ
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager instance = factory.getInstance();
		SecurityUtils.setSecurityManager(instance);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","12345");
		//�ж�subject�Ƿ��Ѿ���¼
		if(subject.isAuthenticated()) {
			System.out.println(subject.getPrincipal() + " �ѵ�¼");
		} else {
			subject.login(token);
			System.out.println(subject.getPrincipal() + " ��¼�ɹ���¼");
		}
		subject.logout();
		if(subject.getPrincipal() == null) {
			System.out.println("�˳��ɹ�");
		}
	}
}
