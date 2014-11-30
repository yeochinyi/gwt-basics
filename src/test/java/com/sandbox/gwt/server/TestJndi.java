package com.sandbox.gwt.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Test;

public class TestJndi {

	@Test
	public void test() {
		try {
			InitialContext ctxt = new InitialContext();
			DataSource ds = (DataSource) ctxt.lookup("ds/myDataSource");
			Connection c = ds.getConnection();
			ResultSet rs = c.createStatement().executeQuery("select * from TEST");
			
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
			c.close();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
	}

}
