package com.edu.hutech.services.implementation;

import com.edu.hutech.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    TrainerServiceImpl trainerService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        try{
            String sql = "Select * from demo.user where account ='"+ account +"'";
            Query query = entityManager.createNativeQuery(sql, User.class);
            return (User) query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
