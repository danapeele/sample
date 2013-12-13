package com.sample.dao;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * User: samirmes
 * Date: 12/9/13
 * Time: 3:23 PM
 */
public class UserDaoImpl implements UserDao {
 
    @Override
    public List<String> show() {
        return UserCache.userNameList;
    }
 
    @Override
    public boolean add(String username) {
        UserCache.userNameList.add(username);
 
        return true;
    }
    @Override
    public boolean makeEmpty() {
        UserCache.userNameList = new ArrayList<String>();
        return true;
    }    
}