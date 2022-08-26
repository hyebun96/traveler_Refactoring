package com.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.notice.NoticeDAO;
import com.notice.NoticeDTO;
import com.travel.TravelDAO;
import com.travel.TravelDTO;
import com.util.MyServlet;
import org.apache.poi.ss.formula.functions.T;

@WebServlet("/main.do")
public class MainServlet extends MyServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uri = req.getRequestURI();

        if (uri.contains("main.do")) {
            //forward(req, resp, "/WEB-INF/views/main/main.jsp");
            mainNotice(req, resp);
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

}
