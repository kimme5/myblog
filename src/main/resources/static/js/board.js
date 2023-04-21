let index = {
  init: function () {
    // function() {}처럼 사용하지 않고 =>{} 한 이유는 this를 바인딩하기 위해서!!!
    $("#btn-save").on("click", () => {
      this.save();
    });
  },

  save: function () {
    // alert("user의 save함수 호출됨");
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
};

index.init();
