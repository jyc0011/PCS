$(function () {
    $("#find").click(function (event) {
        event.preventDefault();
        var userid = $("#userid").val();
        var email = $("#email").val();
        $.ajax({
            type: "POST",
            url: "/login/sendcode",
            data: {userid: userid, email: email},
            success: function (response) {
                console.log(response);
                if (response === true) {
                    $("#verification").show();
                    $("#result").text("Your email has been sent.");
                } else {
                    $("#verification").hide();
                    $("#result").text("This ID or email does not exist!");
                }
            },
            error: function (error) {
                console.log(error.responseText);
            }
        });
    });

    $("#verifybtn").click(function (event) {
        event.preventDefault();
        var userid = $("#userid").val();
        var code = $("#code").val();
        var email = $("#email").val();
        $.ajax({
            type: "POST",
            url: "/login/verify",
            data: {userid: userid, code: code, email: email},
            success: function (response) {
                console.log(response);
                if (response === true) {
                    alert("Your changed password has been sent to your email.");
                    location.href = '/';
                } else {
                    alert("An error occurred!");
                }
            },
            error: function (error) {
                console.log(error.responseText);
            }
        });
    });
});
