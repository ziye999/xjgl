package com.hy.service.impl;

import com.hy.dao.UserDao;
import com.hy.domain.User;
import com.hy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    public List<User> uList() {
        return userDao.uList();
    }
}
