$().ready(function () {
    // $(window).keydown(function(event){
    //     if(event.keyCode == 13) {
    //         event.preventDefault();
    //         return false;
    //     }
    // });

    createPieFirst();
    createPieSecond();

    checkWrongUser();

    checkWrongPassword();

    checkSuccessChangePassword();
    
    checkSuccessUpdateUser();

    menuBar();

    $sidebar = $('.sidebar');
    $sidebar_img_container = $sidebar.find('.sidebar-background');

    $full_page = $('.full-page');

    $sidebar_responsive = $('body > .navbar-collapse');

    window_width = $(window).width();

    fixed_plugin_open = $('.sidebar .sidebar-wrapper .nav li.active a p').html();

    if (window_width > 767 && fixed_plugin_open == 'Dashboard') {
        if ($('.fixed-plugin .dropdown').hasClass('show-dropdown')) {
            $('.fixed-plugin .dropdown').addClass('show');
        }

    }

    $('.fixed-plugin a').click(function (event) {
        // Alex if we click on switch, stop propagation of the event, so the dropdown will not be hide, otherwise we set the  section active
        if ($(this).hasClass('switch-trigger')) {
            if (event.stopPropagation) {
                event.stopPropagation();
            } else if (window.event) {
                window.event.cancelBubble = true;
            }
        }
    });

    $('.fixed-plugin .background-color span').click(function () {
        $(this).siblings().removeClass('active');
        $(this).addClass('active');

        var new_color = $(this).data('color');

        if ($sidebar.length != 0) {
            $sidebar.attr('data-color', new_color);
        }

        if ($full_page.length != 0) {
            $full_page.attr('filter-color', new_color);
        }

        if ($sidebar_responsive.length != 0) {
            $sidebar_responsive.attr('data-color', new_color);
        }
    });

    $('.fixed-plugin .img-holder').click(function () {
        $full_page_background = $('.full-page-background');

        $(this).parent('li').siblings().removeClass('active');
        $(this).parent('li').addClass('active');


        var new_image = $(this).find("img").attr('src');

        if ($sidebar_img_container.length != 0 && $('.switch-sidebar-image input:checked').length != 0) {
            $sidebar_img_container.fadeOut('fast', function () {
                $sidebar_img_container.css('background-image', 'url("' + new_image + '")');
                $sidebar_img_container.fadeIn('fast');
            });
        }

        if ($full_page_background.length != 0 && $('.switch-sidebar-image input:checked').length != 0) {
            var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

            $full_page_background.fadeOut('fast', function () {
                $full_page_background.css('background-image', 'url("' + new_image_full_page + '")');
                $full_page_background.fadeIn('fast');
            });
        }

        if ($('.switch-sidebar-image input:checked').length == 0) {
            var new_image = $('.fixed-plugin li.active .img-holder').find("img").attr('src');
            var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

            $sidebar_img_container.css('background-image', 'url("' + new_image + '")');
            $full_page_background.css('background-image', 'url("' + new_image_full_page + '")');
        }

        if ($sidebar_responsive.length != 0) {
            $sidebar_responsive.css('background-image', 'url("' + new_image + '")');
        }
    });

    $('.switch input').on("switchChange.bootstrapSwitch", function () {

        $full_page_background = $('.full-page-background');

        $input = $(this);

        if ($input.is(':checked')) {
            if ($sidebar_img_container.length != 0) {
                $sidebar_img_container.fadeIn('fast');
                $sidebar.attr('data-image', '#');
            }

            if ($full_page_background.length != 0) {
                $full_page_background.fadeIn('fast');
                $full_page.attr('data-image', '#');
            }

            background_image = true;
        } else {
            if ($sidebar_img_container.length != 0) {
                $sidebar.removeAttr('data-image');
                $sidebar_img_container.fadeOut('fast');
            }

            if ($full_page_background.length != 0) {
                $full_page.removeAttr('data-image', '#');
                $full_page_background.fadeOut('fast');
            }

            background_image = false;
        }
    });
});

type = ['primary', 'info', 'success', 'warning', 'danger'];

demo = {
    initPickColor: function () {
        $('.pick-class-label').click(function () {
            var new_class = $(this).attr('new-class');
            var old_class = $('#display-buttons').attr('data-class');
            var display_div = $('#display-buttons');
            if (display_div.length) {
                var display_buttons = display_div.find('.btn');
                display_buttons.removeClass(old_class);
                display_buttons.addClass(new_class);
                display_div.attr('data-class', new_class);
            }
        });
    },

    initDocumentationCharts: function () {
        /* ----------==========     Daily Sales Chart initialization For Documentation    ==========---------- */

        dataDailySalesChart = {
            labels: ['M', 'T', 'W', 'T', 'F', 'S', 'S'],
            series: [
                [12, 17, 7, 17, 23, 18, 38]
            ]
        };

        optionsDailySalesChart = {
            lineSmooth: Chartist.Interpolation.cardinal({
                tension: 0
            }),
            low: 0,
            high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
            chartPadding: {
                top: 0,
                right: 0,
                bottom: 0,
                left: 0
            },
        }

        var dailySalesChart = new Chartist.Line('#dailySalesChart', dataDailySalesChart, optionsDailySalesChart);

        // lbd.startAnimationForLineChart(dailySalesChart);
    },

    initDashboardPageCharts: function () {

        var dataPreferences = {
            series: [
                [25, 30, 20, 25]
            ]
        };

        var optionsPreferences = {
            donut: true,
            donutWidth: 40,
            startAngle: 0,
            total: 100,
            showLabel: false,
            axisX: {
                showGrid: false
            }
        };

        Chartist.Pie('#chartPreferences', dataPreferences, optionsPreferences);

        Chartist.Pie('#chartPreferences', {
            labels: ['53%', '36%', '11%'],
            series: [53, 36, 11]
        });


        var dataSales = {
            labels: ['9:00AM', '12:00AM', '3:00PM', '6:00PM', '9:00PM', '12:00PM', '3:00AM', '6:00AM'],
            series: [
                [287, 385, 490, 492, 554, 586, 698, 695, 752, 788, 846, 944],
                [67, 152, 143, 240, 287, 335, 435, 437, 539, 542, 544, 647],
                [23, 113, 67, 108, 190, 239, 307, 308, 439, 410, 410, 509]
            ]
        };

        // var optionsSales = {
        //   lineSmooth: false,
        //   low: 0,
        //   high: 800,
        //    chartPadding: 0,
        //   showArea: true,
        //   height: "245px",
        //   axisX: {
        //     showGrid: false,
        //   },
        //   axisY: {
        //     showGrid: false,
        //   },
        //   lineSmooth: Chartist.Interpolation.simple({
        //     divisor: 6
        //   }),
        //   showLine: false,
        //   showPoint: true,
        //   fullWidth: true
        // };
        var optionsSales = {
            lineSmooth: false,
            low: 0,
            high: 800,
            showArea: true,
            height: "245px",
            axisX: {
                showGrid: false,
            },
            lineSmooth: Chartist.Interpolation.simple({
                divisor: 3
            }),
            showLine: false,
            showPoint: false,
            fullWidth: false
        };

        var responsiveSales = [
            ['screen and (max-width: 640px)', {
                axisX: {
                    labelInterpolationFnc: function (value) {
                        return value[0];
                    }
                }
            }]
        ];

        var chartHours = Chartist.Line('#chartHours', dataSales, optionsSales, responsiveSales);

        // lbd.startAnimationForLineChart(chartHours);

        var data = {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            series: [
                [542, 443, 320, 780, 553, 453, 326, 434, 568, 610, 756, 895],
                [412, 243, 280, 580, 453, 353, 300, 364, 368, 410, 636, 695]
            ]
        };

        var options = {
            seriesBarDistance: 10,
            axisX: {
                showGrid: false
            },
            height: "245px"
        };

        var responsiveOptions = [
            ['screen and (max-width: 640px)', {
                seriesBarDistance: 5,
                axisX: {
                    labelInterpolationFnc: function (value) {
                        return value[0];
                    }
                }
            }]
        ];

        var chartActivity = Chartist.Bar('#chartActivity', data, options, responsiveOptions);

        // lbd.startAnimationForBarChart(chartActivity);

        // /* ----------==========     Daily Sales Chart initialization    ==========---------- */
        //
        // dataDailySalesChart = {
        //     labels: ['M', 'T', 'W', 'T', 'F', 'S', 'S'],
        //     series: [
        //         [12, 17, 7, 17, 23, 18, 38]
        //     ]
        // };
        //
        // optionsDailySalesChart = {
        //     lineSmooth: Chartist.Interpolation.cardinal({
        //         tension: 0
        //     }),
        //     low: 0,
        //     high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
        //     chartPadding: { top: 0, right: 0, bottom: 0, left: 0},
        // }
        //
        // var dailySalesChart = Chartist.Line('#dailySalesChart', dataDailySalesChart, optionsDailySalesChart);

        // lbd.startAnimationForLineChart(dailySalesChart);

        //
        //
        // /* ----------==========     Completed Tasks Chart initialization    ==========---------- */
        //
        // dataCompletedTasksChart = {
        //     labels: ['12am', '3pm', '6pm', '9pm', '12pm', '3am', '6am', '9am'],
        //     series: [
        //         [230, 750, 450, 300, 280, 240, 200, 190]
        //     ]
        // };
        //
        // optionsCompletedTasksChart = {
        //     lineSmooth: Chartist.Interpolation.cardinal({
        //         tension: 0
        //     }),
        //     low: 0,
        //     high: 1000, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
        //     chartPadding: { top: 0, right: 0, bottom: 0, left: 0}
        // }
        //
        // var completedTasksChart = new Chartist.Line('#completedTasksChart', dataCompletedTasksChart, optionsCompletedTasksChart);
        //
        // // start animation for the Completed Tasks Chart - Line Chart
        // lbd.startAnimationForLineChart(completedTasksChart);
        //
        //
        // /* ----------==========     Emails Subscription Chart initialization    ==========---------- */
        //
        // var dataEmailsSubscriptionChart = {
        //   labels: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        //   series: [
        //     [542, 443, 320, 780, 553, 453, 326, 434, 568, 610, 756, 895]
        //
        //   ]
        // };
        // var optionsEmailsSubscriptionChart = {
        //     axisX: {
        //         showGrid: false
        //     },
        //     low: 0,
        //     high: 1000,
        //     chartPadding: { top: 0, right: 5, bottom: 0, left: 0}
        // };
        // var responsiveOptions = [
        //   ['screen and (max-width: 640px)', {
        //     seriesBarDistance: 5,
        //     axisX: {
        //       labelInterpolationFnc: function (value) {
        //         return value[0];
        //       }
        //     }
        //   }]
        // ];
        // var emailsSubscriptionChart = Chartist.Bar('#emailsSubscriptionChart', dataEmailsSubscriptionChart, optionsEmailsSubscriptionChart, responsiveOptions);
        //
        // //start animation for the Emails Subscription Chart
        // lbd.startAnimationForBarChart(emailsSubscriptionChart);

    },

    initGoogleMaps: function () {
        var myLatlng = new google.maps.LatLng(40.748817, -73.985428);
        var mapOptions = {
            zoom: 13,
            center: myLatlng,
            scrollwheel: false, //we disable de scroll over the map, it is a really annoing when you scroll through page
            styles: [{
                "featureType": "water",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#e9e9e9"
                }, {
                    "lightness": 17
                }]
            }, {
                "featureType": "landscape",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#f5f5f5"
                }, {
                    "lightness": 20
                }]
            }, {
                "featureType": "road.highway",
                "elementType": "geometry.fill",
                "stylers": [{
                    "color": "#ffffff"
                }, {
                    "lightness": 17
                }]
            }, {
                "featureType": "road.highway",
                "elementType": "geometry.stroke",
                "stylers": [{
                    "color": "#ffffff"
                }, {
                    "lightness": 29
                }, {
                    "weight": 0.2
                }]
            }, {
                "featureType": "road.arterial",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#ffffff"
                }, {
                    "lightness": 18
                }]
            }, {
                "featureType": "road.local",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#ffffff"
                }, {
                    "lightness": 16
                }]
            }, {
                "featureType": "poi",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#f5f5f5"
                }, {
                    "lightness": 21
                }]
            }, {
                "featureType": "poi.park",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#dedede"
                }, {
                    "lightness": 21
                }]
            }, {
                "elementType": "labels.text.stroke",
                "stylers": [{
                    "visibility": "on"
                }, {
                    "color": "#ffffff"
                }, {
                    "lightness": 16
                }]
            }, {
                "elementType": "labels.text.fill",
                "stylers": [{
                    "saturation": 36
                }, {
                    "color": "#333333"
                }, {
                    "lightness": 40
                }]
            }, {
                "elementType": "labels.icon",
                "stylers": [{
                    "visibility": "off"
                }]
            }, {
                "featureType": "transit",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#f2f2f2"
                }, {
                    "lightness": 19
                }]
            }, {
                "featureType": "administrative",
                "elementType": "geometry.fill",
                "stylers": [{
                    "color": "#fefefe"
                }, {
                    "lightness": 20
                }]
            }, {
                "featureType": "administrative",
                "elementType": "geometry.stroke",
                "stylers": [{
                    "color": "#fefefe"
                }, {
                    "lightness": 17
                }, {
                    "weight": 1.2
                }]
            }]
        };

        var map = new google.maps.Map(document.getElementById("map"), mapOptions);

        var marker = new google.maps.Marker({
            position: myLatlng,
            title: "Hello World!"
        });

        // To add the marker to the map, call setMap();
        marker.setMap(map);
    },

    showNotification: function (from, align) {
        color = Math.floor((Math.random() * 4) + 1);

        $.notify({
            icon: "nc-icon nc-app",
            message: "Welcome to <b>Light Bootstrap Dashboard</b> - a beautiful freebie for every web developer."

        }, {
            type: type[color],
            timer: 8000,
            placement: {
                from: from,
                align: align
            }
        });
    }



}

function createPieFirst() {
    let randomScalingFactor = function () {
        return Math.round(Math.random() * 100);
    };

    let config = {
        type: 'pie',
        data: {
            datasets: [{
                data: [
                    wCourse,
                    runCourse,
                    releCourse,
                ],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(255, 205, 86)',
                    'rgb(54, 162, 235)'
                ],
                label: 'Dataset 1'
            }],
            labels: [
                'Waiting',
                'Running',
                'Finished'
            ]
        },
        options: {
            responsive: true
        }
    };


    if (document.getElementById('chart-area-first') != null) {

        let ctx = document.getElementById('chart-area-first').getContext('2d');

        window.myPie = new Chart(ctx, config);

    }
}

function createPieSecond() {
    let randomScalingFactor = function () {
        return Math.round(Math.random() * 100);
    };

    let config = {
        type: 'pie',
        data: {
            datasets: [{
                data: [
                    rTrainee,
                    wTrainee,
                    rnTrainee,
                ],
                backgroundColor: [
                    'rgb(255, 205, 86)',
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)'
                ],
                label: 'Dataset 1'
            }],
            labels: [
                'Attended',
                'Not Attended'
            ]
        },
        options: {
            responsive: true
        }
    };

    if (document.getElementById('chart-area-second') != null) {
        let ctx = document.getElementById('chart-area-second').getContext('2d');
        window.myPie = new Chart(ctx, config);
    }

}

function successInsert() {
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Insert Success',
        showConfirmButton: false,
        timer: 1500
    })
}

function successUpdate() {
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Update Success',
        showConfirmButton: false,
        timer: 1500
    })
}

function error() {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Duplicate Account!'
    })
}

function updatePasswordFail() {
    Swal.fire({
        icon: 'error',
        title: 'Update Fail...',
        text: 'Update password fail, check password again, please !'
    })
}

function emptyPassword() {
    Swal.fire({
        icon: 'error',
        title: 'Update Fail...',
        text: 'Empty field input password or confirm password, input password please!'
    })
}


function successChangePassword() {
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Change password successfully !'
    })
}

function successUpdateUser() {
    Swal.fire({
        position: 'center',
        icon: 'success',
        title: 'Update user successfully !'
    })
}


// JS code for print the notifications for successful Password Change 


function validatePassword() {

    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirm-password").value;
    let oldPasswordConfirm = document.getElementById("old-password-confirm").value;
    let showMessage = document.getElementById("show-message");

    showMessage.innerHTML = "";

    //check empty password field  
    if (oldPasswordConfirm === "") {
        showMessage.innerHTML = "Required input current password!";
        return false;
    }

    //check empty confirm password field  
    if (password === "") {
        showMessage.innerHTML = "Required input new password!";
        return false;
    }


    if (confirmPassword === "") {
        showMessage.innerHTML = "Required input confirm password!";
        return false;
    }


    let passwordValidation = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;

    if(!oldPasswordConfirm.match(passwordValidation)){
        showMessage.innerHTML = "Invalid current password format! Password between 6 to 20 characters which contain at least one numeric digit, one uppercase and one lowercase letter";
        return false;
    }


    if(!password.match(passwordValidation)){
        showMessage.innerHTML = "Invalid new password format! Password between 6 to 20 characters which contain at least one numeric digit, one uppercase and one lowercase letter!";
        return false;
    }

    if(!confirmPassword.match(passwordValidation)){
        showMessage.innerHTML = "Invalid confirm password format! Password between 6 to 20 characters which contain at least one numeric digit, one uppercase and one lowercase letter!";
        return false;
    }


    if (!password.match(confirmPassword)) {
        showMessage.innerHTML = "Password did not match!";
        return false;
    } else {
        return true;
    }
}



function checkSuccessChangePassword() {

    let checkSuccess = document.getElementById("check-success");

    if (checkSuccess != null) {
        successChangePassword();
    }
}


function checkSuccessUpdateUser() {

    let checkSuccess = document.getElementById("check-success-update-user");

    if (checkSuccess != null) {
        successUpdateUser();
    }
}


function checkInputLogin() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let errorLogin = document.getElementById("error-login");

    errorLogin.innerHTML = "";



    if (username === "" || password === "") {
        errorLogin.innerHTML = "Required input username and password!";
        return false;
    }

    let passwordValidation = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;

    if(password.match(passwordValidation)){
        return true;
    }else{
        errorLogin.innerHTML = "Invalid password format! Password between 6 to 20 characters which contain at least one numeric digit, one uppercase and one lowercase letter!";
        return false;
    }
}


function checkWrongUser() {

    let checkErrorInput = document.getElementById("check-error-input");

    let errorLogin = document.getElementById("error-login");

    if (errorLogin != null) {

        errorLogin.innerHTML = "";

        if (checkErrorInput != null) {

            if (checkErrorInput.value === "error") {
                errorLogin.innerHTML = "Username or password is incorrect!";
            }
        }
    }

}


function checkWrongPassword() {
    let checkPasswordInput = document.getElementById("check-password-input");
    let showMessage = document.getElementById("show-message");

    if (showMessage != null) {
        showMessage.innerHTML = "";
        if (checkPasswordInput != null) {
            if (checkPasswordInput.value === "error") {
                showMessage.innerHTML = "Current password is incorrect!";
            }
        }
    }
}

function showNotificationInputDate(from, align) {
    color = 4;

    $.notify({
        message: "Notification: Invalid start date and end date input!"

    }, {
        type: type[color],
        timer: 8000,
        placement: {
            from: from,
            align: align
        }
    });
}

const checkPhoneNumber = () => {
    var phone = $('#tel-phone').val();

    if(!phone.match(/(\+84|0)+(3[2-9]|5[6|8|9]|9\d(\?!5)|8[1-9]|7[0|6-9])+([0-9]{7})\b/)){

        showWarn('Phone number invalid!');
        $('#save').attr("disabled", true);
    }else {
        showNotification('top','right', 'Phone number valid!');
        $('#save').removeAttr("disabled");
    }
    Modal.checkEmail();
}

function showNotification(from, align, message) {
    color = 1;

    $.notify({
        message: message

    }, {
        type: type[color],
        timer: 500,
        placement: {
            from: from,
            align: align
        }
    });
}

function showWarn(message) {
    color = 4;

    $.notify({
        message: message

    }, {
        type: type[color],
        timer: 1000,
        placement: {
            from: 'top',
            align: 'right'
        }
    });
}

$(document).ready(function(){
    const message = $('#message').val();
    if(message === "Update success!"){
        showNotification('top','right', message);
    }
    if(message === "Add success!"){
        showNotification('top','right', message);
    }

});

function checkInputDate(){
    let startDate = document.getElementById("start-date").value;
    let endDate = document.getElementById("end-date").value;

    const x = new Date(startDate);
    const y = new Date(endDate);

    if(x > y){
        showWarn("End Date must bigger than Start Date!");
        $('#save').attr("disabled", true);
    }else{
        $('#save').removeAttr("disabled");
    }
}

$('#pills-tab a').on('click', function (e) {
    e.preventDefault()
    $(this).tab('show')
})

$(function () {
    $('form').each(function () {
        $('#size').keypress(function (e) {
            // Enter pressed?
            let t = $('#size').val();
            if (t < 5 || t > 500) {
                this.form.checkValidity();
            } else {
                if (e.which == 10 || e.which == 13) {
                    this.form.submit();
                }
            }
        });

        $(this).find('input[type=submit]').hide();
    });
});


function menuBar() {
    if (currentLink === 'Dashboard' || currentLink === 'User Details') {
        $("#link-first").addClass("active");
        $("#link-first-user").addClass("active");
    }

    if (currentLink === 'Class Management' || currentLink === 'Change Password') {
        $("#link-second").addClass("active");
        $("#link-second-user").addClass("active");
    }

    if (currentLink === 'Trainee Management' || currentLink === 'User Update') {
        $("#link-third").addClass("active");
        $("#link-third-user").addClass("active");
    }

    if (currentLink === 'Trainer Management') {
        $("#link-fourth").addClass("active");
    }
}

function rowClicked(value) {
    location.href = "../class-management/class-details?id=" + value;
}

function rowClickedSubject(value){
    location.href = "../general-management/subject-list/subject-details?id=" + value;
}

var items = []
var idList = [];

$(function () {
    $( "#tags" ).autocomplete({
        source: "trainingObjectName",
        select: function(event, ui) {
            this.value = ui.item.label;
            $("#tags_id").val(ui.item.value)
            var data = {};
            data["label"] = ui.item.label;
            data["value"] = ui.item.value;
            items.push(data);
            return false;
        }

    });

    $( "#tags" ).change(function () {
        console.log(items);

        var str = "";
        for(var i = 0; i < items.length; i++){
            var item = items[i];
            var label = item.label;
            var value = item.value;
            idList.push(item.value);

            // Example of rendering
            str += "<div class=\"alert w-25 mr-1 alert-warning alert-dismissible fade show\" id=\""+value+"\" role=\"alert\">\n" +
                label+"\n" +
                "  <button type=\"button\" class=\"close\" id=\"close"+value+"\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "    <span aria-hidden=\"true\">&times;</span>\n" +
                "  </button>\n" +
                "</div>";
        }
        $('#train_info').html(str);
    })
    $('.close').click(function() {
        var id = $(this).attr('id');
        var data = $.grep(items, function(e){
            return e.id != id;
        });
        console.log(items);
        return false;
    });
});

$('#save-class').click(function () {
    var data = {}
    data["name"] = $('#name').val();
    data["start"] = $('#start-date').val();
    data["end"] = $('#end-date').val();
    data["planCount"] = $('#plan-count').val();
    data["listObject"] = idList;
    data["trainer"] = $('#exampleFormControlSelect1').val();
    console.log(data);

    $.ajax({
        url: "/class-management/add-class",
        type: "post",
        contentType: "application/json", // dữ liệu gửi lên web-service có dạng là json.
        data: JSON.stringify(data), // object json -> string json

        dataType: "json", // dữ liệu từ web-service trả về là json.
        success: function(jsonResult) { // được gọi khi web-service trả về dữ liệu.
            if (jsonResult.status === 200) {
                console.log(jsonResult.data);
                showNotification('top','right','Add success!')
            }
        },
        error: function(jqXhr, textStatus, errorMessage) { // error callback
            showWarn("Exception! "+ errorMessage);
        }
    });
})

function checkBirthDay(element) {
    var dateX = element.attr('value')
    var date = dateX.substring(0, 2);
    var month = dateX.substring(3, 5);
    var year = dateX.substring(6, 10);
    var dateY = new Date(dateX);
    console.log(dateY);console.log(new Date())
    var crr = new Date();
    if (crr > dateY){
        showNotification('top','right','Good!')
    }else {
        showNotification('top','right','Good!')
    }
}


