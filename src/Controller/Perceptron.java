package Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;

import models.DataPoints;
import models.DataPointsDB;
import models.Fighter;

public class Perceptron {
	private int ITERATIONS = 100;
	private int instances;
	double LEARNING_RATE = .1;
	int theta;
	double attr1[];
	double attr2[];
	double attr3[];
	double attr4[];
	double classifier[];
	double weights[];
	
	
	public void trainData(DataPointsDB data){
		double localError, globalError;
		int cycleNum,prediction;
		if(data.getNumResults() > instances){
			setAttributes(data);
			for(int i = 0; i < weights.length; i++){
				weights[i] = randomNumber(0,1);
			}
			cycleNum = 0;
			do{
				cycleNum++;
				globalError = 0;
				for(int i = 0; i < instances; i++){
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
	
	public void perdict(Fighter fighter1, Fighter fighter2){
		
	}
	
	public void setAttributes(DataPointsDB data){
		ArrayList<DataPoints> datapoints = data.getDataPoints();
		instances = datapoints.size();
		attr1 = new double[instances];
		attr2 = new double[instances];
		attr3 = new double[instances];
		attr4 = new double[instances];
		weights = new double[5];
		classifier = new double[instances];
		for(int i = 0;i < instances; i++){
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
	
	private double randomNumber(int min , int max) {
		DecimalFormat df = new DecimalFormat("#.####");
		double d = min + Math.random() * (max - min);
		String s = df.format(d);
		double x = Double.parseDouble(s);
		return x;
	}
	
	private int calculateOutput(int theta, double weights[], double attr1, double attr2, double attr3, double attr4){
		double sum = attr1 * weights[0] + attr2 * weights[1] + attr3 * weights[2] + attr4*weights[3] + weights[4];
		return(sum >= theta) ? 1:0;
	}
}
