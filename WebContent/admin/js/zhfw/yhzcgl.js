$(function () {
    var model = {
        path: adminContextPath + "/zhfw/froperaccnt",
        resetDataForm: function () {
            $("#data-form").bootstrapValidator('resetForm', true);
            //表单默认值可以在这里设置
            way.set("model.form.data", null);
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
                way.set("model.form.data", respone.data);
            })
        },
        setViewDataById: function (id) {
            $.get(this.path + "/get.do", {id: id}, function (respone) {
                way.set("model.view", respone.data);
            })
        },
        init: function () {//需要初始化的功能
        }
    };
    model.init();
    //渠道管理操作
    $("body").on("click",".qdgl-assign",function(){
        var qdid = $(this).data('qdid');
        var froaid = $(this).data('froaid');
        var struts = $(this).data('struts');
        layer.confirm('确定进行此操作？', {
            shadeClose: true,
            icon: 3,
            anim: 6,
            btn: ['确定', '取消']
            // 按钮
        }, function () {
            $.post(adminContextPath + "/zhfw/froperaccnt/changeZt.do", {
                qdid:qdid,
                froaid:froaid,
                newDqzt:struts
            }, function (respone) {
                if (respone.responeCode == "0") {
                    layer.msg("操作完成");
                    $('#qdglTable').bootstrapTable("refresh");
                }
            });
        });
    });
    // 查询
    $("#search").click(function () {
        model.tableRefresh();
    });
    // 重置密码
    $("#reset").click(function () {
        var rows = $('#table').bootstrapTable("getSelections");
        if (rows.length == 0) {
            layer.msg("请选择一行");
        } else {
            layer.confirm('确定重置所选行密码？', {
                shadeClose: true,
                icon: 3,
                anim: 6,
                btn: ['确定', '取消']
                // 按钮
            }, function () {
                $.post(model.path + "/reset.do", {
                    id: rows[0].froaid
                }, function (respone) {
                    if (respone.responeCode == "0") {
                        layer.msg("密码重置成功");
                        model.tableRefresh();
                    }
                });
            });
        }

    });
    // 用户渠道管理
    $("#yhqdgl").click(function () {
        var rows = $('#table').bootstrapTable("getSelections");
        if (rows.length == 0) {
            layer.msg("请选择一行");
        } else {
            model.setFormTitle("<i class='fa fa-edit'>渠道管理【" + rows[0].yhxm + "】</i>");
            way.set("model.form.froaid", rows[0].froaid);
            $("#form-panel").modal('toggle');
            $('#qdglTable').bootstrapTable("refresh");
        }
    });


    // 列表
    $('#table').bootstrapTable({
        url: model.path + '/qryPage.do',
        columns: [
            {
                checkbox: true
            },
            {
                field: 'yhlbdm',
                title: '用户类别',
                formatter: function (value) {
                    return $.getDictName('YHLBDM', value);
                }
            },
            {
                field: 'yhxm',
                title: '用户名称'
            },
            {
                field: 'xgyhzh1',
                title: '相关用户账号'
            },
            {
                field: 'zjhm',
                title: '身份证号'
            },
            {
                field: 'yhsjhm',
                title: '手机号码'
            },
            {
                field: 'yhzcrq',
                title: '用户注册日期',
                formatter: function (value) {
                    if (value) {
                        return value.split(" ")[0];
                    } else {
                        return '-';
                    }
                }
            },
            {
                field: 'yhzt',
                title: '当前状态',
                formatter: function (value) {
                    var yhztc = '';
                    switch (value) {
                        case '01':
                            yhztc = '启用';
                            break;
                        case '02':
                            yhztc = '停用';
                            break;
                        case '03':
                            yhztc = '已销户';
                            break;
                        default:
                            yhztc = '未知';
                            break;
                    }
                    return yhztc;
                }
            }
        ]
    });

    $('#qdglTable').bootstrapTable({
        url: model.path + '/getYhqdList.do',
        queryParams: function () {
            return {
                froaid: way.get("model.form.froaid") ? way.get("model.form.froaid") : ''
            };
        },
        columns: [
            {
                field: 'QDMC',
                title: '渠道名称'
            },
            {
                field: 'DQZT',
                title: '当前状态',
                formatter: function (value, record) {
                    if (value == 'Y') {
                        return '已启用';
                    } else {
                        return '-';
                    }
                }
            },
            {
                field: 'DQZT',
                title: '操作',
                formatter: function (value, record) {
                    if (value == 'Y') {
                        if (record.QDID == 'QD004') {
                            return '<a href="#" class="text-danger qdgl-assign" data-qdid="'+record.QDID+'" data-froaid="'+record.FROAID+'" data-struts="N">解绑</a>';
                        }
                        return '<a href="#" class="text-danger qdgl-assign" data-qdid="'+record.QDID+'" data-froaid="'+record.FROAID+'" data-struts="N">停用</a>';
                    } else {
                        if (record.QDID == 'QD004') {
                            return '请在微信上绑定'; //微信绑定只能在微信上操作
                        }
                        return '<a href="#" class="text-success qdgl-assign" data-qdid="'+record.QDID+'" data-froaid="'+record.FROAID+'" data-struts="Y">启用</a>';
                    }
                }
            }
        ]
    });


});