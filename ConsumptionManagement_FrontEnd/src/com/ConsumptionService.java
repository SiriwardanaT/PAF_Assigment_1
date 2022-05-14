package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DbConnection;

public class ConsumptionService {

	public static String getAllConsumptions() throws ClassNotFoundException, SQLException {
		Connection con = DbConnection.getDbConnection();
		if (con == null) {
			return "Database connection errror";
		} else {

			PreparedStatement preparedStatement = con.prepareStatement("select * from consumption");

			ResultSet rs = preparedStatement.executeQuery();
			String output = "";

			output = "<table border='1'>";
			output += "<tr>" + "    <th>ConsumptionID</th>" + "    <th>Units</th>" + "    <th>Reading Date</th>"
					+ "    <th>unitPrice</th>" + "    <th>Last Reading</th>" + "    <th>Currunt Reading</th>"
					+ "    <th>Account No</th>" + "    <th>Admin Approvement</th>" + "  </tr>";
			while (rs.next()) {
				output += "<tr>";
				output += "<td>" + rs.getString(1) + "</td>";
				output += "<td>" + rs.getString(2) + "</td>";
				output += "<td>" + rs.getString(3) + "</td>";
				output += "<td style='color:red'>" + rs.getString(4) + "</td>";
				output += "<td>" + rs.getString(5) + "</td>";
				output += "<td>" + rs.getString(6) + "</td>";
				output += "<td>" + rs.getString(7) + "</td>";
				output += "<td>" + rs.getString(8) + "</td>";
				output += "<td><input id='btnRemove' name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-cid='"
						+ rs.getString(1) + "'>" + "</td>";
				output += "<td><input id='btnUpdate' name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-info' data-cid='"
						+ rs.getString(1) + "'>" + "</td>";
				output += "</tr>";
			}
			output += "</table>";
			return output;
		}

	}

	// Add Consumption record
	public static String AddConsmptionRecord(String Acc, int crm, double uprice, String date) {
		try {
			String output = "";
			Connection con = DbConnection.getDbConnection();
			if (con == null) {
				return "Database connection errror";
			} else {

				int lastReading = getLastReading(Acc);
				if (lastReading < crm) {
					PreparedStatement preparedStatement = con.prepareStatement(
							"insert into consumption (units,date,unitPrice,lsReading,cuReading,Macc,status,createBy,createDate,modifiedBy,modifiedDate)  values(?,?,?,?,?,?,?,?,?,?,?)");
					preparedStatement.setDouble(1, calulateUnits(Acc, crm));
					preparedStatement.setString(2, date);
					preparedStatement.setDouble(3, uprice);
					preparedStatement.setInt(4, getLastReading(Acc));
					preparedStatement.setInt(5, crm);
					preparedStatement.setString(6, Acc);
					preparedStatement.setInt(7, 0);
					preparedStatement.setInt(8, 1);
					preparedStatement.setString(9, date);
					preparedStatement.setInt(10, 1);
					preparedStatement.setString(11, date);

					boolean res = preparedStatement.execute();

					if (!res) {
						String Consumptions = getAllConsumptions();
						output = "{\"Mstatus\":\"success\", \"data\": \"" + Consumptions + "\"}";
						return output;
					} else {
						output = "{\"Mstatus\":\"error\", \"data\":\"Error while inserting the item.\"}";

						return output;
					}
				} else {
					output = "{\"Mstatus\":\"error\", \"data\":\"Currunt reading Cannot be less that last reading \"}";

					return output;
				}

			}
		} catch (Exception e) {
			return "{\"Mstatus\":\"error\", \"data\":\"Error while Inserting the item. \"}";
		}

	}

	public static String updateConsumption(int status, double uprice, int id)
			throws ClassNotFoundException, SQLException {
		Connection con = DbConnection.getDbConnection();
		String output = "";
		if (con == null) {
			return "Database connection errror";
		} else {
			PreparedStatement preparedStatement = con
					.prepareStatement("update consumption set status = ? , unitPrice = ?  where id = ? ");
			preparedStatement.setInt(1, status);
			preparedStatement.setDouble(2, uprice);
			preparedStatement.setInt(3, id);

			int res = preparedStatement.executeUpdate();

			if (res == 1) {
				String Consumptions = getAllConsumptions();
				output = "{\"Mstatus\":\"success\", \"data\": \"" + Consumptions + "\"}";
				return output;
			} else {
				return output = "Error:occoured";
			}

		}
	}

	// helper methods
	public static int getLastReading(String Macc) throws ClassNotFoundException, SQLException {
		Connection con = DbConnection.getDbConnection();
		if (con == null) {
			return 0;
		} else {
			PreparedStatement preparedStatement = con
					.prepareStatement("select cuReading from consumption where Macc = ? order by id desc limit 1");
			preparedStatement.setString(1, Macc);
			int lsReading = 0;
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				lsReading = rs.getInt(1);
			}
			return lsReading;
		}

	}

	public static String deleteConsumptionRecord(int id) throws ClassNotFoundException, SQLException {
		Connection con = DbConnection.getDbConnection();
		if (con == null) {
			return " ";
		} else {
			PreparedStatement preparedStatement = con.prepareStatement("delete from consumption where id = ?");
			preparedStatement.setInt(1, id);
			int res = preparedStatement.executeUpdate();
			System.out.println(res);
			String output = "";
			if (res == 1) {
				String Consumptions = getAllConsumptions();
				output = "{\"Mstatus\":\"success\", \"data\": \"" + Consumptions + "\"}";
				return output;
			} else {
				output = "{\"Mstatus\":\"error\", \"data\": \"" + "Error while deleting the item" + "\"}";
				return output;
			}
		}
	}

	public static double calulateUnits(String Macc, int currentReading) throws ClassNotFoundException, SQLException {
		int last = getLastReading(Macc);
		double units = currentReading - last;
		return units;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

	}

}
