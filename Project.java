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
		
		String usageInstructions = ("USAGE INSTRUCTIONS:\r\n" +
				"1. java Project /?\r\n" + 
				"	a. This usage text will be output for the /? command line argument\r\n" + 
				"2. java Project CreateItem <itemCode> <itemDescription> <price>\r\n" + 
				"	a. Creates an item\r\n" + 
				"3. java Project CreatePurchase <itemCode> <PurchaseQuantity>\r\n" + 
				"	a. Creates a Purchase record\r\n" + 
				"4. java Project CreateShipment <itemCode> <ShipmentQuantity> <shipmentDate>\r\n" + 
				"	a. Creates a Shipment record\r\n" + 
				"5. java Project GetItems <itemCode>\r\n" + 
				"	a. Returns all items associated with the itemcode\\r\\n" + 
				"	b. Use % as input to be used for all items\r\n" + 
				"6. java Project GetShipments <itemCode>\r\n" + 
				"	a. Returns all shipments associated with the itemcode\r\n" + 
				"	b. Use % as input to be used for all items\r\n" + 
				"7. java Project GetPurchases <itemCode>\r\n" + 
				"	a. Returns all purchases associated with the itemcode\r\n" + 
				"	b. Use % to be used for all Items\r\n" + 
				"8. java Project ItemsAvailable <itemCode>\r\n" + 
				"	a. Returns a calculation for items.\r\n" + 
				"	b. Use % to be used for a request, this will return all Items\r\n" +  
				"9. java Project UpdateItem <itemCode> <price>\r\n" + 
				"	a. Changes the price for the itemItemCode\r\n" + 
				"10. java Project DeleteItem <itemCode>\r\n" + 
				"	a. Removes only the item exactly matching the parameter (errors are expected if shipments" + 
				" or purchases are referencing the item.)\r\n" + 
				"11. java Project DeleteShipment <itemCode>\r\n" + 
				"	a. Removes only the most recent shipment for one item code, if a shipment exists.\r\n" + 
				"12. java Project DeletePurchase <itemCode>\r\n" + 
				"	a. Removes only the most recent purchase for one item code, if a purchase exists.");
		if(args.length == 0) {
			System.out.println(usageInstructions);
			System.exit(1);
		}
		if(args[0].equals("/?")) {
			System.out.println(usageInstructions);
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
			try {
				stmt = con.prepareStatement("call CreateShipment(?,?)");
				stmt.setString(1, args[1]);
				stmt.setInt(2, Integer.parseInt(args[2]));
				stmt.setString(3, args[3]);
				int i = stmt.executeUpdate();
				System.out.println(i + " records inserted.");
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
				con.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction	
			}				
		}
		if(args[0].equals("GetItems") && args.length == 2) {
			try {
				stmt = con.prepareStatement("call GetItems(?)");
				stmt.setString(1, args[1]);
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();
				
				while (rs.next()) {
					for (int i = 1; i <= numCols; i++) {
						if (i > 1) System.out.print(", ");
						String colVal = rs.getString(i);
						System.out.print(colVal + " " + rsmd.getColumnName(i));
					}
					System.out.println();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
				con.rollback();
			}
		}
		if(args[0].equals("GetShipments") && args.length == 2) {
			try {
				stmt = con.prepareStatement("call GetShipments(?)");
				stmt.setString(1, args[1]);
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();
				
				while (rs.next()) {
					for (int i = 1; i <= numCols; i++) {
						if (i > 1) System.out.print(", ");
						String colVal = rs.getString(i);
						System.out.print(colVal + " " + rsmd.getColumnName(i));
					}
					System.out.println();
				}
			} catch(SQLException e) {
				System.out.println(e.getMessage());
				con.rollback();
			}
		}
		if(args[0].equals("GetPurchases") && args.length == 2) {

		}
		if(args[0].equals("ItemsAvailable") && args.length == 2) {

		}
		if(args[0].equals("UpdateItem") && args.length == 2) {

		}
		if(args[0].equals("DeleteItem") && args.length == 2) {
			try {
				stmt = con.prepareStatement("call DeleteItem(?)");
				stmt.setString(1, args[1]);
				int i = stmt.executeUpdate();
				System.out.println(i + " records deleted.");
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
				con.rollback(); // In case of any exception, we roll back to the database state we had before starting this transaction	
			}				
		}
		if(args[0].equals("DeleteShipment") && args.length == 2) {

		}
		if(args[0].equals("DeletePurchase") && args.length == 2) {

		}

	}
	
}
