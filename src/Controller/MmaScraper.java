package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import models.FightResult;
import models.FightResultDB;
import models.Fighter;
import models.FighterDB;


public class MmaScraper {
	private FighterDB fd;
	private FightResultDB fr;
	private Random random;
	public MmaScraper(){
		fd  = new FighterDB();
		fr = new FightResultDB();
	}
	
	public Fighter serachFighter(String fn, boolean add) throws IOException, InterruptedException{
		String name = "";
		String sherdogUrl = "";
		name = fn.replaceAll("\\s", "+");
		sherdogUrl = searchGoogle(name);
		if(sherdogUrl == "")
			return null;
		Fighter fighter = createFighter(sherdogUrl);
		
		if(add == true){
			for(FightResult res: fighter.getResults()){
				try{
					createFighter("http://www.sherdog.com" + res.getOpponentUrl());
				}catch(IOException e){
					System.out.println("Fighter Url: " + res.getOpponentUrl() +" timed out and the fighter was not added.");
				}
			}
		}
		
		return fighter;
	}
	
	//Searches google to find the sherdog page
	public String searchGoogle(String fn) throws IOException{
		String url = "https://www.google.com/search?q="+fn+"+sherdog&num=5";
		String result = "";
		Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
		Elements results = doc.select("h3.r > a");
		
	    for (int i = 0; i < results.size(); ++i) {
	    	String link = results.get(i).attr("href");
	    	if(link.substring(7, link.indexOf("&")).contains("www.sherdog.com/fighter/")){
	    		url = link.substring(7, link.indexOf("&"));
	    		break;
	    	}
	    }
	    
		return url;
	}
	
	//adds a fighter to the database if it does not exist and returns a fighter object
	public Fighter createFighter(String sherdogUrl) throws IOException, InterruptedException{
		Document doc = Jsoup.connect(sherdogUrl).userAgent("Mozilla/5.0").get();
		int i = 0;
		//Creates fighter by scraping sherdog
		Fighter fighter = new Fighter();
		fighter.setName(doc.select("span.fn").text());
		fighter.setFighterUrl(sherdogUrl.replace("http://www.sherdog.com", ""));
		fighter.setNickname(doc.select("span.nickname").text().replaceAll("\"", ""));
		fighter.setWeightClass(doc.select(".item.wclass > strong").text());
		fighter.setBirthdate(doc.select(".item.birthday > span").text());
		fighter.setSherdogId(sherdogUrl.replaceAll("\\D",""));
		fighter.setWins(doc.select(".left_side > .bio_graph > .card > .counter").first().text());
		fighter.setLosses(doc.select(".left_side > .bio_graph.loser > .card > .counter").text());
		Elements ncAndDec = doc.select(".right_side > .bio_graph > .card");
		
		for(Element el: ncAndDec){
			if(el.select(".result").text().equals("Draws"))
				fighter.setDraws(el.select(".counter").text());
			if(el.select(".result").text().equals("N/C"))
				fighter.setNC(el.select(".counter").text());	
		}
		
		Elements results = doc.select(".module.fight_history tr:not(.table_head)");
		//Skips element if it is an upcoming event.
		if(doc.select(".module_header > h2").get(0).text().equals("Upcoming Fights"))
			results.remove(0);
		if(doc.select(".module_header > h2").get(0).text().equals("Upcoming Fights"))
			results.remove(0);
		//creates results for matches by scraping fighter profiles
		int wins = 0;
		int losses = 0;
		int winstreak = 0;
		for(int k= results.size()-1; k >=0; k--){
			Element el = results.get(k);
			FightResult result = new FightResult();
			result.setSherdogId(sherdogUrl.replaceAll("\\D",""));
			result.setResult(el.select(".final_result").text());
			result.setOpponentUrl(el.select("td:nth-child(2) a").attr("href"));
			result.setOpponentId(result.getOpponentUrl().replaceAll("\\D",""));
			result.setEvent(el.select("td:nth-child(3) a").text());
			result.setEventUrl(el.select("td:nth-child(3) a").attr("href"));
			result.setDate(el.select("td:nth-child(3) .sub_line").text().replaceAll("\\s+",""));
			result.setMethod( el.select("td:nth-child(4)").text().replaceAll("\\s+\\(.*",""));
			result.setRound(el.select("td:nth-child(5)").text());
			result.setWins(wins);
			result.setLosses(losses);
			result.setWinstreak(winstreak);
						if(el.select(".final_result").text().equals("win")){
				wins++;
				if(winstreak < 0)
					winstreak = 1;
				else
					winstreak++;
			}
			if(el.select(".final_result").text().equals("loss")){
				losses++;
				if(winstreak > 0)
					winstreak = -1;
				else
					winstreak--;
			}
			fighter.setWinstreak(winstreak);
			fighter.addResult(result);
			
		}
		
		try {
			if(!fd.containsFighter(fighter)){
				fd.insertFighter(fighter);
			    fr.insertResults(fighter);
				System.out.println("Added Fighter: " + fighter.getName());
			}
			else{
				System.out.println("Fighter: " + fighter.getName() + " is alredy in the databse.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Thread.sleep(3000);
		return fighter;
	}
	
	//scrapes an entire event and adds all fighters to database
	public ArrayList<Fighter> eventScrapper(String eventUrl) throws IOException, InterruptedException{
		Document doc = Jsoup.connect(eventUrl).userAgent("Mozilla/5.0").get();
		String  fighterUrl= "http://www.sherdog.com";
		ArrayList<Fighter> fighters = new ArrayList<Fighter>();
		
		//add main event fighters on left and right sides of fight card
		System.out.println(fighterUrl + doc.select("fighter.left_side a").attr("href"));
		fighters.add(createFighter(fighterUrl + doc.select(".fighter.left_side a").attr("href")));
		fighters.add(createFighter(fighterUrl + doc.select(".fighter.right_side a").attr("href")));
		
		//adds rest of the fighters on the fight card
		Elements results = doc.select(".module.event_match tr:not(.table_head)");
		for(Element el: results){
			fighters.add(createFighter(fighterUrl + el.select("td:nth-child(2) a").attr("href")));
			fighters.add(createFighter(fighterUrl + el.select("td:nth-child(4) a").attr("href")));

		}
		
		return fighters;	
	}
}


