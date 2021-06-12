package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	//lista per prendere gli anni dalla tabella sql
	public List<Integer> getAnni(){
		String sql="SELECT DISTINCT YEAR(e.reported_date) AS anno "
				+ "FROM events e "
				+ "ORDER BY YEAR(e.reported_date) ";
		List<Integer> result = new LinkedList<Integer>();
		
		try {
				Connection conn = DBConnect.getConnection() ;
				PreparedStatement st = conn.prepareStatement(sql) ;
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					result.add(res.getInt("anno"));
				}
		
			conn.close();
			return result;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
	//VERTICI 
	public List<Integer> getVertici(){
		String sql="SELECT DISTINCT e.district_id AS id "
				+ "FROM EVENTS e ";
		List<Integer> result = new LinkedList<Integer>();
		
		try {
				Connection conn = DBConnect.getConnection() ;
				PreparedStatement st = conn.prepareStatement(sql) ;
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					result.add(res.getInt("id"));
				}
		
			conn.close();
			return result;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	//metodo per latitudine media di un distretto in quell'anno
	public Double getLatMedia(Integer anno, Integer district) {
		String sql = "SELECT AVG(geo_lat) as lat FROM events WHERE YEAR(reported_date) = ? AND district_id = ?";
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, district);
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				conn.close();
				return res.getDouble("lat");
			} else {
				conn.close();
				return null;
			}
			
			
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	//metodo per longitudine media di un distretto in quell'anno
	public Double getLonMedia(Integer anno, Integer district) {
		String sql = "SELECT AVG(geo_lon) as lon FROM events WHERE YEAR(reported_date) = ? AND district_id = ?";
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, district);
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				conn.close();
				return res.getDouble("lon");
			} else {
				conn.close();
				return null;
			}
			
			
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	}


}
