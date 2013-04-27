package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logic.BabyName;
import logic.Session;
import logic.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private User user;
	private Session session;

	@Before
	public void createUser() throws Exception{
		HashMap<String, List<BabyName>> names = new HashMap<String, List<BabyName>>();
		user = new User("teste","@teste","teste");
		List<BabyName> listNames = new ArrayList<BabyName>();
		listNames.add(new BabyName("Hercules",0));
		names.put("email", listNames);
		session = new Session(names, "teste");
	}
	
	@Test
	public void testConstructor() {
		try{
			user = new User("", "@teste", "tst");
		}catch(Exception e){
			Assert.assertEquals("Invalid name!", e.getMessage());
		}try{
			user = new User(null, "@teste", "tst");
		}catch(Exception e){
			Assert.assertEquals("Invalid name!", e.getMessage());
		}try{
			user = new User("teste", "@teste", null);
		}catch(Exception e){
			Assert.assertEquals("Invalid password!", e.getMessage());
		}try{
			user = new User("teste", "", "tst");
		}catch(Exception e){
			Assert.assertEquals("Invalid email!", e.getMessage());
		}try{
			user = new User("teste", "testem", "tst");
		}catch(Exception e){
			Assert.assertEquals("Invalid email!", e.getMessage());
		}
	}
	@Test
	public void testSet(){
		try{
			user.setName("");
		}catch(Exception e){
			Assert.assertEquals("Invalid name!", e.getMessage());
		}try{
			user.setName(null);
		}catch(Exception e){
			Assert.assertEquals("Invalid name!", e.getMessage());
		}try{
			user.setPassword(null);
		}catch(Exception e){
			Assert.assertEquals("Invalid password!", e.getMessage());
		}try{
			user.setEmail("");
		}catch(Exception e){
			Assert.assertEquals("Invalid email!", e.getMessage());
		}
		try{
			user.setEmail("testem");
		}catch(Exception e){
			Assert.assertEquals("Invalid email!", e.getMessage());
		}
	}
	@Test
	public void testAddSession(){
		try{
			user.addSession(session);
		}catch(Exception e){
			Assert.fail("It wasn't expected exception!");
		}try{			
			user.addSession(session);
			Assert.fail();
		}
		catch(Exception e){
			Assert.assertEquals("This session already exists!", e.getMessage());
		}
	}
	@Test
	public void testRemoveSession(){
		try{
			user.addSession(session);
			user.removeSession(session);
		}catch(Exception e){
			Assert.fail("It wasn't expected exception!");
		}try{
			user.removeSession(session);
		}catch(Exception e){
			Assert.assertEquals("This session doesn't exist!", e.getMessage());
		}
	}
	
	@Test 
	public void testEquals(){
		User newUser = null;
		try{
			newUser = new User("nomenome", "nome@email", "senhanome"); 
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertFalse(user.equals(newUser));
		try{
			newUser.setEmail("@teste");
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertTrue(newUser.equals(user));
	}
	
	@Test
	public void testSearchSession(){
		testAddSession();
		try{
			Assert.assertEquals("teste", user.searchSession("teste").getId());
		}
		catch(Exception e){
			Assert.fail();
		}
		try{
			user.searchSession("session");
			Assert.fail();
		}
		catch(Exception e){
			Assert.assertEquals("Session not found!", e.getMessage());
		}
	}

}