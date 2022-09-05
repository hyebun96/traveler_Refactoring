package com.board;

import com.util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private final Connection conn = DBConn.getConnection();
    private StringBuilder sb;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public void inputBoard(BoardDTO dto) {
        sb = new StringBuilder();
        pstmt = null;
        
        try {
            sb.append("INSERT INTO board(name, id, title, content) VALUES(?,?,?,?)");
            
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getId());
            pstmt.setString(3, dto.getTitle());
            pstmt.setString(4, dto.getContent());

            pstmt.executeUpdate();
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
    }

    // 전체 데이터 갯수
    public int dataCount() {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT COALESCE(COUNT(*),0) FROM board");

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

    // 검색했을 때 데이터 갯수
    public int dataCount(String condition, String keyword) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT COALESCE(COUNT(*),0) FROM board ");
            if (condition.equals("writer")) {
                sb.append("  WHERE INSTR(name, ? ) = 1");
            } else if (condition.equals("contents")) {
                sb.append("  WHERE INSTR(content,?) >=1");
            } else {
                sb.append("  WHERE INSTR(").append(condition).append(", ?) >= 1");
            }

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, keyword);

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

	public List<BoardDTO> allBoard(int offset, int rows) {
		List<BoardDTO> list = new ArrayList<>();
		pstmt= null;
		rs = null;
		sb = new StringBuilder();

		try {
			sb.append("SELECT boardNum, title, name, DATE_FORMAT(created,'%Y-%m-%d') created, viewCount, imageFilename");
			sb.append("  FROM board b");
			sb.append("  JOIN member m on b.id = m.userId");
			sb.append("  ORDER BY boardNum DESC ");
			sb.append("  LIMIT ? OFFSET ? ");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, rows);
            pstmt.setInt(2, offset);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                BoardDTO dto = new BoardDTO();

                dto.setNum(rs.getInt("boardNum"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setCreated(rs.getString("created"));
                dto.setViewCount(rs.getInt("viewCount"));
                dto.setImageFilename(rs.getString("imageFilename") + ".png");

                list.add(dto);
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

        return list;
    }

    public List<BoardDTO> allBoard(int offset, int rows, String condition, String keyword) {
        List<BoardDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT boardNum, title,name, DATE_FORMAT(created,'%Y-%m-%d') created, viewCount, imageFilename ");
            sb.append("  FROM board b");
            sb.append("  JOIN member m on b.id = m.userId");
            if (condition.equals("writer")) {
                sb.append("  WHERE INSTR(name, ? ) = 1");
            } else if (condition.equals("contents")) {
                sb.append("  WHERE INSTR(content,?) >=1");
            } else {
                sb.append("  WHERE INSTR(").append(condition).append(", ?) >= 1");
            }
            sb.append("  ORDER BY boardNum DESC ");
            sb.append("  LIMIT ? OFFSET ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, keyword);
            pstmt.setInt(2, rows);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                BoardDTO dto = new BoardDTO();

                dto.setNum(rs.getInt("boardNum"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setCreated(rs.getString("created"));
                dto.setViewCount(rs.getInt("viewCount"));
                dto.setImageFilename(rs.getString("imageFilename") + ".png");

                list.add(dto);
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
        return list;
    }

    public void updateViewCount(int num) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE board SET viewCount=viewCount+1 WHERE boardNum = ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);

            pstmt.executeUpdate();
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
    }

    public BoardDTO readBoard(int num) {
        BoardDTO dto = null;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT boardNum, id, name, title, content, created, viewCount, imageFilename");
            sb.append("  FROM board b ");
            sb.append("  JOIN member m on b.id = m.userId ");
            sb.append("  WHERE boardNum = ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new BoardDTO();
                dto.setNum(rs.getInt("boardNum"));
                dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setCreated(rs.getString("created"));
                dto.setViewCount(rs.getInt("viewCount"));
                dto.setImageFilename(rs.getString("imageFilename") + ".png");
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

    public void updateBoard(BoardDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE board SET title=?, content=? WHERE boardNum=? AND id=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setInt(3, dto.getNum());
            pstmt.setString(4, dto.getId());

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

    public void deleteBoard(int num, String userId) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            if (userId.equals("admin")) {
                sb.append("DELETE FROM board WHERE boardNum=?");
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setInt(1, num);
            } else {
                sb.append("DELETE FROM board WHERE boardNum=? AND id=?");
                pstmt = conn.prepareStatement(sb.toString());
                pstmt.setInt(1, num);
                pstmt.setString(2, userId);
            }
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
