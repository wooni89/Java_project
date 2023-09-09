<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>회원</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>회원정보</h1>
<c:if test="${empty customer}">
    <p>해당 회원이 없습니다!</p>
</c:if>
<c:if test="${not empty customer}">
    <form action='/customer/update' method='post' enctype='multipart/form-data'>
        <table border='1'>
            <tr>
                <th style='width:120px;'>사진</th>
                <td style='width:300px;'>
                    <c:if test="${empty customer.photo}">
                        <img style='height:80px' src='/images/avatar.jpeg'>
                    </c:if>
                    <c:if test="${not empty customer.photo}">
                        <a href='https://kr.object.ncloudstorage.com/bank-bukit-1/customer/${customer.photo}'>
                            <img src='http://hfwoinxujgwq19373562.cdn.ntruss.com/customer/${customer.photo}?type=f&w=60&h=80&faceopt=true&ttype=jpg'>
                        </a>
                    </c:if>
                    <input type='file' name='photo'>
                </td>
            </tr>
            <tr>
                <th style='width:120px;'>번호</th>
                <td style='width:300px;'><input type='text' name='no' value='${customer.no}' readonly></td>
            </tr>
            <tr>
                <th>이름</th>
                <td><input type='name' name='name' value='${customer.name}'></td>
            </tr>
            <tr>
                <th>이메일</th>
                <td><input type='email' name='email' value='${customer.email}'></td>
            </tr>
            <tr>
                <th>암호</th>
                <td><input type='password' name='password'></td>
            </tr>
            <tr>
                <th>주소</th>
                <td><input type='address' name='address' value='${customer.address}'></td>
            </tr>
            <tr>
                <th>성별</th>
                <td><select name='gender'>
                    <option value='M' ${String.valueOf(customer.getGender()) == 'M' ? "selected" : ""}>남자</option>
                    <option value='W' ${String.valueOf(customer.getGender()) == 'W' ? "selected" : ""}>여자</option>
                </select>
                </td>
            </tr>
            <tr>
                <th>신용등급</th>
                <td><input type='credit_rating' name='creditRating' value='${customer.creditRating}'></td>
            </tr>
        </table>

        <div>
            <button>변경</button>
            <button type='reset'>초기화</button>
            <a href='/customer/delete?no=${customer.no}'>삭제</a>
            <a href='/customer/list'>목록</a>
        </div>
    </form>
</c:if>

<jsp:include page="../footer.jsp"/>

</body>
</html>