package com.jdbcproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestDemo {
	// Muni Singh..
		private Connection con=null;
		private Statement stmt=null;
		private ResultSet rs=null;
		private PreparedStatement pstmt=null;
		private Scanner sc=null;
		private String globalDbName=null;
		
		public String getGlobalDbName() {
			return globalDbName;
		}
		public void setGlobalDbName(String globalDbName) {
			this.globalDbName = globalDbName;
		}
		
		public void changeDb() {
			String dbName=null;
			System.out.println("Enter/Select DataBase Name: ");
			dbName=sc.nextLine();
			this.setGlobalDbName(dbName);
		}
		public Connection getCon() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+globalDbName, "root", "password");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}  
			return(con);
		}
		public void closeConnection() {
			try {
				stmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {e.printStackTrace();}
			
		}
		
		public void showDatabases() {
			try {
				stmt=this.getCon().createStatement();
				String q="show databases";
				rs=stmt.executeQuery(q);
				System.out.println(":: List Of All DB's ::");
				while(rs.next()) {
					System.out.println("\t"+rs.getString(1));
				}
				this.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void dropDatabase() {
			try {
				stmt=this.getCon().createStatement();
				System.out.println("Enter DB Name");
				String dbName=sc.nextLine();
				if(dbName.equals("sys") || dbName.equals("mysql") || dbName.equals("information_schema") || dbName.equals("performance_schema")) {
					throw new CanNotDeletePreDefinedDb("Can Not Delete Pre-Defined Db");
				}
				
				String q="drop database "+dbName;
				int res=stmt.executeUpdate(q);
				System.out.println(dbName+" Dropped: Query Result=> "+res);
				con.close();
				stmt.close();
			}catch(CanNotDeletePreDefinedDb pd) {
				pd.printStackTrace();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void createDatabase() {
			try {
				stmt=this.getCon().createStatement();
				System.out.println("Enter DB Name");
				String dbName=sc.nextLine();
				
				String q="create database "+dbName;
				int res=stmt.executeUpdate(q);
				System.out.println(dbName+" Created: Query Result=> "+res);
				con.close();
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void showTables() {
			try {
				stmt=this.getCon().createStatement();
				String q="show tables";
				rs=stmt.executeQuery(q);
				System.out.println(":: List Of All Tables ::");
				while(rs.next()) {
					System.out.println("\t"+rs.getString(1));
				}
				this.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void dropTables() {
			try {
				stmt=this.getCon().createStatement();
				System.out.println("Enter Table Name");
				String tableName=sc.nextLine();
				String q="drop table "+tableName;
				int res=stmt.executeUpdate(q);
				System.out.println(tableName+" Table Dropped: Query Result=> "+res);
				con.close();
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void createTable() {
			try {
				stmt=this.getCon().createStatement();
				System.out.println("Enter Query To Create The Table::");
				String q=sc.nextLine();
				stmt.executeUpdate(q);
				System.out.println("Table Created In "+globalDbName+" database");
				con.close();
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void insertRecordIntoTable() {
			try {
				stmt=this.getCon().createStatement();
				System.out.println("Enter Query To Insert The Record Into Table::");
				String q=sc.nextLine();
				int res=stmt.executeUpdate(q);
				System.out.println(res+" Record Inserted");
				con.close();
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void deleteRecordFromTable() {
			try {
				stmt=this.getCon().createStatement();
				System.out.println("Enter Query To Delete The Record From Table::");
				String q=sc.nextLine();
				int res=stmt.executeUpdate(q);
				System.out.println(res+" Record Deleted");
				con.close();
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public void updateRecordInTable() {
			try {
				stmt=this.getCon().createStatement();
				System.out.println("Enter Query To Update The Record In Table::");
				String q=sc.nextLine();
				int res=stmt.executeUpdate(q);
				System.out.println(res+" Record Updated");
				con.close();
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void selectAllRecordFromATable() {
			try {
				System.out.println("Enter Table Name");
				String tableName=sc.nextLine();
			
				stmt=this.getCon().createStatement();
				String q="select * from "+tableName;
				rs=stmt.executeQuery(q);
				
				// to count the number of column of a table
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				
				System.out.println();
				System.out.println("==============================================");
				System.out.println(":: All Records From "+tableName+" Table ::");
				while(rs.next()) {
					for(int i=1;i<=columnCount;i++)
						System.out.print(rs.getString(i)+"             ");
					System.out.println();
				}
				System.out.println("==============================================");
				System.out.println();
				this.closeConnection();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public int getChoice() {
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
			
			System.out.println("1. Show Databases");
			System.out.println("2. Drop Database");
			System.out.println("3. Create Database");
			System.out.println("4. Show Tables");
			System.out.println("5. Drop Tables");
			System.out.println("6. Create Tables");
			System.out.println("7. Insert Record Into Table");
			System.out.println("8. Delete Record From Table");
			System.out.println("9. Update Record In Table");
			System.out.println("10. Show All Records Of A Table");
			System.out.println("11. Change DB");
			System.out.println("12. EXIT");
			sc=new Scanner(System.in);
			System.out.println("Enter Your Choice:: ");
			int choice=sc.nextInt();
			sc.nextLine();
			return(choice);
		}
		public static void main(String[] args) {
			
			TestDemo td=new TestDemo();
			
			//System.out.println("Enter/Select DB Name:: ");
			//Scanner scanner=new Scanner(System.in);
			//String dbName=scanner.nextLine();
			td.setGlobalDbName("sys");
			
			int flag=0;
			while(true) {
				switch(td.getChoice()){
					case 1:
						td.showDatabases();
						break;
					case 2:
						td.dropDatabase();
						break;
					case 3:
						td.createDatabase();
						break;
					case 4:
						td.showTables();
						break;
					case 5:
						td.dropTables();
						break;
					case 6:
						td.createTable();
						break;
					case 7:
						td.insertRecordIntoTable();
						break;
					case 8:
						td.deleteRecordFromTable();
						break;
					case 9:
						td.updateRecordInTable();
						break;
					case 10:
						td.selectAllRecordFromATable();
						break;
					case 11:
						td.changeDb();
						break;
					case 12:
						flag=1;
						System.out.println(":: Program Ended ::");
						break;
					default:
							System.out.println("::Invalid Choice, Enter Again::");
				}// end of switch
				if(flag==1) {
					break;
				}
			}//end of infinite loop
		}// end of main
}// end of class
