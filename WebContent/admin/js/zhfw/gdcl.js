/**
 * 工单处理
 */
$(function () {
    var model = {
        path: adminContextPath + "/zhfw/gdxx",
        resetDataForm: function () {
            $("#data-form").bootstrapValidator('resetForm', true);
            //表单默认值可以在这里设置
            way.set("model.form.data", null);
            $("#data-form").find(".form-control-static").empty();
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
                way.set("model.form.data.lyqdid", $.getQdName(respone.data.lyqdid));
                way.set("model.form.data.gdlyc", $.getDictName('GDLY', respone.data.gdly));
                way.set("model.form.data.jjcd", $.getDictName('JJCD', respone.data.jjcd));
                way.set("model.form.data.yqwcsj", respone.data.yqwcsj ? respone.data.yqwcsj.substring(0, 10) : '');
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
            })
        },
        init: function () {//需要初始化的功能
        }
    };
    model.init();
    // 校验
    $("#data-form").bootstrapValidator().on("success.form.bv", function (e) {// 提交
        e.preventDefault();
        var id = model.getFormData().ywlsh;
        if (!id){
            layer.msg("数据异常，请刷新重试，如再次异常请上报中心。");
            return false;
        }
        way.set("model.form.data.dqzt", "1"); //状态设置为处理中
        var optUrl = model.path + "/update.do";
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
    // 编辑
    $("#edit").click(function () {
        var rows = $('#table').bootstrapTable("getSelections");
        if (rows.length == 0) {
            layer.msg("请选择一行");
        } else {
            model.resetDataForm();
            model.setFormDataById(rows[0].ywlsh);
            model.setFormTitle("<i class='fa fa-edit'>处理工单</i>");
            $("#form-panel").modal('toggle');
        }
    });
    // 列表
    $('#table').bootstrapTable({
        url: model.path + '/qryPageByUser.do',
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
                    var result = $.getDictName('JJCD', value);
                    if(result && value == '1'){
                        result = '<span style="color: red">'+result+'</span>';
                    }
                    return result;
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
            }
        ]
    });

    //处理完毕按钮
    $('#finished').click(function () {
        var id = model.getFormData().ywlsh;
        if (!id){
            layer.msg("数据异常，请刷新重试，如再次异常请上报中心。");
            return false;
        }
        way.set("model.form.data.dqzt", "2"); //状态设置为处理中
        var optUrl = model.path + "/update.do";
        $.post(optUrl, $("#data-form").serialize(), function (respone) {
            if (respone.responeCode == '0') {
                layer.msg("保存成功");
                model.tableRefresh();
                $("#form-panel").modal('toggle');
            }
        });
    })
});
