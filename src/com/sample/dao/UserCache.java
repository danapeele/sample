package com.sample.dao;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * User: samirmes
 * Date: 12/9/13
 * Time: 3:25 PM
 */
public class UserCache {
 
    private static UserCache userCache;
    public static List<String> userNameList = new ArrayList<String>();
 
    private UserCache() {
    }
 
    public static UserCache getInstance() {
        if (userCache == null)
            userCache = new UserCache();
 
        return userCache;
    }
}