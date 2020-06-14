package com.woniu.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class ShiroTest {
	/**
	 * Shiro��֤
	 */
	@Test
	public void test1() {
		/**
		 * ��ʼ��SecurityManager�Ĺ�����
		 * ���������ini�ļ���·��
		 */
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		//��ȡ��SecurityManager����
		SecurityManager instance = factory.getInstance();
		//SecurityUtils����SecurityManager
		SecurityUtils.setSecurityManager(instance);
		/**
		 * ��ʾ�û�
		 */
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "12345");
		//��½
		if(subject.isAuthenticated()) {
			System.out.println(subject.getPrincipal().toString() + "�ѵ�½");
			return;
		}
		subject.login(token);
		System.out.println(subject.getPrincipal().toString() + "��½�ɹ�");
		
		subject.logout();
		if(subject.getPrincipal()==null) {
			System.out.println("�˳���½");
		}
	}
	@Test
	public void test2() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_jdbc.ini");
		SecurityManager instance = factory.getInstance();
		SecurityUtils.setSecurityManager(instance);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("woniu1", "123456");
		//��½
		if(subject.isAuthenticated()) {
			System.out.println(subject.getPrincipal().toString() + "�ѵ�½");
			return;
		}
		subject.login(token);
		System.out.println(subject.getPrincipal().toString() + "��½�ɹ�");
		
		subject.logout();
		if(subject.getPrincipal()==null) {
			System.out.println("�˳���½");
		}
	}
	@Test
	public void test3() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_permission.ini");
		SecurityManager instance = factory.getInstance();
		SecurityUtils.setSecurityManager(instance);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
		//��½
		if(subject.isAuthenticated()) {
			System.out.println(subject.getPrincipal().toString() + "�ѵ�½");
			return;
		}
		subject.login(token);
		//��ɫ�ж�
		System.out.println("zhangsan:rol3��ɫ" + subject.hasRole("role3"));
		System.out.println("zhangsan:rol1��ɫ" + subject.hasRole("role1"));
		System.out.println("zhangsan:rol2��ɫ" + subject.hasRole("role2"));
		//Ȩ���ж�
		System.out.println(subject.isPermitted("system:create"));
		System.out.println(subject.isPermitted("mean:create"));
		
		if(subject.isPermitted("system:create")) {
			System.out.println("���Դ�������");
		} else {
			System.out.println("��ǰ�û��޲���Ȩ�ޣ�����ϵ����Ա");
		}
	}
}
