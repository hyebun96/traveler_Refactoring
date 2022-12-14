package com.util;

import java.util.regex.Pattern;

public class MyUtil {
	/**
	 * 전체 페이지 수 구하기
	 * @param rows			한행에 출력할 목록 수
	 * @param dataCount		총 데이터 개수
	 * @return				전체 페이지 수
	 */
	public int pageCount(int rows, int dataCount) {
		if(dataCount <= 0) 
			return 0;
	
		return dataCount / rows + (dataCount % rows > 0 ? 1 : 0);
	}

	/**
	 * 페이징(paging) 처리(GET 방식)
	 * @param current_page		현재 표시되는 페이지 번호
	 * @param total_page		전체 페이지 수
	 * @param list_url			링크를 설정할 주소
	 * @return					페이징 처리 결과
	 */
	public String paging(int current_page, int total_page, String list_url) {
		StringBuilder sb=new StringBuilder();
		
		int numPerBlock=10;
		int currentPageSetup;
		int n, page;
		
		if(current_page<1 || total_page < 1) {
			return "";
		}
		
		if(list_url.contains("?")) {
			list_url+="&";
		} else {
			list_url+="?";
		}

		// currentPageSetup : 표시할첫페이지-1
		currentPageSetup=(current_page/numPerBlock)*numPerBlock;
		if(current_page%numPerBlock==0) {
			currentPageSetup=currentPageSetup-numPerBlock;
		}
		
		sb.append("<td id='paginate' colspan='5' >");

		// 처음페이지, 이전(10페이지 전)
		n=current_page-numPerBlock;
		if(total_page > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href='"+list_url+"page="+1+"'>Pre</a>");
		}

		// 바로가기
		page=currentPageSetup+1;
		while(page<=total_page && page <=(currentPageSetup+numPerBlock)) {
			if(page==current_page) {
				sb.append("<span class='curBox'>"+page+"</span>");
			} else {
				sb.append("<a href='"+list_url+"page="+page+"' class='numBox'>"+page+"</a>");
			}
			page++;
		}

		// 다음(10페이지 후), 마지막페이지
		n=current_page+numPerBlock;
		if(n>total_page) n=total_page;
		if(total_page-currentPageSetup>numPerBlock) {

			sb.append("<a href='"+list_url+"page="+total_page+"'>Next</a>");
		}
		sb.append("</td>");
	
		return sb.toString();
	}

	/**
	 * javascript로 페이징(paging) 처리 : javascript listPage() 함수 호출
	 * @param current_page		현재 표시되는 페이지 번호
	 * @param total_page		전체 페이지 수
	 * @return					페이징 처리 결과
	 */
    public String paging(int current_page, int total_page) {
    	StringBuilder sb=new StringBuilder();

        int numPerBlock = 10;	// 리스트에 나타낼 페이지 수
        int currentPageSetUp;
        int n, page;
        
        if(current_page < 1 || total_page < 1) {
        	return "";
        }

        currentPageSetUp = (current_page / numPerBlock) * numPerBlock;
        if (current_page % numPerBlock == 0) {
            currentPageSetUp = currentPageSetUp - numPerBlock;
        }
		
		sb.append("<div id='paginate'>");

        n = current_page - numPerBlock;
        if ((total_page > numPerBlock) && (currentPageSetUp > 0)) {
			sb.append("<a onclick='listPage(1);'>Pre</a>");
			sb.append("<a onclick='listPage("+n+");'>Next</a>");
        }

        page = currentPageSetUp + 1;
        while((page <= total_page) && (page <= currentPageSetUp + numPerBlock)) {
           if(page == current_page) {
        	   sb.append("<span class='curBox'>"+page+"</span>");
           } else {
			   sb.append("<a onclick='listPage("+page+");' class='numBox'>"+page+"</a>");
           }
           page++;
        }

        n = current_page + numPerBlock;
		if(n>total_page) n=total_page;
        if (total_page - currentPageSetUp > numPerBlock) {
			sb.append("<a onclick='listPage("+n+");'>Pre</a>");
			sb.append("<a onclick='listPage("+total_page+");'>NEXT</a>");
        }
		sb.append("</div>");

        return sb.toString();
    }

	/**
	 * javascript로 페이징(paging) 처리 : javascript 지정 함수 호출
	 * @param current_page		현재 표시되는 페이지 번호
	 * @param total_page		전체 페이지 수
	 * @param methodName		호출할 자바 스크립트 함수명
	 * @return					페이징 처리 결과
	 */
    public String pagingMethod(int current_page, int total_page, String methodName) {
    	StringBuilder sb=new StringBuilder();

        int numPerBlock = 10;
        int currentPageSetUp;
        int n, page;
        
        if(current_page < 1 || total_page < 1) {
        	return "";
        }

        currentPageSetUp = (current_page / numPerBlock) * numPerBlock;
        if (current_page % numPerBlock == 0) {
            currentPageSetUp = currentPageSetUp - numPerBlock;
        }
		
		sb.append("<div id='paginate'>");

        n = current_page - numPerBlock;
        if ((total_page > numPerBlock) && (currentPageSetUp > 0)) {
			sb.append("<a onclick='"+methodName+"(1);'>Pre</a>");
			sb.append("<a onclick='"+methodName+"("+n+");'>NEXT</a>");
        }

        page = currentPageSetUp + 1;
        while((page <= total_page) && (page <= currentPageSetUp + numPerBlock)) {
           if(page == current_page) {
        	   sb.append("<span class='curBox'>"+page+"</span>");
           } else {
			   sb.append("<a onclick='"+methodName+"("+page+");' class='numBox'>"+page+"</a>");
           }
           page++;
        }

        n = current_page + numPerBlock;
		if(n>total_page) n=total_page;
        if (total_page - currentPageSetUp > numPerBlock) {
			sb.append("<a onclick='"+methodName+"("+n+");'>PRE</a>");
			sb.append("<a onclick='"+methodName+"("+total_page+");'>NEXT</a>");
        }
		sb.append("</div>");

        return sb.toString();
    }

	/**
	 * 특수문자를 HTML 문자로 변경 및 엔터를 <br>로 변경
	 * @param str	변경할 문자열
	 * @return		HTML 문자로 변경된 문자열
	 */
     public String htmlSymbols(String str) {
		if(str==null||str.length()==0)
			return "";

    	 str=str.replaceAll("&", "&amp;");
    	 str=str.replaceAll("\"", "&quot;");
    	 str=str.replaceAll(">", "&gt;");
    	 str=str.replaceAll("<", "&lt;");
    	 
    	 str=str.replaceAll("\n", "<br>");
    	 str=str.replaceAll("\\s", "&nbsp;");
    	 
    	 return str;
     }

	/**
	 * NULL을 ""로 변경하기
	 * @param str	변경할 문자열
	 * @return		NULL을 ""로 변경된 문자열
	 */
     public String checkNull(String str) {
         return str == null ? "" : str;
     }

	/**
	 * E-Mail 검사
	 * @param email  검사 할 E-Mail
	 * @return E-Mail 검사 결과
	 */
     public boolean isValidEmail(String email) {
         if (email==null) return false;
         
         return Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
     }
}
