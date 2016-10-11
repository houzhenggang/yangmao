package com.yangmao.service.admin;

import com.yangmao.dal.dataobj.YangmaoUser;
import com.yangmao.model.common.Page;

import java.util.List;

/**
 * 用户服务
 * Created by liyongfeng on 2016/10/8.
 */
public interface AdminUserService {

    /**
     * 保存用户
     * @param user 用户对象
     * @return
     */
    int insertUser(YangmaoUser user) throws Exception;

    /**
     * 更新用户状态
     * @param userId 用户id
     * @param status 用户状态
     * @return
     */
    int updateUserStatus(long userId,int status) throws Exception;

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return
     */
    int updateUser(YangmaoUser user) throws Exception;

    /**
     * 通过用户id查询用户信息
     * @param userId 用户id
     * @return
     */
    YangmaoUser selectOneByUserId(long userId) throws Exception;

    /**
     * 获取用户列表
     * @param page 分页
     * @param name 用户名称
     * @return
     */
    List<YangmaoUser> selectUserListByPage(Page page, String name) throws Exception;
}
