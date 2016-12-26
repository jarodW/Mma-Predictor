package models;

import java.util.Hashtable;

public class FightResult {
	private String result;
	private String sherdogId;
	private String date;
	private String event;
	private String method;
	private String round;
	private String opponentId;
	private String opponentUrl;
	private String eventUrl;
	private int wins;
	private int losses;
	private int winstreak;
	Hashtable<String,String> dateConverter = new Hashtable<String,String>();
	
	public FightResult(){
		setDateConverter();
	}

	public String getName() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		String dateParts[] = date.split("/");
		dateParts[0] = dateConverter.get(dateParts[0]);
		String formatedDate = dateParts[2] + "-" + dateParts[0] + "-" + dateParts[1];
		this.date = formatedDate;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRound() {
		return round;
	}
	public void setRound(String round) {
		this.round = round;
	}
	public String getOpponentId() {
		return opponentId;
	}
	public void setOpponentId(String opponent) {
		this.opponentId = opponent;
	}
	public String getOpponentUrl() {
		return opponentUrl;
	}
	public void setOpponentUrl(String opponentUrl) {
		this.opponentUrl = opponentUrl;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	
	private void setDateConverter() {
		dateConverter.put("Jan", "01");
		dateConverter.put("Feb", "02");
		dateConverter.put("Mar", "03");
		dateConverter.put("Apr", "04");
		dateConverter.put("May", "05");
		dateConverter.put("Jun", "06"); 
		dateConverter.put("Jul", "07");
		dateConverter.put("Aug", "08");
		dateConverter.put("Sep", "09"); 
		dateConverter.put("Oct", "10");
		dateConverter.put("Nov", "11");
		dateConverter.put("Dec", "12"); 	
	}

	public String getSherdogId() {
		return sherdogId;
	}

	public void setSherdogId(String sherdogId) {
		this.sherdogId = sherdogId;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getWinstreak() {
		return winstreak;
	}

	public void setWinstreak(int winstreak) {
		this.winstreak = winstreak;
	}
	
}


