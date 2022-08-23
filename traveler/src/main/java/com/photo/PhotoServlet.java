package com.photo;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.MemberDTO;
import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@WebServlet("/photo/*")
@MultipartConfig(						// @MultipartConfig�� ����ϸ� req.getParameter()�� �Ķ���͸� ������ �ִ�.
		maxFileSize = 1024*1024*5,		// ���ε�� �ϳ��� ���� ũ��
		maxRequestSize = 1024*1024*5*5	// �� ��ü �뷮
)
public class PhotoServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { // �α��ε��� ���� ���
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// �̹����� ������ ���(pathname)
		String root=session.getServletContext().getRealPath("/");
		pathname=root+File.separator+"uploads"+File.separator+"photo";
		
		// uri�� ���� �۾� ����
		if(uri.indexOf("photoMain.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("photoarticle.do")!=-1) {
			photoarticle(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}
	}

	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �Խù� ����Ʈ
		PhotoDAO dao=new PhotoDAO();
		MyUtil util=new MyUtil();
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		
		String keyword=req.getParameter("nam");
		if(keyword==null) {
			keyword="";
		}
		
		// ��ü������ ����
		int dataCount=dao.dataCount();

		// ��ü��������
		int rows=6;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		// �Խù� ������ ���۰� ����ġ
		int offset=(current_page-1)*rows;
		
		// �Խù� ��������
//		List<PhotoDTO> photoMain=dao.listPhoto(offset, rows);
		List<PhotoDTO> photoMain=null;
		if(keyword.length()==0) {
			photoMain=dao.listPhotoUser(offset, rows);
			
		} else {
			photoMain=dao.listPhotoUser(keyword);
		}
		
		List<MemberDTO> photoMain1=null;
		if(keyword.length()==0) {
			photoMain1=dao.listMemberUser(offset, rows);
		} else {
			photoMain1=dao.listMemberUser(keyword);
		}
		
		
		
		
		String query="";
		if(keyword.length()!=0) {
			query= "nam="+URLEncoder.encode(keyword, "utf-8");
		}
		
		
		// ����¡ ó��
		String listUrl=cp+"/photo/photoMain.do";
		String photoarticleUrl = cp + "/photo/photoarticle.do?page="+current_page;
		String paging=util.paging(current_page, total_page, listUrl);
		if(query.length()!=0) {
			listUrl+="?"+query;
			photoarticleUrl+="&"+query;
		}
		
		
		// �������� list.jsp�� �ѱ� ��
		req.setAttribute("photoMain", photoMain);
		req.setAttribute("photoMain1", photoMain1);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("photoarticleUrl", photoarticleUrl);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("nam", keyword);
		
		forward(req, resp, "/WEB-INF/views/photo/photoMain.jsp");
	}
	
	private void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �۾��� ��
		req.setAttribute("mode", "created");

		forward(req, resp, "/WEB-INF/views/photo/created.jsp");
	}
	
	private void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �Խù� ����
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		PhotoDAO dao=new PhotoDAO();
		PhotoDTO dto=new PhotoDTO();
		
		
		dto.setUserId(info.getUserId());
		
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		
		
		String filename=null;
		Part p = req.getPart("upload");
		Map<String, String> map = doFileUpload(p, pathname);
		if(map != null) {
			filename = map.get("saveFilename");
		}
		
/*		
		for(Part p : req.getParts()) { // ������ ���� ���ε��� ��� ��
			String contentType = p.getContentType();

			if(contentType!=null) { // �����̸�
				// long size=p.getSize();
				Map<String, String> map = doFileUpload(p, pathname);
				if(map==null) continue;
				filename = map.get("saveFilename");
			} else {
				// ������ �ƴϸ�(<input type="text"... ��)
			}
		}
*/
		if(filename!=null) {
			dto.setImageFilename(filename);
			dao.insertPhoto(dto);
		}
			
		resp.sendRedirect(cp+"/photo/photoMain.do");
	}

	private void photoarticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �Խù� ����
		String cp=req.getContextPath();
		
		PhotoDAO dao=new PhotoDAO();
		
		int photoNum=Integer.parseInt(req.getParameter("photoNum"));
		String page=req.getParameter("page");
		
		PhotoDTO dto=dao.readPhoto(photoNum);
		if(dto==null) {
			resp.sendRedirect(cp+"/photo/photoMain.do?page="+page);
			return;
		}
		
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/photo/photoarticle.jsp");
	}
	
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� ��
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		PhotoDAO dao=new PhotoDAO();
	
		String page=req.getParameter("page");
		int photoNum=Integer.parseInt(req.getParameter("photoNum"));
		PhotoDTO dto=dao.readPhoto(photoNum);
		
		if(dto==null) {
			resp.sendRedirect(cp+"/photo/photoMain.do?page="+page);
			return;
		}
		
		// �Խù��� �ø� ����ڰ� �ƴϸ�
		if(! dto.getUserId().equals(info.getUserId())) {
			resp.sendRedirect(cp+"/photo/photoMain.do?page="+page);
			return;
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/photo/created.jsp");
	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� �Ϸ�
		String cp=req.getContextPath();
		PhotoDAO dao=new PhotoDAO();
		PhotoDTO dto=new PhotoDTO();
		
		String page=req.getParameter("page");
		if(req.getMethod().equalsIgnoreCase("")) {
			resp.sendRedirect(cp+"/photo/photoMain.do?page="+page);
			return;
		}
		
		String imageFilename=req.getParameter("imageFilename");
		dto.setPhotoNum(Integer.parseInt(req.getParameter("photoNum")));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		
		Part p = req.getPart("upload");
		Map<String, String> map = doFileUpload(p, pathname);
		if(map != null) { // �̹��� ������ ���ε� �Ѱ��
			String filename = map.get("saveFilename");
			// ���� �̹��� ���� �����
			FileManager.doFiledelete(pathname, imageFilename);
			dto.setImageFilename(filename);
		} else {
			// ���ο� �̹��� ������ �ø��� ���� ��� ���� �̹��� ���Ϸ�
			dto.setImageFilename(imageFilename);
		}
		
		dao.updatePhoto(dto);
		
		resp.sendRedirect(cp+"/photo/photoMain.do?page="+page);	
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� �Ϸ�
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		PhotoDAO dao=new PhotoDAO();
		
		int num=Integer.parseInt(req.getParameter("photoNum"));
		String page=req.getParameter("page");
		
		PhotoDTO dto=dao.readPhoto(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/photo/photoMain.do?page="+page);	
			return;
		}
		
		// �Խù��� �ø� ����ڳ� admin�� �ƴϸ�
		if(! dto.getUserId().equals(info.getUserId()) && ! info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/photo/photoMain.do?page="+page);
			return;
		}
		
		// �̹��� ���� �����
		FileManager.doFiledelete(pathname, dto.getImageFilename());
		
		// ���̺� ������ ����
		dao.deletePhoto(num);
		
		resp.sendRedirect(cp+"/photo/photoMain.do?page="+page);			
	}
}
