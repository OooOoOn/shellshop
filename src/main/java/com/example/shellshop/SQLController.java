package com.example.shellshop;

import org.springframework.web.bind.annotation.*;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by OooOoOn on 06/04/2019.
 */

@CrossOrigin(origins = "*")
@RestController
public class SQLController {

	private String url = "jdbc:mysql://localhost:3306/shop?&serverTimezone=UTC";
	private String user = "OooOoOn";
	private String password = "secret";

	private PreparedStatement pstmt = null;
	private Connection conn = null;

	private List<Phone> phones = new ArrayList<>();
	private List<Shell> shells = new ArrayList<>();
	private String model;
	private int price;
	private String manufacturer;
	private int weight;
	private String color;
	private String name;
	private String deletedShells;
	private int id;
	private String description;
	private int success;

	/**
	 *
	 * //Method to display phones.
	 *
	 */

	@RequestMapping(value = "/getPhones", method = RequestMethod.GET)
	public List<Phone> getPhones() throws SQLException {

		phones.clear();

		try {
			establishConnection();
			pstmt = conn.prepareStatement("SELECT * FROM shop.phones");
			ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					model = rs.getString("Model");
					manufacturer = rs.getString("Manufacturer");
					weight = rs.getInt("Weight");
					phones.add(new Phone(model, manufacturer, weight));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

		return phones;

	}

	/**
	 *
	 * //Method to add phone.
	 *
	 */

	@RequestMapping(value = "/addPhone", method = RequestMethod.POST)
	public String addPhone(@RequestParam String model, @RequestParam String manufacturer,
			@RequestParam int weight) throws SQLException {

		if (model.trim().length() < 1 || manufacturer.trim().length() < 1)
			return "Insert failed. Model and manufacturer must contain values.";

		try {
			establishConnection();
			pstmt = conn.prepareStatement("INSERT INTO shop.phones (Model, Manufacturer, Weight) VALUES (?, ?, ?)");
			pstmt.setString(1, model.trim().toUpperCase());
			pstmt.setString(2, manufacturer.trim().toUpperCase());
			pstmt.setInt(3, weight);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			return e.toString();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				return ex.toString();
			}
		}

		return "Phone '" + model + "' successfully added!";
	}

	/**
	 *
	 * //Method to display shells based on phone model.
	 * 
	 *
	 */

	@RequestMapping(value = "/getShells", method = RequestMethod.GET)
	public List<Shell> getShells(@RequestParam String model) throws SQLException {

		shells.clear();

		try {
			establishConnection();
			pstmt = conn.prepareStatement("SELECT * FROM shop.shells WHERE Model = ?");
			pstmt.setString(1, model.toUpperCase().trim());
			ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					color = rs.getString("Color");
					price = rs.getInt("Price");
					model = rs.getString("Model");
					name = rs.getString("Name");
					id = rs.getInt("Id");
					description = rs.getString("Description");
					shells.add(new Shell(color, price, model, name, id, description));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

		return shells;

	}

	/**
	 *
	 * //Method to add shells.
	 * 
	 *
	 */

	@RequestMapping(value = "/addShell", method = RequestMethod.POST)
	public String addShell(@RequestParam String color, @RequestParam int price, @RequestParam String model,
			@RequestParam String name, @RequestParam (required = false, defaultValue = "") String description) throws SQLException {
		
		if (color.trim().length() < 1 || model.trim().length() < 1 || name.trim().length() < 1)
			return "Insert failed. Color, model and name must contain values.";

		try {
			establishConnection();
			pstmt = conn.prepareStatement("SELECT Model FROM shop.phones WHERE Model = ?");
			pstmt.setString(1, model);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return "Unable to add shell. Phone model not in store.";
			}

			pstmt = conn.prepareStatement(
					"INSERT INTO shop.shells (Color, Price, Model, Name, Description) VALUES (?, ?, ?, ?, ?)");

			pstmt.setString(1, color.trim().toUpperCase());
			pstmt.setInt(2, price);
			pstmt.setString(3, model.trim().toUpperCase());
			pstmt.setString(4, name.trim().toUpperCase());
			pstmt.setString(5, description.trim());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return e.toString();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				return ex.toString();
			}
		}

		return "Shell successfully added!";
	}

	/**
	 *
	 * //Method to delete phones by model.
	 * 
	 *
	 */

	@RequestMapping(value = "/deletePhone", method = RequestMethod.DELETE)
	public String deletePhone(@RequestParam String model) throws SQLException {

		try {
			establishConnection();
			pstmt = conn.prepareStatement("DELETE FROM shop.phones WHERE Model = ?");
			pstmt.setString(1, model.trim().toUpperCase());
			success = pstmt.executeUpdate();
			deletedShells = deleteShell(model.trim().toUpperCase());
		} catch (SQLException e) {
			return e.toString();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				return ex.toString();
			}
		}
		if (success == 1)
			return "Phone model '" + model.trim().toUpperCase() + "' successfully deleted!" + deletedShells;

		return "Phone model does not exist!";
	}

	/**
	 *
	 * //Method to delete shells by phone model.
	 * 
	 *
	 */

	@RequestMapping(value = "/deleteShell", method = RequestMethod.DELETE)
	public String deleteShell(String model) throws SQLException {

		try {
			establishConnection();
			pstmt = conn.prepareStatement("DELETE FROM shop.shells WHERE Model = ?");
			pstmt.setString(1, model.trim().toUpperCase());
			success = pstmt.executeUpdate();
		} catch (SQLException e) {
			return e.toString();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				return ex.toString();
			}
		}

		if (success == 1)
			return "\nShell(s) associated with model: '" + model.toUpperCase() + "' have been successfully deleted!";

		else
			return "No associated shells found";
	}

	/**
	 *
	 * //Method to delete shells by id.
	 * 
	 *
	 */

	@RequestMapping(value = "/deleteShellById", method = RequestMethod.DELETE)
	public String deleteShell(int id) throws SQLException {

		try {
			establishConnection();
			pstmt = conn.prepareStatement("DELETE FROM shop.shells WHERE Id = ?");
			pstmt.setInt(1, id);
			success = pstmt.executeUpdate();

		} catch (SQLException e) {
			return e.toString();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				return ex.toString();
			}
		}
		if (success == 1)
			return "\nShell successfully deleted!";

		return "Shell id does not exist!";
	}

	/**
	 *
	 * //Method to establish database connection.
	 * 
	 *
	 */

	private void establishConnection() throws SQLException {
		conn = DriverManager.getConnection(url, user, password);
	}
}