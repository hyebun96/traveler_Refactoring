package com.qna;

import com.util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QnaDAO {
    private Connection conn = DBConn.getConnection();
    private StringBuilder sb;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public int insertQna(QnaDTO dto, String mode) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            if (mode.equals("write")) {
                dto.setDepth(0);
                dto.setParent(0);
            } else if (mode.equals("reply")) {
                dto.setDepth(1);
            }
            sb.append("INSERT INTO qna(userId, subject, content, groupNum, depth, parent) VALUES (?,?,?,?,?,?)");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getUserId());
            pstmt.setString(2, dto.getSubject());
            pstmt.setString(3, dto.getContent());
            pstmt.setInt(4, dto.getGroupNum());
            pstmt.setInt(5, dto.getDepth());
            pstmt.setInt(6, dto.getParent());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
					e2.printStackTrace();
                }
            }
        }
        return result;
    }

    public int dataCount() {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT COALESCE(COUNT(*),0) FROM qna");
            sb.append(" WHERE depth = 0");

            pstmt = conn.prepareStatement(sb.toString());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e2) {
					e2.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
					e2.printStackTrace();
                }
            }
        }
        return result;
    }

    public int dataCount(String condition, String keyword) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            if (condition.equals("write")) {
                keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
                sb.append("SELECT COALESCE(COUNT(*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE DATE_FORMAT(created, '%Y-%m-%d') = ?  ");
            } else if (condition.equals("userName")) {
                sb.append("SELECT COALESCE(COUNT(*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE INSTR(userName, ?) = 1 ");
            } else {
                sb.append("SELECT COALESCE(COUNT(*), 0) FROM qna q JOIN member m ON q.userId=m.userId WHERE INSTR(").append(condition).append(", ?) >= 1 ");
            }
            sb.append(" WHERE depth = 0");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, keyword);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e2) {
					e2.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
					e2.printStackTrace();
                }
            }
        }
        return result;
    }

    public List<QnaDTO> listQna(int offset, int rows, int depth) {
        List<QnaDTO> list = new ArrayList<QnaDTO>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT qnaNum, q.userId, userName, ");
            sb.append(" subject, content, groupNum, depth, parent, hitCount, ");
            sb.append(" DATE_FORMAT(created,'%Y-%m-%d') AS created ");
            sb.append(" FROM qna q JOIN member m ON q.userId=m.userId ");
            if(depth == 0){
                sb.append(" WHERE depth = 0 ");
                sb.append(" ORDER BY qnaNum DESC ");
                sb.append(" LIMIT ? OFFSET ?  ");

                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setInt(1, rows);
                pstmt.setInt(2, offset);

            } else {
                sb.append(" WHERE depth = 1 ");
                sb.append(" ORDER BY qnaNum DESC ");

                pstmt = conn.prepareStatement(sb.toString());
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                QnaDTO dto = new QnaDTO();
                dto.setQnaNum(rs.getInt("qnaNum"));
                dto.setUserId(rs.getString("userId"));
                dto.setUserName(rs.getString("userName"));
                dto.setSubject(rs.getString("subject"));
                dto.setContent(rs.getString("content"));
                dto.setGroupNum(rs.getInt("groupNum"));
                dto.setDepth(rs.getInt("depth"));
                dto.setParent(rs.getInt("parent"));
                dto.setHitCount(rs.getInt("hitCount"));
                dto.setCreated(rs.getString("created"));

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


    public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword, int depth) {
        List<QnaDTO> list = new ArrayList<QnaDTO>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT qnaNum, q.userId, userName, ");
            sb.append(" subject, content, groupNum, depth, parent, hitCount, ");
            sb.append(" DATE_FORMAT(created,'%Y-%m-%d') created ");
            sb.append(" FROM qna q JOIN member m ON q.userId=m.userId ");

            if(depth == 0){
                sb.append(" WHERE depth = 0 AND ");
            } else {
                sb.append(" WHERE depth = 1 AND ");
            }

            if (condition.equals("write")) {
                keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
                sb.append(" DATE_FORMAT(created, '%Y-%m-%d') = ?  ");
            } else if (condition.equals("userName")) {
                sb.append(" INSTR(userName, ?) = 1 ");
            } else {
                sb.append(" INSTR(").append(condition).append(", ?) >= 1  ");
            }

            sb.append(" ORDER BY qnaNum DESC ");
            if(depth == 0){
                sb.append(" LIMIT ? OFFSET ?  ");
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setInt(2, rows);
                pstmt.setInt(3, offset);
            } else {
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setString(1, keyword);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                QnaDTO dto = new QnaDTO();
                dto.setQnaNum(rs.getInt("qnaNum"));
                dto.setUserId(rs.getString("userId"));
                dto.setUserName(rs.getString("userName"));
                dto.setSubject(rs.getString("subject"));
                dto.setContent(rs.getString("content"));
                dto.setGroupNum(rs.getInt("groupNum"));
                dto.setDepth(rs.getInt("depth"));
                dto.setParent(rs.getInt("parent"));
                dto.setHitCount(rs.getInt("hitCount"));
                dto.setCreated(rs.getString("created"));

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

    public QnaDTO readQna(int qnaNum) {
        QnaDTO dto = null;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT qnaNum, q.userId, subject, content, imageFilename, ");
            sb.append("   created, hitCount, groupNum, depth, parent");
            sb.append(" FROM qna q JOIN member m ON q.userId=m.userId ");
            sb.append(" WHERE qnaNum=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, qnaNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new QnaDTO();
                dto.setQnaNum(rs.getInt("qnaNum"));
                dto.setUserId(rs.getString("userId"));
                dto.setSubject(rs.getString("subject"));
                dto.setContent(rs.getString("content"));
                dto.setCreated(rs.getString("created"));
                dto.setHitCount(rs.getInt("hitCount"));
                dto.setGroupNum(rs.getInt("groupNum"));
                dto.setDepth(rs.getInt("depth"));
                dto.setParent(rs.getInt("parent"));
                dto.setImageFilename(rs.getString("imageFilename") + ".png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return dto;
    }

    public int updateHitCount(int qnaNum) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE qna SET hitCount=hitCount+1 WHERE qnaNum=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, qnaNum);

            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return result;
    }

    public int updateQna(QnaDTO dto, String userId) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE qna SET subject=?, content=? WHERE qnaNum=? AND userId=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getSubject());
            pstmt.setString(2, dto.getContent());
            pstmt.setInt(3, dto.getQnaNum());
            pstmt.setString(4, userId);

            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return result;
    }

    public int deleteQna(int qnaNum, String userId) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;

        try {
            if (userId.equals("admin")) {
                sb.append("DELETE FROM qna WHERE qnaNum=?");
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setInt(1, qnaNum);
            } else {
                sb.append("DELETE FROM qna WHERE qnaNum=? AND userId=?");
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setInt(1, qnaNum);
                pstmt.setString(2, userId);
            }
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return result;
    }
}
