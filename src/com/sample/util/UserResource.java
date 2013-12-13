package com.sample.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.dao.UserDao;
import com.sample.dao.UserDaoImpl;

public class UserResource implements WebServiceResource {

	private UserDao userDao;
	
	public UserResource () {
		userDao = new UserDaoImpl();
	} 
	
	/*
	 * get the results of the method call to this resource in the form of an object
	 */
	@Override
	public Object getResponseObject(String resourceMethod, String parameter) throws Exception {
		
		Method method;		
		Object result;
		
		//Use reflection to invoke the appropriate method and store the result
		try {
			if (parameter != null) {
				
				method = this.getClass().getMethod(resourceMethod, String.class);
				result = method.invoke(this, parameter);
			} else {
				
				method = this.getClass().getMethod(resourceMethod);
				result = method.invoke(this);
			}		  
		} catch (SecurityException se) {
			
			throw new Exception ("Security exception getting method", se);
		} catch (NoSuchMethodException nsme) {
			
			throw new Exception ("No such method exception getting method", nsme);
		} catch (IllegalArgumentException iae) {
			
			throw new Exception ("Illegal argument exception invoking method", iae);			
		} catch (IllegalAccessException iace) {
			
			throw new Exception ("Illegal access exception getting method", iace);
		} catch (InvocationTargetException ite) {
			
			throw new Exception ("Invocation target exception getting method", ite);			
		}
		
		return result;
	}
	
	/*
	 * return the user list from the data access object
	 */
	public List<String> show () {
		return userDao.show();
	}
	
	/*
	 * add user list from the data access object
	 */
	public Map<String, Object> add (String user) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		//get current list of users
		List<String> currentUsers = userDao.show();
		
		boolean success = false;
		
		//Check to see if user already exists, if so then don't add them again
		if (currentUsers.contains(user)) {
			
			result.put("Message", "User already exists.");
		} else {
			
			//add user
			success = userDao.add(user);			
		
			if (success) {
			
				result.put("Message", "User was successfully added.");
			} else {
			
				result.put("Message", "User addition was unsuccessful.");
			}
		}
		
		result.put("success", success);
		
		return result;
	}	
}
