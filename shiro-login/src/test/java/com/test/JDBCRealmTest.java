package com.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 作    者 : DongLiu
 * 日    期 : 2018/4/20 14:36
 * 描    述 :
 */
public class JDBCRealmTest {
    DruidDataSource druidDataSource = new DruidDataSource();

    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }

    @Test
    public void Test() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);
        //设置权限的开关
        jdbcRealm.setPermissionsLookupEnabled(true);
        //使用自己的sql查询
        String sql = "select password from test_user where user_name=?";
        jdbcRealm.setAuthenticationQuery(sql);
        //权限查询
        String roleSql = "select role_name from tets_role where user_name=?";
        jdbcRealm.setUserRolesQuery(roleSql);
        //构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("abc", "123");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        subject.checkRole("admin");
    }
}
