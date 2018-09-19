import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/* Areeba Waheed
 * Comp 482
 * Project 1: Stable Matching
 */

public class Project1 {
	public static void main(String[] args) {
		
		Map<Integer, List<Integer>> manpref = new HashMap<Integer,List<Integer>>();
		Map<Integer, List<Integer>> womanpref = new HashMap<Integer,List<Integer>>();
		Map<Integer, Integer> matches = new HashMap<Integer,Integer>();
		List<Integer> pref;
		
		//Reading the file
		java.io.File file = new java.io.File("input1.txt");	 
		try {
			Scanner scan = new Scanner(file);
			int n = scan.nextInt();
			int count = 0;
			int count1 =0;
			int count2 = 1;
			while(scan.hasNext()) {
				count++;
				
				if(count >= 1 && count <= n) {
					pref = new ArrayList<Integer>();
					for(int i = 0; i<n; i++) {
						pref.add(scan.nextInt());
					}
					manpref.put(count, pref);
				}
				else if(count > n && count <= n+n) {
					pref = new ArrayList<Integer>();
					count1++;
					for(int i = 0; i<n; i++) {
						pref.add(scan.nextInt());
					}
					womanpref.put(count1, pref);
				}
				else {
						matches.put(count2, scan.nextInt());
						count2++;
				}
			}
			//Checking to see if marriages are stable
			if(checkMatches(matches,manpref,womanpref)){
				System.out.println("Yes\n\n"+"because the matching is stable \n\n");
		    }
			scan.close();
			
		}
		catch(FileNotFoundException e){	
			System.out.println("File not found");
		}
	}

	private static boolean checkMatches(Map<Integer, Integer> matches, Map<Integer, List<Integer>> manpref,
			Map<Integer, List<Integer>> womanpref) {
		Map<Integer, Integer> invertedMatches = new TreeMap<Integer, Integer>();
		
        for(Map.Entry<Integer, Integer> couple:matches.entrySet()){
            invertedMatches.put(couple.getValue(), couple.getKey());
        }
 
        for(Map.Entry<Integer, Integer> couple:matches.entrySet()){
            List<Integer> shePrefers = womanpref.get(couple.getKey());
            List<Integer> sheLikesBetter = new LinkedList<Integer>();
            sheLikesBetter.addAll(shePrefers.subList(0, shePrefers.indexOf(couple.getValue())));
            List<Integer> hePrefers = manpref.get(couple.getValue());
            List<Integer> heLikesBetter = new LinkedList<Integer>();
            heLikesBetter.addAll(hePrefers.subList(0, hePrefers.indexOf(couple.getKey())));
 
            for(int guy : sheLikesBetter){
            		int guysFinace = invertedMatches.get(guy);
                List<Integer> thisGuyPrefers = manpref.get(guy);
                if(thisGuyPrefers.indexOf(guysFinace) >
                        thisGuyPrefers.indexOf(couple.getKey())){
                    System.out.printf("No (%s,%s) \n\n" + "because man %s prefers woman %s to his current match and woman %s prefers man %s to her current match \n\n",
                       couple.getKey(), guy, couple.getKey(), guy, couple.getValue(), guy, couple.getKey());
                    return false;
                }
            }
        }
        return true;
	}
   
}
