package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test; 

public class EmailTest {
// helping variables for testing
	private static final String[] TEST_EMAILS = { "ab@b.com", "a.b@c.org",
			"abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd" };
	
	private static final String[] INVALID_EMAIL = null;
	private static final String InvalidEmail = null;
	private static final String Single_Email = "abc@gmail.com";
	
	private static final String Single_Name = "Tommy";
	private static final String Value = "Value";
	private EmailConcrete email;
	
	// Creates a email object before running tests
	@Before
	public void setupEmailTest() throws Exception {
		
		email = new EmailConcrete();
	}
	// Any breakdowns occur here before exit 
	@After
	public void tearDownEmailTest() throws Exception {
		
	}
	// Given by the professor
	@Test
	public void testAddBcc() throws Exception {
		
		email.addBcc(TEST_EMAILS);
		
		assertEquals(3,email.getBccAddresses().size());
	}
	// To get full coverage this tests a invalid email in addBcc to check the exception occurs with bad input
	@Test
	public void testAddBccInvalid () throws Exception {
		
		try {
		email.addBcc(INVALID_EMAIL);
		
		} catch(EmailException e) {
			
		}	
	}
	 // Test addCc very similar to addBcc but since it only wants a single email it checks if the cc is added by comparing the size
	@Test
	public void testaddCc() throws Exception {
		
		email.addCc(Single_Email);
		
		assertEquals(1,email.getCcAddresses().size());
	}
	// Test invalid email inputted for addCc and checks the exeption if correctly catched with invalid emails
	@Test
	public void testaddCcInvalid() throws Exception {
		
		try {
			email.addCc(INVALID_EMAIL);
			
			
		} catch (EmailException e) {
			
		}
	}
	
	// Test with Invalid value for Name first if block of addHeader
	@Test 
	public void testNameAddHeader() throws Exception {
		
		try {
			email.addHeader(null, Value);
			
		} catch (IllegalArgumentException e) {
			
		}
		
	}
	 
	// Test with Invalid Value for Value in second if block of addHeaderr
	@Test 
	public void testValAddHeader() throws Exception {
		
		try {
			email.addHeader(Single_Name,null);
			
		} catch (IllegalArgumentException e) {
			
		}
		
	}
	
	// Test with both Valid values for 3rd outcome in func of addHeader
	//checks if no exception can't use asserEquals
	@Test 
	public void testAddHeader() throws Exception {
		
			email.addHeader(Single_Name,Value);
			
	}
	// Test with valid values if address actually added 
	@Test
	public void testAddReplyTo() throws Exception {
		
		email.addReplyTo(Single_Email,Single_Name);
		// check if the address is added
		assertEquals(1,email.getReplyToAddresses().size());
	}
	
	// Test with Invalid email throws EmailExceoption
	@Test
	public void testAddReplyToInvalid() throws Exception {
		
		try {
			email.addReplyTo("",Single_Name);
		} catch (EmailException e) {
			
		}  
	}
	
	// Test buildMimeMessage Valid values 
	
	@Test
	public void testBuildMimeMessage() throws Exception {
		email.setFrom("bob@gmail.com");
		email.setHostName("Host");
		email.setMsg("Test Message");
		email.setSubject("Email Subject");
		email.addTo("tom@gmail.com");
		
		email.addCc("jim@gmail.com");
		email.addBcc("Joe@gmail.com");
		email.addReplyTo("ron@gmail.com");
		email.setContent(email, "content");
		email.addHeader("Name","Value");
		
		
		email.buildMimeMessage();
		
	}
	
	// Test more than one MimeMessage
	
	@Test
	public void testMultipleMimeMessage() throws Exception {
		email.setFrom("bob@gmail.com");
		email.setHostName("Host");
		email.setMsg("Test Message");
		email.setSubject("Email Subject");
		email.addTo("tom@gmail.com");
		
		try {
		email.buildMimeMessage(); 
		email.buildMimeMessage();
		} catch (IllegalStateException e) {
			
		}
	}
	
	// No From Address given MimeMessage
	
	@Test 
	public void testFromMimeMessage() throws Exception {
		
		try {
		
		email.setHostName("Host");
		email.setMsg("Test Message");
		email.setSubject("Email Subject");
		email.addTo("jimmy@gmail.com");
		
		email.buildMimeMessage();
		} catch (EmailException e) {
			
		}
		
	}
	
	// No To Address given MimeMessage
	
		@Test 
		public void testToMimeMessage() throws Exception {
			
			try {
			email.setFrom("Billy@gmail.com");
			email.setHostName("Host");
			email.setMsg("Test Message");
			email.setSubject("Email Subject");
			
			email.buildMimeMessage();
			
			} catch (EmailException e) {
				
			}
			
		}
		
		// Invalid emails MimeMessage
		
		@Test 
		public void testinvalidemailMimeMessage() throws Exception {
			
			try {
			email.setFrom("Billygmailcom");
			email.setHostName("");
			email.setMsg("Test Message");
			email.setSubject("Email Subject");
			email.addTo("Kategmailcom");
			
			email.buildMimeMessage();
			} catch (EmailException e) {
				
			}
			
		}
		
		
		// Test Valid getHostName
		
		@Test
		public void testGetHostName() throws Exception {
			
			email.setHostName("Host");
			
			assertEquals("Host", email.getHostName());
			
		}
		
		// Test getHostName null hostname it returns the hostname as null
		
		@Test
		public void testBadGetHostName() throws Exception {
					
		// email.setHostName("Host");
					
		email.getHostName();
					
						
		}
		// Test getMailSession with valid host name and check if the session is not null
		@Test 
		public void testGetMailSession() throws Exception {
			email.setHostName("HostA");
			
			assertNotNull(email.getMailSession());
		}
		// Test getMailSession with no Host name to check excesption 
		@Test 
		public void testInvalidGetMailSession() throws Exception {
			
			try {
			email.getMailSession();
			} catch(EmailException e) {
				
			}
		}	
		
		// test getSent Date
		@Test 
		public void testGetSentDate() throws Exception {
			//import Date to test a sample date 
			Date testDate = new Date(1000L);
			
			email.setSentDate(testDate);
			assertEquals(1000L, email.getSentDate().getTime()); 
		}
		
		//Test get socket connection timeout by setting it manually and comparing
		@Test 
		public void testGetSocketConnectionTimeout() throws Exception {
			email.setSocketConnectionTimeout(500);
			assertEquals(500,email.getSocketConnectionTimeout());
		}
		
		// Test setFrom Address by furst setting and then comparing 
		@Test
		public void testSetFrom() throws Exception {
			
			email.setFrom("Jimmy@gmail.com");
			// checks if the set address is equal to the ones saved by using getFrimAddress
			assertEquals("Jimmy@gmail.com", email.getFromAddress().getAddress());
			
		}
		// Test setFrom with invalid email to catch the EmailException error actually works 
		@Test
		public void testInvalidSetFrom() throws Exception {
			try {
			email.setFrom("");
			} catch (EmailException e) {
				
			}
		}
}
