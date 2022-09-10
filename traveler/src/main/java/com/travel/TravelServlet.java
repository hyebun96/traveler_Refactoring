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
import java.text.SimpleDateFormat;
import java.util.*;

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

		Date nowTime = new Date();
		SimpleDateFormat day = new SimpleDateFormat("MM dd, yyyy");
		WeatherDTO weatherDTO = dao.listWeather(type);
		weatherDTO.setDate(day.format(nowTime));

		int dataCount;
		List<TravelDTO> list;
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		dataCount = dao.dataCount();
		if (info != null) {
			list = dao.listTravel(type, info.getUserId());
		} else {
			list = dao.listTravel(type, null);
		}

		req.setAttribute("list", list);
		req.setAttribute("weatherDTO", weatherDTO);
		req.setAttribute("type", type);

		forward(req, resp, "/WEB-INF/views/travel/list.jsp?type=" + type);
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

		forward(req, resp, "/WEB-INF/views/travel/write.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String type = req.getParameter("type");

		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/travel/list.do?type=" + type);
			return;
		}

		TravelDAO dao = new TravelDAO();
		TravelDTO dto = new TravelDTO();

		dto.setUserId(info.getUserId());
		dto.setPlace(req.getParameter("place"));
		dto.setInformation(req.getParameter("information"));
		dto.setType(req.getParameter("type"));

		dao.insertTravel(dto);

		if (req.getParts() != null) {

			List<Part> list = new ArrayList<>();
			for (Part part : req.getParts()) {
				list.add(part);
			}

			Map<String, String[]> map = doFileUpload(list, pathname);
			if (map != null) {
				String[] originalFileNames = map.get("originalFilenames");
				String[] saveFileNames = map.get("saveFileNames");
				for (int i = 0; i < originalFileNames.length; i++) {
					dao.insertImage(dto, originalFileNames[i], saveFileNames[i]);
				}
			}
		}

		resp.sendRedirect(cp + "/travel/list.do?type=" + type);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String type = req.getParameter("type");

		TravelDAO dao = new TravelDAO();
		TravelDTO dto = dao.readTravel(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/travel/list.do?type=" + type);
			return;
		}

		if (!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/travel/list.do?type=" + type);
			return;
		}

		Map<String, String> imageList = new HashMap<>();
		if (dto.getSaveFileName() != null) {
			for (int i = 0; i < dto.getSaveFileName().length; i++) {
				imageList.put(dto.getOriginalFileName()[i], dto.getSaveFileName()[i]);
			}
		}

		req.setAttribute("type", type);
		req.setAttribute("dto", dto);
		req.setAttribute("imageList", imageList);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/travel/write.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TravelDAO dao = new TravelDAO();
		String cp = req.getContextPath();

		TravelDTO dto = new TravelDTO();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/travel/list.do");
		}

		dto.setNum(Integer.parseInt(req.getParameter("num")));
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
				String[] originalFileNames = map.get("originalFilenames");
				String[] saveFileNames = map.get("saveFileNames");
				for (int i = 0; i < originalFileNames.length; i++) {
					dao.insertImage(dto, originalFileNames[i], saveFileNames[i]);
				}
			}
		}

		dao.updateTravel(dto);

		resp.sendRedirect(cp + "/travel/list.do?type=" + type);

	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		TravelDAO dao = new TravelDAO();

		int num = Integer.parseInt(req.getParameter("num"));
		String type = req.getParameter("type");
		TravelDTO dto = dao.readTravel(num);

		if (dto == null) {
			resp.sendRedirect(cp + "/travel/list.do?type=" + type);
			return;
		}

		if (!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/travel/list.do?type=" + type);
			return;
		}

		if (dto.getSaveFileName() != null) {
			for (int i = 0; i < dto.getSaveFileName().length; i++) {
				FileManager.doFiledelete(pathname, dto.getSaveFileName()[i]);
				dao.deleteImage(dto.getSaveFileName()[i]);
			}
		}

		dao.deleteTravel(num);

		resp.sendRedirect(cp + "/travel/list.do?type=" + type);

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
			resp.sendRedirect(cp + "/travel/list.do?type=" + type);
			return;
		}

		if (!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/travel/list.do?type=" + type);
			return;
		}

		dao.deleteImage(filename);
		FileManager.doFiledelete(pathname, filename);
		
		dto = dao.readTravel(num);
		
		req.setAttribute("dto", dto);
		req.setAttribute("type", type);
		req.setAttribute("mode", "update");

		forward(req, resp,   "/travel/update.do?num=" + num);
	}

	protected void like(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		TravelDAO dao = new TravelDAO();

		String cp = req.getContextPath();

		int num = Integer.parseInt(req.getParameter("num"));
		String type = req.getParameter("type");

		int likeCount = dao.searchTravelLike(num, info.getUserId());
		dao.insertLike(likeCount, num, info.getUserId());
		dao.insertLikeCount(likeCount, num);

		resp.sendRedirect(cp + "/travel/list.do?type="+type);
	}
}
