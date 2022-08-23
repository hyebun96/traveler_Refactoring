package com.travel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TravelDAO {
	private final Connection conn=DBConn.getConnection();
	private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
	private final String apiKey = "5e12679699fcfba7406abda339c17eb4";
	
	// �Խù� ����
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COALESCE(COUNT(*),0) FROM travel ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return result;
	}
	
	// �˻��� �Խù� ���� 
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COALESCE(COUNT(*),0) FROM travel t JOIN member m ON t.userId = m.userId ";
			
			if(condition.equals("userName"))sql+= "WHERE INSTR(userName, ?) = 1 ";
			else if(condition.equals("created")) {
				keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql+=  "WHERE DATE_FORMAT(created,'%Y-%m-%d') = ?";
			}else sql += "WHERE INSTR("+condition+",?) >= 1";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return result;
	}

	public List<TravelDTO> listTravel(String type) {
		List<TravelDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT t.travelNum, place, information, t.userid, username, likeNum, ");
			sb.append("  	DATE_FORMAT(created, '%Y-%m-%d') AS created, img.saveFilename  ");
			sb.append("  FROM travel t  JOIN member m on t.userId = m.userId ");
			sb.append("  LEFT OUTER JOIN ( ");
			sb.append("  	SELECT travelNum, group_concat(saveFilename) AS saveFilename ");
			sb.append(" 	FROM travelFile f  ");
			sb.append("     GROUP BY travelNum ");
			sb.append("   	) img ON t.travelNum = img.travelNum");
			sb.append("	 WHERE type=? ");
			
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, type);
			
			rs = pstmt.executeQuery();

			if(rs == null){
				return null;
			}
			
			while(rs.next()) {
				
				TravelDTO dto = new TravelDTO();
				dto.setNum(rs.getInt("travelNum"));
				dto.setPlace(rs.getString("place"));
				dto.setInformation(rs.getString("information"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("username"));	
				if(rs.getString("saveFilename")!=null) {
					dto.setImageFilename(rs.getString("saveFilename").split(","));
				}
				dto.setCreated(rs.getString("created"));
				dto.setLikeNum(rs.getInt("likeNum"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public List<TravelDTO> listTravel(String condition, String keyword) {
		List<TravelDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT travelNum, place, information, t.userid, username, likeNum, ");
			sb.append("         DATE_FORMAT(created, '%Y-%m-%d') created, subquery1.saveFilename saveFilename ");
			sb.append("  FROM travel t JOIN member m ON t.userId = m.userId, ");
			sb.append("   (SELECT GROUP_CONCAT(SAVEFILENAME) AS saveFilename");
			sb.append(" FROM TRAVELFILE ");
			sb.append(" GROUP BY travelNum ");
			sb.append("   ) subquery1 ");
			if(condition.equalsIgnoreCase("created")) {
				keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE DATE_FORMAT(created, '%Y-%m-%d') = ?   ");
			}else if(condition.equalsIgnoreCase("userid")) {
				sb.append(" WHERE INSTR(userid,?) > 0 ");
			}else {
				sb.append(" WHERE INSTR(").append(condition).append(",?) >= 1 ");
			}
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TravelDTO dto = new TravelDTO();
				dto.setNum(rs.getInt("travelNum"));
				dto.setPlace(rs.getString("place"));
				dto.setInformation(rs.getString("information"));
				dto.setUserId(rs.getString("userId"));
				if(rs.getString("saveFilename")!=null) {
					dto.setImageFilename(rs.getString("saveFilename").split(","));
				}
				dto.setCreated(rs.getString("created"));
				dto.setLikeNum(rs.getInt("likeNum"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public int insertTravel(TravelDTO dto) {
		int result = 0;
		StringBuilder sb = new StringBuilder();
		PreparedStatement pstmt = null;
		
		try {
			sb.append("INSERT INTO travel ");
			sb.append(" (place, information, userid, type) ");
			sb.append(" VALUES(?,?,?,?) ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getPlace());
			pstmt.setString(2, dto.getInformation());
			pstmt.setString(3, dto.getUserId());
			pstmt.setString(4, dto.getType());
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
	
	public TravelDTO readTravel(int num) {
		TravelDTO dto = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT t.travelNum, place, information, t.userid, userName , saveFilename, type ");
			sb.append(" FROM travel t JOIN member m ON t.userid = m.userid ");
			sb.append(" LEFT OUTER JOIN ( ");
			sb.append("    SELECT travelNum, GROUP_CONCAT(SAVEFILENAME) AS saveFilename");
			sb.append("    FROM TRAVELFILE f ");
			sb.append("    WHERE f.travelNum = ? ");
			sb.append("    GROUP BY travelNum ");
			sb.append(") img ON t.travelNum = img.travelNum ");
			sb.append("	WHERE t.travelNum =? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setInt(2, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new TravelDTO();
				
				dto.setNum(rs.getInt("travelNum"));
				dto.setPlace(rs.getString("place"));
				dto.setInformation(rs.getString("information"));
				dto.setUserId(rs.getString("userid"));
				dto.setUserName(rs.getString("userName"));
				if(rs.getString("saveFilename")!=null) {
					dto.setImageFilename(rs.getString("saveFilename").split(","));
				}
				dto.setType(rs.getString("type"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return dto;
	}
	
	public int updateTravel(TravelDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE travel SET place=?, information=? WHERE travelNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPlace());
			pstmt.setString(2, dto.getInformation());
			pstmt.setInt(3, dto.getNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public int deleteTravel(int num) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "DELETE FROM travel WHERE travelNum= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public int likeInsert(int num) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE travel SET likeNum=likeNum+1 WHERE travelNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public void insertImage(TravelDTO dto ,String s) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(dto.getNum() == 0){
				sql = "INSERT INTO travelFile(travelNum, saveFilename) VALUES( (SELECT MAX(travelNum) from travel), ?)";
			} else {
				sql = "INSERT INTO travelFile(travelNum, saveFilename) VALUES( "+ dto.getNum()  +", ?)";
			}

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s);

			result = pstmt.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public int deleteImage(String name) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "DELETE FROM travelFile WHERE saveFilename=? ";
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			result = pstmt.executeUpdate();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return result;
	}	
	
	public WeatherDTO listWeather(String area) {
		StringBuilder urlBuilder = new StringBuilder(BASE_URL);
		URL url;
		BufferedReader bf;
		String line;
		String result = "";
		WeatherDTO dto = new WeatherDTO();

		try {
			urlBuilder.append("?" + URLEncoder.encode("q", "UTF-8") + "=" + area);
			urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + apiKey);

			url = new URL(urlBuilder.toString());

			// openStream 메소드로 날씨 정보를 받아옴
			bf = new BufferedReader(new InputStreamReader(url.openStream()));

			// 버퍼에 있는 정보를 문자열로 변환
			while((line = bf.readLine()) != null){
				result = result.concat(line);
			}

			JSONParser jsonParser = new JSONParser();
			// To JSONObject
			JSONObject jsonObj = (JSONObject) jsonParser.parse(result);

			JSONObject WeatherObj = (JSONObject) ((JSONArray) jsonObj.get("weather")).get(0);
			JSONObject CoordObj = (JSONObject) jsonObj.get("coord");

			dto.setWeather(WeatherObj.get("main").toString() + ".svg");
			dto.setTem(String.format("%.2f", (Float) CoordObj.get("lat") ));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return dto;
	}
}
