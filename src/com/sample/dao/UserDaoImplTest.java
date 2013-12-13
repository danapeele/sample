package com.sample.dao;
 
import org.junit.Test;
 
import java.util.List;
 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
 
/**
 * User: samirmes
 * Date: 12/9/13
 * Time: 3:34 PM
 */
public class UserDaoImplTest {
 
    UserDaoImpl userDao = new UserDaoImpl();
 
    @Test
    public void shouldReturnEmptyUserNameList() {
    	userDao.makeEmpty();
        List<String> userNameList = userDao.show();
 
        assertNotNull(userNameList);
        assertTrue(userNameList.size() == 0);
    }
 
    @Test
    public void shouldReturnPopulatedUserNameList() {
        boolean result = userDao.add("test");
        assertTrue(result);
 
        List<String> userNameList = userDao.show();
 
        assertNotNull(userNameList);
        assertTrue(userNameList.size() == 1);
    }
 
}