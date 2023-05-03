<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>
        <div class="container">
            <br />
            <button id="btn-back" class="btn btn-secondary" onclick="history.back()">목록</button>

            <c:if test="${board.user.id == principal.user.id}">
                <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
                <button id="btn-delete" class="btn btn-danger">삭제</button>
            </c:if>
            <hr>
            <br />
            <div class="form-group">
                <h3> ${board.title} </h3>
            </div>
            <hr>
            <div>
                <h6>글번호: <span id="id"><i>${board.id} </i></span></h6>
            </div>
            <div>
                <h6>작성자: <span id="username"><i>${board.user.username}</i></span></h6>
            </div>
            <div>
                <h6>작성일: <span id="createDate"><i>${board.createDate}</i></span></h6>
            </div>
            <div>
                <h6>조회수: <span id="createDate"><i>${board.count}</i></span></h6>
            </div>
            <hr>
            <br />
            <div class="form-group">
                <div> ${board.content} </div>
            </div>
            <br />
            <hr>
        </div>
        <script src="/js/board.js"></script>
        <%@ include file="../layout/footer.jsp" %>