package project;
import java.sql.*;

public class SqlPlusConnect {

	public static void main(String[] args) 
	{
		try 
		{ 
			//Keep an eye open for errors
			String driverName = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driverName);
			
			System.out.println("Connecting to Oracle...");
			String url = "jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g";
			Connection conn = DriverManager.getConnection(url,"ainguane","upsoni");
			
			System.out.println("Connected!");
			Statement stmt = conn.createStatement(); //Create a new statement
			Statement stmt2 = conn.createStatement(); //Create a new statement
			Statement stmt3 = conn.createStatement(); //Create a new statement
			Statement stmt4 = conn.createStatement(); //Create a new statement
			
			//Now we execute our query and store the results in the my results object:
			ResultSet myresults = stmt.executeQuery(
					"SELECT S.Eno, S.Ename, S.Title " +
					"FROM STAFF S, VISITOUTCOME V " +
					"WHERE S.Eno=V.Eno AND V.Xdate >= 160101 AND V.Xdate <= 160331 "+
					"ORDER BY S.Eno");
			
			stmt2 = conn.createStatement(); //Create a new statement
			ResultSet myresults2 = stmt2.executeQuery(
					"SELECT  V.Xdate, P.SSN, P.pname, (V.Ppay+V.Ipay) AS TotalCharge " +
					"FROM PATIENT P, VISITOUTCOME V, STAFF S " +
					"WHERE P.SSN=V.SSN " +
									"AND S.Eno=V.Eno " +
									"AND V.Xdate >= 160101 " +
									"AND V.Xdate <= 160331 " +
					"ORDER BY S.Eno"
					);
			
			stmt3 = conn.createStatement(); //Create a new statement
			ResultSet myresults3 = stmt3.executeQuery(
					"SELECT  COUNT(P.SSN) AS TOTALVISIT, " +
							"SUM(V.Ppay+V.Ipay) AS TOTALCHARGES, " +
							"AVG(V.Ppay+V.Ipay) AS AVERAGEPAY " +
					"FROM PATIENT P, VISITOUTCOME V, STAFF S " +
					"WHERE P.SSN=V.SSN " +
									"AND S.Eno=V.Eno " +
									"AND V.Xdate >= 160101 " +
									"AND V.Xdate <= 160331 " 
					
					);
			
			ResultSet myresults4 = stmt4.executeQuery(
					"SELECT * " +
			        "FROM STAFF " +
					"MINUS " +
					"(SELECT S.Eno, S.Ename, S.Title " +
					"FROM STAFF S, VISITOUTCOME V " +
					"WHERE S.Eno=V.Eno AND V.Xdate >= 160101 AND V.Xdate <= 160331 )"
					);
			
			System.out.println("===========================FIRST REPORT===============================");
			System.out.println("\tStaff Activity for the Quarter January 1 - March 31, 2016");
			System.out.println("======================================================================\n");
			
			
			while (myresults.next() && myresults2.next()) //pass to the next row and loop until the last
			{
				System.out.println("Employee Number: (number),\tName: (name),  Title: (title)");
				System.out.println("-------------\t---------\t--------------"); //Print a header
				System.out.println("\t\t"+myresults.getString("Eno") +
						"\t\t" + myresults.getString("Ename") +
						"\t\t" + myresults.getString("Title")); //Print the current row
				
				System.out.println("\tDate: \t"+myresults2.getString("Xdate") +
						"\n\tPatient SSN: \t "+myresults2.getString("SSN") +
						"\n\tPatient Name: \t "+myresults2.getString("pname") +
						"\n\tTotal charge: \t $"+myresults2.getString("TotalCharge")+".00"
						); //Print the current row
				

				System.out.println("----------------------------------------");
				System.out.println("--------------------------------------\n");
			}
			
			System.out.println("--------------------------------------------");
			while (myresults3.next()){
					System.out.println("\tTOTAL NUMBER OF VISITS: \t\t "+myresults3.getString("TOTALVISIT") +
					 "\n\tTOTAL CHARGES: \t\t\t\t $"+myresults3.getString("TOTALCHARGES")+".00" +	  
					 "\n\tAVERAGE PAY PER VISIT: \t\t\t $"+myresults3.getString("AVERAGEPAY")+".00"+"\n"
				); //Print the current row
			}
			System.out.println("========================END SUMMARY============================");
			System.out.println("---------------------------------------------------------------");
			System.out.println("\tThe following staff members were not active in this period");
			
			System.out.println("-------------------------------------------------------------\n");
			System.out.println("Employee Number: (number),\tName: (name),  Title: (title)");
			System.out.println("-------------\t---------\t--------------"); //Print a header
			while (myresults4.next()){
				System.out.println("\t\t"+myresults4.getString("Eno") +
						"\t\t" + myresults4.getString("Ename") +
						"\t\t" + myresults4.getString("Title")); //Print the current row
			}
			
			
			
			System.out.println("\n===========================SECOND REPORT===============================");
			System.out.println("\tStaff Activity for the Quarter January 1 - March 31, 2016");
			System.out.println("========================================================================\n");
			
			Statement stmt5 = conn.createStatement(); //Create a new statement
			Statement stmt6 = conn.createStatement(); //Create a new statement
			Statement stmt7 = conn.createStatement(); //Create a new statement
			Statement stmt8 = conn.createStatement(); //Create a new statement
			
			//Now we execute our query and store the results in the my results object:
			ResultSet myresults5 = stmt5.executeQuery(
					"SELECT P.SSN, P.pname " +
					"FROM   PATIENT P " );
			
			stmt6 = conn.createStatement(); //Create a new statement
			ResultSet myresults6 = stmt6.executeQuery(
					"SELECT  V.Xdate, S.Eno, S.Ename,  " +
					"(V.Ppay+V.Ipay) AS TotalCharge, " +
					"V.Ppay, V.Ipay, ((V.Ppay+V.Ipay)-(V.Ppay+V.Ipay)) AS BALANCE " +
					"FROM PATIENT P, VISITOUTCOME V, STAFF S " +
					"WHERE P.SSN=V.SSN " +
									"AND S.Eno=V.Eno " +
									"AND V.Xdate >= 160101 " +
									"AND V.Xdate <= 160331 " +
					"ORDER BY S.Eno"
					);
			
			stmt7 = conn.createStatement(); //Create a new statement
			ResultSet myresults7 = stmt7.executeQuery(
					"SELECT  COUNT(P.SSN) AS TOTALVISIT, " +
							"SUM(V.Ppay+V.Ipay) AS TOTALCHARGES, " +
							"SUM(V.Ipay) AS TOTALPAIDBYINS, " +
							"SUM(V.Ppay) AS TOTALPAIDBYPAT " +
					"FROM PATIENT P, VISITOUTCOME V, STAFF S " +
					"WHERE P.SSN=V.SSN " +
									"AND S.Eno=V.Eno " +
									"AND V.Xdate >= 160101 " +
									"AND V.Xdate <= 160331 "

					);
			
			stmt8 = conn.createStatement(); //Create a new statement
			ResultSet myresults8 = stmt8.executeQuery(
					"SELECT  ((V.Ppay + V.Ipay)-(V.Ppay + V.Ipay)) AS BALANCE  " +
					"FROM PATIENT P, VISITOUTCOME V, STAFF S " +
					"WHERE P.SSN=V.SSN " +
									"AND S.Eno=V.Eno " +
									"AND V.Xdate >= 160101 " +
									"AND V.Xdate <= 160331 "

					);
			
			while (myresults5.next() && myresults6.next()) //pass to the next row and loop until the last
			{
				System.out.println("Patient SSN: ( SSN ),\t\tName: (name)");
				System.out.println("-------------\t---------"); //Print a header
				System.out.println("\t"+myresults5.getString("SSN") +
						"\t\t" + myresults5.getString("pname") ); //Print the current row
	
				System.out.println("\tVisit Date: \t\t"+myresults6.getString("Xdate") +
						"\n\tEmployee Number: \t "+myresults6.getString("Eno") +
						"\n\tEmployee Name: \t\t "+myresults6.getString("Ename") +
						"\n\tTotal charge: \t\t $"+myresults6.getString("TotalCharge")+".00"+
						"\n\tPatient Pay: \t\t $"+myresults6.getString("Ppay")+".00"+
						"\n\tInsurance Pay: \t\t $"+myresults6.getString("Ipay")+".00"+
						"\n\tBalance: \t\t $"+myresults6.getString("BALANCE")+".00"
						); //Print the current row
				
				System.out.println("-----------------------------------------");
				System.out.println("-----------------------------------------");
			}
			
			System.out.println("-----------SECOND SUMMARY------------------------");
			while (myresults7.next() && myresults8.next()){
					System.out.println("\tTOTAL NUMBER OF VISITS: \t "+myresults7.getString("TOTALVISIT") +
							"\n\tTotal charge: \t\t\t $"+myresults7.getString("TOTALCHARGES")+".00" +
							"\n\tTotal Paid By Insurance: \t $"+myresults7.getString("TOTALPAIDBYINS")+".00"+
							"\n\tTotal Paid By Patients: \t $"+myresults7.getString("TOTALPAIDBYPAT")+".00" +
							"\n\tBalance: \t\t\t $"+myresults8.getString("BALANCE")+".00"
				); //Print the current row
			}
			System.out.println("====================END SECOND SUMMARY============================");
			
			
			
			conn.close(); // Close connection.
			}
		catch (Exception e) {SQLError(e);} //if any error occurred in the try..catch block, call the SQLError function
	}
	
	public static void SQLError (Exception e) //function for handling SQL errors
	{
		System.out.println("ORACLE error detected:");
		e.printStackTrace();
	}
	
}
