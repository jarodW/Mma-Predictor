package Controller;

import models.DataPointsDB;
import models.Fighter;

public class NaiveBayes {
	private int totalFights;
	private int wins;
	private int losses;
	private int draws;
	private int winsPerGreater;
	private int winsPerLower;
	private int winsPerEqual;
	private int lossesPerGreater;
	private int lossesPerLower;
	private int lossesPerEqual;
	private int winsAgeOlder;
	private int winsAgeYounger;
	private int winsAgeEqual;
	private int lossesAgeOlder;
	private int lossesAgeYounger;
	private int lossesAgeEqual;
	private int winsExpMore;
	private int winsExpLess;
	private int winsExpEqual;
	private int lossesExpMore;
	private int lossesExpLess;
	private int lossesExpEqual;
	private int winsStreakLonger;
	private int winsStreakShorter;
	private int winsStreakEqual;
	private int lossesStreakLonger;
	private int lossesStreakShorter;
	private int lossesStreakEqual;
	
	public void predict(Fighter fighter1, Fighter fighter2){
		double posterior1;
		double posterior2;
		double winperWinProb;
		double winperLossProb;
		double winstreakWinProb;
		double winstreakLossProb;
		double expWinProb;
		double expLossProb;
		double ageWinProb;
		double ageLossProb;
		
		if(fighter1.getBirthdate().compareTo(fighter2.getBirthdate()) > 0){
			ageWinProb = (double)winsAgeOlder/wins;
			ageLossProb = (double)lossesAgeOlder/losses;
		}else if(fighter1.getBirthdate().compareTo(fighter2.getBirthdate()) < 0){
			ageWinProb = (double)winsAgeYounger/wins;
			ageLossProb = (double)lossesAgeYounger/losses;
		}else{
			ageWinProb = (double)winsAgeEqual/wins;
			ageLossProb = (double)lossesAgeEqual/losses;
		}
		
		
		if(fighter1.getWinstreak() > fighter2.getWinstreak()){
			winstreakWinProb = (double)winsStreakLonger/wins;
			winstreakLossProb = (double)lossesStreakLonger/losses;
		}else if(fighter1.getWinstreak() < fighter2.getWinstreak()){
			winstreakWinProb = (double)winsStreakShorter/wins;
			winstreakLossProb = (double)lossesStreakShorter/losses;
		}else{
			winstreakWinProb = (double)winsStreakEqual/wins;
			winstreakLossProb = (double)lossesStreakEqual/losses;
		}
		
		int total1 = fighter1.getWins() + fighter1.getLosses() + fighter1.getDraws();
		int total2 = fighter2.getWins() + fighter2.getLosses() + fighter1.getDraws();
		if(total1  > total2){
			expWinProb =(double)winsExpMore/wins;
			expLossProb = (double)lossesExpMore/losses;
		}else if (total1  < total2){
			expWinProb =(double)winsExpLess/wins;
			expLossProb = (double)lossesExpLess/losses;
		}else{
			expWinProb =(double)winsExpEqual/wins;
			expLossProb = (double)lossesExpEqual/losses;
		}
		
		double winprob1 =  (double)fighter1.getWins()/(fighter1.getWins()  + fighter1.getLosses() + fighter1.getDraws());
		double winprob2 =  (double)fighter2.getWins()/(fighter2.getWins()  + fighter2.getLosses() + fighter2.getDraws());
		if(winprob1 > winprob2){
			winperWinProb =(double)winsPerGreater/wins;
			winperLossProb = (double)lossesPerGreater/losses;
		}else if(winprob2 < winprob2){
			winperWinProb =(double)winsExpLess/wins;
			winperLossProb = (double)lossesExpLess/losses;
		}else{
			winperWinProb =(double)winsExpEqual/wins;
			winperLossProb = (double)lossesExpEqual/losses;
		}
		
		posterior1 = ((double)wins/totalFights) * ageWinProb * expWinProb * winstreakWinProb *  winperWinProb;
		posterior2 = ((double)losses/totalFights) * ageLossProb * expLossProb * winstreakLossProb *  winperLossProb;
		System.out.println("Posterior for " + fighter1.getName()  + " beating " + fighter2.getName() + ": " + posterior1);
		System.out.println("Posterior for " + fighter2.getName()  + " beating " + fighter2.getName() + ": " + posterior2);
		System.out.println("Predicted Winner: "  + (posterior1 > posterior2 ? fighter1.getName():fighter2.getName()));
	}
	
	public void trainData(DataPointsDB data){
		if(data.getNumResults() > totalFights){
			totalFights = data.getNumResults();
			wins = data.getNumResults("win");
			losses = data.getNumResults("loss");
			draws = data.getNumResults("draw");
			winsPerGreater = data.cmpFightersWinPer(">", "win");
			winsPerLower = data.cmpFightersWinPer("<", "win");
			winsPerEqual = data.cmpFightersWinPer("=", "win");
			lossesPerGreater = data.cmpFightersWinPer(">", "loss");
			lossesPerLower = data.cmpFightersWinPer("<", "loss");
			lossesPerEqual = data.cmpFightersWinPer("=", "loss");
			winsStreakLonger = data.cmpFightersWinStreak(">", "win");
			winsStreakShorter = data.cmpFightersWinStreak("<", "win");
			winsStreakEqual = data.cmpFightersWinStreak("=", "win");
			lossesStreakLonger = data.cmpFightersWinStreak(">", "loss");
			lossesStreakShorter = data.cmpFightersWinStreak("<", "loss");
			lossesStreakEqual = data.cmpFightersWinStreak("=", "loss");
			winsAgeOlder = data.cmpFightersAge(">", "win");
			winsAgeYounger = data.cmpFightersAge("<", "win");
			winsAgeEqual = data.cmpFightersAge("=", "win");
			lossesAgeOlder = data.cmpFightersAge(">", "loss");
			lossesAgeYounger = data.cmpFightersAge("<", "loss");
			lossesAgeEqual = data.cmpFightersAge("=", "loss");
			winsExpMore = data.cmpFightersExp(">", "win");
			winsExpLess = data.cmpFightersExp("<", "win");
			winsExpEqual = data.cmpFightersExp("=", "win");
			lossesExpMore = data.cmpFightersExp(">", "loss");
			lossesExpLess = data.cmpFightersExp("<", "loss");
			lossesExpEqual = data.cmpFightersExp("=", "loss");
		}
	}
}
