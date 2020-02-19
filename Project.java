import java.io.*;
import java.sql.*;
import java.util.*;

public class Project {

	static int nRemotePort;
	static String strDbPassword;
	static String dbName;
	static Connection con;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		Connection con = null;
		PreparedStatement stmt = null, stmt2 = null;
		try {	
			int nRemotePort = 52457; // remote port number of your database
			String strDbPassword = "skeletonKey"; // database login password
			String dbName = "finalProject";  
			/*
			 * STEP 1 and 2
			 * LOAD the Database DRIVER and obtain a CONNECTION
			 * 
			 * */
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("jdbc:mysql://localhost:"+nRemotePort+"/"+dbName+"?verifyServerCertificate=false&useSSL=true");
			con = DriverManager.getConnection("jdbc:mysql://localhost:"+nRemotePort+"/"+dbName+"?verifyServerCertificate=false&useSSL=true", "msandbox",
					strDbPassword);
			// Do something with the Connection
			System.out.println("Database ["+dbName+" db] connection succeeded!");
			System.out.println();
			
			/*
			 * STEP 3
			 * EXECUTE STATEMENTS (by using Transactions)
			 * 
			 * */
			

		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			con.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction
		}
		
		
		if(args.length == 0) {
			System.out.println("Usage instructions.");
			System.exit(1);
		}
		if(args[0].equals("/?")) {
			System.out.println("Usage instructions");
			System.exit(1);
		}
		if(args[0].equals("CreateItem") && args.length == 4) {
			try {
				stmt = con.prepareStatement("call CreateItem(?,?,?)");
				stmt.setString(1, args[1]);
				stmt.setString(2, args[2]);
				stmt.setDouble(3, Double.parseDouble(args[3]));
				int i = stmt.executeUpdate();
				System.out.println(i + " records inserted.");
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
				con.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction	
			}
		}
		if(args[0].equals("CreatePurchase") && args.length == 3) {
			try {
				stmt = con.prepareStatement("call CreatePurchase(?,?)");
				stmt.setString(1, args[1]);
				stmt.setInt(2, Integer.parseInt(args[2]));
				int i = stmt.executeUpdate();
				System.out.println(i + " records inserted.");
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
				con.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction	
			}		
		}
		if(args[0].equals("CreateShipment") && args.length == 4) {
			createShipment(args[1], Integer.parseInt(args[2]), args[3]);
		}
		if(args[0].equals("GetItems") && args.length == 2) {
			getItems(args[1]);
		}
		if(args[0].equals("GetShipments") && args.length == 2) {
			getShipments(args[1]);
		}
		if(args[0].equals("GetPurchases") && args.length == 2) {
			getPurchases(args[1]);
		}
		if(args[0].equals("ItemsAvailable") && args.length == 2) {
			itemsAvailable(args[1]);
		}
		if(args[0].equals("UpdateItem") && args.length == 2) {
			updateItem(args[1], Double.parseDouble(args[2]));
		}
		if(args[0].equals("DeleteItem") && args.length == 2) {
			deleteItem(args[1]);
		}
		if(args[0].equals("DeleteShipment") && args.length == 2) {
			deleteShipment(args[1]);
		}
		if(args[0].equals("DeletePurchase") && args.length == 2) {
			deletePurchase(args[1]);
		}

	}
	
	public static void createItem(String itemCode, String itemDescription, double price) {
		Statement stmt;
		String createItem = "call CreateItem("+ itemCode + ", " + itemDescription + ", " + price + ")";
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createItem);
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
//			con.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction	
		}
		
	}
	
	public static void createPurchase(String itemCode, int PurchaseQuantity) {
		Statement stmt;
		String createPurchase = "call CreatePurchase("+ itemCode + ", " + PurchaseQuantity + ")";
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createPurchase);
		}
		catch(Exception e) {
			
		}
	}
	
	public static void createShipment(String itemCode, int ShipmentQuantity, String date) {
		Statement stmt;
		String insert = "Insert into `" + dbName + "`.`Shipment` (ItemCode, ShipmentQuantity, Date) Values ('" + 
				itemCode + "','" + ShipmentQuantity + "','" + date + "')";
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(insert);
		}
		catch(Exception e) {
			
		}
	}
	
	public static void getItems(String itemCode) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from `"+dbName+"`.`Student`;");
		}
		catch(Exception e) {
			
		}

	}
	
	public static void getShipments(String itemCode) {
		
	}
	
	public static void getPurchases(String itemCode) {
		
	}
	
	public static void itemsAvailable(String itemCode) {
		
	}
	
	public static void updateItem(String itemCode, double price) {
		
	}
	
	public static void deleteItem(String itemCode) {
		
	}
	
	public static void deleteShipment(String itemCode) {
		
	}

	public static void deletePurchase(String itemCode) {
		
	}
}
