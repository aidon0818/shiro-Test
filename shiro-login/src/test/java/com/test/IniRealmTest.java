package com.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 作    者 : DongLiu
 * 日    期 : 2018/4/20 14:11
 * 描    述 :
 */
public class IniRealmTest {
    @Test
    public void Test() {
        IniRealm iniRealm = new IniRealm("classpath:user.ini");
        //构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("abc", "123");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        subject.checkRole("admin");
        //判断用户是否具有权限
        subject.checkPermission("user:delete");
    }

    public static void main(String[] args) {
        Md5Hash md5Hash=new Md5Hash("123");
        System.out.println(md5Hash);
    }
}
