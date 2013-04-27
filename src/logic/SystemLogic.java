package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import logic.BabyName;
import logic.GradeComparator;
import logic.Session;
import logic.SystemReader;
import logic.User;
/**
 * @author Ana Luiza Motta Gones 
 * @author Leticia Farias Wanderley
 *	@author Maysa De Macedo Souza
 *
 */
public class SystemLogic implements Serializable{

	private List<User> users = new ArrayList<User>();
	private List<String> types = new ArrayList<String>();

	/**
	 * Constructor of Sistema 			
	 */
	public SystemLogic(){
		this.types.add("girls.txt");
		this.types.add("boys.txt");
		this.types.add("IndianNames.txt");
		this.types.add("NamesfromtheAlcoran.txt");
		this.types.add("NewTestament.txt");
		this.types.add("TorahNames.txt");
		this.types.add("FrenchNames.txt");
		this.types.add("EnglishNames.txt");
		this.types.add("SpanishNames.txt");
		this.types.add("BrazilianNames.txt");
		this.types.add("FamousNames.txt");
		this.types.add("ItalianNames.txt");
	}

	
	/**
	 * Registers a new user at the system
	 * @param user
	 * 		User to be registered
	 * @throws Exception if the user is already registered
	 */
	public void registersUser(User user) throws Exception{
		if (this.users.contains(user)){
			throw new Exception("This email is already register!");
		}
		this.users.add(user);
	}
	
	/**
	 * Removes user
	 * @param user
	 * 		User to be removed
	 * @throws Exception if the user is not found
	 */
	public void removeUser(User user) throws Exception{
		boolean checker = true;
		
		if (this.users.contains(user)){
			checker = false;
			this.users.remove(user);
		}
		if (checker){
			throw new Exception("User not found!");
		}
	}
	
	/**
	 * Opens a session of the user
	 * @param user
	 * 		Owner of the session
	 * @param id
	 * 		Name of the session
	 * @return session
	 * @throws Exception if the session is not found
	 */
	public Session openSession(User user, String id) throws Exception{
		for (Session s : user.getSessions()){
			if (s.getId().equals(id)){
				return s;
			}
		}
		throw new Exception("Session not found!");
	}
	
	/**
	 * Logs the user in to the system
	 * @param email
	 * 		User's email
	 * @param password
	 * 		User's password
	 * @return if the user is logged or not
	 */
	public User login(String email,String password){
		return confirmsLogin(email,password);
	}
	
	/**
	 * Compares and confirms the email of the user and its password
	 * @param email
	 * 		User's email
	 * @param password
	 * 		User's password
	 * @return if the email and the password match or not
	 */
	private User confirmsLogin(String email, String password){
		for(User user: this.users){
			if(user.getEmail().equals(email)){
				if(user.getPassword().equals(password)){
					return user;
				}else{
				return null;
				}
			} 
		}return null;
	}

	/**
	 * Search for the user in the system list
	 * @param email
	 * 		User's email
	 * @return if the user was found or not
	 */
	public boolean searchUser(String email){
		for (User user : this.users){
			if (user.getEmail().equals(email)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Filters the names at the files by its types 
	 * @param tag
	 * 		Types of names chosen by the session owner
	 * @return a list with the names of the chosen types
	 * @throws Exception if the file is not found
	 */
	public List<BabyName> filterByType(List<String> tag) throws Exception{
		List<BabyName> names = new ArrayList<BabyName>();
		List<BabyName> nameType;
		SystemReader reader = new SystemReader();
		for (String tipo : this.types){
			nameType = new ArrayList<BabyName>();
			if (tag.contains(tipo)){
				reader.openFile(tipo);
				nameType = reader.getNames();
			}
			names.addAll(nameType);
		}
		return names;
	}

	/**
	 * Filters the names by its gender
	 * @param names
	 * 		List of names to be sorted by gender
	 * @param g
	 * 		Gender chosen (0 for boys and 1 for girls)
	 * @return a list with the names of the chosen gender
	 */
	public List<BabyName> filterByGender(List<BabyName> names, int g){
		List<BabyName> genero = new ArrayList<BabyName>();
		for (BabyName name : names){
			if (name.getGender() == g && !genero.contains(name)){
				genero.add(name);
			}
		}
		return genero;
	}
	
	/**
	 * Creates list of names based on the types and gender chosen
	 * @param genero
	 * 		Gender chosen by the user
	 * @param tiposEscolhidos
	 * 		List of types chosen by the user
	 * @return list of names
	 * @throws Exception if a file is not found
	 */
	public List<BabyName> createListOfNames(int genero, List<String> tiposEscolhidos) throws Exception{
		List<BabyName> nomes = filterByGender((filterByType(tiposEscolhidos)), genero);
		return nomes;
	}
	
	/**
	 * Saves a session at the user list of sessions
	 * @param user
	 * 		User owner of the session
	 * @param session
	 * 		Session to be saved
	 * @throws Exception if the user already has this session 
	 */
	public void saveSession(User user, Session session)throws Exception{
		user.addSession(session);
	}

	/**
	 * Gives grades to the names of the session
	 * @param nomes
	 * 		List of names
	 * @param grades
	 * 		List of grades
	 * @return the list of names with the new given grades
	 * @throws Exception if the grade is negative
	 */
	public List<BabyName> giveGrade(List<BabyName> nomes, List<Integer> grades) throws Exception{
		for (int i = 0; i < nomes.size(); i++){
			nomes.get(i).setGrade(grades.get(i));
		}
		return nomes;
	}

	/**
	 * Compares the grades given by the users at a session
	 * @param mapNames
	 * 		Map of user and their list of names
	 * @return a new list of names with the most popular names at the session
	 */
	public List<BabyName> compareGrades(HashMap<String, List<BabyName>> mapNames){
		int checker, grade;
		List<BabyName> maxNotas = new ArrayList<BabyName>();
		List<BabyName> medNotas = new ArrayList<BabyName>();
		GradeComparator comparator = new GradeComparator();
		Object[] users = mapNames.keySet().toArray();
		String[] emails = new String[users.length];
		for (int i = 0; i < users.length; i++){
			emails[i] = (String) users[i];
		}
		if (mapNames.size() == 0){
			return medNotas;
		}
		else if (mapNames.size() == 1){
			for (BabyName name : mapNames.get(emails[0])){
				if (name.getGrade() == 2)
					maxNotas.add(name);
				else if (name.getGrade() == 1)
					medNotas.add(name);
			}
			if (maxNotas.size() > 0)
				return maxNotas;
			else
				return medNotas;
		}
		for (int i = 0; i < mapNames.get(emails[0]).size(); i++){
			checker = 0;
			grade = 0;
			for (int j = 0; j < emails.length - 1; j ++){
				checker += comparator.compare(mapNames.get(emails[j]).get(i), mapNames.get(emails[j + 1]).get(i));
				grade = mapNames.get(emails[j + 1]).get(i).getGrade();
			}
			if (checker == 0 && grade > 0){
				if (grade == 2){
					maxNotas.add(mapNames.get(emails[0]).get(i));
				}
				else if (grade == 1){
					medNotas.add(mapNames.get(emails[0]).get(i));
				}
			}
		}
		if (maxNotas.size() > 0){
			return maxNotas;
		}
		return medNotas;
	}

	/**
	 * Creates a new session
	 * @param user
	 * 		User owner of the session
	 * @param id
	 * 		Name of the session
	 * @param emails
	 * 		Users that are part of that session
	 * @return the created session
	 * @throws Exception if the file of names is not found or if the grades given are negative 
	 */
	public Session createSession(User user, List<String> emails, String id) throws Exception{
		if (! searchUser(user.getEmail())){
			throw new Exception("User not register!");
		}
		HashMap<String, List<BabyName>> mapNames = new HashMap<String, List<BabyName>>();
		for (String email : emails){
			mapNames.put(email, new ArrayList<BabyName>());
		}
		Session session = new Session(mapNames, id);
		user.addSession(session);
		return session;
	}
	
	/**
	 * Finds the most popular name in a session
	 * @param mostPopular
	 * 		Map of the most popular names from a session
	 * @return the most popular name
	 * @throws Exception if the given position is negative
	 */
	public BabyName findTheMostPopular(HashMap<String, List<BabyName>> mostPopular) throws Exception{
		List<BabyName> orderedNames = new ArrayList<BabyName>();
		if (mostPopular.size() == 0){
			return null;
		}
		Object[] users = mostPopular.keySet().toArray();
		String[] emails = new String[users.length];
		for (int i = 0; i < users.length; i++){
			emails[i] = (String) users[i];
		}
		int soma;
		for (int i = 0; i < mostPopular.get(emails[0]).size(); i++){
			soma = 0;
			for (int j = 0; j < emails.length; j++){
				soma += mostPopular.get(emails[j]).get(i).getGrade();
			}
			mostPopular.get(emails[0]).get(i).setGrade(soma);
			orderedNames.add(mostPopular.get(emails[0]).get(i));
		}
		Collections.sort(orderedNames, Collections.reverseOrder(new GradeComparator()));
		if (orderedNames.size() > 0){
			return orderedNames.get(orderedNames.size() - 1);
		}
		return null;		
	}
	
	/**
	 * Retrieves the name chosen at the session
	 * @param session
	 * 		Session 
	 * @return the name chosen at the session
	 */
	public BabyName sessionName(Session session){
		Object[] users = session.getNames().keySet().toArray();
		String[] emails = new String[users.length];
		for (int i = 0; i < users.length; i++){
			emails[i] = (String) users[i];
		}
		if (emails.length == 0 || session.getNames().get(emails[0]).size() > 1){
			return null;
		}
		else{
			return session.getNames().get(emails[0]).get(0);
		}
	}

	/**
	 * Retrieves the list of user of the system
	 * @return list of users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Sets a new list of user for the system
	 * @param users
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	/**
	 * Retrives the types of the system
	 * @return types
	 */
	public List<String> getTipos() {
		return types;
	}
	/**
	 * Sets the list of types of the system
	 * @param types
	 */
	public void setTipos(List<String> types) {
		this.types = types;
	}
}
