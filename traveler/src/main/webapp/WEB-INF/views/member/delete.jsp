<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>

    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/member.js"></script>
</head>
<body>

<form name="deleteForm" method="post" action="MainForm.jsp?contentPage=member/pro/DeletePro.jsp"
      onsubmit="return checkValue()">

    <table>
        <tr>
            <td>비밀번호</td>
            <td><input type="password" name="password" maxlength="50"></td>
        </tr>
    </table>

    <br>
    <input type="button" value="취소" onclick="javascript:window.location='MainForm.jsp'">
    <input type="submit" value="탈퇴"/>
</form>


</body>
</html>