package com.member;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.notice.NoticeDTO;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/member/*")

public class MemberServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathname;


	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
				
		HttpSession session=req.getSession();
		
		// ??????? ?????? ???(pathname)
		String root = session.getServletContext().getRealPath("/");
     	pathname = root + "uploads" + File.separator + "travel";
		
		
		String uri=req.getRequestURI();
		if(uri.contains("login.do")) {
			loginForm(req, resp);
		} else if(uri.contains("login_ok.do")) {
			loginSubmit(req, resp);
		} else if("logout.do".contains(uri)) {
			logout(req, resp);
		} else if(uri.contains("member.do")) {
			memberForm(req, resp);
		} else if(uri.contains("member_ok.do")) {
			memberSubmit(req, resp);
		} else if(uri.contains("pwd.do")) {
			pwdForm(req, resp);
		} else if(uri.contains("pwd_ok.do")) {
			pwdSubmit(req, resp);
		} else if(uri.contains("update.do")) {
			updateForm(req,resp);
		} else if(uri.contains("update_ok.do")) {
			updateSubmit(req, resp);
		} else if(uri.contains("delete.do")) {
			deleteSubmit(req, resp);
		} else if(uri.contains("myPage.do")) {
			myPage(req, resp);
		} else if(uri.contains("list.do")) {
			listForm(req, resp);
		}
	}
//?α??? ??
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}
	
//?α??? ????	
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		
		MemberDAO dao=new MemberDAO();
		
		String userId=req.getParameter("userId");
		String userPwd=req.getParameter("userPwd");
		
		MemberDTO dto=dao.readMember(userId);
		
		if(dto==null || !dto.getUserPwd().equals(userPwd)) {
			String s="????? ??? ?н????? ??????? ??????. ??? ??????????.";
			req.setAttribute("messege", s);
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		//?α??? ????
		HttpSession session = req.getSession();
		
		//???? ?????ð? 30??
		session.setMaxInactiveInterval(30*60);
		
		//????? ?α??? ???? ????
		SessionInfo info=new SessionInfo();
		info.setUserId(dto.getUserId());
		info.setUserName(dto.getUserName());
		
		session.setAttribute("member", info);
	
		resp.sendRedirect(cp);//???????? ????????????????
	}
	
//?α???
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		
		session.invalidate();
		
		resp.sendRedirect(cp);
	}
	
//?????????	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("title", "Sign up");
		req.setAttribute("mode", "created");
		
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}
	
//??????? ???
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao=new MemberDAO();
		MemberDTO dto = new MemberDTO();
		String cp=req.getContextPath();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");	
		if (tel1.length() != 0 && tel2.length() != 0 && tel3.length() != 0) {
			dto.setUserTel(tel1 + "-" + tel2 + "-" + tel3);
		}
		
		String email1 =req.getParameter("email1");
		String email2 =req.getParameter("email2");		
		if (email1.length() != 0 && email2.length() != 0) {
			dto.setUserEmail(email1 + "@" + email2);
		}
		
		dto.setUserBirth(req.getParameter("userBirth"));
	
		Part p = req.getPart("upload"); 
		Map<String, String> map = doFileUpload(p,pathname);
		  
		 // map?? null??? ???????? ?????? ???? ???????? 
		if(map!=null) { 
			String saveFilename = map.get("saveFilename");
			if(saveFilename!=null) {
				dto.setImageFilename(saveFilename);
			}	  
		}
		
		try {
			dao.insertMember(dto);
		}catch(Exception e){
			String message = "??? ?????? ???? ??????.";
						
			req.setAttribute("title", "Sign up");
			req.setAttribute("mode", "created");	
			req.setAttribute("message", message);
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
					  
			return;
		}
		
		resp.sendRedirect(cp+"/member/main.do");
	}		 

		
// ?н????? ??? ??	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session=req.getSession();
		String cp=req.getContextPath();
				
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		// ?α??????????
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
				
		String mode=req.getParameter("mode");
		if(mode.equals("update")) {
			req.setAttribute("title", "??? ???? ????");
		}else {
			req.setAttribute("title", "??? ???");
		}
		
		req.setAttribute("mode", mode);
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");	
	}
	
//?н????????	
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAO();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //?α??? ?? ???
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// DB???? ??? ??? ???? ????????
		MemberDTO dto=dao.readMember(info.getUserId());
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
		String userPwd=req.getParameter("userPwd");
		String mode=req.getParameter("mode");
		
		if(! dto.getUserPwd().equals(userPwd)) {
			if(mode.equals("update")) {
				req.setAttribute("title", "??? ???? ????");
			}else {
				req.setAttribute("title", "??? ???");
			}
			req.setAttribute("mode", mode);
			req.setAttribute("message","<span style='color:red;'>?н????? ??????? ??????.</span>");
			forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
			return;
		}else {
			myPage(req,resp);
		}
		
		if(mode.equals("delete")) {
			// ??????
			try {
				dao.deleteMember(info.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			session.removeAttribute("member");
			session.invalidate();
			
			resp.sendRedirect(cp);
		}
	}	
	
//???????????	
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//???? ?????? ??????? ?????? (DAO)
		// ??????????? - ????????????? ???
		HttpSession session=req.getSession();
		MemberDAO dao=new MemberDAO();
		String cp=req.getContextPath();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		if(info==null) { //?α??? ?? ???
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// DB???? ??? ??? ???? ????????
		MemberDTO dto=dao.readMember(info.getUserId());
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
			
		req.setAttribute("title", "??? ???? ????");
		req.setAttribute("dto", dto);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}
	
	
//??????? ???????	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAO();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //?α??? ?? ???
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		MemberDTO dto = new MemberDTO();
	
		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		String tel1 = req.getParameter("tel1");
		String tel2 = req.getParameter("tel2");
		String tel3 = req.getParameter("tel3");
		if (tel1.length() != 0 && tel2.length() != 0 && tel3.length() != 0) {
			dto.setUserTel(tel1 + "-" + tel2 + "-" + tel3);
		}
		String email1 =req.getParameter("email1");
		String email2 =req.getParameter("email2");
		if (email1.length() != 0 && email2.length() != 0) {
			dto.setUserEmail(email1 + "@" + email2);
		}
		dto.setUserBirth(req.getParameter("userBirth"));

	//????	

		dto.setUserId(info.getUserId());
		
		Part p =req.getPart("upload");
		Map<String, String> map = doFileUpload(p, pathname);
		if(map!=null) {
			// ???? ???? ????
			if(req.getParameter("imageFilename").length()!=0) {
				FileManager.doFiledelete(pathname, req.getParameter("imageFilename"));
			}
			
			//???ο? ????
			String saveFilename = map.get("saveFilename");
			dto.setImageFilename(saveFilename);
		}
		
		try {
			dao.updateMember(dto);
			resp.sendRedirect(cp+"/member/myPage.do");
		} catch (Exception e) {
			String message = "??? ?????? ???? ??????.";
			
			dto=dao.readMember(info.getUserId());
			if(dto==null) {
				session.invalidate();
				resp.sendRedirect(cp);
				return;
			}
				
			req.setAttribute("title", "??? ???? ????");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			req.setAttribute("message", message);
			
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
		}
	}
//??????	
	private void deleteSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session=req.getSession();
		MemberDAO dao=new MemberDAO();
		String cp=req.getContextPath();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //?α??? ?? ???
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// DB???? ??? ??? ???? ????????
		MemberDTO dto=dao.readMember(info.getUserId());
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
			try {
				dao.deleteMember(info.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			session.removeAttribute("member");
			session.invalidate();
			
			resp.sendRedirect(cp);

	}
//myPage(?????? ??)	
	private void myPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		MemberDAO dao=new MemberDAO();
		String cp = req.getContextPath();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //?α??? ?? ???
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		// DB???? ??? ??? ???? ????????
		MemberDTO dto = dao.readMember(info.getUserId());
		
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
		
		req.setAttribute("title", "MyPage");
		 
		req.setAttribute("dto", dto);	

		forward(req, resp, "/WEB-INF/views/member/myPage.jsp");
	}
//????????
	private void listForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		HttpSession session=req.getSession();
		MemberDAO dao = new MemberDAO();
		String cp=req.getContextPath();
		MyUtil myUtil=new MyUtil();
		
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { //?α??? ?? ???
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
	
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {  //condition?? null??? keyword?? ????? ???...
			condition="userId";
			keyword="";
		} 
		//?????? ?????? Post?? ????
		
		//??????? ??????? ??????? ????
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword,"utf-8");
		}
		
		int dataCount;
		if(keyword.length()==0) {
			dataCount=dao.dataCount();
		}else {
			dataCount=dao.dataCount(condition,keyword);
		}
		
		int rows=10;
		int total_page=myUtil.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset = (current_page - 1) * rows;
	
		List<MemberDTO> list;
		if (keyword.length() != 0)
			list = dao.listBoard(offset, rows, condition, keyword);
		else
			list = dao.listBoard(offset, rows);
		
		// ???? ????? ??????, ???????? ??????? ??????????
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+"&keyword="+URLEncoder.encode(keyword,"utf-8");
		}
		//????¡???
		String listUrl=cp+"/member/list.do";
		if (query.length() != 0) {
			listUrl += "?" + query;
		}
		
		String paging = myUtil.paging(current_page, total_page, listUrl);
		
		// DB???? ??? ??? ???? ????????
		MemberDTO dto=dao.readMember(info.getUserId());
		if(dto==null) {
			session.invalidate();
			resp.sendRedirect(cp);
			return;
		}
		
		//list.jsp?? ????? ??????
		
		req.setAttribute("dto", dto);	
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/views/member/list.jsp");
	}
	
}
