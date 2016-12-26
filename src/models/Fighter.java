package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fighter {
	private String name;
	private String fighterUrl;
	private String nickname;
	private Date birthdate;
	private String sherdogId;
	private String weightClass;
	private int wins;
	private int losses;
	private int nc;
	private int draws;
	private int winstreak;
	private ArrayList<FightResult> results = new ArrayList<FightResult>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date date){
		this.birthdate = date;
	}
	
	public void setBirthdate(String birthdate) {
		if(birthdate.equals("N/A")){
			this.birthdate = null;
		}
		else{
		try {
			this.birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		}
	}
	
	public String getSherdogId() {
		return sherdogId;
	}
	public void setSherdogId(String sherdogId) {
		this.sherdogId = sherdogId;
	}
	public String getWeightClass() {
		return weightClass;
	}
	public void setWeightClass(String weightClass) {
		this.weightClass = weightClass;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(String string) {
		this.losses = Integer.parseInt(string);
	}
	public int getWins() {
		return wins;
	}
	public void setWins(String string) {
		this.wins = Integer.parseInt(string);
	}
	public int getNC() {
		return nc;
	}
	public void setNC(String nC) {
		this.nc = Integer.parseInt(nC);
	}
	public int getDraws() {
		return draws;
	}
	public void setDraws(String draws) {
		this.draws = Integer.parseInt(draws);
	}
	
	public void addResult(FightResult result){
		results.add(result);
	}
	
	public ArrayList<FightResult> getResults(){
		return results;
	}
	public String getFighterUrl() {
		return fighterUrl;
	}
	public void setFighterUrl(String fighterUrl) {
		this.fighterUrl = fighterUrl;
	}
	public int getWinstreak() {
		return winstreak;
	}
	public void setWinstreak(int winstreak) {
		this.winstreak = winstreak;
	}
}


