package View;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Controller.MmaScraper;
import Controller.Predictor;
import models.Database;
import models.Fighter;
import models.FighterDB;
import models.FightResult;


/**
 * Example program to list links from a URL.
 */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
    	Scanner scan = new Scanner(System.in);
    	Connection connection = Database.createDatabase();
      	MmaScraper mma = new MmaScraper();
      	Predictor predict = new Predictor();
      	Boolean loop = true;
      	HashSet<String> set = setWeightClasses(); 
    	
    	while(loop){
    		System.out.print(":>");
    		String command = scan.nextLine();
    		switch(command.toLowerCase()){
    			case "add":
    		    	String name = scan.nextLine();
    		    	String answer;
    		    	boolean add = false;
    		    	System.out.println("Would you like to add the fighters opponents?");
    		    	answer = scan.next().toLowerCase();
    		    	if(answer.equals("yes") || answer.equals("y"))
    		    		add = true;
    			    Fighter fighter = mma.serachFighter(name,add);
        	    	connection.commit();
    		    	System.out.println("finished");
    		    	break;
    			case "event":
        			String event = scan.nextLine();
        			System.out.println("Feature not supported");
        			break;
    			case "commit":
        	    	connection.commit();
        	    	System.out.println("committed database");
        	    	break;
    			case "q":
    			case "exit":
    			case "quit":
    			case "-1":
        			System.out.print("exiting");
        			loop = false;
        			break;
    			case "list":
    				System.out.println("Which weight class?");
    				String weightclass = scan.next().toLowerCase();
    				if(weightclass.equals("all")){
    					predict.listFighters();
    				}   
    				else if(set.contains(weightclass))
    					predict.listFighters(weightclass);
    				else
    					System.out.println("Not a viable weight class");
    	
    				break;
    			case "cmp":
    				System.out.println("Fighter 1");
    				String name1 = scan.nextLine();
    				System.out.println("Fighter 2");
    				String name2 = scan.nextLine();
    				predict.compareFightersSimpleNaiveBayes(name1, name2);
    				break;
        	    default:
        			System.out.println("command not supported");
        			scan.nextLine();
        			break;
    		}
    	}
    	connection.commit();
        connection.close();
    }

	private static HashSet<String> setWeightClasses() {
		HashSet<String> set = new HashSet<String>();
		set.add("strawweight");
		set.add("flyweight");
		set.add("featherweight");
		set.add("lightweight");
		set.add("welterweight");
		set.add("middleweight");
		set.add("light heavyweight");
		set.add("heavyweight");
		return set;
	}
}


