package com.niemiec.connection;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

public class ConnectionTest {
	
	Socket socket;
			
			
	
	@Before
	private void createSocket() {
		try {
			socket = new Socket("localhost", 6666);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testRun() {
		fail("Not yet implemented");
	}

	@Test
	public void testInterrupt() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeTheConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsConnected() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendTheObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloseTheConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetClient() {
		fail("Not yet implemented");
	}

}
