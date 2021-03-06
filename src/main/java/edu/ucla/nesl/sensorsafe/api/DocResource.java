package edu.ucla.nesl.sensorsafe.api;

import java.net.URISyntaxException;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import edu.ucla.nesl.sensorsafe.init.SensorSafeSwaggerConfig;

@Path("doc")
@PermitAll
public class DocResource {
	
	@Context 
	private HttpServletRequest httpReq;
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String doGet() throws URISyntaxException {
		String addr = httpReq.getLocalAddr();
		int port = httpReq.getLocalPort();
		boolean isHttps = httpReq.isSecure();
		
		String basePath;
		if (isHttps) {
			basePath = "https://";
		} else { 
			basePath = "http://";
		}
		basePath += addr + ":" + port;
		String apiBasePath = basePath + "/api";
		SensorSafeSwaggerConfig.setBasePath(apiBasePath);
		
		//return Response.temporaryRedirect(new URI(basePath + "/api-docs")).build();
		return "<iframe src=\"" + basePath + "/api-docs" + "\" "
				+ "style=\"width:100%;height:100%;\" >"
				+ "<p>Your browser does not support iframes.</p>"
				+ "</iframe>";
	}
}
