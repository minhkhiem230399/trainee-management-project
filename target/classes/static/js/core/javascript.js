var Modal = {

    addTrainee: function (element) {
        var data = {};
        data["account"] = $('#account' + element.attr('data-id')).val();
        data["classId"] = element.attr('data-id');


        $.ajax({
            url: "/class-management/add-trainee",
            type: "post",
            contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
            data: JSON.stringify(data), // object json -> string json

            dataType: "json", // dữ liệu từ web-service trả về là json.
            success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                if (jsonResult.status === 200) {
                    const cr = $('#currentCount' + element.attr('data-id')).text();
                    $('#currentCount' + element.attr('data-id')).text(Number(cr) + Number(1));
                    $('.modal').modal('hide');
                    showNotification('top', 'right', 'Add success!')
                }
                if (jsonResult.status === 401) {
                    showWarn("Account not valid!")
                }
                if (jsonResult.status === 402) {
                    showWarn("Account existed!");
                }
                if (jsonResult.status === 400) {
                    showWarn("Course full!");
                }

            },
            error: function (jqXhr, textStatus, errorMessage) { // error callback

            }
        });
    },

    checkEmail: function () {
        email = $('#email').val();

        const regex = /[A-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[A-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/
        if (!email.match(regex)) {
            showWarn('Email invalid!');
            $('#save').attr("disabled", true);
        }
        $.ajax({
            url: "/check-email",
            type: "post",
            contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
            data: email, // object json -> string json

            dataType: "json", // dữ liệu từ web-service trả về là json.
            success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                if (jsonResult.status === 200) {
                    showNotification('top', 'right', 'Email valid')
                    $('#save').removeAttr("disabled");
                }
                if (jsonResult.status === 400) {
                    showWarn('Email existed or contains "admin"!');
                    $('#save').attr("disabled", true);
                }

            },
            error: function (jqXhr, textStatus, errorMessage) { // error callback
            }
        });
    },

    delete: function (row) {
        result = window.confirm("Do you sure want to delete?");

        if (result) {
            var id = row.attr("data");
            $.ajax({
                url: "/trainer-management/delete",
                type: "post",
                contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
                data: id, // object json -> string json

                dataType: "json", // dữ liệu từ web-service trả về là json.
                success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                    if (jsonResult.status === 200) {
                        row.closest('tr').remove()
                        showNotification('top', 'right', 'Delete success!');
                    }
                },
                error: function (jqXhr, textStatus, errorMessage) { // error callback

                }
            });
        }
    },

    deleteTrainee: function (row) {
        result = window.confirm("Do you sure want to delete?");

        if (result) {
            var id = row.attr("data-id");
            $.ajax({
                url: "/trainee-management/delete",
                type: "post",
                contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
                data: id, // object json -> string json

                dataType: "json", // dữ liệu từ web-service trả về là json.
                success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                    if (jsonResult.status === 200) {
                        $('#' + id).remove();
                        showNotification('top', 'right', 'Delete success!');
                    }
                },
                error: function (jqXhr, textStatus, errorMessage) { // error callback
                    showWarn("Exception!");
                }
            });
        }
    },

    addTraining: function () {
        var data = $('#name-object').val();

        $.ajax({
            url: "/class-management/add-training-object",
            type: "post",
            contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
            data: data, // object json -> string json

            dataType: "json", // dữ liệu từ web-service trả về là json.
            success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                if (jsonResult.status === 200) {
                    $('.modal').modal('hide');
                    showNotification('top', 'right', 'Add success!')
                }
            },
            error: function (jqXhr, textStatus, errorMessage) { // error callback
                showWarn("Exception!");
            }
        });
    },

    updateScore: function (el, e) {
        var data = {};
        data["score"] = $('#account' + el.attr('data-id')).val();
        data["subId"] = el.attr('data-id');

        if (parseInt(data.score) > 10) {
            showWarn("Score must be less than 10!");
        } else {
            $.ajax({
                url: "/class-management/update-score",
                type: "post",
                contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
                data: JSON.stringify(data), // object json -> string json

                dataType: "json", // dữ liệu từ web-service trả về là json.
                success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                    $("#progress").attr('style', 'width:' + data.score * 10 + '%');
                    $('#prefix').text(data.score);
                    $("#progress-final").attr('style', 'width:' + jsonResult.data * 10 + '%');
                    $('#final').text(jsonResult.data * 10);
                    location.reload();
                    if(parseInt(jsonResult.data) > 9 ){
                        $('.score-final').text("A+")
                    }
                    if(parseInt(jsonResult.data) >= 8 &&parseInt(jsonResult.data) < 9 ){
                        $('.score-final').text("A")
                    }
                    if(parseInt(jsonResult.data) >= 7 &&parseInt(jsonResult.data) < 8 ){
                        $('.score-final').text("B")
                    }
                    if(parseInt(jsonResult.data) >= 6 &&parseInt(jsonResult.data) < 7 ){
                        $('.score-final').text("C")
                    }
                    if(parseInt(jsonResult.data) >= 5 &&parseInt(jsonResult.data) < 6){
                        $('.score-final').text("D")
                    }
                    if(parseInt(jsonResult.data) >= 0 &&parseInt(jsonResult.data) < 5){
                        $('.score-final').text("F")
                    }
                    $('.modal').modal('hide');
                    showNotification('top', 'right', 'Updated score!')

                },
                error: function (jqXhr, textStatus, errorMessage) { // error callback
                    showWarn("Exception!")
                }
            });
            e.stopImmediatePropagation();
            return false;
        }


    },

    updateScoreReview: function (el) {
        var data = {};
        data["score"] = $('#account' + el.attr('data-id') + 'a').val();
        data["subId"] = el.attr('data-id');

        if (parseInt(data.score) > 10) {
            showWarn("Score must be less than 10!");
        } else {
            $.ajax({
                url: "/class-management/update-score-review",
                type: "post",
                contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
                data: JSON.stringify(data), // object json -> string json

                dataType: "json", // dữ liệu từ web-service trả về là json.
                success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                    if (jsonResult.status === 200) {
                        $('#aa'+el.attr('data-id')).attr('style', 'width:' + data.score * 10 + '%');
                        $('.a'+el.attr('data-id')).text(data.score);
                        $('.modal').modal('hide');
                        showNotification('top', 'right', 'Updated score review!')
                        console.log(data);
                    }
                },
                error: function (jqXhr, textStatus, errorMessage) { // error callback
                    showWarn("Exception!")
                }
            });
        }
    },

    changePassword: function (element) {
        var data = {};
        data["old"] = $('#password-old').val();
        data["new"] = $('#password-new').val();
        data["retype"] = $('#password-re').val();


        $.ajax({
            url: "/change-password",
            type: "post",
            contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
            data: JSON.stringify(data), // object json -> string json

            dataType: "json", // dữ liệu từ web-service trả về là json.
            success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                if (jsonResult.status === 200) {
                    console.log(jsonResult.data);
                    $('.modal').modal('hide');
                    showNotification('top', 'right', 'Change password success!')
                }
                if (jsonResult.status === 400) {
                    showWarn("Old password not correct!");
                }

                if (jsonResult.status === 401) {
                    showWarn("New password must different old password!");
                }
                if (jsonResult.status === 402) {
                    showWarn("Password retype not match new password!");
                }


            },
            error: function (jqXhr, textStatus, errorMessage) { // error callback

            }
        });
    },

    changePassword1: function (element) {
        var data = {};
        data["old"] = $('#password-old1').val();
        data["new"] = $('#password-new1').val();
        data["retype"] = $('#password-re1').val();


        $.ajax({
            url: "/change-password1",
            type: "post",
            contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
            data: JSON.stringify(data), // object json -> string json

            dataType: "json", // dữ liệu từ web-service trả về là json.
            success: function (jsonResult) { // được gọi khi web-service trả về dữ liệu.
                if (jsonResult.status === 200) {
                    console.log(jsonResult.data);
                    $('.modal').modal('hide');
                    showNotification('top', 'right', 'Change password success!')
                }
                if (jsonResult.status === 400) {
                    showWarn("Old password not correct!");
                }

                if (jsonResult.status === 401) {
                    showWarn("New password must different old password!");
                }
                if (jsonResult.status === 402) {
                    showWarn("Password retype not match new password!");
                }


            },
            error: function (jqXhr, textStatus, errorMessage) { // error callback

            }
        });
    },

}