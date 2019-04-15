package com.example.shellshop;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShellshopApplicationTests {
	
	SQLController s1 = new SQLController();

	@Test
	public void addShellSuccess() throws SQLException {
		s1.addPhone("Devil 666", "Grim Reaper incorporated", 666);
		assertEquals("Shell successfully added!", s1.addShell("red", 1000, "Devil 666", "Blood red", "#TrendyWarlock"));
	}
	
	@Test
	public void addShellFailureDuplicate() throws SQLException {
		s1.addPhone("test", "test", 0);
		s1.addShell("Rainbow", 1000, "test", "Lacoste proud", "One shell to rule them all");
		String reply = s1.addShell("Rainbow", 1000, "test", "Lacoste proud", "One shell to rule them all");
		assertTrue(reply.contains("Duplicate entry"));
	}
	
	@Test
	public void addPhoneSuccess() throws SQLException {
		assertEquals("Phone 'jons phone' successfully added!", s1.addPhone("jons phone", "Jon AB", 125));
	}
	
	@Test
	public void addPhoneFailure() throws SQLException {
		String reply = s1.addPhone(" ", "Jon AB", 0);
		assertTrue(reply.contains("must contain values"));
	}

}
