package com.contact;

import com.util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private final Connection conn = DBConn.getConnection();
    private StringBuilder sb;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public void insertContact(ContactDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("INSERT INTO contact ( ctSubject, ctContent, ctName, ctTel, ctEmail, ctSort) VALUES (?,?,?,?,?,?)");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getCtSubject());
            pstmt.setString(2, dto.getCtContent());
            pstmt.setString(3, dto.getCtName());
            pstmt.setString(4, dto.getCtTel());
            pstmt.setString(5, dto.getCtEmail());
            pstmt.setString(6, dto.getCtSort());

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

    public int dataCount(String ctSort) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT COALESCE(COUNT(*),0) FROM contact");
            if (ctSort.length() != 0) {
                sb.append("WHERE ctSort= ? ");
            }

            pstmt = conn.prepareStatement(sb.toString());
            if (ctSort.length() != 0) {
                pstmt.setString(1, ctSort);
            }

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

    public int dataCount(String condition, String keyword, String ctSort) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT COALESCE(COUNT(*),0) FROM contact");
            if (condition.equals("ctDate")) {
                keyword = keyword.replaceAll("-", "");
                sb.append("   WHERE DATE_FORMAT(ctDate, '%Y-%m-%d')=?");
            } else {
                sb.append("   WHERE INSTR(").append(condition).append(", ?) >=1");
            }
            if (ctSort.length() != 0) {
                sb.append("   AND ctSort= ? ");
            }
            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, keyword);
            if (ctSort.length() != 0) {
                pstmt.setString(2, ctSort);
            }

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

    public List<ContactDTO> listContact(int offset, int rows, String ctSort) {
        List<ContactDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT ctSort, ctNum, ctName, ctSubject, DATE_FORMAT(ctDate, '%Y-%m-%d') ctDate, fin ");
            sb.append(" FROM contact ");
            if (ctSort.length() != 0) {
                sb.append(" WHERE ctSort= ? ");
            }
            sb.append(" ORDER BY ctNum DESC ");
            sb.append(" LIMIT ? OFFSET ? ");

            pstmt = conn.prepareStatement(sb.toString());

            if (ctSort.length() == 0) {
                pstmt.setInt(1, rows);
                pstmt.setInt(2, offset);
            } else {
                pstmt.setString(1, ctSort);
                pstmt.setInt(2, rows);
                pstmt.setInt(3, offset);
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ContactDTO dto = new ContactDTO();

                dto.setCtNum(rs.getInt("ctNum"));
                switch (rs.getString("ctSort")) {
                    case "sugg":
                        dto.setCtSort("제안");
                        break;
                    case "edit":
                        dto.setCtSort("정보수정요청");
                        break;
                    case "ad":
                        dto.setCtSort("광고문의");
                        break;
                    default:
                        dto.setCtSort("기타");
                        break;
                }
                dto.setCtName(rs.getString("ctName"));
                dto.setCtSubject(rs.getString("ctSubject"));
                dto.setCtDate(rs.getString("ctDate"));
                dto.setFin(rs.getInt("fin"));

                list.add(dto);
            }
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return list;
    }

    public List<ContactDTO> listContact(int offset, int rows, String condition, String keyword, String ctSort) {
        List<ContactDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT ctSort, ctNum, ctName, ctSubject, DATE_FORMAT(ctDate, '%Y-%m-%d') ctDate, fin ");
            sb.append(" FROM contact ");
            if (condition.equals("ctDate")) {
                keyword = keyword.replaceAll("-", "");
                sb.append(" WHERE DATE_FORMAT(ctDate, '%Y-%m-%d')=?");
            } else {
                sb.append(" WHERE INSTR(").append(condition).append(", ?) >=1");
            }

            if (ctSort.length() != 0) {
                sb.append(" AND ctSort= ? ");
            }
            sb.append(" ORDER BY ctNum DESC ");
            sb.append(" LIMIT ? OFFSET ? ");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, keyword);
            if (ctSort.length() == 0) {
                pstmt.setInt(2, rows);
                pstmt.setInt(3, offset);
            } else {
                pstmt.setString(2, ctSort);
                pstmt.setInt(3, rows);
                pstmt.setInt(4, offset);
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ContactDTO dto = new ContactDTO();
                switch (rs.getString("ctSort")) {
                    case "sugg":
                        dto.setCtSort("제안");
                        break;
                    case "edit":
                        dto.setCtSort("정보수정요청");
                        break;
                    case "ad":
                        dto.setCtSort("광고문의");
                        break;
                    default:
                        dto.setCtSort("기타");
                        break;
                }
                dto.setCtNum(rs.getInt("ctNum"));
                dto.setCtName(rs.getString("ctName"));
                dto.setCtSubject(rs.getString("ctSubject"));
                dto.setCtDate(rs.getString("ctDate"));
                dto.setFin(rs.getInt("fin"));

                list.add(dto);
            }
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return list;
    }

    public ContactDTO readContact(int ctNum) {
        ContactDTO dto = null;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT ctNum, ctSort, ctName, ctSubject, ctContent");
            sb.append(", ctTel, ctEmail, ctDate, fin");
            sb.append(" FROM contact WHERE ctNum=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, ctNum);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                dto = new ContactDTO();
                dto.setCtNum(rs.getInt("ctNum"));
                switch (rs.getString("ctSort")) {
                    case "sugg":
                        dto.setCtSort("제안");
                        break;
                    case "edit":
                        dto.setCtSort("정보수정요청");
                        break;
                    case "ad":
                        dto.setCtSort("광고문의");
                        break;
                    default:
                        dto.setCtSort("기타");
                        break;
                }
                dto.setCtName(rs.getString("ctName"));
                dto.setCtSubject(rs.getString("ctSubject"));
                dto.setCtContent(rs.getString("ctContent"));
                dto.setCtTel(rs.getString("ctTel"));
                dto.setCtEmail(rs.getString("ctEmail"));
                dto.setCtDate(rs.getString("ctDate"));
                dto.setFin(rs.getInt("fin"));
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

    public void updateContact(int ctNum, String fin) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            if (fin.equals("1")) {
                sb.append("UPDATE contact SET fin=0 WHERE ctNum=?");
            } else {
                sb.append("UPDATE contact SET fin=1 WHERE ctNum=?");
            }

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, ctNum);
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

    public void deleteContact(int ctNum) {
		sb = new StringBuilder();
		pstmt = null;

        try {
            sb.append( "DELETE FROM contact WHERE ctNum=?");
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, ctNum);
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
}
