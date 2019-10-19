$(function() {
    /**
     * 字典列表与code转汉字
     */
    $.extend({
        //获取渠道列表
        getQdList : function() {
            // 先从sessionStorage 取
            var typeDict = sessionStorage.getItem("model.qd" );
            if (typeDict) {
                return $.parseJSON(typeDict);
            } else {
                // 取消异步
                $.ajaxSetup({
                    async : false
                });
                var dict = [];
                $.get(adminContextPath + "/zhfw/qdxxb/qryAll.do", {

                }, function(respone) {
                    if (respone.responeCode == "0") {
                        dict = respone.data;
                        // 存入sessionStorage
                        if (dict) {
                            sessionStorage.setItem("model.qd" , JSON
                                .stringify(dict));
                        }
                    }
                });
                $.ajaxSetup({
                    async : true
                });
                return dict;
            }
        },
        // 字典code 转value
        getQdName : function(value) {
            if ( !value) {
                return null;
            }
            // 先从sessionStorage 取
            var typeMap = sessionStorage.getItem("model.qd.map" );
            if (typeMap) {
                var json = $.parseJSON(typeMap);
                return json[value];
            } else {
                var map = {};
                var dictList = $.getQdList();
                if (dictList) {
                    $.each(dictList, function(i, v) {
                        map[v.qdid] = v.qdmc
                    });
                    if (map) {
                        sessionStorage.setItem("model.qd.map", JSON
                            .stringify(map));
                    }
                    return map[value];
                }
            }
        },
        //渠道下拉框
        qdInputhandler:function(){
            /**
             * <div class="col-sm-5"> <label class="radio-inline"> <input type="radio"
             * required way-data="status" name="status" value="1">有效 </label> <label
             * class="radio-inline"> <input type="radio" required way-data="status"
             * name="status" value="0">无效 </label> </div>
             *
             * <div class="col-sm-5"> <label class="checkbox-inline"> <input
             * type="checkbox" required way-data="status" name="status" value="1">有效
             * </label> <label class="checkbox-inline"> <input type="checkbox" required
             * way-data="status" name="status" value="0">无效 </label> </div>
             *
             */
            // 字典渲染
            //$(".sf-radio,.sf-checkbox").each(function(i, v) {
            //	var inputName = $(this).data("sf-input-name");
            //	var dictType = $(this).data("sf-dict-type");
            //	var required = $(this).data("sf-required");
            //
            //	var dictList = $.getDictList(dictType);
            //	if (dictList) {
            //		$.each(dictList, function(j, k) {
            //			k.inputName = inputName;
            //			if (required) {
            //				k.required = required
            //			}
            //			// 禁用
            //			if (k.status == '0') {
            //				k.disabled = "disabled";
            //			}
            //		});
            //	}
            //	if ($(this).hasClass("sf-radio")) {
            //		$("#sf-radio-template").tmpl(dictList).appendTo(this);
            //	} else {
            //		$("#sf-checkbox-template").tmpl(dictList).appendTo(this);
            //	}
            //});
            /**
             * eg:
             * <div class="form-group">
             <label class="col-sm-3 control-label">用户类型</label>
             <div class="col-sm-5">
             <select class="form-control sf-select" way-data="userType"
             name="userType" data-sf-dict-type="sys_user_type">
             <option value="">请选择</option>
             </select>
             </div>
             </div>
             */
            $(".qd-select").each(function(i, v) {
                var dictList = $.getQdList();
                if (dictList) {
                    $.each(dictList, function(j, k) {
                        // 禁用
                        if (k.qdzt != '01') {
                            k.disabled = "disabled";
                        }
                    });
                }
                $("#qd-template").tmpl(dictList).appendTo(this);
                //重新渲染
                $('.selectpicker').selectpicker('refresh');
            });
        },


        /**
         使用说明:
         1.页面添加 template 模板 :
         <!--用户下拉框template-->
         <script id="sysUser-select-template" type="text/x-jquery-tmpl">
            <option value="${id}" ${disabled}>${name}</option>
         </script>
         2. 添加下拉框 class样式添加sysUser-select   添加属性 data-sf-dict-type="sysUser"
         <select class="form-control sysUser-select" way-data=""  name="" data-sf-dict-type="sysUser"></select>
         */
        //获取sysUser用户列表
        getSysUser:function () {
            // 先从sessionStorage 取
            var typeDict = sessionStorage.getItem("model.sysUser" );
            if (typeDict) {
                return $.parseJSON(typeDict);
            } else {
                // 取消异步
                $.ajaxSetup({
                    async : false
                });
                var dict = [];
                $.get(adminContextPath + "/sys/user/qryAll.do", {

                }, function(respone) {
                    if (respone.responeCode == "0") {
                        dict = respone.data;
                        // 存入sessionStorage
                        if (dict) {
                            sessionStorage.setItem("model.sysUser" , JSON .stringify(dict));
                        }
                    }
                });
                $.ajaxSetup({
                    async : true
                });
                return dict;
            }
        },
        //用户账户id 转 姓名name
        getSysUserName:function (value) {
            if ( !value) {
                return null;
            }
            // 先从sessionStorage 取
            var typeMap = sessionStorage.getItem("model.sysUser.map" );
            if (typeMap) {
                var json = $.parseJSON(typeMap);
                return json[value];
            } else {
                var map = {};
                var dictList = $.getSysUser();
                if (dictList) {
                    $.each(dictList, function(i, v) {
                        map[v.id] = v.name
                    });
                    if (map) {
                        sessionStorage.setItem("model.sysUser.map", JSON.stringify(map));
                    }
                    return map[value];
                }
            }
        },
        //用户下拉框
        userInputHandler:function () {
            $(".sysUser-select").each(function(i, v) {
                var dictList = $.getSysUser();
                if (dictList) {
                    $.each(dictList, function(j, k) {
                        // 禁用
                        if (k.status != '1') {
                            k.disabled = "disabled";
                        }
                    });
                }
                $("#sysUser-select-template").tmpl(dictList).appendTo(this);
                //重新渲染
                $('.selectpicker').selectpicker('refresh');
            });
        },
        
        
        
        
        /**
        使用说明:
        1.页面添加 template 模板 :
        <!--栏目下拉框template-->
        <script id="lm-template" type="text/x-jquery-tmpl">
		<option value="${classid}" ${disabled}>${classname}</option>
		</script>
        2. 添加下拉框 class样式添加lm-select   添加属性 data-sf-dict-type="lm"
        <select class="form-control lm-select" way-data="" name="" data-sf-dict-type="lm"></select>
        */
        //获取栏目分类列表
        getFljs:function(){
        	 // 先从sessionStorage 取
            var typeDict = sessionStorage.getItem("model.lm" );
            if (typeDict) {
                return $.parseJSON(typeDict);
            } else {
                // 取消异步
                $.ajaxSetup({
                    async : false
                });
                var dict = [];
                $.get(adminContextPath + "/zhfw/zhfwptqtclass/qryAll.do", {

                }, function(respone) {
                    if (respone.responeCode == "0") {
                        dict = respone.data;
                        // 存入sessionStorage
                        if (dict) {
                            sessionStorage.setItem("model.lm" , JSON .stringify(dict));
                        }
                    }
                });
                $.ajaxSetup({
                    async : true
                });
                return dict;
            }
        },
        //检索classid 转检索名称 classname
        getFljsName:function (value) {
            if ( !value) {
                return null;
            }
            // 先从sessionStorage 取
            var typeMap = sessionStorage.getItem("model.lm.map" );
            if (typeMap) {
                var json = $.parseJSON(typeMap);
                return json[value];
            } else {
                var map = {};
                var dictList = $.getSysUser();
                if (dictList) {
                    $.each(dictList, function(i, v) {
                        map[v.id] = v.name
                    });
                    if (map) {
                        sessionStorage.setItem("model.lm.map", JSON.stringify(map));
                    }
                    return map[value];
                }
            }
        },
        //文章按照栏目分类检索的下拉框
        jsInputHandler:function(){
        	$(".lm-select").each(function(i, v){
        		var dictList = $.getFljs();
                if (dictList) {
                    $.each(dictList, function(j, k) {
                        // TODO 禁用
                        if (k.classid == '9' || k.classid == '21' || k.classid == '26' || k.classid == '15' || k.classid == '34') {
                            k.disabled = "disabled";
                        }
                    });
                }
                $("#lm-template").tmpl(dictList).appendTo(this);
                //重新渲染
                $('.selectpicker').selectpicker('refresh');
        	});
        },
        
        
        
        
        //获取业务明细下拉框
        getYwmx:function(){
            // 先从sessionStorage 取
            var typeDict = sessionStorage.getItem("model.ywmx" );
            if (typeDict) {
                return $.parseJSON(typeDict);
            } else {
                // 取消异步
                $.ajaxSetup({
                    async : false
                });
                var dict = [];
                $.get(adminContextPath + "/zhfw/fwdjxx/qryYwmx.do", {

                }, function(respone) {
                    if (respone.responeCode == "0") {
                        dict = respone.data;
                        // 存入sessionStorage
                        if (dict) {
                            sessionStorage.setItem("model.ywmx" , JSON .stringify(dict));
                        }
                    }
                });
                $.ajaxSetup({
                    async : true
                });
                return dict;
            }
        },
        //检索classid 转检索名称 classname
        getYwmxName:function (value) {
            if ( !value) {
                return null;
            }
            // 先从sessionStorage 取
            var typeMap = sessionStorage.getItem("model.ywmx.map" );
            if (typeMap) {
                var json = $.parseJSON(typeMap);
                return json[value];
            } else {
                var map = {};
                var dictList = $.getYwmx();
                if (dictList) {
                    $.each(dictList, function(i, v) {
                        map[v.id] = v.name
                    });
                    if (map) {
                        sessionStorage.setItem("model.ywmx.map", JSON.stringify(map));
                    }
                    return map[value];
                }
            }
        },
        //文章按照栏目分类检索的下拉框
        ywmxInputHandler:function(){
            $(".ywmx-select").each(function(i, v){
                var dictList = $.getYwmx();
                if (dictList) {
                    $.each(dictList, function(j, k) {
                        // TODO 禁用
                        /*if (k.status != '1') {
                         k.disabled = "disabled";
                         }*/
                    });
                }
                $("#sf-ywmx-template").tmpl(dictList).appendTo(this);
                //重新渲染
                $('.selectpicker').selectpicker('refresh');
            });
        }

    });
    $.qdInputhandler();
    $.userInputHandler();
    $.jsInputHandler();
    $.ywmxInputHandler();
});
