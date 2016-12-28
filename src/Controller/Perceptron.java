package Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import models.DataPoints;
import models.DataPointsDB;
import models.Fighter;

public class Perceptron {
	private int ITERATIONS = 1000;
	private int totalInstances;
	double LEARNING_RATE = .1;
	int theta;
	double attr1[];
	double attr2[];
	double attr3[];
	double attr4[];
	double classifier[];
	double weights[];
	
	//Trains the current dataset
	public void trainData(DataPointsDB data){
		double localError, globalError;
		int cycleNum,prediction;
		if(data.getNumResults() > totalInstances){
			setAttributes(data);
			for(int i = 0; i < weights.length; i++){
				weights[i] = randomNumber(0,1);
			}
			cycleNum = 0;
			do{
				cycleNum++;
				globalError = 0;
				for(int i = 0; i < totalInstances; i++){
					prediction = calculateOutput(theta, weights, attr1[i],attr2[i],attr3[i],attr4[i]);
					localError = classifier[i] - prediction;
					weights[0] += LEARNING_RATE * localError * attr1[i];
					weights[1] += LEARNING_RATE * localError * attr2[i];
					weights[2] += LEARNING_RATE * localError * attr3[i];
					weights[3] += LEARNING_RATE * localError * attr4[i];
					weights[4] += LEARNING_RATE * localError;
					globalError += (localError*localError);
				}
			}while(globalError != 0 && cycleNum <= ITERATIONS);
			

		}
			
	}
	
	//Sets each attribute to 1,0, or -1 depending on if the fighters attribute is greater, equal, or less than the opponents attribute
	//Calculates the predicted outcome on the calculated attributes
	public void predict(Fighter fighter1, Fighter fighter2){
		double a1,a2,a3,a4;
		int output;
		double winPer = (double)fighter1.getWins()/(fighter1.getWins() + fighter1.getLosses());
		double opPer = (double)fighter2.getWins()/(fighter2.getWins() + fighter2.getLosses());
		int exp = fighter1.getWins() + fighter1.getLosses();
		int  opExp = fighter2.getWins() + fighter2.getLosses();
		
		if(winPer > opPer)
			a1 = 1;
		else if( winPer < opPer)
			a1 = -1;
		else
			a1 = 0;
		
		if(fighter1.getWinstreak() > fighter2.getWinstreak())
			a2 = 1;
		else if (fighter1.getWinstreak() < fighter2.getWinstreak())
			a2 = -1;
		else
			a2 = 0;
		
		if(exp > opExp)
			a3 = 1;
		else if(exp < opExp)
			a3 = -1;
		else
			a3 = 0;
		
		if(fighter1.getBirthdate().compareTo(fighter2.getBirthdate()) > 0)
			a4 = 1;
		else if(fighter1.getBirthdate().compareTo(fighter2.getBirthdate()) < 0)
			a4 = -1;
		else
			a4 = 0;
		
		output = calculateOutput(theta,weights,a1,a2,a3,a4);
		System.out.println("Perceptron");
		System.out.println("Predicted Winner: " + (output == 1 ? fighter1.getName():fighter2.getName()));
	}
	
	//Sets each attribute to 1,0, or -1 depending on if the fighters attribute is greater, equal, or less than the opponents attribute
	//The classifier is set to 1 or 0 depending on if a win or loss
	//Use the calculated attributes to calculate  the weights
	public void setAttributes(DataPointsDB data){
		ArrayList<DataPoints> datapoints = data.getDataPoints();
		totalInstances = datapoints.size();
		attr1 = new double[totalInstances];
		attr2 = new double[totalInstances];
		attr3 = new double[totalInstances];
		attr4 = new double[totalInstances];
		weights = new double[5];
		classifier = new double[totalInstances];
		for(int i = 0;i < totalInstances; i++){
			DataPoints dp = datapoints.get(i);
			double winPer = (double)dp.getWins()/(dp.getLosses() + dp.getWins());
			double opWinPer = (double)dp.getOpponentWins()/(dp.getOpponentLosses() + dp.getOpponentWins());
			double exp = dp.getLosses() + dp.getWins();
			double opExp = dp.getOpponentLosses() + dp.getOpponentWins();
			
			if(winPer > opWinPer){
				attr1[i] = 1;
			}else if(winPer < opWinPer){
				attr1[i] = -1;
			}else
				attr1[i] = 0;
			
			if(dp.getStreak() > dp.getOpponentStreak()){
				attr2[i] = 1;
			}else if(dp.getStreak() < dp.getOpponentStreak()){
				attr2[i]  = -1;
			}else
				attr2[i] = 0;
			
			if(exp > opExp){
				attr3[i] = 1;
			}else if(exp < opExp){
				attr3[i] = -1;
			}else
				attr3[i] = 0;
			
			if(dp.getBirthdate().compareTo(dp.getOpponentBirthdate()) > 0){
				attr4[i] = 1;
			}else if(dp.getBirthdate().compareTo(dp.getOpponentBirthdate())< 0){
				attr4[i] = -1;
			}else
				attr4[i] = 0;
			
			if(dp.getResult().equals("win")){
				classifier[i] = 1;
			}else
				classifier[i] = 0;
		}
	}
	
	//Calculates the starting weight values
	private double randomNumber(int min , int max) {
		DecimalFormat df = new DecimalFormat("#.####");
		double d = min + Math.random() * (max - min);
		String s = df.format(d);
		double x = Double.parseDouble(s);
		return x;
	}
	
	//Calculates the predicted outcome given the given attributes.
	private int calculateOutput(int theta, double weights[], double attr1, double attr2, double attr3, double attr4){
		double sum = attr1 * weights[0] + attr2 * weights[1] + attr3 * weights[2] + attr4*weights[3] + weights[4];
		return(sum >= theta) ? 1:0;
	}
}
