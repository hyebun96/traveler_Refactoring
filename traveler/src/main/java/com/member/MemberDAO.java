package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MemberDAO {
    private final Connection conn = DBConn.getConnection();

    public MemberDTO readMember(String userId) {
        MemberDTO dto = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();

        try {
            sb.append("SELECT userId,userPwd,userName,userTel,userEmail,imageFilename");
            sb.append(" ,DATE_FORMAT(userBirth, '%Y-%m-%d') userBirth");
            sb.append(" FROM member");
            sb.append(" WHERE userId=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new MemberDTO();
                dto.setUserId(rs.getString("userId"));
                dto.setUserPwd(rs.getString("userPwd"));
                dto.setUserName(rs.getString("userName"));
                dto.setUserTel(rs.getString("userTel"));
                dto.setUserEmail(rs.getString("userEmail"));
                dto.setUserBirth(rs.getString("userBirth"));
                dto.setImageFilename(rs.getString("imageFilename"));

                if (dto.getUserTel() != null) {
                    String[] ss = dto.getUserTel().split("-");
                    if (ss.length == 3) {
                        dto.setTel1(ss[0]);
                        dto.setTel2(ss[1]);
                        dto.setTel3(ss[2]);
                    }
                }
                dto.setUserEmail(rs.getString("userEmail"));
                if (dto.getUserEmail() != null) {
                    String[] ss = dto.getUserEmail().split("@");
                    if (ss.length == 2) {
                        dto.setEmail1(ss[0]);
                        dto.setEmail2(ss[1]);
                    }
                }
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

    public void insertMember(MemberDTO dto) throws Exception {
        PreparedStatement pstmt = null;
        String sql;
        try {
            sql = "INSERT INTO member(userId, userPwd, userName, userTel, userEmail, userBirth, imageFilename) VALUES(?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getUserId());
            pstmt.setString(2, dto.getUserPwd());
            pstmt.setString(3, dto.getUserName());
            pstmt.setString(4, dto.getUserTel());
            pstmt.setString(5, dto.getUserEmail());
            pstmt.setString(6, dto.getUserBirth());
            pstmt.setString(7, dto.getImageFilename());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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

    public void updateMember(MemberDTO dto) throws Exception {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "UPDATE member SET userTel=?,userEmail=?,userBirth=?,imageFilename=? WHERE userId=?";

            pstmt = conn.prepareStatement(sql);

            // pstmt.setString(1, dto.getUserPwd());
            pstmt.setString(1, dto.getUserTel());
            pstmt.setString(2, dto.getUserEmail());
            pstmt.setString(3, dto.getUserBirth());
            pstmt.setString(4, dto.getImageFilename());
            pstmt.setString(5, dto.getUserId());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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

    public void deleteMember(String userId) throws Exception {
        PreparedStatement pstmt = null;
        String sql;

        try {
            sql = "DELETE FROM  member WHERE userId=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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

    public int dataCount() {
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;

        try {
            sql = "SELECT COALESCE(COUNT(*), 0) FROM member";
            pstmt = conn.prepareStatement(sql);

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

    public List<MemberDTO> listBoard(int offset, int rows) {
        List<MemberDTO> list = new ArrayList<>();
        MemberDTO dto;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();

        try {
            sb.append("SELECT userId,userPwd,userName,userTel,userEmail");
            sb.append(" ,DATE_FORMAT(userBirth, '%Y-%m-%d') userBirth");
            sb.append(" FROM member");
            sb.append(" LIMIT ? OFFSET ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, rows);
            pstmt.setInt(2, offset);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                dto = new MemberDTO();
                dto.setUserId(rs.getString("userId"));
                dto.setUserPwd(rs.getString("userPwd"));
                dto.setUserName(rs.getString("userName"));
                dto.setUserTel(rs.getString("userTel"));
                dto.setUserEmail(rs.getString("userEmail"));
                dto.setUserBirth(rs.getString("userBirth"));


                if (dto.getUserTel() != null) {
                    String[] ss = dto.getUserTel().split("-");
                    if (ss.length == 3) {
                        dto.setTel1(ss[0]);
                        dto.setTel2(ss[1]);
                        dto.setTel3(ss[2]);
                    }
                }
                dto.setUserEmail(rs.getString("userEmail"));
                if (dto.getUserEmail() != null) {
                    String[] ss = dto.getUserEmail().split("@");
                    if (ss.length == 2) {
                        dto.setEmail1(ss[0]);
                        dto.setEmail2(ss[1]);
                    }
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

    public int dataCount(String condition, String keyword) {
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;

        try {
            sql = "SELECT COALESCE(COUNT(*),0) FROM member WHERE INSTR(" + condition + ", ?) >=1";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, keyword);

            rs = pstmt.executeQuery();

            if (rs.next())
                result = rs.getInt(1);
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

    public List<MemberDTO> listBoard(int offset, int rows, String condition, String keyword) {
        List<MemberDTO> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuilder sb = new StringBuilder();

        try {
            sb.append("SELECT userId,userPwd,userName,userTel,userEmail");
            sb.append(" ,DATE_FORMAT(userBirth, '%Y-%m-%d') userBirth");
            sb.append(" FROM member");
            sb.append(" WHERE INSTR(").append(condition).append(", ? ) >=1");
            sb.append(" LIMIT ? OFFSET ?");

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, keyword);
            pstmt.setInt(2, rows);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                MemberDTO dto = new MemberDTO();
                dto = new MemberDTO();
                dto.setUserId(rs.getString("userId"));
                dto.setUserPwd(rs.getString("userPwd"));
                dto.setUserName(rs.getString("userName"));
                dto.setUserTel(rs.getString("userTel"));
                dto.setUserEmail(rs.getString("userEmail"));
                dto.setUserBirth(rs.getString("userBirth"));

                list.add(dto);
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
        return list;
    }
}
