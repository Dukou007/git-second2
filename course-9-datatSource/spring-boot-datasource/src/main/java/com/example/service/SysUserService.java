package com.example.service;

import com.example.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author lv-success
 * @since 2018-09-21
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findUserByFirstDb(long id);

    SysUser findUserBySecondDb(long id);

    SysUser findUserByFirstThenChangeSecond(long id);
}
