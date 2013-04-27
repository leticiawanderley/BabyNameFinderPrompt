package logic;

import java.util.Comparator;
/**
 * @author Ana Luiza Motta Gones 
 * @author Leticia Farias Wanderley
 *	@author Maysa De Macedo Souza
 *
 */
public class GradeComparator implements Comparator<BabyName>{
	
	@Override
	public int compare(BabyName baby0, BabyName baby1) {
		return baby0.getGrade() - baby1.getGrade();
	}
}

