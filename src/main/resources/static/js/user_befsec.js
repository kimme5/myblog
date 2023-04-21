let index = {
  init: function () {
    // function() {}처럼 사용하지 않고 =>{} 한 이유는 this를 바인딩하기 위해서!!!
    $("#btn-save").on("click", () => {
      this.save();
    });

    $("#btn-login").on("click", () => {
      this.login();
    });
  },

  save: function () {
    // alert("user의 save함수 호출됨");
    let data = {
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val(),
    };

    // console.log(data);

    /**
     * ajax 통신을 이용해 3개의 파라미터를 json으로 변경하여 insert 요청함
     * ajax 통신은 기본적으로 비동기 호출이기 때문에, ajax()에서의 함수 실행이 100초가 걸린다고 하더라도
     * 하단에 다른 function()이 연달아 수행되는 도중, 100초가 진행되면 다른 함수 실행 도중에
     * done(function() {})이나 fail(function() {})이 수행됨
     * */
    $.ajax({
      // 회원가입 요청 실행
      type: "POST",
      url: "/api/user",
      // 위의 data 변수는 javascript 오브젝트이므로 JSON 형태로 변경해야 함
      data: JSON.stringify(data),
      // 서버로 보내는 body 데이터 타입 선언(MIME)
      contentType: "application/json;charset=utf-8",
      // 서버에서 받은 응답(Response)을 받는 타입 선언 --> javascript 오브젝트로 변경함
      // dataType을 생략하더라도 자동으로 json을 javascript 오브젝트로 변환해주는 것을 확인함
      dataType: "json",
    })
      .done(function (response) {
        // 요청에 대한 결과가 정상적으로 수행된 경우 실행
        alert("회원가입이 완료되었습니다.");
        console.log(response);
        location.href = "/";
      })
      .fail(function (error) {
        // 요청에 대한 결과가 실패한 경우 실행
        // alert(JSON.stringify(error));
        console.log(JSON.stringify(error));
      });
  },

  login: function () {
    // alert("user의 save함수 호출됨");
    let data = {
      username: $("#username").val(),
      password: $("#password").val(),
    };

    $.ajax({
      // 로그인 요청 실행
      type: "POST",
      url: "/api/user/login",
      data: JSON.stringify(data),
      contentType: "application/json;charset=utf-8",
      dataType: "json",
    })
      .done(function (response) {
        alert("로그인이 완료되었습니다.");
        console.log(response);
        location.href = "/";
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

index.init();
