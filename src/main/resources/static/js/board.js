let index = {
  init: function () {
    $("#btn-save").on("click", () => {
      this.save();
    });
    $("#btn-delete").on("click", () => {
      this.deleteById();
    });
    $("#btn-update").on("click", () => {
      this.update();
    });
    $("#btn-reply-save").on("click", () => {
      this.replySave();
    });
    // $("#btn-reply-delete").on("click", () => {
    //   this.deleteReply();
    // });
  },

  save: function () {
    let data = {
      title: $("#title").val(),
      content: $("#content").val(),
    };

    $.ajax({
      type: "POST",
      url: "/api/board",
      data: JSON.stringify(data),
      contentType: "application/json;charset=utf-8",
      dataType: "json",
    })
      .done(function (response) {
        alert("글쓰기가 완료되었습니다.");
        console.log(response);
        location.href = "/";
      })
      .fail(function (error) {
        console.log(JSON.stringify(error));
      });
  },

  deleteById: function () {
    let id = $("#id").text();
    $.ajax({
      type: "DELETE",
      url: "/api/board/" + id,
      dataType: "json",
    })
      .done(function (response) {
        alert("삭제가 완료되었습니다.");
        console.log(response);
        location.href = "/";
      })
      .fail(function (error) {
        console.log(JSON.stringify(error));
      });
  },

  update: function () {
    let id = $("#id").val();
    let data = {
      title: $("#title").val(),
      content: $("#content").val(),
    };

    $.ajax({
      type: "PUT",
      url: "/api/board/" + id,
      data: JSON.stringify(data),
      contentType: "application/json;charset=utf-8",
      dataType: "json",
    })
      .done(function (response) {
        alert("수정이 완료되었습니다.");
        console.log(response);
        location.href = "/";
      })
      .fail(function (error) {
        console.log(JSON.stringify(error));
      });
  },

  replySave: function () {
    let data = {
      userId: $("#userId").val(),
      boardId: $("#boardId").val(),
      content: $("#reply-content").val(),
    };
    // console.log(data);
    $.ajax({
      type: "POST",
      url: `/api/board/${data.boardId}/nativereply`,
      data: JSON.stringify(data),
      contentType: "application/json;charset=utf-8",
      dataType: "json",
    })
      .done(function (response) {
        alert("댓글 작성이 완료되었습니다.");
        console.log(response);
        location.href = `/board/${data.boardId}`;
      })
      .fail(function (error) {
        console.log(JSON.stringify(error));
      });
  },

  deleteReply: function (boardId, replyId) {
    // alert(replyId);
    $.ajax({
      type: "DELETE",
      url: `/api/board/${boardId}/reply/${replyId}`,
      dataType: "json",
    })
      .done(function (response) {
        alert("댓글 삭제가 완료되었습니다.");
        console.log(response);
        location.href = `/board/${boardId}`;
      })
      .fail(function (error) {
        console.log(JSON.stringify(error));
      });
  },
};

index.init();
