package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;
import logic.BabyName;
import logic.Session;
import logic.SystemLogic;
import logic.SystemReader;
import logic.User;

import org.junit.Before;
import org.junit.Test;

public class SystemLogicTest {
	private User user;
	private User user2;
	private SystemLogic system = new SystemLogic();
	
	@Before
	public void testSystem() throws Exception{
		user = new User("Hercule","hercules@gmail.com","102");
		user2 = new User("Maria","maria@gmail.com","202");		
	}
	
	@Test
	public void testRegistersUser() {
		try{
			system.registersUser(user);
			system.registersUser(user2);
		}
		catch(Exception e){
			Assert.fail();
		}
		try{
			User newUser = new User("Mariana", "maria@gmail.com", "0987");
			system.registersUser(newUser);
			Assert.fail();
		}
		catch(Exception e){
			Assert.assertEquals("This email is already register!", e.getMessage());
		}
	}

	@Test
	public void testRemoveUser() {
		try{
			system.registersUser(user);
		}
		catch(Exception e){
			Assert.fail();
		}
		try{
			system.removeUser(user);
		}
		catch(Exception e){
			Assert.fail();
		}
		try{
			system.removeUser(user);
			Assert.fail();
		}
		catch(Exception e){
			Assert.assertEquals("User not found!", e.getMessage());			
		}
	}

	@Test
	public void testOpenSession() {
		HashMap<String, List<BabyName>> map = new HashMap<String, List<BabyName>>();
		Session s = null;
		try{
			s = new Session(map, "teste");
		}
		catch(Exception e){
			Assert.fail();
		}
		try{
			user.addSession(s);
		}
		catch(Exception e){
			Assert.fail();
		}
		try{
			Assert.assertEquals("teste", system.openSession(user, "teste").getId());
		}
		catch(Exception e){
			Assert.fail();
		}
		try{
			system.openSession(user, "id");
			Assert.fail();
		}
		catch(Exception e){
			Assert.assertEquals("Session not found!", e.getMessage());
		}
	}

	@Test
	public void testLogin() {
		try{
			system.registersUser(user2);
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertEquals("maria@gmail.com", system.login("maria@gmail.com", "202").getEmail());
		Assert.assertEquals(null, system.login("maria@gmail.com", "22"));
		Assert.assertEquals(null, system.login("mariam@gmail.com", "202"));
	}

	@Test
	public void testSearchUser() {
		try{
			system.registersUser(user);
			system.registersUser(user2);
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertEquals(true, system.searchUser("hercules@gmail.com"));
		Assert.assertEquals(false, system.searchUser("maria"));
		Assert.assertEquals(true, system.searchUser(user2.getEmail()));	
	}

	@Test
	public void testFilterByType() {
		SystemReader reader = new SystemReader();
		try{
			reader.openFile("boys.txt");
		}
		catch(Exception e){
			Assert.fail();
		}
		ArrayList<String> tipos = new ArrayList<String>();
		tipos.add("boys.txt");
		List<BabyName> lista = null;
		try{
			lista = system.filterByType(tipos);
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertTrue(lista.size() == reader.getNames().size());
		for (int i = 0; i < lista.size(); i++){
			Assert.assertTrue(lista.get(i).getName().equals(reader.getNames().get(i).getName()));
		}
		tipos.add("girls.txt");
		List<BabyName> nomes = new ArrayList<BabyName>();
		nomes.addAll(reader.getNames());
		try{
			reader.openFile("girls.txt");
		}
		catch(Exception e){
			Assert.fail();
		}
		nomes.addAll(reader.getNames());
		try{
			lista = system.filterByType(tipos);
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertTrue(lista.size() == nomes.size());
	}

	@Test
	public void testFilterByGender() {
		SystemReader reader = new SystemReader();
		try{
			reader.openFile("girls.txt");
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertEquals(3, system.filterByGender(reader.getNames(), 1).size());
		Assert.assertEquals(0, system.filterByGender(reader.getNames(), 0).size());
	}

	@Test
	public void testCompareGrades() {
		HashMap<String, List<BabyName>> map = new HashMap<String, List<BabyName>>();
		List<Integer> notas = new ArrayList<Integer>();
		notas.add(0);
		notas.add(2);
		notas.add(2);
		SystemReader reader = new SystemReader();
		try{
			reader.openFile("boys.txt");
		}
		catch(Exception e){
			Assert.fail();
		}
		ArrayList<BabyName> nomes = reader.getNames();
		try{
			system.giveGrade(nomes, notas);
		}
		catch(Exception e){
			Assert.fail();
		}
		map.put("email@1", nomes);
		ArrayList<BabyName> names = reader.getNames();
		try{
			system.giveGrade(names, notas);
		}
		catch(Exception e){
			Assert.fail();
		}
		map.put("email@2", names);
		Assert.assertEquals(2, system.compareGrades(map).size());
		notas = new ArrayList<Integer>();
		notas.add(1);
		notas.add(1);
		notas.add(1);
		try{
			system.giveGrade(nomes, notas);
			system.giveGrade(names, notas);
		}
		catch(Exception e){
			Assert.fail();
		}
		map = new HashMap<String, List<BabyName>>();
		map.put("email", nomes);
		map.put("teste@", names);
		Assert.assertEquals(3, system.compareGrades(map).size());
	}

	@Test
	public void testFindTheMostPopular() {
		HashMap<String, List<BabyName>> map = new HashMap<String, List<BabyName>>();
		try{
			Assert.assertEquals(null, system.findTheMostPopular(map));
		}
		catch(Exception e){
			Assert.fail();
		}
		List<Integer> notas = new ArrayList<Integer>();
		notas.add(3);
		notas.add(1);
		notas.add(2);
		SystemReader reader = new SystemReader();
		try{
			reader.openFile("girls.txt");
		}
		catch(Exception e){
			Assert.fail();
		}
		ArrayList<BabyName> nomes = reader.getNames();
		ArrayList<BabyName> names = reader.getNames();
		try{
			system.giveGrade(nomes, notas);
			system.giveGrade(names, notas);
		}
		catch(Exception e){
			Assert.fail();
		}
		map.put("email@test", nomes);
		map.put("test@email", names);
		try{
			Assert.assertEquals("Amelie", system.findTheMostPopular(map).getName());
		}
		catch(Exception e){
			Assert.fail();
		}
	}

	@Test
	public void testSessionName() {
		HashMap<String, List<BabyName>> map = new HashMap<String, List<BabyName>>();
		SystemReader reader = new SystemReader();
		Session session = null;
		BabyName name = null;
		try{
			reader.openFile("girls.txt");
		}
		catch(Exception e){
			Assert.fail();
		}
		try{
			session = new Session(map, "testeNull");
			Assert.assertEquals(null, system.sessionName(session));
		}
		catch (Exception e){
			Assert.fail();
		}
		map.put("teste", reader.getNames());
		try{
			session = new Session(map, "teste");
			Assert.assertEquals(null, system.sessionName(session));
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			Assert.fail();
		}
		try{
			name = new BabyName("Bia", 1);
		}
		catch(Exception e){
			Assert.fail();
		}
		List<BabyName> listNames = new ArrayList<BabyName>();
		listNames.add(name);
		map = new HashMap<String, List<BabyName>>();
		map.put("email", listNames);
		try{
			session = new Session(map, "teste2");
			Assert.assertEquals(name, system.sessionName(session));
		}
		catch (Exception e){
			Assert.fail();
		}
	}

}
