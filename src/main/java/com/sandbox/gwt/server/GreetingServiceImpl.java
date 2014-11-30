package com.sandbox.gwt.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sandbox.gwt.client.GreetingService;
import com.sandbox.gwt.shared.FieldVerifier;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	private String name;

	public GreetingServiceImpl() {
		try {
			InitialContext ctxt = new InitialContext();
			DataSource ds = (DataSource) ctxt.lookup("ds/myDataSource");
			Connection c = ds.getConnection();
			ResultSet rs = c.createStatement().executeQuery("select * from TEST");
			
			while(rs.next()){
				name = rs.getString(1);
				System.out.println(name);
			}
			c.close();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}		
		
	}

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		//input = escapeHtml(input);
		input = name;
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
