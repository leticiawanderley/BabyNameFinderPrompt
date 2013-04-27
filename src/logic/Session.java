package logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
/**
 * @author Ana Luiza Motta Gones 
 * @author Leticia Farias Wanderley
 *	@author Maysa De Macedo Souza
 *
 */
public class Session implements Serializable{
	private String id;
	private HashMap<String, List<BabyName>> names = new HashMap<String, List<BabyName>>();
	
	/**
	 * Constructor of Session
	 * @param names
	 * 		Map of users and their list of names with their given grades
	 * @param id
	 * 		Name of the session
	 * @throws Exception if 
	 */
	public Session(HashMap<String, List<BabyName>> names, String id) throws Exception{
		setNames(names);
		setId(id);
	}
	
	/**
	 * Retrieves the id of the session
	 * @return id
	 * 		Name of the session
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets a new id for the session
	 * @param id
	 * @throws Exception if the id is invalid
	 */
	public void setId(String id)throws Exception {
		if(id == null || id.equals(""))
			throw new Exception("Invalid id!");
		this.id = id;
	}

	/**
	 * Retrieves the map of the session
	 * @return names
	 * 		Map of user and lists of names
	 */
	public HashMap<String, List<BabyName>> getNames() {
		return names;
	}

	/**
	 * Sets a new map for the session
	 * @param novaLista
	 */
	public void setNames(HashMap<String, List<BabyName>> novaLista){		
		this.names = novaLista;
	}
	
	@Override
	public boolean equals(Object obj){
		if (this.getClass() != obj.getClass()){
			return false;
		}
		Session newSession = (Session) obj;
		if (this.id.equals(newSession.getId())){
			return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return "Nome da sessao: " + this.id + " Participantes: " + this.names.keySet().toString().replace("[", "").replace("]", "");
	}
}
