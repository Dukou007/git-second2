package com.example.service.impl;

import com.example.dataSource.DataSource;
import com.example.dataSource.DataSourceNames;
import com.example.entity.SysUser;
import com.example.mapper.SysUserMapper;
import com.example.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author lv-success
 * @since 2018-09-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public SysUser findUserByFirstDb(long id) {
        return this.baseMapper.selectById(id);
    }

    @DataSource(name = DataSourceNames.SECOND)
    @Override
    public SysUser findUserBySecondDb(long id) {
        tran();
        return this.baseMapper.selectById(id);
    }

    @Transactional
    public void tran() {
        System.out.println("udapta delete");
        System.out.println("udapta delete");
        System.out.println("udapta delete");
        System.out.println("udapta delete");
        System.out.println("udapta delete");
        System.out.println("udapta delete");
        System.out.println("udapta delete");
    }

    @Override
//    @DataSource(name = DataSourceNames.FIRST)
    public SysUser findUserByFirstThenChangeSecond(long id) {

        SysUser user = this.findUserByFirstDb(id);
        System.out.println("第one个数据库---------》" + user.toString());

        SysUser user2 = this.findUserBySecondDb(id);
        System.out.println("切换到第二个数据库---------》" + user2.toString());

        return null;
    }
}
