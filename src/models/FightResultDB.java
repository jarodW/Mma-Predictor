package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FightResultDB {
    Connection connection = null;
    
    public FightResultDB(){
    	connection = Database.getDB();
    }
    
	public void insertResults(Fighter fighter){
		PreparedStatement preparedStatement = null;
		ArrayList<FightResult> results = fighter.getResults();
    	try {
	    	for(int i = 0; i < results.size(); i++){

			    Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(results.get(i).getDate());
		    	java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				preparedStatement = connection.prepareStatement("Insert into  FightResults values (default,?,?,?,?,?,?,?,?,?,?,?,?,default)");
		    	preparedStatement.setString(1, results.get(i).getResult());
		    	preparedStatement.setDate(2, sqlDate);
		    	preparedStatement.setString(3,results.get(i).getEvent());
		    	preparedStatement.setString(4, results.get(i).getMethod());
		    	preparedStatement.setString(5, results.get(i).getRound());
		    	preparedStatement.setString(6, results.get(i).getOpponentId());
		    	preparedStatement.setString(7, results.get(i).getOpponentUrl());
		    	preparedStatement.setString(8, results.get(i).getEventUrl());
		    	preparedStatement.setString(9, results.get(i).getSherdogId());
		    	preparedStatement.setInt(10, results.get(i).getWins());
		    	preparedStatement.setInt(11, results.get(i).getLosses());
		    	preparedStatement.setInt(12, results.get(i).getWinstreak());
		    	preparedStatement.executeUpdate();
	    	}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}


