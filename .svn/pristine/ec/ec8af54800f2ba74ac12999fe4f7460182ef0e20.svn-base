package com.hy.dao.impl;

import com.hy.dao.UserDao;
import com.hy.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW )
public class UserDaoImpl implements UserDao{
    @Autowired
    private HibernateTemplate hibernateTemplate;
    public List<User> uList() {

        List<User> list = (List<User>) hibernateTemplate.find("from User");
        User user = new User();
        user.setUser("g");
        hibernateTemplate.save(user);
        return list;
    }
}
