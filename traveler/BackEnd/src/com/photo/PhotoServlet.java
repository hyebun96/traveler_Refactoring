package com.photo;

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
        } else if (uri.contains("updated.do")) {
            updateForm(req, resp);
        } else if (uri.contains("updated_ok.do")) {
            updateSubmit(req, resp);
        } else if (uri.contains("delete.do")) {
            delete(req, resp);
        } else if (uri.contains("createdTag.do")) {
            createdTag(req, resp);
        } else if (uri.contains("removeTag.do")) {
            removeTag(req, resp);
        } else if (uri.contains("deleteFile.do")) {
            deleteFile(req, resp);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PhotoDAO dao = new PhotoDAO();

        int photoNum;
        if(req.getParameter("photoNum") == null){
            photoNum = dao.firstdataNum();
        }else {
            photoNum = Integer.parseInt(req.getParameter("photoNum"));
        }

        List<PhotoDTO> allList = null;
        allList = dao.listPhoto(0);

        for(PhotoDTO dto: allList){
            List<TagDTO> tagList= dao.readTag(dto.getPhotoNum());
            if (tagList != null) {
                dto.setTagList(tagList);
            }
        }

        List<PhotoDTO> list = null;
        list = dao.listPhoto(photoNum);

        for(PhotoDTO dto: list){
            List<TagDTO> tagList= dao.readTag(dto.getPhotoNum());
            if (tagList != null) {
                dto.setTagList(tagList);
            }
        }

        PhotoDTO prevPhotoDTO = null;
        PhotoDTO dto = null;
        PhotoDTO nextPhotoDTO = null;

        if(list.size() == 2){
            dto = list.get(0);
            if(list.get(1).getPhotoNum() > photoNum ){
               nextPhotoDTO = list.get(1);
           }else {
               prevPhotoDTO = list.get(1);
           }
        } else if(list.size() == 1){
            dto = list.get(0);
        } else {
            prevPhotoDTO = list.get(1);
            dto = list.get(0);
            nextPhotoDTO = list.get(2);
        }


        req.setAttribute("allList", allList);
        req.setAttribute("prevPhotoDTO", prevPhotoDTO);
        req.setAttribute("dto", dto);
        req.setAttribute("nextPhotoDTO", nextPhotoDTO);

        forward(req, resp, "/WEB-INF/views/photo/list.jsp");
    }

    private void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("mode", "created");

        forward(req, resp, "/WEB-INF/views/photo/write.jsp");
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
        dto.setPlace(req.getParameter("place"));

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

        req.setAttribute("mode", "updated");

        forward(req, resp, "/WEB-INF/views/photo/write.jsp");
    }

    private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cp = req.getContextPath();
        PhotoDAO dao = new PhotoDAO();
        PhotoDTO dto = new PhotoDTO();

        int photoNum = Integer.parseInt(req.getParameter("photoNum"));
        if (req.getMethod().equalsIgnoreCase("")) {
            resp.sendRedirect(cp + "/photo/list.do?photoNum=" + photoNum);
            return;
        }

        dto.setPhotoNum(photoNum);
        dto.setSubject(req.getParameter("subject"));
        dto.setContent(req.getParameter("content"));
        dto.setPlace(req.getParameter("place"));
        String imageFilename = req.getParameter("imageFilename");

        if(imageFilename == null || imageFilename.equals("")){
            Part p = req.getPart("upload");
            Map<String, String> map = doFileUpload(p, pathname);
            if (map != null) {
                String filename = map.get("saveFilename");
                dto.setImageFilename(filename);
            }
        } else {
            dto.setImageFilename(imageFilename);
        }

        dao.updatePhoto(dto);
        resp.sendRedirect(cp + "/photo/list.do?photoNum=" + photoNum);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        PhotoDAO dao = new PhotoDAO();

        int photoNum = Integer.parseInt(req.getParameter("photoNum"));

        PhotoDTO dto = dao.readPhoto(photoNum);
        if (dto == null) {
            resp.sendRedirect(cp + "/photo/list.do");
            return;
        }

        if (!dto.getUserId().equals(info.getUserId()) && !info.getUserId().equals("admin")) {
            resp.sendRedirect(cp + "/photo/list.do?page");
            return;
        }

        FileManager.doFiledelete(pathname, dto.getImageFilename());
		dao.deletePhoto(photoNum);
        resp.sendRedirect(cp + "/photo/list.do");
    }

    private  void createdTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        if(info == null){
            String returnPage = "returnPage=" + "/photo/list.do";
            resp.sendRedirect(cp + "/main/access.do?" + returnPage);
            return;
        }

        PhotoDAO dao = new PhotoDAO();
        int photoNum = Integer.parseInt(req.getParameter("photoNum"));
        String addTag = req.getParameter("tag");

        dao.insertTag(addTag, photoNum);

        resp.sendRedirect(cp + "/photo/list.do?photoNum=" + photoNum);
    }


    private void removeTag(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        if(info == null){
            String returnPage = "returnPage=" + "/photo/list.do";
            resp.sendRedirect(cp + "/main/access.do?" + returnPage);
            return;
        }

        PhotoDAO dao = new PhotoDAO();
        int tagNum = Integer.parseInt(req.getParameter("tagNum"));
        int photoNum = Integer.parseInt(req.getParameter("photoNum"));

        dao.removeTag(tagNum);

        resp.sendRedirect(cp + "/photo/list.do?photoNum=" + photoNum);
    }

    private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cp = req.getContextPath();
        HttpSession session = req.getSession();
        SessionInfo info = (SessionInfo) session.getAttribute("member");

        if(info == null){
            String returnPage = "returnPage=" + "/photo/list.do";
            resp.sendRedirect(cp + "/main/access.do?" + returnPage);
            return;
        }

        int photoNum = Integer.parseInt(req.getParameter("photoNum"));
        String mode = req.getParameter("mode");

        PhotoDAO dao = new PhotoDAO();
        dao.deleteFile(photoNum);

        resp.sendRedirect(cp + "/photo/" + mode + ".do?photoNum=" + photoNum);
    }
}
