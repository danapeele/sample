package com.sample.service;

import static org.junit.Assert.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.sample.util.WebServiceResource;

public class SampleServiceTest {
	
	SampleService sampleService = new SampleService();

	@Test
	public void shouldCreateAndWriteJsonToString() {
		
		Map<String,Object> testMap = new HashMap<String,Object>();
		testMap.put("Success", true);
		StringWriter sw = new StringWriter();
		sampleService.createAndWriteJson(sw, testMap);
		
		assertTrue(sw.toString().equals("{\"Success\":true}"));
	}

	@Test
	public void shouldSplitPathIntoResourceAndMethod() {
		String path = "/user/add";
		String[] serviceInfo = null;
		
		try {
			
			serviceInfo = sampleService.getPathInformation(path);
		} catch (Exception ex) {
			
			fail("Error parsing path information");
		}
		
		assertTrue(serviceInfo[0].equals("user") && 
							serviceInfo[1].equals("add"));
	}

	@Test
	public void shouldGetResourcesAndInstantiateProperClass() {
		Map<String, Class<? extends WebServiceResource>> resources = 
											sampleService.getResources();
		
		if (resources == null) {
			fail("Resource map not returned");
		}
		
		if (resources.size() == 0) {
			fail("Resource map is empty");
		}
		
		Object[] keySet = resources.keySet().toArray();
		
		String resourceName = keySet[0].toString();
		
		Class<? extends WebServiceResource> resourceClass = 
									resources.get(resourceName);
		
		WebServiceResource resource = null;
		try {
			resource = sampleService.getResourceInstance(resourceName);
		} catch (Exception ex) {
			fail("Exception instantiating resource class");
		}
		
		assertTrue(resource.getClass() == resourceClass);
	}


}
