package com.main;

import com.notice.NoticeDAO;
import com.notice.NoticeDTO;
import com.travel.TravelDAO;
import com.util.MyServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@WebServlet("/main/*")
public class MainServlet extends MyServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uri = req.getRequestURI();

        if (uri.contains("main.do")) {
            mainNotice(req, resp);
        } else if (uri.contains("access.do")) {
            access(req, resp);
        }
    }

    protected void mainNotice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoticeDAO dao = new NoticeDAO();
        String cp = req.getContextPath();
        List<NoticeDTO> list = dao.importantList();

        String articleUrl = cp + "/notice/viewNotice.do";

        for (NoticeDTO dto : list) {
            dto.setCreated(dto.getCreated().substring(0, 10));
        }

        // 이미지 출력
        TravelDAO travelDAO = new TravelDAO();
        List<Map<String, String>> travelImageList = travelDAO.allTravelImage();

        req.setAttribute("list", list);
        req.setAttribute("articleUrl", articleUrl);
        req.setAttribute("travelImageList", travelImageList);
        forward(req, resp, "/WEB-INF/views/main/main.jsp");
    }

    protected void access(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cp = req.getContextPath();

        String returnPage = req.getParameter("returnPage");
        String num = req.getParameter("num");
        String page = req.getParameter("page");
        String query = "";

        if(num != null && page != null){
            query = "num=" + num + "&page=" + page;
        } else if(num != null && page == null) {
            query = "num=" + num;
        } else if(num == null && page != null) {
            query = "page=" + page;
        }

        if(query.equals("")){
            req.setAttribute("query", returnPage);
        } else {
            req.setAttribute("query", returnPage + "?" + query);
        }
        forward(req, resp, "/WEB-INF/views/main/access.jsp");
    }
}
