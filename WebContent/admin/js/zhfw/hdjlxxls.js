$(function () {
    var url = decodeURI(window.location.href);
    var argsIndex = url.split("?ywlsh=");
    var ywlsh = "";
    if (argsIndex.length > 1) {
        ywlsh = argsIndex[1];
    }
    $.post(adminContextPath + "/zhfw/hdjlxxls/getByXgywlsh.do", {
        xgywlsh: ywlsh
    }, function (respone) {
        var msgData = respone.data;
        if (msgData.length) {
            var time = 0;
            for (var i = 0; i < msgData.length; i++) {
                var record = msgData[i];
                var currTime = record.fysj;
                if (currTime) {
                    var currTimeStamp = currTime.replace(/-/g, '/');
                    var timestamp = new Date(currTimeStamp).getTime();
                    if (!time || timestamp - time > 300000) { //说话间隔大于5分钟
                        $(".mobile-page").append('<div class="msg-time">' + currTime.substring(0, 16) + '</div>');
                    }
                    time = timestamp;
                }
                var msg = '';
                if (record.fyrlx == '1') { //中心人员
                    msg = '<div class="admin-group">' +
                        '<img class="admin-img" src="../../img/rgfw.png"/>' +
                        '<div class="admin-msg">' +
                        '<i class="triangle-admin"></i>' +
                        '<span class="admin-reply">' + record.fyrnr + '</span>' +
                        '</div>' +
                        '</div>';
                } else { //咨询用户
                    msg = '<div class="user-group">' +
                        '<div class="user-msg">' +
                        '<span class="user-reply">' + record.fyrnr + '</span>' +
                        '<i class="triangle-user"></i>' +
                        '</div>' +
                        '<img class="user-img" src="../../img/twr.png"/>' +
                        '</div>';
                }
                $(".mobile-page").append(msg);
            }
            $(document).scrollTop($(document).height()); //记录加载后 默认到底部
        }
    });

});