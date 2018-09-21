package com.example;

import com.example.dataSource.DataSource;
import com.example.dataSource.DataSourceNames;
import com.example.entity.SysUser;
import com.example.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDatasourceApplicationTests {

    @Autowired
    SysUserService userService;

    @Test
    public void test() {
        SysUser user = userService.findUserByFirstDb(1);
        System.out.println("第one个数据库---------》" + user.toString());


        SysUser user2 = userService.findUserBySecondDb(1);
        System.out.println("第二个数据库---------》" + user2.toString());
    }

    /**
     * 出现多数据源动态切换失败的原因是因为在事务开启后，数据源就不能再进行随意切换了，
     * 也就是说，一个事务对应一个数据源。
     */
    @Test
    public void testWithTran() {
        System.out.println("------------------------->有事务测试");
        SysUser user = userService.findUserByFirstDb(1);
        System.out.println("第one个数据库---------》" + user.toString());


        SysUser user2 = userService.findUserBySecondDb(1);
        System.out.println("第二个数据库---------》" + user2.toString());
    }

//    @Test
//    @DataSource(name = DataSourceNames.FIRST)
//    public void findUserByFirstThenChangeSecond() {
//
//        SysUser user = userService.findUserByFirstDb(1);
//        System.out.println("第one个数据库---------》" + user.toString());
//
//        SysUser user2 = userService.findUserBySecondDb(1);
//        System.out.println("切换到第二个数据库---------》" + user2.toString());
//
//    }

    @Test
    public void findUserByFirstThenChangeSecond() {

        SysUser user = userService.findUserByFirstThenChangeSecond(1);

    }

}
