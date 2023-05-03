<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <%@ include file="../layout/header.jsp" %>

    <div class="container">
      <!-- <form action="/user/join" method="POST"> -->
      <form>
        <input type="hidden" id="id" value="${principal.user.id}">
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" class="form-control" placeholder="Enter Username" id="username"
            value="${principal.user.username}" readonly>
        </div>
        <!-- kakako 로그인 사용자인 경우, 패스워드 변경 금지 -->
        <c:if test="${empty principal.user.oauth}">
          <div class="form-group">
            <label for="pasword">Password</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password">
          </div>
        </c:if>
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" class="form-control" placeholder="Enter email" id="email" value="${principal.user.email}">
        </div>

        <!-- <button type="submit" class="btn btn-primary">회원가입</button> -->
      </form>
      <!-- <form>...</form> tag의 method를 사용하지 않기 위해 일부러 form 태그에서 excluding하고 type="submit"도 제거함 -->
      <button id="btn-update" class="btn btn-primary">수정</button>
    </div>
    <script src="/js/user.js"></script>
    <%@ include file="../layout/footer.jsp" %>