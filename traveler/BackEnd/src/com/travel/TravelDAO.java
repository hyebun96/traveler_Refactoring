package com.travel;

import com.util.DBConn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TravelDAO {
    private final Connection conn = DBConn.getConnection();
    private StringBuilder sb;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private final String apiKey = "5e12679699fcfba7406abda339c17eb4";

    public int dataCount() {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT COALESCE(COUNT(*),0) FROM travel");
            pstmt = conn.prepareStatement(sb.toString());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public List<TravelDTO> listTravel(String type, String userId) {
        List<TravelDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT t.travelNum, t.userId, place, information, likeCount,DATE_FORMAT(created, '%Y-%m-%d') AS created, f.saveFileName");
            if (userId != null) {
                sb.append("       , l.likeNum");
            }
            sb.append("   FROM travel t");
            sb.append("   LEFT OUTER JOIN (");
            sb.append("      SELECT travelNum, group_concat(saveFileName) AS saveFileName");
            sb.append(" 	 FROM travelFile");
            sb.append("      GROUP BY travelNum ");
            sb.append("   	 ) f on t.travelNum = f.travelNum");
            if (userId != null) {
                sb.append("   LEFT OUTER JOIN ( ");
                sb.append("   	 SELECT travelNum, likeNum ");
                sb.append("   	 FROM travelLike");
                sb.append("   	 WHERE userId = ? ");
                sb.append("   	 ) l on t.travelNum = l.travelNum ");
            }
            sb.append("	  WHERE type=? ");

            pstmt = conn.prepareStatement(sb.toString());
            if (userId != null) {
                pstmt.setString(1, userId);
                pstmt.setString(2, type);
            } else {
                pstmt.setString(1, type);
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {

                TravelDTO dto = new TravelDTO();
                dto.setNum(rs.getInt("travelNum"));
                dto.setPlace(rs.getString("place"));
                dto.setInformation(rs.getString("information"));
                dto.setUserId(rs.getString("userId"));
                dto.setCreated(rs.getString("created"));
                dto.setLikeCount(Math.max(rs.getInt("likeCount"), 0));
                if (rs.getString("saveFileName") != null) {
                    dto.setSaveFileName(rs.getString("saveFileName").split(","));
                }
                if (userId != null) {
                    dto.setLikeNum(rs.getInt("likeNum"));
                } else {
                    dto.setLikeNum(0);
                }
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
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
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("INSERT INTO travel(place, information, userid, type) VALUES(?,?,?,?) ");

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
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT t.travelNum, place, information, t.userid, userName, f.saveFileName, f.originalFileName, type ");
            sb.append("   FROM travel t JOIN member m ON t.userid = m.userid");
            sb.append("   LEFT OUTER JOIN (");
            sb.append("      SELECT travelNum, GROUP_CONCAT(saveFileName) AS saveFileName, group_concat(originalFileName) AS originalFileName");
            sb.append("      FROM travelFile");
            sb.append("      WHERE travelNum = ? ");
            sb.append("   GROUP BY travelNum ");
            sb.append("   ) f ON t.travelNum = f.travelNum ");
            sb.append("	  WHERE t.travelNum =? ");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);
            pstmt.setInt(2, num);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new TravelDTO();

                dto.setNum(rs.getInt("travelNum"));
                dto.setPlace(rs.getString("place"));
                dto.setInformation(rs.getString("information"));
                dto.setUserId(rs.getString("userid"));
                dto.setUserName(rs.getString("userName"));
                if (rs.getString("saveFileName") != null) {
                    dto.setOriginalFileName(rs.getString("originalFileName").split(","));
                    dto.setSaveFileName(rs.getString("saveFileName").split(","));
                }
                dto.setType(rs.getString("type"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
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
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE travel SET place=?, information=? WHERE travelNum=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getPlace());
            pstmt.setString(2, dto.getInformation());
            pstmt.setInt(3, dto.getNum());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public int deleteTravel(int num) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("DELETE FROM travel WHERE travelNum= ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);

            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public int searchTravelLike(int num, String userId){
        pstmt = null;
        sb = new StringBuilder();
        rs = null;

        try {
            sb.append("SELECT * FROM travelLike WHERE travelNum = ? AND userId = ?");
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);
            pstmt.setString(2, userId);

            rs = pstmt.executeQuery();

            if(rs.next()){
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public void insertLike(int likeCount, int num, String userId) {
        pstmt = null;
        sb = new StringBuilder();
        rs = null;

        try {
            if (likeCount <= 0) {
                sb.append("INSERT INTO travelLike(travelNum, userId) VALUES (?,?)");
            } else {
                sb.append("DELETE FROM travelLike WHERE travelNum = ? AND userId = ?");
            }

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertLikeCount(int likeCount, int num){
        pstmt = null;
        sb = new StringBuilder();
        rs = null;

        try {
            if (likeCount <= 0) {
                sb.append("UPDATE travel SET likeCount=likeCount+1 WHERE travelNum=?");
            } else {
                sb.append("UPDATE travel SET likeCount=likeCount-1 WHERE travelNum=?");
            }

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertImage(TravelDTO dto, String originalFileName, String saveFileName) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;

        try {
            if (dto.getNum() == 0) {
                sb.append("INSERT INTO travelFile(travelNum, originalFileName, saveFileName) VALUES((SELECT MAX(travelNum) from travel), ?, ?)");
            } else {
                sb.append("INSERT INTO travelFile(travelNum, originalFileName, saveFileName) VALUES(" + dto.getNum() + ", ?, ?)");
            }

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, originalFileName);
            pstmt.setString(2, saveFileName);

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int deleteImage(String name) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;

        try {

            sb.append("DELETE FROM travelFile WHERE saveFileName = ? ");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, name);
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public WeatherDTO listWeather(String area) {
        StringBuilder urlBuilder = new StringBuilder("https://api.openweathermap.org/data/2.5/weather");
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
            while ((line = bf.readLine()) != null) {
                result = result.concat(line);
            }

            JSONParser jsonParser = new JSONParser();
            // To JSONObject
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);

            JSONObject WeatherObj = (JSONObject) ((JSONArray) jsonObj.get("weather")).get(0);
            JSONObject CoordObj = (JSONObject) jsonObj.get("main");

            dto.setWeather(WeatherObj.get("main").toString() + ".svg");
            dto.setTem(String.format("%.2f", Float.parseFloat(String.valueOf(CoordObj.get("temp"))) - 273.15));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    public List<Map<String, String>> allTravelImage() {
        sb = new StringBuilder();
        pstmt = null;
        rs = null;
        List<Map<String, String>> list = new ArrayList<>();

        try {
            sb.append("SELECT saveFileName, place, type FROM travelFile JOIN travel t ON travelFile.travelNum = t.travelNum");
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String type = "";
                Map<String, String> map = new HashMap<>();
                map.put("saveFileName", rs.getString("saveFileName"));
                map.put("place", rs.getString("place"));
                map.put("type", rs.getString("type"));

                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
