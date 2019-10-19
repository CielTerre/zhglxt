$(function () {
    var model = {
        path: adminContextPath + "/zhfw/gdxx",
        resetDataForm: function () {
            $("#data-form").bootstrapValidator('resetForm', true);
            //表单默认值可以在这里设置
            way.set("model.form.data", null);
            $('#data-form').find('input,textarea,select,button').attr('disabled',false);
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
                if(respone.data.dqzt == '2'){ //处理完毕,不能更改.
                    $('#data-form').find('input,textarea,select,button').attr('disabled',true);
                    // $("#data-form").find('button').attr('disabled',true)
                }else {
                    $('#data-form').find('input,textarea,select,button').attr('disabled',false);
                }
            })
        },
        setViewDataById: function (id) {
            $("#modal-view").find(".form-control-static").empty();
            $.get(this.path + "/get.do", {id: id}, function (respone) {
                way.set("model.view", respone.data);
                way.set("model.view.lyqdid", $.getQdName(respone.data.lyqdid));
                way.set("model.view.gdly", $.getDictName('GDLY', respone.data.gdly));
                way.set("model.view.jjcd", $.getDictName('JJCD', respone.data.jjcd));
                way.set("model.view.dqzt", $.getDictName('CLZT', respone.data.dqzt));
                way.set("model.view.yqwcsj", respone.data.yqwcsj ? respone.data.yqwcsj.substring(0, 10) : '');
                way.set("model.view.clr", $.getSysUserName(respone.data.clr));
                way.set("model.view.hfzt", $.getDictName('yes_no', respone.data.hfzt));
                way.set("model.view.hfsj", respone.data.hfsj ? respone.data.hfsj.substring(0, 10) : '');
                way.set("model.view.yhmyd", $.getDictName('YHMYD', respone.data.yhmyd));
            });
        },
        init: function () {//需要初始化的功能
        }
    };
    model.init();
    // 校验
    $("#data-form").bootstrapValidator().on("success.form.bv", function (e) {// 提交
        e.preventDefault();
        var id = model.getFormData().ywlsh;
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
        console.log($(this).data)
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
        way.set("model.form.data.dqzt", '0');
        model.setFormTitle("<i class='fa fa-plus'>添加</i>");
        $("#data-form-dqzt").attr('hidden','hidden');
        $("#form-panel").modal('toggle');
    });
    // 编辑
    $("#edit").click(function () {
        var rows = $('#table').bootstrapTable("getSelections");
        if (rows.length == 0) {
            layer.msg("请选择一行");
        } else {
            model.resetDataForm();
            model.setFormDataById(rows[0].ywlsh);
            model.setFormTitle("<i class='fa fa-edit'>编辑</i>");
            $("#data-form-dqzt").removeAttr('hidden');
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
                    id: rows[0].ywlsh
                }, function (respone) {
                    if (respone.responeCode == "0") {
                        layer.msg("删除成功");
                        model.tableRefresh();
                    }
                });
            });
        }
    });
    // 列表
    $('#table').bootstrapTable({
        url: model.path + '/qryPage.do',
        columns: [{
            checkbox: true
        },
            {
                field: 'gdbh',
                title: '工单编号',
                formatter: function (value, row, index) {
                    return "<a href='#' class='view text-success' data-id='" + row.ywlsh + "'>" + value + "</a>"
                }
            },
            {
                field: 'lyqdid',
                title: '渠道',
                formatter: function (value) {
                    return $.getQdName(value);
                }
            },
            {
                field: 'gdly',
                title: '工单来源',//(01投诉02建议03在线咨询04 12329)
                formatter: function (value, row, index) {
                    return $.getDictName('GDLY', value);
                }
            },
            {
                field: 'gdbt',
                title: '工单标题'
            },
            {
                field: 'jjcd',
                title: '紧急程度',//(0一般1紧急)
                formatter: function (value) {
                    return $.getDictName('JJCD', value);
                }
            },
            {
                field: 'clr',
                title: '指定处理人',
                formatter: function (value) {
                    return $.getSysUserName(value);
                }
            },
            {
                field: 'yqwcsj',
                title: '要求完成时间',
                formatter: function (value) {
                    if (value) {
                        return value.substring(0, 10);
                    }
                    return '-';
                }
            },
            {
                field: 'dqzt',
                title: '工单状态',//(0未处理1处理中2处理完毕)
                formatter: function (value) {
                    return $.getDictName('CLZT', value);
                }
            },
            {
                field: 'llr',
                title: '录入人'
            },
            {
                field: 'llrq',
                title: '录入日期'
            },
            {
                field: 'hfzt',
                title: '回访状态',
                visible:false,
                formatter: function (value) {
                    return $.getDictName('yes_no', value);
                }
            },
        ]
    });

});
