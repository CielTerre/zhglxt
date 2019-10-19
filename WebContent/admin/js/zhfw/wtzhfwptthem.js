$(function () {
    var model = {
        path: adminContextPath + "/zhfw/wtzhfwptthem",
        resetDataForm: function () {
            $("#data-form").bootstrapValidator('resetForm', true);
            //表单默认值可以在这里设置
            way.set("model.form.data", null);
            optionSize = 1;
            $("#data-form .modal-body").find('.option:gt(0)').remove();
        },
        getFormData: function () {
            var data = way.get("model.form.data");
            return data ? data : {};
        },
        setFormTitle: function (title) {
            way.set("model.form.title", title);
        },
        tableRefresh: function () {
            $('#table').bootstrapTable("refresh");
        },
        setFormDataById: function (id) {
            $.get(this.path + "/get.do", {id: id}, function (respone) {
                way.set("model.form.data", respone.data.data);
                var options = respone.data.options;
                if (options.length) {
                    optionSize = options.length;
                }
                for (var i = 0; i < options.length; i++) {
                    if (i > 0) {
                        $("#data-form .modal-body").append('<div class="form-group option op' + (i + 1) + '">\n' +
                            '                            <input type="hidden" way-data="voteoptionid' + (i + 1) + '" name="voteoptionid' + (i + 1) + '" value="' + options[i].voteoptionid + '">\n' +
                            '                        <label class="col-sm-3 control-label">投票选项' + (i + 1) + '</label>\n' +
                            '                        <div class="col-sm-6">\n' +
                            '                            <input class="form-control" way-data="options' + (i + 1) + '" name="options' + (i + 1) + '" placeholder="不填即删除该选项" value="' + options[i].xpnr + '">\n' +
                            '                        </div>\n' +
                            '                    </div>');
                    } else {
                        way.set('model.form.data.voteoptionid0', options[i].voteoptionid);
                        way.set('model.form.data.options0', options[i].xpnr);
                    }
                }
            });
        },
        setViewDataById: function (id) {
            $("#modal-view").find(".viewOptions").remove();
            $.get(this.path + "/get.do", {id: id}, function (respone) {
                way.set("model.view", respone.data.data);
                way.set("model.view.dqzt",$.getDictName('ISTOP',respone.data.data.dqzt));
                var options = respone.data.options;
                var tpjg = 0;
                for (var i = 0; i < options.length; i++) {
                    tpjg += Number(options[i].xpjg);
                }
                for (var i = 0; i < options.length; i++) {
                    var parent = (options[i].xpjg*100/tpjg).toFixed(2);
                    if(tpjg == 0){
                        parent = 0 ;
                    }
                    $("#modal-view .form-horizontal").append('<div class="form-group viewOptions">\n' +
                        '                        <label class="col-sm-4 control-label">投票选项' + (i + 1) + ':</label>\n' +
                        '                        <div class="col-sm-4">\n' +
                        '                            <p class="form-control-static">' + options[i].xpnr + '</p>\n' +
                        '                        </div>\n' +
                        '                       <div class="col-sm-4">\n' +
                        '                            <p class="form-control-static">' + options[i].xpjg + ' ('+parent+'%)</p>\n' +
                        '                        </div>\n' +
                        '                    </div>');
                }
            });
        },
        init: function () {//需要初始化的功能
        }
    };
    model.init();
    // 校验
    $("#data-form").bootstrapValidator().on("success.form.bv", function (e) {// 提交
        e.preventDefault();
        var id = model.getFormData().themeid;
        var optUrl = model.path + "/save.do";
        if (id) {
            optUrl = model.path + "/update.do";
        }
        $.post(optUrl, $("#data-form").serialize(), function (respone) {
            if (respone.responeCode == '0') {

                layer.msg("保存成功");
                model.tableRefresh();
                $("#form-panel").modal('toggle');
            }
        });
    });
    /**
     * 查看
     */
    $("body").on("click", ".view", function () {
        var id = $(this).data("id");
        model.setViewDataById(id);
        $('#modal-view').modal('toggle');
    });
    // 查询
    $("#search").click(function () {
        model.tableRefresh();
    });
    // 添加
    $("#add").click(function () {
        model.resetDataForm();
        model.setFormTitle("<i class='fa fa-plus'>添加</i>");
        $("#form-panel").modal('toggle');
    });
    // 编辑
    $("#edit").click(function () {
        var rows = $('#table').bootstrapTable("getSelections");
        if (rows.length == 0) {
            layer.msg("请选择一行");
        } else {
            model.resetDataForm();
            model.setFormDataById(rows[0].themeid);
            model.setFormTitle("<i class='fa fa-edit'>编辑</i>");
            $("#form-panel").modal('toggle');
        }
    });
    // 删除
    $("#delete").click(function () {
        var rows = $('#table').bootstrapTable("getSelections");
        if (rows.length == 0) {
            layer.msg("请选择一行");
        } else {
            layer.confirm('确定删除？', {
                shadeClose: true,
                icon: 3,
                anim: 6,
                btn: ['确定', '取消']
                // 按钮
            }, function () {
                $.post(model.path + "/delete.do", {
                    id: rows[0].themeid
                }, function (respone) {
                    if (respone.responeCode == "0") {
                        layer.msg("删除成功");
                        model.tableRefresh();
                    }
                });
            });
        }
    });


    var optionSize = 1;
    //增加一行选项
    $("#addOption").on('click', function () {
        optionSize++;
        $("#data-form .modal-body").append('<div class="form-group option op' + optionSize + '">\n' +
            '                            <input type="hidden" way-data="voteoptionid' + optionSize + '" name="voteoptionid' + optionSize + '">\n' +
            '                        <label class="col-sm-3 control-label">投票选项' + optionSize + '</label>\n' +
            '                        <div class="col-sm-6">\n' +
            '                            <input class="form-control" way-data="options' + optionSize + '" name="options' + optionSize + '" placeholder="不填即删除该选项">\n' +
            '                        </div>\n' +
            '                    </div>');
    });

    // 列表
    $('#table').bootstrapTable({
        url: model.path + '/qryPage.do',
        columns: [
            {
                checkbox: true
            },
            {
                field: 'themetitle',
                title: '投票主题',
                formatter: function (value, row, index) {
                    return "<a href='#' class='view text-success' data-id='" + row.themeid + "'>" + value + "</a>"
                }
            },
            {
                field: 'lrrzh',
                title: '录入人',
                formatter: function (v) {
                    return $.getSysUserName(v);
                }
            },
            {
                field: 'lrrq',
                title: '录入日期'
            },
            {
                field: 'dqzt',
                title: '发布状态',
                formatter: function (value) {
                    return $.getDictName('ISTOP', value);
                }
            }
        ]
    });

});