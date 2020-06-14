package com.woniu.realm;

import java.sql.Connection;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.woniu.dao.UserDao;
import com.woniu.entity.Users;
import com.woniu.util.DBUtil;

public class MyJdbcRealm extends AuthorizingRealm{
	private UserDao userDao = new UserDao();
	
	//Ϊ��ǰ��¼�ɹ����û�������Ȩ
	@Override	
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String username = (String) principals.getPrimaryPrincipal(); //��ȡ�û���
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            authorizationInfo.setRoles(userDao.getRoles(conn, username)); //���ý�ɫ
            authorizationInfo.setStringPermissions(userDao.getPermission(conn, username)); //����Ȩ��            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizationInfo;
	}
	//��֤������������֤�û���Ϣ������������֤�û����������Ƿ���ȷ
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		String username = (String) token.getPrincipal(); // ��ȡ�û���
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            Users user = userDao.getUsers(conn, username); // �����Ǹ����û���������û���Ϣ�����漰������
            if (user != null) {
                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                        user.getUsername(), user.getPassword(), "myJdbcRealm");
                return authcInfo;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
	}
}
