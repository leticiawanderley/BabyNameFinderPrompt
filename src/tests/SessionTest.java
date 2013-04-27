package tests;

import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;
import logic.BabyName;
import logic.Session;

import org.junit.Before;
import org.junit.Test;

public class SessionTest {
	private Session sessao;
	private HashMap<String, List<BabyName>> names;

	@Before
	public void CreateSession() throws Exception{
		names = new  HashMap<String, List<BabyName>>();;
		sessao = new Session(names, "1");	
	}
	
	@Test
	public void testSession() {
		Session s = null;
		try{
			s = new Session(names, "");
			Assert.fail();
		}catch(Exception e){
			Assert.assertEquals("Invalid id!", e.getMessage());
		}
		try{
			s = new Session(names, "teste");
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertEquals("teste", s.getId());
		try{
			s = new Session(names, null);
			Assert.fail();
		}catch(Exception e){
			Assert.assertEquals("Invalid id!", e.getMessage());
		}
	}
	
	@Test
	public void testSet(){
		try{
			sessao.setId("id");
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertEquals("id", sessao.getId());
		try{
			sessao.setId("");
			Assert.fail();
		}catch(Exception e){
			Assert.assertEquals("Invalid id!", e.getMessage());
		}
		try{
			sessao.setId(null);
			Assert.fail();
		}catch(Exception e){
			Assert.assertEquals("Invalid id!", e.getMessage());
		}
	}
	
	@Test
	public void testEquals(){
		Session s = null;
		try{
			s = new Session(names, "teste");
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertFalse(s.equals(sessao));
		try{
			s.setId("1");
		}
		catch(Exception e){
			Assert.fail();
		}
		Assert.assertTrue(sessao.equals(s));
	}

}
