package models;

import java.util.Date;

public class DataPoints {
	private int wins;
	private int losses;
	private int streak;
	private String result;
	private String opponentId;
	private String fighterId;
	private int opponentWins;
	private int opponentLosses;
	private int opponentStreak;
	private Date birthdate;
	private Date opponentBirthdate;
	private Date fightDate;
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
	public int getStreak() {
		return streak;
	}
	public void setStreak(int streak) {
		this.streak = streak;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getOpponentWins() {
		return opponentWins;
	}
	public void setOpponentWins(int opponentWins) {
		this.opponentWins = opponentWins;
	}
	public int getOpponentLosses() {
		return opponentLosses;
	}
	public void setOpponentLosses(int opponentLosses) {
		this.opponentLosses = opponentLosses;
	}
	public int getOpponentStreak() {
		return opponentStreak;
	}
	public void setOpponentStreak(int opponentStreak) {
		this.opponentStreak = opponentStreak;
	}
	public String getOpponentId() {
		return opponentId;
	}
	public void setOpponentId(String opponentId) {
		this.opponentId = opponentId;
	}
	public String getFighterId() {
		return fighterId;
	}
	public void setFighterId(String fighterId) {
		this.fighterId = fighterId;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public Date getOpponentBirthdate() {
		return opponentBirthdate;
	}
	public void setOpponentBirthdate(Date opponentBirthdate) {
		this.opponentBirthdate = opponentBirthdate;
	}
	public Date getFightDate() {
		return fightDate;
	}
	public void setFightDate(Date fightDate) {
		this.fightDate = fightDate;
	}
	
}
