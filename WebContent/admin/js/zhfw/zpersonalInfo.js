$(function () {
    var model = {
        path: adminContextPath + "/zhfw/zpersonalinfo",
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
            $.get(this.path + "/get.do", {grzh: id}, function (respone) {
                //性别
                if (respone.data) {
                    way.set("model.view", respone.data);
                    if (respone.data.XINGBIE) { //性别
                        way.set('model.view.XINGBIE', $.getDictName('sex', respone.data.XINGBIE));
                    }
                }
                way.set("model.search.grzh", id);
                $('#dkTable').bootstrapTable("refresh");
                $('#dbTable').bootstrapTable("refresh");
                $('#ywlsTable').bootstrapTable("refresh");
            })
        },
        init: function () {//需要初始化的功能
        }
    };
    model.init();

    /**
     * 查看
     */
    $("body").on("click", ".view", function () {
        var grzh = $(this).data("id");
        model.setViewDataById(grzh);
        $('#modal-view').modal('toggle');
    });
    // 查询
    $("#search").click(function () {
        model.tableRefresh();
    });
    // 列表
    $('#table').bootstrapTable({
        url: model.path + '/qryPage.do',
        columns: [
            {
                checkbox: true
            },
            {
                field: 'GRZH',
                title: '公积金账号'
            }, {
                field: 'XINGMING',
                title: '姓名',
                formatter: function (value, row) {
                    return "<a href='#' class='view text-success' data-id='" + row.id + "'>" + value + "</a>"
                }
            },
            {
                field: 'ZJHM',
                title: '身份证号'
            }, {
                field: 'SJHM',
                title: '手机号码'
            }, {
                field: 'DWMC',
                title: '单位名称'
            }, {
                field: 'GRCKZHHM',
                title: '银联卡号'
            }, {
                field: 'GRJZNY',
                title: '缴至年月'
            }, {
                field: 'HJYJCE',
                title: '合计月缴'
            }, {
                field: 'GRZHYE',
                title: '公积金余额'
            }, {
                field: 'GRZHZT',
                title: '账号状态'
            }
        ]
    });

//贷款
    $('#dkTable').bootstrapTable({
        url: model.path + '/qryDkPage.do',
        columns: [
            {
                field: 'JKHTBH',
                title: '贷款合同编号'
            }, {
                field: 'HTDKJE',
                title: '借款金额'
            }, {
                field: 'SQQS',
                title: '借款期限'
            }, {
                field: 'WHBJ',
                title: '本金余额'
            }, {
                field: 'HKNY',
                title: '最近还款'
            }, {
                field: 'JKHTQDRQ',
                title: '贷款日期'
            }
        ]
    });
    //贷款
    $('#dbTable').bootstrapTable({
        url: model.path + '/qryDbPage.do',
        columns: [
            {
                field: 'JKHTBH',
                title: '贷款合同编号'
            },
            {
                field: 'JKRXM',
                title: '贷款人姓名'
            },
            {
                field: 'JKRGJJZH',
                title: '贷款人公积金账号'
            },
            {
                field: 'HTDKJE',
                title: '贷款金额'
            },
            {
                field: 'YDKRGX',
                title: '与贷款人关系'
            },
            {
                field: 'DQZT',
                title: '锁定状态',
                formatter: function (value) {
                    switch (value) {
                        case '0':
                            return '新录入';
                        case 'Y':
                            return '已锁定';
                        case 'N':
                            return '已一般解锁';
                        case 'F':
                            return '已结清解锁';
                        case 'D':
                            return '已删除';
                        default:
                            return '未知';
                    }
                }
            },
            {
                field: 'DBFS',
                title: '互保方式',
                formatter:function (value) {
                    switch (value){
                        case 1:
                            return '公积金完全质押';
                        case 2:
                            return '公积金部分质押';
                        case 3:
                            return '质押物';
                        default:
                            return '未知';
                    }
                }
            },
            {
                field: 'ZYWJZ',
                title: '互保金额'
            }
        ]
    });
    //贷款
    $('#ywlsTable').bootstrapTable({
        url: model.path + '/qryYwlsPage.do',
        columns: [
            {
                field: 'KJNY',
                title: '会计年月'
            },{
                field: 'JZRQ',
                title: '业务日期'
            },{
                field: 'YSYWLB',
                title: '业务类型'
            },{
                field: 'ZJJE',
                title: '增加金额'
            },{
                field: 'JSJE',
                title: '减少金额'
            },{
                field: 'FSE',
                title: '合计金额'
            },{
                field: 'ZY',
                title: '摘要'
            },{
                field: 'TQYY',
                title: '提取原因'
            }
        ]
    });
});
