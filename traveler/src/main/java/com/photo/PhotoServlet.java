package com.photo;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@WebServlet("/photo/*")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
public class PhotoServlet extends MyUploadServlet {
    private static final long serialVersionUID = 1L;

    private String pathname;

    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uri = req.getRequestURI();

        HttpSession session = req.getSession();

        String root = session.getServletContext().getRealPath("/");
        pathname = root + "uploads" + File.separator + "photo";

        if (uri.contains("list.do")) {
            list(req, resp);
        } else if (uri.contains("created.do")) {
            createdForm(req, resp);
        } else if (uri.contains("created_ok.do")) {
            createdSubmit(req, resp);
        } else if (uri.contains("photoarticle.do")) {
            photoarticle(req, resp);
        } else if (uri.contains("update.do")) {
            updateForm(req, resp);
        } else if (uri.contains("update_ok.do")) {
            updateSubmit(req, resp);
        } else if (uri.contains("delete.do")) {
            delete(req, resp);
        } else if (uri.contains("createdTag.do")) {
            createdTag(req, resp);
        } else if (uri.contains("removeTag.do")) {
            removeTag(req, resp);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PhotoDAO dao = new PhotoDAO();
        MyUtil util = new MyUtil();
        String cp = req.getContextPath();

        String page = req.getParameter("page");
        int current_page = 1;
        if (page != null) {
            current_page = Integer.parseInt(page);
        }

        String keyword = req.getParameter("nam");
        if (keyword == null) {
            keyword = "";
        }

        int dataCount = dao.dataCount();

        int rows = 6;
        int total_page = util.pageCount(rows, dataCount);
        if (current_page > total_page) {
            current_page = total_page;
        }

        int offset = (current_page - 1) * rows;
        if(offset < 0){
            offset = 0;
        }

        List<PhotoDTO> list = null;
        list = dao.listPhoto(offset, rows);

        for(PhotoDTO dto: list){
            List<TagDTO> tagList= dao.readTag(dto.getPhotoNum());
            if (tagList != null) {
                dto.setTagList(tagList);
            }
        }

        String query = "";
        if (keyword.length() != 0) {
            query = "nam=" + URLEncoder.encode(keyword, "utf-8");
        }

        String listUrl = cp + "/photo/list.do";
        String photoarticleUrl = cp + "/photo/photoarticle.do?page=" + current_page;
        String paging = util.paging(current_page, total_page, listUrl);
        if (query.length() != 0) {
            listUrl += "?" + query;
            photoarticleUrl += "&" + query;
        }

        req.setAttribute("list", list);
        req.setAttribute("dataCount", dataCount);
        req.setAttribute("photoarticleUrl", photoarticleUrl);
        req.setAttribute("page", current_page);
        req.setAttribute("total_page", total_page);
        req.setAttribute("paging", paging);
        req.setAttribute("nam", keyword);

        forward(req, resp, "/WEB-INF/views/photo/list.jsp");
    }

    private void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("mode", "created");

        forward(req, resp, "/WEB-INF/views/photo/created.jsp");
    }

    private void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        PhotoDAO dao = new PhotoDAO();
        PhotoDTO dto = new PhotoDTO();

        dto.setUserId(info.getUserId());
        dto.setSubject(req.getParameter("subject"));
        dto.setContent(req.getParameter("content"));

        String filename = null;
        Part p = req.getPart("upload");
        Map<String, String> map = doFileUpload(p, pathname);
        if (map != null) {
            filename = map.get("saveFilename");
        }

        if (filename != null) {
            dto.setImageFilename(filename);
            dao.insertPhoto(dto);
        }

        resp.sendRedirect(cp + "/photo/list.do");
    }

    private void photoarticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();

        PhotoDAO dao = new PhotoDAO();

        int photoNum = Integer.parseInt(req.getParameter("photoNum"));
        String page = req.getParameter("page");

        PhotoDTO dto = dao.readPhoto(photoNum);

        if (dto == null) {
            resp.sendRedirect(cp + "/photo/list.do?page=" + page);
            return;
        }

        List<TagDTO> tagList= dao.readTag(dto.getPhotoNum());
        if (tagList != null) {
            dto.setTagList(tagList);
        }

        dto.setContent(dto.getContent().replaceAll("\n", "<br>"));

        req.setAttribute("dto", dto);
        req.setAttribute("page", page);

        forward(req, resp, "/WEB-INF/views/photo/photoarticle.jsp");
    }

    private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        PhotoDAO dao = new PhotoDAO();

        String page = req.getParameter("page");
        int photoNum = Integer.parseInt(req.getParameter("photoNum"));
        PhotoDTO dto = dao.readPhoto(photoNum);

        if (dto == null) {
            resp.sendRedirect(cp + "/photo/list.do?page=" + page);
            return;
        }

        if (!dto.getUserId().equals(info.getUserId())) {
            resp.sendRedirect(cp + "/photo/list.do?page=" + page);
            return;
        }

        req.setAttribute("dto", dto);
        req.setAttribute("page", page);

        req.setAttribute("mode", "update");

        forward(req, resp, "/WEB-INF/views/photo/created.jsp");
    }

    private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cp = req.getContextPath();
        PhotoDAO dao = new PhotoDAO();
        PhotoDTO dto = new PhotoDTO();

        String page = req.getParameter("page");
        if (req.getMethod().equalsIgnoreCase("")) {
            resp.sendRedirect(cp + "/photo/list.do?page=" + page);
            return;
        }

        String imageFilename = req.getParameter("imageFilename");
        dto.setPhotoNum(Integer.parseInt(req.getParameter("photoNum")));
        dto.setSubject(req.getParameter("subject"));
        dto.setContent(req.getParameter("content"));

        Part p = req.getPart("upload");
        Map<String, String> map = doFileUpload(p, pathname);
        if (map != null) {
            String filename = map.get("saveFilename");
            FileManager.doFiledelete(pathname, imageFilename);
            dto.setImageFilename(filename);
        } else {
            dto.setImageFilename(imageFilename);
        }

        dao.updatePhoto(dto);
        resp.sendRedirect(cp + "/photo/list.do?page=" + page);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        PhotoDAO dao = new PhotoDAO();

        int num = Integer.parseInt(req.getParameter("photoNum"));
        String page = req.getParameter("page");

        PhotoDTO dto = dao.readPhoto(num);
        if (dto == null) {
            resp.sendRedirect(cp + "/photo/list.do?page=" + page);
            return;
        }

        if (!dto.getUserId().equals(info.getUserId()) && !info.getUserId().equals("admin")) {
            resp.sendRedirect(cp + "/photo/list.do?page=" + page);
            return;
        }

        FileManager.doFiledelete(pathname, dto.getImageFilename());
		dao.deletePhoto(num);
        resp.sendRedirect(cp + "/photo/list.do?page=" + page);
    }

    private  void createdTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        if(info == null){
            resp.sendRedirect(cp + "/board/access.do");
            return;
        }

        PhotoDAO dao = new PhotoDAO();
        int photoNum = Integer.parseInt(req.getParameter("photoNum"));
        String addTag = req.getParameter("tag");

        dao.insertTag(addTag, photoNum);

        resp.sendRedirect(cp + "/photo/list.do");
    }

    private void removeTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        if(info == null){
            resp.sendRedirect(cp + "/board/access.do");
            return;
        }

        PhotoDAO dao = new PhotoDAO();
        int tagNum = Integer.parseInt(req.getParameter("tagNum"));

        dao.removeTag(tagNum);

        resp.sendRedirect(cp + "/photo/list.do");
    }

}
