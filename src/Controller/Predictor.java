package Controller;

import java.util.ArrayList;
import java.util.Date;

import models.DataPoints;
import models.DataPointsDB;
import models.FightResultDB;
import models.Fighter;
import models.FighterDB;

public class Predictor {
	private DataPointsDB data;
	private FighterDB fd;
	private FightResultDB fr;
	private ArrayList<DataPoints> dataPoints;
	private NaiveBayes nb;
	private Perceptron p;
	
	public Predictor(DataPointsDB data){
		fr = new FightResultDB();
		fd = new FighterDB();
		nb = new NaiveBayes();
		p = new Perceptron();
		this.data = data;
		data.createDataPoints();
		dataPoints = data.getDataPoints();
	}
	
	public void compareFightersSimpleNaiveBayes(String name1, String name2){

		nb.trainData(data);
		Fighter fighter1 = fd.getFighter(name1);
		Fighter fighter2 = fd.getFighter(name2);
		if(fighter1 != null && fighter2 != null)
			nb.predict(fighter1,fighter2);
	}
	
	public void compareFightersSimplePerceptron(String name1, String name2){
		p.trainData(data);
		Fighter fighter1 = fd.getFighter(name1);
		Fighter fighter2 = fd.getFighter(name2);
		if(fighter1 != null && fighter2 != null)
			p.predict(fighter1,fighter2);
	}
	
	public void listFighters(){
		fd.getFighters();
	}

	public void listFighters(String weightclass) {
		fd.getFighters(weightclass);
		
	}
}
