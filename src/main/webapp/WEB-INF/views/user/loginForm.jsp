<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container">
            <form action="/auth/loginProc" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" placeholder="Enter Username" id="username" name="username">
                </div>
                <div class="form-group">
                    <label for="pasword">Password</label>
                    <input type="password" class="form-control" placeholder="Enter password" id="password"
                        name="password">
                </div>
                <button type="submit" id="btn-login" class="btn btn-primary">로그인</button>
                <a
                    href="https://kauth.kakao.com/oauth/authorize?client_id=b5f8de641726a4aa2ab2c95f7bd085c8&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img
                        height="35" width="72" src="/image/kakao_login_button.png" alt=""></a>
            </form>
        </div>
        <%@ include file="../layout/footer.jsp" %>