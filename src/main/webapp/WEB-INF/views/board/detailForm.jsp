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
            <hr />
            <!-- 댓글 작성 -->
            <div class="card">
                <form>
                    <input type="hidden" id="userId" value="${principal.user.id}">
                    <input type="hidden" id="boardId" value="${board.id}" />
                    <div class="card-body">
                        <textarea id="reply-content" class="form-control" name="" id="" rows="1"></textarea>
                    </div>
                    <div class="card-footer">
                        <button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
                    </div>
                </form>
            </div>
            <br />
            <!-- 댓글 목록 -->
            <div class="card">
                <div class="card-header">Reply List</div>
                <ul id="reply-box" class="list-group">
                    <c:forEach var="reply" items="${board.replys}">
                        <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                            <div>${reply.content}</div>
                            <div class="d-flex">
                                <div class="font-italic">작성자 : ${reply.user.username} &nbsp;</div>
                                <c:if test="${principal.user.id == reply.user.id}">
                                    <button onClick="index.deleteReply(${board.id}, ${reply.id})"
                                        class="badge">삭제</button>
                                </c:if>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <script src="/js/board.js"></script>
        <%@ include file="../layout/footer.jsp" %>