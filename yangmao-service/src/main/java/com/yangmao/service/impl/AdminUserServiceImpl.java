package com.yangmao.service.impl;

import com.yangmao.dal.dao.NewYangmaoUserMapper;
import com.yangmao.dal.dao.YangmaoUserMapper;
import com.yangmao.dal.dataobj.YangmaoUser;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.Page;
import com.yangmao.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 后台用户服务
 * Created by liyongfeng on 2016/10/8.
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    /**
     * 用户dao
     */
    @Autowired
    private YangmaoUserMapper yangmaoUserMapper;

    /**
     * 手工填写用户dao
     */
    @Autowired
    private NewYangmaoUserMapper newYangmaoUserMapper;

    /**
     * 保存用户信息
     * @param user 用户对象
     * @return
     */
    @Override
    public int insertUser(YangmaoUser user) throws Exception {
        int result = 0;
        Date date = new Date();

        user.setCreateTime(date);
        user.setLastUpdateTime(date);
        yangmaoUserMapper.insert(user);
        return 0;
    }

    /**
     * 修改用户状态
     * @param userId 用户id
     * @param status 用户状态
     * @return
     */
    @Override
    public int updateUserStatus(long userId, int status) throws Exception {
        int result = 0;
        Date date = new Date();
        YangmaoUser user = yangmaoUserMapper.selectByPrimaryKey(userId);
        user.setLastUpdateTime(date);
        user.setStatus((byte) status);
        result = yangmaoUserMapper.updateByPrimaryKey(user);
        return result;
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return
     */
    @Override
    public int updateUser(YangmaoUser user) throws Exception{
        int result = 0;
        Date date = new Date();
        YangmaoUser updateUser = yangmaoUserMapper.selectByPrimaryKey(user.getUserId());
        updateUser.setIsAdmin(user.getIsAdmin());
        updateUser.setLastUpdateTime(date);
        updateUser.setEmail(user.getEmail());
        updateUser.setName(user.getName());
        updateUser.setPassword(user.getPassword());
        result = yangmaoUserMapper.updateByPrimaryKey(updateUser);
        return result;
    }

    /**
     * 通过用户id查询用户对象
     * @param userId 用户id
     * @return
     */
    @Override
    public YangmaoUser selectOneByUserId(long userId) throws Exception {
        YangmaoUser user = yangmaoUserMapper.selectByPrimaryKey(userId);
        return user;
    }

    /**
     * 获取用户列表
     * @param page 分页
     * @param name 用户名称
     * @return
     * @throws Exception
     */
    @Override
    public List<YangmaoUser> selectUserListByPage(Page page, String name) throws Exception {
        List<YangmaoUser> users = new ArrayList<YangmaoUser>();
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        int count = newYangmaoUserMapper.countUserListByPage(map);
        page.setTotalElements(count);
        map.put("start", page.getNumber() * page.getSize());
        map.put("size", page.getSize());
        users = newYangmaoUserMapper.selectUserListByPage(map);
        return users;
    }

}
