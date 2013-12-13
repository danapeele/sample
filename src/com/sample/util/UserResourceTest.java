package com.sample.util;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class UserResourceTest {
	
	UserResource userService = new UserResource();

	@Test
	public void shouldReturnUserNameListFromGetResponseObject() {
		
		Object result = null;
		
		try {
			
			result = userService.getResponseObject("show", null);
		} catch (Exception ex) {
			
			fail("Exception from UserService "+ ex);
		}
		assertTrue(result instanceof List);
	}
	
	@Test
	public void shouldReturnMapFromAddWithSuccess() {
		
		Map<String,Object> resultMap = null;
		
		try {
			
			resultMap = userService.add("John Smith");			
		} catch (Exception ex) {
			
			fail("Exception from UserService "+ ex);
		}	
		
		Object success = resultMap.get("success");
		
		if (success == null) {
			
			fail("No object keyed by 'success' in resultMap");
		}
		
		if (!(success instanceof Boolean)) {
			
			fail("Object keyed by 'success' in resultMap is not a Boolean");
		}
		
		assertTrue((boolean)success);
	}	
	
	@Test
	public void shouldAddOneToListReturnedFromShow() {
		
		List<String> result = null;
		int size = 0;
		
		try {
			
			result = userService.show();
			size = result.size();
			userService.add("John Doe");
			result = userService.show();
			assert (result.size() == (size+1));
		} catch (Exception ex) {
			
			fail("Exception from UserService "+ ex);
		}
		
	}	
	
	@Test
	public void shouldReturnMapFromGetResponseObject() {
		
		Object result = null;
		
		try {
			
			result = userService.getResponseObject("add", "John Smith");			
		} catch (Exception ex) {
			
			fail("Exception from UserService "+ ex);
		}
		
		assertTrue(result instanceof Map);
	}	
}