<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

  <%@ include file="../layout/header.jsp" %>

    <div class="container">
      <!-- <form action="/user/join" method="POST"> -->
      <form>
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" class="form-control" placeholder="Enter Username" id="username">
        </div>

        <div class="form-group">
          <label for="pasword">Password</label>
          <input type="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" class="form-control" placeholder="Enter email" id="email">
        </div>

        <!-- <button type="submit" class="btn btn-primary">회원가입</button> -->
      </form>
      <!-- <form>...</form> tag의 method를 사용하지 않기 위해 일부러 form 태그에서 excluding하고 type="submit"도 제거함 -->
      <button id="btn-save" class="btn btn-primary">회원가입</button>
    </div>
    <script src="/js/user.js"></script>
    <%@ include file="../layout/footer.jsp" %>