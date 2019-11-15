import java.util.Comparator;

public class TableSorter implements Comparator<String>{
	//in order: T, F, H
    public int compare(String s1, String s2){
    	if(s1.substring(0, 1).equals("T") && (s2.substring(0, 1).equals("F") || s2.substring(0, 1).equals("H"))) {
    		return -1;
    	}else if(s1.substring(0, 1).equals("F") && s2.substring(0, 1).equals("H")) {
    		return -1;
    	}else if (s1.substring(0, 1).equals(s2.substring(0, 1))) {
    		if(Integer.parseInt(s1.substring(1, 3)) < Integer.parseInt(s2.substring(1, 3))) {
    			return -1;
    		}else {
    			return 1;
    		}
    	}else {
			return 0;
    	}
    }
}