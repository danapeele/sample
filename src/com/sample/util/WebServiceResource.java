package com.sample.util;

	/* 
	 * Interface for resource classes that contain the web service methods 
	 */
	public interface WebServiceResource {
	    /*
	     * return the output for the requested method as an object
	     */
	    public Object getResponseObject(String method,
	                       String parameter) throws Exception;
}
