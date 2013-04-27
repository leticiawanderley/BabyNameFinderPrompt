package tests;

import logic.BabyName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BabyNameTest {

	private BabyName babyName;
	
	@Before
	public void CreateBabyName() throws Exception{	
		babyName = new BabyName("Hercules",0);
	}
	
	@Test 
	public void testConstructor(){
		try{
			babyName = new BabyName("", 0);
		}catch(Exception e){
			Assert.assertEquals("Invalid name!", e.getMessage());
		}try{
			babyName = new BabyName(null, 0);
		}catch(Exception e){
			Assert.assertEquals("Invalid name!", e.getMessage());
		}try{
			babyName = new BabyName("teste", -1);
		}catch(Exception e){
			Assert.assertEquals("Invalid gender!", e.getMessage());
		}try{
			babyName = new BabyName("teste", 0);
		}catch(Exception e){
			Assert.fail("It isn't expected exception!");
		}	
	}
	
	@Test
	public void testSets() {		
		try{
			babyName.setName("");
		}catch(Exception e){
			Assert.assertEquals("Invalid name!", e.getMessage());
		}try{
			babyName.setName(null);
		}catch(Exception e){
			Assert.assertEquals("Invalid name!", e.getMessage());
		}try{
			babyName.setGrade(-1);
		}catch(Exception e){
			Assert.assertEquals("Invalid grade!", e.getMessage());
		}try{
			babyName.setGender(-1);
		}catch(Exception e){
			Assert.assertEquals("Invalid gender!", e.getMessage());
		}try{
			babyName.setGender(0);
			babyName.setGrade(1);
			babyName.setName("Hercules");
		}catch(Exception e){
			Assert.fail("It isn't expected exception!");
		}	
	}
}

