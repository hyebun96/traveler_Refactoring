package com.photo;

import com.util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDAO {
    private final Connection conn = DBConn.getConnection();
    private StringBuilder sb;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public int firstdata() {
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;

        try {
            sql = "SELECT MIN(photoNum) FROM photo";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finalexit(rs, pstmt);
        }
        return result;
    }

    public void insertPhoto(PhotoDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        sb.append("INSERT INTO photo (userId, place, subject, content, originalFilName, saveFilename ) VALUES (?,?,?,?,?,?)");
        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getUserId());
            pstmt.setString(2, dto.getPlace());
            pstmt.setString(3, dto.getSubject());
            pstmt.setString(4, dto.getContent());
            pstmt.setString(5, dto.getOriginalFilename());
            pstmt.setString(6, dto.getSaveFilename());

            pstmt.executeUpdate();

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
    }

    public List<PhotoDTO> listPhoto(int photoNum) {
        List<PhotoDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            if(photoNum != 0){
                sb.append("(SELECT photoNum, userId , place, subject, content, originalFilename, saveFilename, DATE_FORMAT(created,'%Y-%m-%d') AS created ");
                sb.append("   FROM photo ");
                sb.append("   WHERE photoNum <= ? ");
                sb.append("   ORDER BY photoNum DESC LIMIT 2)");
                sb.append("UNION ");
                sb.append("(SELECT photoNum, userId , place, subject, content, originalFilename, saveFilename, DATE_FORMAT(created,'%Y-%m-%d') AS created ");
                sb.append("   FROM photo ");
                sb.append("   WHERE photoNum > ? ");
                sb.append("   ORDER BY photoNum ASC LIMIT 1) ");

                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setInt(1, photoNum);
                pstmt.setInt(2, photoNum);

            } else {
                sb.append("SELECT photoNum, userId, place, subject, content, originalFilename, saveFilename, DATE_FORMAT(created, '%Y-%m-%d') AS created ");
                sb.append("   FROM photo ");
                sb.append("   ORDER BY photoNum ASC ");

                pstmt = conn.prepareStatement(sb.toString());
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                PhotoDTO dto = new PhotoDTO();
                dto.setPhotoNum(rs.getInt("photoNum"));
                dto.setUserId(rs.getString("userId"));
                dto.setPlace(rs.getString("place"));
                dto.setSubject(rs.getString("subject"));
                dto.setContent(rs.getString("content"));
                dto.setOriginalFilename(rs.getString("originalFilename"));
                dto.setSaveFilename(rs.getString("saveFilename"));
                dto.setCreated(rs.getString("created"));

                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finalexit(rs, pstmt);
        }
        return list;
    }

    public PhotoDTO readPhoto(int photoNum) {
        PhotoDTO dto = null;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT photoNum, userId , place, subject, content, ");
            sb.append("   originalFilename, saveFilename, DATE_FORMAT(created,'%Y-%m-%d') AS created  ");
            sb.append("   FROM photo");
            sb.append("   WHERE photoNum=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, photoNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new PhotoDTO();
                dto.setPhotoNum(rs.getInt("photoNum"));
                dto.setUserId(rs.getString("userId"));
                dto.setPlace(rs.getString("place"));
                dto.setSubject(rs.getString("subject"));
                dto.setContent(rs.getString("content"));
                dto.setOriginalFilename(rs.getString("originalFilename"));
                dto.setSaveFilename(rs.getString("saveFilename"));
                dto.setCreated(rs.getString("created"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finalexit(rs, pstmt);
        }
        return dto;
    }

    public void updatePhoto(PhotoDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE photo SET subject=?, content=?,  originalFilename=?, saveFilename=?, place=? ");
            sb.append(" WHERE photoNum=?");
            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContent());
            pstmt.setString(3, dto.getOriginalFilename());
            pstmt.setString(4, dto.getSaveFilename());
            pstmt.setString(5, dto.getPlace());
            pstmt.setInt(6, dto.getPhotoNum());

            pstmt.executeUpdate();

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
    }

    public void deletePhoto(int photoNum) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("DELETE FROM photo WHERE photoNum = ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, photoNum);

            pstmt.executeUpdate();
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
    }

    public void insertTag(String tag, int photoNum){
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("INSERT INTO photoTag(tag, photoNum) VALUES(?, ?)");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, tag);
            pstmt.setInt(2, photoNum);

            pstmt.executeUpdate();

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
    }

    public List<TagDTO> readTag(int photoNum){
        List<TagDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT tagNum, tag, photoNum FROM photoTag WHERE photoNum = ?");
            sb.append("   ORDER BY tagNum DESC");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, photoNum);

            rs = pstmt.executeQuery();
            while(rs.next()){
                TagDTO tagDTO = new TagDTO();

                tagDTO.setTagNum(rs.getInt("tagNum"));
                tagDTO.setTag(rs.getString("tag"));
                tagDTO.setPhotoNum(rs.getInt("photoNum"));

                list.add(tagDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finalexit(rs, pstmt);
        }
        return list;
    }

    public void removeTag(int tagNum) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("DELETE FROM photoTag WHERE tagNum = ?");
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, tagNum);
            pstmt.executeUpdate();
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
    }

    public void deleteFile(int photoNum){
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE photo SET saveFilename = null, originalFilename = null WHERE photoNum = ?");
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, photoNum);
            pstmt.executeUpdate();
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
    }

    public void finalexit(ResultSet rs, PreparedStatement pstmt){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException
                    e) {
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
}
