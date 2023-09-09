<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>Chain Bank</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>회원</h1>
<form action='add' method="post" enctype="multipart/form-data">
    <table border="1">
        <tr>
            <th>이름</th> <td style="width:200px;"><input type='text' name='name'></td>
        </tr>
        <tr>
            <th>이메일</th> <td><input type='email' name='email'></td>
        </tr>
        <tr>
            <th>암호</th> <td><input type='password' name='password'></td>
        </tr>
        <tr>
            <th>성별</th>
            <td>
                <select name="gender">
                    <option value="M">남자</option>
                    <option value="W">여자</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>주소</th> <td><input type='address' name='address'></td>
        </tr>
        <tr>
            <th>신용등급</th> <td><input type='credit_rating' name='creditRating'></td>
        </tr>
        <tr>
            <th>사진</th> <td><input type='file' name='photo'></td>
        </tr>
    </table>
    <button>등록</button>
</form>

<jsp:include page="../footer.jsp"/>

</body>
</html>






