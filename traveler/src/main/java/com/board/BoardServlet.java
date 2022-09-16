package com.board;

import com.member.SessionInfo;
import com.util.MyUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/board/*")
public class BoardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(path);
        rd.forward(req, resp);
    }

    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();

        if (uri.contains("list.do")) {
            board(req, resp);
        } else if (uri.contains("write.do")) {
            writeForm(req, resp);
        } else if (uri.contains("write_ok.do")) {
            writeSubmit(req, resp);
        } else if (uri.contains("view.do")) {
            viewBoard(req, resp);
        } else if (uri.contains("update.do")) {
            updateForm(req, resp);
        } else if (uri.contains("update_ok.do")) {
            updateSubmit(req, resp);
        } else if (uri.contains("delete.do")) {
            delete(req, resp);
        }
    }

    protected void board(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BoardDAO dao = new BoardDAO();
        MyUtil util = new MyUtil();

        String cp = req.getContextPath();

        String page = req.getParameter("page");
        int current_page = 1;
        if (page != null) {
            current_page = Integer.parseInt(page);
        }

        String condition = req.getParameter("condition");
        String keyword = req.getParameter("keyword");
        if (condition == null) {
            condition = "title";
            keyword = "";
        }

        if (req.getMethod().equalsIgnoreCase("GET")) {
            keyword = URLDecoder.decode(keyword, "utf-8");
        }

        int dataCount;
        if (keyword.length() == 0) {
            dataCount = dao.dataCount();
        } else {
            dataCount = dao.dataCount(condition, keyword);
        }

        int rows = 10;
        int total_page = util.pageCount(rows, dataCount);
        if (current_page > total_page)
            current_page = total_page;

        int offset = (current_page - 1) * rows;
        if (offset < 0) {
            offset = 0;
        }

        List<BoardDTO> list;
        if (keyword.length() == 0) {
            list = dao.allBoard(offset, rows);
        } else {
            list = dao.allBoard(offset, rows, condition, keyword);
        }

        int listNum, n = 0;
        for (BoardDTO dto : list) {
            listNum = dataCount - (offset + n);
            dto.setListNum(listNum);
            n++;
        }

        String query = "";
        if (keyword.length() != 0) {
            query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
        }

        String listUrl = cp + "/board/list.do";
        String articleUrl = cp + "/board/view.do?page=" + current_page;
        if (query.length() != 0) {
            listUrl += "?" + query;
            articleUrl += "&" + query;
        }

        String paging = util.paging(current_page, total_page, listUrl);

        req.setAttribute("list", list);
        req.setAttribute("page", current_page);
        req.setAttribute("total_page", total_page);
        req.setAttribute("dataCount", dataCount);
        req.setAttribute("articleUrl", articleUrl);
        req.setAttribute("paging", paging);
        req.setAttribute("condition", condition);
        req.setAttribute("keyword", keyword);

        forward(req, resp, "/WEB-INF/views/board/list.jsp");
    }

    protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("mode", "write");
        forward(req, resp, "/WEB-INF/views/board/write.jsp");
    }

    protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");


        BoardDAO dao = new BoardDAO();
        BoardDTO dto = new BoardDTO();
        dto.setTitle(req.getParameter("title"));
        dto.setContent(req.getParameter("content"));
        dto.setId(info.getUserId());
        dto.setName(info.getUserName());

        dao.inputBoard(dto);
        resp.sendRedirect(cp + "/board/list.do");
    }

    protected void viewBoard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cp = req.getContextPath();
        BoardDAO dao = new BoardDAO();
        MyUtil util = new MyUtil();

        int num = Integer.parseInt(req.getParameter("num"));
        String page = req.getParameter("page");
        String condition = req.getParameter("condition");
        String keyword = req.getParameter("keyword");
        if (condition == null) {
            condition = "title";
            keyword = "";
        }
        keyword = URLDecoder.decode(keyword, "utf-8");

        String query = "page=" + page;
        if (keyword.length() != 0) {
            query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
        }
        dao.updateViewCount(num);
        BoardDTO dto = dao.readBoard(num);
        if (dto == null) {
            resp.sendRedirect(cp + "/board/list.do?" + query);
            return;
        }
        dto.setContent(util.htmlSymbols(dto.getContent()));

        req.setAttribute("dto", dto);
        req.setAttribute("page", page);
        req.setAttribute("query", query);
        forward(req, resp, "/WEB-INF/views/board/view.jsp");
    }

    protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cp = req.getContextPath();
        BoardDAO dao = new BoardDAO();

        HttpSession session = req.getSession();

        String page = req.getParameter("page");
        int num = Integer.parseInt(req.getParameter("num"));
        BoardDTO dto = dao.readBoard(num);

        if (dto == null) {
            resp.sendRedirect(cp + "/board/list.do?page=" + page);
            return;
        }

        req.setAttribute("dto", dto);
        req.setAttribute("mode", "update");
        req.setAttribute("page", page);

        forward(req, resp, "/WEB-INF/views/board/write.jsp");
    }

    protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cp = req.getContextPath();
        BoardDAO dao = new BoardDAO();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        String page = req.getParameter("page");

        if (req.getMethod().equalsIgnoreCase("GET")) {
            resp.sendRedirect(cp + "/board/list.do?page=" + page);
            return;
        }


        BoardDTO dto = new BoardDTO();
        dto.setNum(Integer.parseInt(req.getParameter("num")));
        dto.setTitle(req.getParameter("title"));
        dto.setContent(req.getParameter("content"));
        dto.setId(info.getUserId());
        dao.updateBoard(dto);
        resp.sendRedirect(cp + "/board/list.do?page=" + page);
    }


    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cp = req.getContextPath();
        BoardDAO dao = new BoardDAO();

        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        String page = req.getParameter("page");
        int num = Integer.parseInt(req.getParameter("num"));
        String condition = req.getParameter("condition");
        String keyword = req.getParameter("keyword");
        if (condition == null) {
            condition = "title";
            keyword = "";
        }
        keyword = URLDecoder.decode(keyword, "utf-8");

        String query = "page=" + page;
        if (keyword.length() != 0) {
            query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
        }

        dao.deleteBoard(num, info.getUserId());
        resp.sendRedirect(cp + "/board/list.do?" + query);
    }
}
