package mortgage;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.util.*;
/*
 * we always need to add mysql jar to make the connection with the database
 * To do that right click on folder name -> build path -> custom build->
 * add external mysql jar file -> select jar file from downloads or wherever it is
 * -> then finish
 */
public class Main {
	public static void main(String args[])throws Exception {
		double principal = (double) read("principal: ", 0);
		double interest = (double) read("interest: ", 0);
		interest = interest / 100 / 12;
		int years = (int) read("years: ", 0);

		double result = Mortgage.mortgage(interest, years, principal); 
		System.out.println("your mortgage is " + result);
		remainingBalance(principal, interest, years);
		
		
		String Query2 = "insert into calculate values(?,?,?,?)";
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mortgage", "root", "Ranch4Road8@");
		
		//Statement st = con.createStatement();
		//int count = st.executeUpdate(Query);
		//when multiple values coming from somewhere we use preparesatatement
		PreparedStatement st = con.prepareStatement(Query2);
		
		//setting values in question mark ?
		st.setDouble(1, principal);
		st.setDouble(2, interest);
		st.setInt(3, years);
		st.setDouble(4, result);
		int count = st.executeUpdate();
		System.out.println(count + " row/s affected");
		/*ResultSet rs = st.executeQuery(Query);
		
		while(rs.next()){
		//String name = rs.getString("Age");
		String userData = rs.getInt(1)+ ": " + rs.getString(2)
				+" "+rs.getString(3) +" "+rs.getString(4)+" "+
				rs.getInt(5)+" "+rs.getString(6);
		System.out.println(userData);
		}*/
		st.close();
		con.close();
		

		/*
		 * Scanner scan = new Scanner(System.in);
		 * System.out.println("Enter principal:"); double principal =
		 * scan.nextDouble(); boolean enter = (principal < 1); while(enter){
		 * System.out.println("Enter a number greater than zero"); principal =
		 * scan.nextDouble(); //if(principal > 0) //if(enter == false) wrong
		 * implementation //if(enter = (principal>0)) // break; if(principal >
		 * 0) enter = false; }
		 * 
		 * System.out.println("Enter interest rate:"); double interest =
		 * scan.nextDouble(); boolean enter1 = (interest < 1); while(enter1){
		 * System.out.println("Enter a number greater than zero"); interest =
		 * scan.nextDouble(); if(interest > 0) enter1 = false; } interest =
		 * (interest/100)/12;
		 * 
		 * 
		 * System.out.println("Enter years:"); int years = scan.nextInt();
		 * boolean enter2 = (years < 1); while(enter2){
		 * System.out.println("Enter a number greater than zero"); years =
		 * scan.nextInt(); if(years > 0) enter2 = false; }
		 * 
		 * double principal = Mortgage.principal(); double interest =
		 * Mortgage.interest(); int years = Mortgage.years();
		 
		double principal = (double) read("principal: ", 0);
		double interest = (double) read("interest: ", 0);
		interest = interest / 100 / 12;
		int years = (int) read("years: ", 0);

		String result = Mortgage.mortgage(interest, years, principal);
		System.out.println("your mortgage is " + result);
		remainingBalance(principal, interest, years);*/

	}

	public static double read(String prompt, double min) {
		Scanner scan = new Scanner(System.in);
		double value;
		System.out.println(prompt);
		value = scan.nextDouble();
		boolean enter2 = (value < min);
		while (enter2) {
			System.out.println("Enter a number greater than " + min);
			value = scan.nextInt();
			if (value > min)
				enter2 = false;
		}
		return value;
	}

	public static void remainingBalance(double principal, double interest,
			int years) {
		double balance;
		int months = years * 12;
		for (int i = 1; i <= months; i++) {
			double r = (1 + interest);
			balance = (principal * ((Math.pow(r, months)) - (Math.pow(r, i))))
					/ ((Math.pow(r, months)) - 1);
			System.out.println("After " + i + " payment your mortgage is"
					+ ": "
					+ (NumberFormat.getCurrencyInstance().format(balance)));

		}
	}
}
