package com.photo;

import com.mysql.cj.protocol.Resultset;
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

    public int dataCount() {
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;

        try {
            sql = "SELECT COALESCE(COUNT(*), 0) FROM photo";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next())
                result = rs.getInt(1);

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
        return result;
    }

    public void insertPhoto(PhotoDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        sb.append("INSERT INTO photo (userId,subject, content, imageFilename ) VALUES (?,?,?,?)");
        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getUserId());
            pstmt.setString(2, dto.getSubject());
            pstmt.setString(3, dto.getContent());
            pstmt.setString(4, dto.getImageFilename());

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
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(rs!= null){
                try{
                    rs.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List<PhotoDTO> listPhoto(int offset, int rows) {
        List<PhotoDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT photoNum, userId , place, subject, content, ");
            sb.append("   imageFilename, DATE_FORMAT(created,'%Y-%m-%d') AS created  ");
            sb.append("   FROM photo");
            sb.append("   ORDER BY photoNum DESC ");
            sb.append("   LIMIT ? OFFSET ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, rows);
            pstmt.setInt(2, offset);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                PhotoDTO dto = new PhotoDTO();
                dto.setPhotoNum(rs.getInt("photoNum"));
                dto.setUserId(rs.getString("userId"));
                dto.setPlace(rs.getString("place"));
                dto.setSubject(rs.getString("subject"));
                dto.setContent(rs.getString("content"));
                dto.setImageFilename(rs.getString("imageFilename"));
                dto.setCreated(rs.getString("created"));

                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
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
            sb.append("   imageFilename, tag, DATE_FORMAT(created,'%Y-%m-%d') AS created  ");
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
                dto.setImageFilename(rs.getString("imageFilename"));
                dto.setCreated(rs.getString("created"));
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
        return dto;
    }

    public void updatePhoto(PhotoDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE photo SET subject=?, content=?,  imageFilename=? ");
            sb.append(" WHERE photoNum=?");
            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContent());
            pstmt.setString(3, dto.getImageFilename());
            pstmt.setInt(4, dto.getPhotoNum());

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
}
