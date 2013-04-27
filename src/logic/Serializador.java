package logic;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
/**
 * @author Ana Luiza Motta Gones 
 * @author Leticia Farias Wanderley
 *	@author Maysa De Macedo Souza
 *
 */
public class Serializador {
    public void Serealizar(Object obj){
                        try{
                                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Sistema_.dat"));
                                oos.writeObject(obj);
                                oos.close();
                    }catch(Exception e){
                        e.printStackTrace();
                   }               
        }
 
}
