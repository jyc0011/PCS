$(function () {
    $("#find_pwd").on('click', function () {
        location.href = "/login/findpwd";
    });
    $("#back").on('click', function () {
        location.href = "/login";
    });
    $("#find").click(function (event) {
        event.preventDefault();
        var username = $("#username").val();
        var email = $("#email").val();
        $.ajax({
            type: "POST",
            url: "/login/findidOk",
            data: {username: username, email: email},
            success: function (r) {
                console.log(r);
                if (r == "") {
                    $("#finded").css('display', 'block');
                    $("#find_pwd").css('display', 'none');
                    $("#result").text("ID not found.");
                } else {
                    $("#finded").css('display', 'block');
                    $("#find_pwd").css('display', 'block');
                    $("#result").text("Your ID is " + r + ".");
                }
            },
            error: function (e) {
                console.log(e.responseText);
            }
        });
    });
});
