package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataPointsDB {
	
	 Connection connection = null;
	 
    public DataPointsDB(){
    	connection = Database.getDB();
    }
    
    public void createDataPoints(){
    	Statement stmnt = null;
    	String sql;
    	try {
    		stmnt = connection.createStatement();
    		sql = "DROP VIEW if EXISTS DataPoints";
			stmnt.execute(sql);
			sql = "create view DataPoints as select a.sherdogId as fighterId, a.wins, a.losses, a.winstreak, c.birthdate as birthdate, a.event, a.fightDate, a.opponentId, a.fightresult, "
					+ "b.wins as opponentWins, b.losses as opponentLosses, b.winstreak as opponentWinstreak, d.birthdate as opponentBirthdate from fightresults as a join fightresults as b "
					+ "on a.opponentId = b.sherdogId and a.event = b.event and a.sherdogId = b.opponentId join fighters as c on c.sherdogId = a.sherdogId join fighters as d on d.sherdogId "
					+ "= a.opponentId where c.birthdate is not null and d.birthdate is not null and a.fightresult != \"draw\" and a.fightresult != \"NC\"";
			stmnt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public ArrayList<DataPoints> getDataPoints(){
    	Statement stmnt = null;
    	String sql;
    	ResultSet rs;
    	ArrayList<DataPoints> data = new ArrayList<DataPoints>();
    	try{
    		stmnt = connection.createStatement();
    		sql = "Select * From DataPoints";
    		rs = stmnt.executeQuery(sql);
    	      while(rs.next()){
    	          //Retrieve by column name
    	    	  String  fighterId = rs.getString("fighterId");
    	          int wins  = rs.getInt("wins");
    	          int losses = rs.getInt("losses");
    	          int winStreak = rs.getInt("winstreak");
    	          String result = rs.getString("fightresult");
    	          int opponentWins  = rs.getInt("opponentWins");
    	          int opponentLosses = rs.getInt("opponentLosses");
    	          int opponentWinStreak = rs.getInt("opponentWinstreak");
    	          String  opponentId = rs.getString("opponentId");
    	          Date birthdate = rs.getDate("birthdate");
    	          Date opponentBirthdate = rs.getDate("opponentBirthdate");
    	          Date fightDate = rs.getDate("fightDate");
    	          DataPoints datapoint = new DataPoints();
    	          datapoint.setFighterId(fighterId);
    	          datapoint.setLosses(losses);
    	          datapoint.setWins(wins);
    	          datapoint.setStreak(winStreak);
    	          datapoint.setResult(result);
    	          datapoint.setOpponentLosses(opponentLosses);
    	          datapoint.setWins(opponentWins);
    	          datapoint.setStreak(opponentWinStreak);
    	          datapoint.setOpponentId(opponentId);
    	          datapoint.setBirthdate(birthdate);
    	          datapoint.setOpponentBirthdate(opponentBirthdate);
    	          datapoint.setFightDate(fightDate);
    	          data.add(datapoint);
    	       }
    	       rs.close();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		return data;    	
    }
    
    public int getNumResults(){
		ResultSet rs;
		String sql;
		Statement stmnt;
		int total = 0;
		
		try {
			stmnt = connection.createStatement();
			sql = "Select count(*) as total from datapoints";
			rs = stmnt.executeQuery(sql);
			if(rs.next())
				total = rs.getInt("total");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return total;
    	
    }
    
    public int getNumResults(String result){
		ResultSet rs;
		String sql;
		PreparedStatement stmnt = null;
		int total = 0;
		
		try {
			stmnt = connection.prepareStatement("Select count(*) as total from datapoints where fightresult = ?");
			stmnt.setString(1, result);
			rs = stmnt.executeQuery();
			if(rs.next())
				total = rs.getInt("total");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return total;
    	
    }
    
    
    public int cmpFightersWinPer(String sign, String result){
		ResultSet rs;
		String sql;
		PreparedStatement stmnt = null;
		int total = 0;
		
		try {
			stmnt = connection.prepareStatement("select count(*) as total from datapoints where (wins/(wins + losses)) " + sign + " (opponentWins/(opponentWins+opponentLosses)) and fightresult = ?");
			stmnt.setString(1, result);
			rs = stmnt.executeQuery();
			if(rs.next())
				total = rs.getInt("total");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return total;
    	
    }
    
    public int cmpFightersWinStreak(String sign, String result){
		ResultSet rs;
		String sql;
		PreparedStatement stmnt = null;
		int total = 0;
		
		try {
			stmnt = connection.prepareStatement("select count(*) as total from datapoints where winstreak " + sign + "opponentWinstreak and fightresult = ?");
			stmnt.setString(1, result);
			rs = stmnt.executeQuery();
			if(rs.next())
				total = rs.getInt("total");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return total;
    	
    }
    
    public int cmpFightersAge(String sign, String result){
		ResultSet rs;
		String sql;
		PreparedStatement stmnt = null;
		int total = 0;
		
		try {
			stmnt = connection.prepareStatement("select count(*) as total from datapoints where birthdate " + sign + "opponentBirthdate and fightresult = ?");
			stmnt.setString(1, result);
			rs = stmnt.executeQuery();
			if(rs.next())
				total = rs.getInt("total");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return total;
    	
    }
    
    public int cmpFightersExp(String sign, String result){
		ResultSet rs;
		String sql;
		PreparedStatement stmnt = null;
		int total = 0;
		
		try {
			stmnt = connection.prepareStatement("select count(*) as total from datapoints where (wins+losses) " + sign + "(opponentWins + opponentLosses) and fightresult = ?");
			stmnt.setString(1, result);
			rs = stmnt.executeQuery();
			if(rs.next())
				total = rs.getInt("total");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return total;
    	
    }
    

    
}
