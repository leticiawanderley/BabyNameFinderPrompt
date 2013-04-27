package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Ana Luiza Motta Gones 
 * @author Leticia Farias Wanderley
 *	@author Maysa De Macedo Souza
 *
 */
public class User implements Serializable{
	private String name, email, password;
	private List<Session> sessions = new ArrayList<Session>();
	
	/**
	 * Constructor of User
	 * @param name
	 * 		Name of the user
	 * @param email
	 * 		Email of the user
	 * @param password
	 * 		The user password
	 */
	public User(String name, String email,String password) throws Exception{
		setName(name);
		setEmail(email);
		setPassword(password);
	}
	
	/**
	 * Retrieves the name of the user
	 * @return name
	 * 		Name of the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets a new name for the user
	 * @param name
	 * @throws Exception if the name given is invalid
	 */
	public void setName(String name) throws Exception{
		if (name == null ||name.equals("") ){
			throw new Exception("Invalid name!");
		}
		this.name = name;
	}
	
	/**
	 * Retrieves the password of the user
	 * @return password
	 * 		Password of the user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets a new password for the use
	 * @param password
	 * @throws Exception if the password is invalid
	 */
	public void setPassword(String password) throws Exception{
		if (password == null){
			throw new Exception("Invalid password!");
		}
		this.password = password;
	}
	
	/**
	 * Retrieves the e-mail of the user
	 * @return e-mail
	 * 		E-mail of the user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets a new e-mail for the user
	 * @param email
	 * @throws Exception if the e-mail given is invalid
	 */
	public void setEmail(String email) throws Exception{
		if (email.equals("") || !email.contains("@")){
			throw new Exception("Invalid email!");
		}
		this.email = email;
	}
	
	/**
	 * Adds a new session for the user
	 * @param session
	 * 		New session
	 */
	public void addSession(Session session) throws Exception{
		for (Session s : sessions){
			if (s.getId().equals(session.getId())){
				throw new Exception("This session already exists!");
			}
		}
		this.sessions.add(session);
	}
	
	/**
	 * Removes the session given from the user
	 * @param session
	 * 		Session to be removed
	 */
	public void removeSession(Session session) throws Exception{
		boolean checker = true;
		for (Session s : this.sessions){
			if (s.getId().equals(session.getId())){
				this.sessions.remove(s);
				checker = false;
				break;
			}
		}
		if (checker){
			throw new Exception("This session doesn't exist!");
		}
	}

	/**
	 * Retrieves user list of sessions
	 * @return sessions
	 */
	public List<Session> getSessions() {
		return sessions;
	}

	/**
	 * Sets a new list of sessions for the user
	 * @param sessions
	 */
	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
	
	/**
	 * Searches for a session in the user's list of sessions
	 * @param newId
	 * 		Name of the session to be searched
	 * @return the session if it's found
	 */
	public Session searchSession(String newId) throws Exception{
		for (Session s : this.sessions){
			if (s.getId().equals(newId)){
				return s;
			}
		}
		throw new Exception("Session not found!");
	}
	
	/**
	 * Compare users
	 * @param obj
	 * 		User to be compared
	 */
	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (this.email.equals(other.getEmail())){
			return true;
		}
		return false;
	}
}