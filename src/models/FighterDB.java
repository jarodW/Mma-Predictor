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

public class FighterDB {
    Connection connection = null;
    
    public FighterDB(){
    	connection = Database.getDB();
    }
    
	public void insertFighter(Fighter fighter){
		PreparedStatement preparedStatement = null;
		java.sql.Date sqlDate;
    	try {
	    	if(fighter.getBirthdate() == null){
	    		sqlDate = null;
	    	}
	    	else{
		    	sqlDate = new java.sql.Date(fighter.getBirthdate().getTime());
	    	}
			preparedStatement = connection.prepareStatement("Insert into  Fighters values (default,?,?,?,?,?,?,?,?,?,?,?,default)");
	    	preparedStatement.setString(1, fighter.getName());
	    	preparedStatement.setString(2, fighter.getFighterUrl());
	    	preparedStatement.setString(3, fighter.getNickname());
	    	preparedStatement.setDate(4, sqlDate);
	    	preparedStatement.setString(5, fighter.getSherdogId());
	    	preparedStatement.setString(6, fighter.getWeightClass());
	    	preparedStatement.setInt(7, fighter.getWins());
	    	preparedStatement.setInt(8, fighter.getLosses());
	    	preparedStatement.setInt(9, fighter.getNC());
	    	preparedStatement.setInt(10, fighter.getDraws());
	    	preparedStatement.setInt(11, fighter.getWinstreak());
	    	preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean containsFighter(Fighter fighter) throws SQLException{
		PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("select * from Fighters WHERE sherdogId = ?");
        preparedStatement.setString(1, fighter.getSherdogId());
        ResultSet rs =  preparedStatement.executeQuery();
        rs =  preparedStatement.executeQuery();
        if(!rs.next()){
        	return false;
        }else{
        	return true;
        }
    	
	}
	
	public ArrayList<String> getFighters(){
		ArrayList<String> fighterNames = null;
		try {
			PreparedStatement ps = connection.prepareStatement("select fighterName from fighters");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("fighterName"));
			}
		} catch (SQLException e) {
			System.out.println("could not list fighters");
		}
		return fighterNames;
		
	}
	
	
	public ArrayList<String> getFighters(String weightclass){
		ArrayList<String> fighterNames = null;
		try {
			PreparedStatement ps = connection.prepareStatement("select fighterName from fighters where weightClass = ?");
	        ps.setString(1, weightclass);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("fighterName"));
			}
		} catch (SQLException e) {
			System.out.println("could not list fighters");
		}
		return fighterNames;
		
	}
	
	public Fighter getFighter(String name){
		Fighter fighter = new  Fighter();
		try{
		PreparedStatement preparedStatement = null;
		preparedStatement = connection.prepareStatement("select * from Fighters WHERE fighterName = ?");
        preparedStatement.setString(1, name);
        ResultSet rs =  preparedStatement.executeQuery();
        rs =  preparedStatement.executeQuery();
        if(rs.next()){
        	System.out.println(rs.getDate("birthdate"));
        	fighter.setBirthdate(rs.getDate("birthdate"));
        	fighter.setDraws(rs.getString("draws"));
        	fighter.setFighterUrl(rs.getString("fighterUrl"));
        	fighter.setLosses(rs.getString("losses"));
        	fighter.setName(rs.getString("fighterName"));
        	fighter.setNC(rs.getString("nc"));
        	fighter.setNickname(rs.getString("nickName"));
        	fighter.setSherdogId(rs.getString("sherdogId"));
        	fighter.setWeightClass(rs.getString("weightClass"));
        	fighter.setWins(rs.getString("wins"));
        	fighter.setWinstreak(rs.getInt("winstreak"));
        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.printf("Fighter %s does not exist", name);
			e.printStackTrace();
		}
		return fighter;
		
	}
}


