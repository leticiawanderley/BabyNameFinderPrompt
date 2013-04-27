package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Ana Luiza Motta Gones 
 * @author Leticia Farias Wanderley
 *	@author Maysa De Macedo Souza
 *
 */
public class Deserializador {
    
    public List<User> deserializa() throws Exception, IOException, ClassNotFoundException{
                    File f = new File("Sistema_.dat");
                    if (!(f.exists())){
                        throw new Exception("Arquivo nao existe!");
                    }
            
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                    List<User> usuarios = (List<User>) ois.readObject();
                    ois.close();

            return usuarios;
    }
		
	
}
