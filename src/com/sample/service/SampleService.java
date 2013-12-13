package com.sample.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLDecoder;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.sample.util.UserResource;
import com.sample.util.WebServiceResource;

@WebServlet("/*")
public class SampleService extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
	private static final Logger logger = Logger.getLogger(SampleService.class.getName());

	protected void doGet(HttpServletRequest request, 
						 HttpServletResponse response) 
								 throws ServletException, IOException {
        
		String path = request.getPathInfo();
		
		logger.log(Level.INFO,"Request received: " + path);
		
		//get the resource and method requested
		String[] pathInformation = getPathInformation(path); 
		
		//get the parameter, if any and decode
		String queryString = request.getQueryString(); 
		
		if (queryString != null) {
			queryString = URLDecoder.decode(queryString, "UTF-8");
			logger.log(Level.INFO,"Parameter received: " + queryString);
		}
		
		//get an instance of the resource the client wants
        WebServiceResource resource = getResourceInstance(pathInformation[0]);          
        
        Object responseObject = null;
        
        //pass the method and parameter to resource        	
		//and receive an Object containing the result
        try {
        	responseObject = resource.getResponseObject(pathInformation[1], queryString);         	
        } catch (Exception ex) {
        	throw new ServletException ("Unable to execute method of resource", ex);
        }
        
        writeResponse(response, responseObject); //write the result to the response        
    }
	
	/*
	 * Take the returned object from the resource and generate the response
	 */	
	protected void writeResponse (HttpServletResponse response, 
								  Object responseObject) 
										  throws ServletException, IOException {		
		
		if (responseObject == null) {
			throw new ServletException("Null result returned from resource");
		}
		
		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
		
		createAndWriteJson(out, responseObject);
				
		logger.log(Level.INFO,"Result written to response");
	}
	
	/*
	 * Writer the object to the Writer as a JSON String 
	 */
	protected void createAndWriteJson(Writer out, Object responseObject) {
		
		Gson gson = new GsonBuilder().create();
		gson.toJson(responseObject, out);

	}
	
	/*
	 * Confirm path information is valid and return it as an array 
	 * containing the resource and method
 	 */
    protected String[] getPathInformation (String path) 
    		throws ServletException {
        if (path == null) {
            throw new ServletException("Invalid Web Service Call, path null");
        }

        String[] pathInformation = path.replaceFirst("^/", "").split("/");        

        if (pathInformation.length != 2) {
            throw new ServletException("Invalid Web Service Call, malformed path");
        }
        
        return pathInformation;
    }

    /* 
     * Get an instance of the resource class
     */
    protected WebServiceResource getResourceInstance(String resourceName)
    		throws ServletException {

        Map<String, Class<? extends WebServiceResource>> resources = getResources();

        if (resources == null) {
            throw new ServletException("No valid resources found");
        }

        Class<? extends WebServiceResource> resourceClass = resources.get(resourceName);

        if (resourceClass == null) {
            throw new ServletException("Could not find valid resource for " + 
                                        resourceName);
        }

        WebServiceResource resource = null;
        
        try {
            resource = resourceClass.getConstructor().newInstance();
        }
        catch (Exception ex) {
            throw new ServletException("Could not instantiate resource class", ex);
        }        

        return(resource);
    }

	
   /* 
    * @return Map of resource name to class
    */
   protected Map<String, Class<? extends WebServiceResource>> getResources() {
	   
       Map<String, Class<? extends WebServiceResource>> resources = 
                           new HashMap<String, Class<? extends WebServiceResource>>();
       resources.put("user",UserResource.class);
       return(resources);
   }	

}
