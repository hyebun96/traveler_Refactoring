package com.notice;

import com.util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAO {
    private final Connection conn = DBConn.getConnection();
    private StringBuilder sb;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public void insertNotice(NoticeDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("INSERT INTO notice(num,id,name,title,content,important) ");
            sb.append("VALUES(?,?,?,?,?,?)");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, dto.getNum());
            pstmt.setString(2, dto.getId());
            pstmt.setString(3, dto.getName());
            pstmt.setString(4, dto.getTitle());
            pstmt.setString(5, dto.getContent());
            pstmt.setInt(6, dto.getImportant());

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

    public void insertNoticeFile(NoticeDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("INSERT INTO noticeFile(num, saveFilename, originalFilename, filesize) ");
            sb.append(" VALUES( (SELECT MAX(num) FROM notice ),?,?,?)");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, dto.getSaveFileName());
            pstmt.setString(2, dto.getOriginalFileName());
            pstmt.setLong(3, dto.getFileSize());

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

    public int dataCount() {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT COALESCE(COUNT(*), 0) FROM notice");

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

    public int dataCount(String condition, String keyword) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT COALESCE(COUNT(*),0) FROM notice ");
            if (condition.equals("writer")) {
                sb.append("WHERE INSTR(name, ? ) = 1");
            } else if (condition.equals("contents")) {
                sb.append("WHERE INSTR(content,?) >=1");
            } else {
                sb.append("WHERE INSTR(").append(condition).append(", ?) >= 1");
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

    public List<NoticeDTO> listNotice(int offset, int rows) {
        List<NoticeDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT num, id, name, title, ");
            sb.append("       viewCount, DATE_FORMAT(created,'%Y-%m-%d') created ");
            sb.append(" FROM notice ");
            sb.append(" ORDER BY num DESC  ");
            sb.append(" LIMIT ? OFFSET ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, rows);
            pstmt.setInt(2, offset);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                NoticeDTO dto = new NoticeDTO();

                dto.setNum(rs.getInt("num"));
                dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setViewCount(rs.getInt("viewCount"));
                dto.setCreated(rs.getString("created"));

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

    public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
        List<NoticeDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT num, id, name, title, ");
            sb.append("       viewCount, DATE_FORMAT(created,'%Y-%m-%d') created ");
            sb.append(" FROM notice ");
            if (condition.equals("write")) {
                sb.append("WHERE INSTR(name, ? ) = 1");
            } else if (condition.equals("contents")) {
                sb.append("WHERE INSTR(content,?) >=1");
            } else {
                sb.append("WHERE INSTR(").append(condition).append(", ?) >= 1");
            }
            sb.append(" ORDER BY num DESC  ");
            sb.append(" LIMIT ? OFFSET ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, keyword);
            pstmt.setInt(2, rows);
            pstmt.setInt(3, offset);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                NoticeDTO dto = new NoticeDTO();

                dto.setNum(rs.getInt("num"));
                dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setViewCount(rs.getInt("viewCount"));
                dto.setCreated(rs.getString("created"));

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

    public NoticeDTO readNotice(int num) {
        NoticeDTO dto = null;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT num,important,id,name,title,content,viewCount,created ");
            sb.append(" FROM notice ");
            sb.append(" WHERE num = ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new NoticeDTO();

                dto.setNum(rs.getInt("num"));
                dto.setImportant(rs.getInt("important"));
                dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setViewCount(rs.getInt("viewCount"));
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

    public List<NoticeDTO> fileList(int num) {
        List<NoticeDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT fileNum, num, saveFileName, originalFileName, fileSize ");
            sb.append(" FROM noticeFile ");
            sb.append(" WHERE num = ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NoticeDTO dto = new NoticeDTO();

                dto.setFileNum(rs.getInt("fileNum"));
                dto.setNum(rs.getInt("num"));
                dto.setSaveFileName(rs.getString("saveFileName"));
                dto.setOriginalFileName(rs.getString("originalFileName"));
                dto.setFileSize(rs.getLong("fileSize"));

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

    public void updateHitCount(int num) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE notice SET viewCount = notice.viewCount+1 WHERE num=?");

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setInt(1, num);
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

    public List<NoticeDTO> importantList() {
        List<NoticeDTO> list = new ArrayList<>();
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT num,id,name,title,viewCount,created ");
            sb.append("FROM notice ");
            sb.append("WHERE important = 1 ");
            sb.append(" ORDER BY created DESC ,num DESC  ");

            pstmt = conn.prepareStatement(sb.toString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                NoticeDTO dto = new NoticeDTO();

                dto.setNum(rs.getInt("num"));
                dto.setId(rs.getString("id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setCreated(rs.getString("created"));
                dto.setViewCount(rs.getInt("viewCount"));

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

    public void updateNotice(NoticeDTO dto) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("UPDATE notice");
            sb.append(" SET important=?, title=?, content=?, created = NOW()");
            sb.append(" WHERE num=?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, dto.getImportant());
            pstmt.setString(2, dto.getTitle());
            pstmt.setString(3, dto.getContent());
            pstmt.setInt(4, dto.getNum());
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

    public void deleteNotice(int num) {
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("DELETE FROM notice WHERE num = ? ");
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);
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

    public NoticeDTO readFileNum(int fileNum) {
        NoticeDTO dto = null;
        sb = new StringBuilder();
        pstmt = null;
        rs = null;

        try {
            sb.append("SELECT fileNum, num, saveFileName, originalFileName, fileSize ");
            sb.append(" FROM noticeFile ");
            sb.append(" WHERE fileNum = ?");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, fileNum);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new NoticeDTO();

                dto.setFileNum(rs.getInt("fileNum"));
                dto.setNum(rs.getInt("num"));
                dto.setSaveFileName(rs.getString("saveFileName"));
                dto.setOriginalFileName(rs.getString("originalFileName"));
                dto.setFileSize(rs.getLong("fileSize"));
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

    public void deleteNoticeFile(int fileNum) {
        int result = 0;
        sb = new StringBuilder();
        pstmt = null;

        try {
            sb.append("DELETE FROM noticeFile WHERE fileNum = ? ");

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, fileNum);
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
