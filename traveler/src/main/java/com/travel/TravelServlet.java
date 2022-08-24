package com.travel;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/travel/*")
@MultipartConfig
public class TravelServlet extends MyUploadServlet {

	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		HttpSession session = req.getSession();

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "travel";

		if (uri.contains("list.do")) {
			list(req, resp);
		} else if (uri.contains("created.do")) {
			createdForm(req, resp);
		} else if (uri.contains("created_ok.do")) {
			createdSubmit(req, resp);
		} else if (uri.contains("update.do")) {
			updateForm(req, resp);
		} else if (uri.contains("update_ok.do")) {
			updateSubmit(req, resp);
		} else if (uri.contains("delete.do")) {
			delete(req, resp);
		} else if (uri.contains("deleteFile.do")) {
			deleteFile(req, resp);
		} else if (uri.contains("like.do")) {
			like(req, resp);
		}

	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		String type = req.getParameter("type");

		// �˻�
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");

		if (condition == null) {
			condition = "subject";
			keyword = "";
		}

		if (req.getMethod().equalsIgnoreCase("GET")) {
			keyword = URLDecoder.decode(keyword, "utf-8");
		}

		// ���� ��¥ ���
		Date nowTime = new Date();
		SimpleDateFormat day = new SimpleDateFormat("MM dd, yyyy");
		String date = day.format(nowTime);

		int dataCount;
		List<TravelDTO> list;
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if(info == null){
			dataCount = dao.dataCount();
			list = dao.listTravel(type);
		} else if(info != null && keyword.length() != 0){
			dataCount = dao.dataCount(condition, keyword);
			list = dao.listTravel(condition, keyword, info.getUserId());
		} else {
			dataCount = dao.dataCount();
			list = dao.listTravel(type, info.getUserId());
		}

		String query = "";
		String listUrl = "";
		String articleUrl = "";

		listUrl = cp + "/travel/list.do";
		articleUrl = cp + "/travel/article.do";
		if (keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");

			listUrl += "?" + query;
			articleUrl += "?" + query;
		}
		
		WeatherDTO weatherDTO = dao.listWeather(type);

		// ������ jsp�� �ѱ� ������
		req.setAttribute("list", list);
		req.setAttribute("vo", weatherDTO);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("listUrl", listUrl);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("date", date);
		req.setAttribute("type", type);

		// JSP�� ������
		forward(req, resp, "/WEB-INF/views/travel/list.jsp?type="+type);

	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String type = req.getParameter("type");

		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(req.getContextPath() + "/travel/list.do?type="+type);
			return;
		}
		

		TravelDTO dto = new TravelDTO();

		dto.setUserId(info.getUserId());
		dto.setUserName(info.getUserName());

		req.setAttribute("type", type);
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/travel/created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String type = req.getParameter("type");
		
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/travel/list.do?type="+type);
			return;
		}

		TravelDTO dto = new TravelDTO();
		TravelDAO dao = new TravelDAO();

		dto.setUserId(info.getUserId());

		dto.setPlace(req.getParameter("place"));
		dto.setInformation(req.getParameter("information"));
		dto.setType(req.getParameter("type"));

		dao.insertTravel(dto);

		List<Part> list = new ArrayList<>();

		for (Part part : req.getParts()) {
			list.add(part);
		}

		Map<String, String[]> map = doFileUpload(list, pathname);
		if (map != null) {
			String[] saveFilenames = map.get("saveFilenames");

			for (String s : saveFilenames) {
				dao.insertImage(dto,s);
			}

		}

		resp.sendRedirect(cp + "/travel/list.do?type="+type);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String type = req.getParameter("type");
		

		TravelDTO dto = dao.readTravel(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/travel/list.do?type="+type);
			return;
		}

		if (!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/travel/list.do?type="+type);
			return;
		}

		req.setAttribute("type", type);
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/travel/created.jsp");

	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		TravelDTO dto = new TravelDTO();

		int num = Integer.parseInt(req.getParameter("num"));

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/travel/list.do");
		}

		dto.setNum(num);
		dto.setPlace(req.getParameter("place"));
		dto.setInformation(req.getParameter("information"));
		dto.setUserName(req.getParameter("name"));
		dto.setType(req.getParameter("type"));

		String type = dto.getType();
		
		if (req.getParts() != null) {

			List<Part> list = new ArrayList<>();

			for (Part part : req.getParts()) {
				list.add(part);
			}

			Map<String, String[]> map = doFileUpload(list, pathname);
			if (map != null) {
				dto.setImageFilename(map.get("saveFilenames"));

				for (String s : dto.getImageFilename()) {
					dao.insertImage(dto,s);
				}
			}
		}

		dao.updateTravel(dto);

		resp.sendRedirect(cp + "/travel/list.do?type="+type);

	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		
		String type = req.getParameter("type");
		
		TravelDTO dto = dao.readTravel(num);
		

		if (dto == null) {
			resp.sendRedirect(cp + "/travel/list.do?type="+type);
			return;
		}

		if (!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/travel/list.do?type="+type);
			return;
		}

		if (dto.getImageFilename() != null ) {
			for(int i=0; i<dto.getImageFilename().length; i++) {
				FileManager.doFiledelete(pathname, dto.getImageFilename()[i]);
				dao.deleteImage(dto.getImageFilename()[i]);
			}
		}

		dao.deleteTravel(num);

		resp.sendRedirect(cp + "/travel/list.do?type="+type);

	}

	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		TravelDAO dao = new TravelDAO();

		int num = Integer.parseInt(req.getParameter("num"));
		String filename = req.getParameter("filename");
		String type = req.getParameter("type");

		TravelDTO dto = dao.readTravel(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/travel/list.do?type="+type);
			return;
		}


		if (!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/travel/list.do?type="+type);
			return;
		}

		dao.deleteImage(filename);
		FileManager.doFiledelete(pathname, filename);
		
		dto = dao.readTravel(num);
		
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/travel/created.jsp?num="+num);

	}

	protected void like(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		TravelDAO dao = new TravelDAO();

		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String type = req.getParameter("type");
		
		dao.likeInsert(num, info.getUserId());

		resp.sendRedirect(cp + "/travel/list.do?type="+type);
	}

}
