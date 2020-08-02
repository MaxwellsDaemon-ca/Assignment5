package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.sql.*;

import beans.UserModel;
import business.OrdersBusinessInterface;

@ManagedBean
@ViewScoped
public class FormController {
	@Inject
	OrdersBusinessInterface services;
	
	private final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private final String USERNAME = "postgres";
	private final String PASSWORD = "lofihiphop987";
	

	public String onSubmit() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		UserModel userModel = context.getApplication().evaluateExpressionGet(context, "#{userModel}", UserModel.class);
		
		services.test();
		
		getAllOrders();
		insertOrder();
		getAllOrders();
		
		
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("userModel", userModel);
		
		return "Response.xhtml";
	}
	
	public OrdersBusinessInterface getService() {
		return services;
	}
	
	private void getAllOrders() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String sqlRequest = "SELECT * FROM testapp.\"Orders\"";
			rs = stmt.executeQuery(sqlRequest);
			
			while (rs.next()) {
				System.out.printf("ID: %d ||| Name: %s ||| Price: $%.2f %n", rs.getInt("ID"), rs.getString("PRODUCT_NAME").trim(), rs.getFloat("PRICE"));
            }
			
		} catch (SQLException e) {
			System.out.println("Connection failure!");
		} finally {
			try {
				if (conn != null) { conn.close(); }
			} catch (Exception e) { e.printStackTrace(); }
			
			try {
				if (stmt != null) { stmt.close(); }
			} catch (Exception e) { e.printStackTrace(); }
			
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e) { e.printStackTrace(); }
		}
		
	}
	
	private void insertOrder() {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String sqlRequest = 
					"INSERT INTO testapp.\"Orders\"(\"ORDER_NO\", \"PRODUCT_NAME\", \"PRICE\", \"QUANTITY\")"
					+ "VALUES('001122334455', 'This was inserted new', 100.00, 25)";
			stmt.executeUpdate(sqlRequest);
		} catch (SQLException e) {
			System.out.println("Failed to insert Order");
		} finally {
			try {
				if (conn != null) { conn.close(); }
			} catch (Exception e) { e.printStackTrace(); }
			try {
				if (stmt != null) { stmt.close(); }
			} catch (Exception e) { e.printStackTrace(); }
		}
	}
	
}
