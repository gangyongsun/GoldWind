var tableName="memberListTable";
//server分页
$(function () {
    var t = $("#"+tableName+"").bootstrapTable({
        url: '/member/pageList',
        method: 'get',
        dataType: "json",
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        striped: true,//设置隔行变色效果
        clickToSelect: true,
        undefinedText: "空",//当数据为undefined时显示的字符
        //paginationLoop:true,//设置为启用分页条无限循环的功能
      	//singleSelect: false,//设置是否禁止多选
        //search: true, //显示搜索框，client分页用的，server分页没意义
        //maintainSelected:true,//设置为 true 在点击分页按钮或搜索按钮时，将记住checkbox的选择项
        //sortable: true,//是否启用排序
        //sortOrder: "asc", //排序方式
        //sortName: "create_time",//排序字段
        showRefresh:true,//显示刷新按钮
        showToggle: true,//是否显示切换试图（table/card）按钮
        showColumns: true,//是否显示内容列下拉框
        pagination: true, //启用分页
        showPaginationSwitch:true,//是否显示数据条数选择框
        pageNumber: 1,//分页首页页码
        pageSize: 10,//分页页面数据条数
        pageList: [10, 20, 50, 100],//设置可供选择的页面数据条数；设置为All则显示所有记录
        minimumCountColumns: 2,//最少允许的列数
        paginationFirstText: "首页",//指定“首页”按钮的图标或文字
        paginationPreText: '上一页',//指定“上一页”按钮的图标或文字
        paginationNextText: '下一页',//指定“下一页”按钮的图标或文字
        paginationLastText: "末页",//指定“末页”按钮的图标或文字
        data_local: "zh-US",//表格汉化
        sidePagination: "server", //服务端处理分页
        queryParamsType:'',//默认值为'limit',在默认情况下传给服务端的参数为：offset,limit,sort;设置为'',在这种情况下传给服务器的参数为：pageSize,pageNumber
       /*  queryParams:function (params) {
            //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
            var temp = {   
            	pageNumber: params.pageNumber,//当前页(开始页)
        	    pageSize: params.pageSize,//每页的数量
        		sortOrder: params.sortOrder,//排序方式
        	    sortName: params.sortName,//根据什么排序
            };
            return temp;
        }, */
      	idField: "id",//指定主键列
        columns: [
            {
                checkbox:true,
            },
            {
                visible:false,
                field: 'id',//json数据中rows数组中的属性名
                align: 'center'//水平居中
            },
            {
                title: '昵称',
                field: 'nickname',
                align: 'left'
            },
            {
                title: '账号',
                field: 'userName',
                align: 'left'
            },
            {
                title: '是否有效',
                field: 'userEnable',
                align: 'left',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
                	 return row.userEnable == 0 ? "无效" : "有效";
                }
            },
            {
                title: '创建时间',
                field: 'createTime',
                align: 'left'
            },
            {
                title: '最后登录时间',
                field: 'lastLoginTime',
                align: 'left'
            },
            {
                title: '操作',
                align: 'center',
                width:300,
                clickToSelect:false,
                formatter: function (value, row, index) {//自定义显示，也可以写标签
                	var forbid_content=
                		'<shiro:hasPermission name="user/deactive">'+
						'<a class="btn btn-outline btn-warning btn-sm" th:id="'+row.id+'" onclick="activeUserByStatusAndId(0,'+ row.id +')"><span class="fa fa-lock"></span> 禁止登录</a>'+
						'</shiro:hasPermission>&nbsp;';
                	var active_content=
                		'<shiro:hasPermission name="user/active">'+
						'<a class="btn btn-outline btn-success btn-sm" th:id="'+row.id+'" onclick="activeUserByStatusAndId(1,'+ row.id +')"><span class="fa fa-unlock"></span> 激活登录</a>'+
						'</shiro:hasPermission>&nbsp;';
					var resetPwd_content=
						'<shiro:hasPermission name="user/passwordreset">'+
						'<a class="btn btn-outline btn-danger btn-sm" th:id="'+row.id+'" onclick="openResetPasswdWindow('+ row.id +')"><span class="fa fa-key"></span> 重置密码</a>'+
						'</shiro:hasPermission>&nbsp;';
					var delete_content=
						'<shiro:hasPermission name="user/delete">'+
						'<a class="btn btn-outline btn-danger btn-sm" th:id="'+row.id+'" onclick="_deleteone('+ row.id +')"><span class="fa fa-trash"></span> 删除</a>'+
						'</shiro:hasPermission>';
					if(row.userEnable==1){
						var operate=forbid_content+resetPwd_content+delete_content;
					}else{
						var operate=active_content+resetPwd_content+delete_content;
					}
                    return operate;
                }
            }
        ]
    });
    t.on('load-success.bs.table', function (data) {//table加载成功后的监听函数
        console.log("load success");
        $(".pull-right").css("display", "block");
    });
});

/**
 * 删除所有选中用户
 * @returns
 */
function deleteSelected(){
	var idArray=getSelections(tableName);
	if (idArray.length == 0) {
		layer.msg("请先选择要删除的用户!");
	}else{
		var index = layer.confirm("确定删除这" + idArray.length + "个用户？", function() {
			$.post('deleteUserById', {
				ids : idArray.join(',')
			}, function(result) {
				if (result) {
					layer.msg(result.message);
				}
				refreshPage(tableName);
			}, 'json');
		});
	}
}

/**
 * 根据ID数组，删除
 * 
 * @param ids
 * @returns
 */
function _deleteone(id) {
	var index = layer.confirm("确定删除该用户？", function() {
		$.post('deleteUserById', {
			ids : id
		}, function(result) {
			if (result) {
				layer.msg(result.message);
			}
			refreshPage(tableName);
		}, 'json');
	});
}

/**
 * 重置密码弹框
 * 
 * @param id
 * @returns
 */
function openResetPasswdWindow(id) {
	$("#userId").val(id);
	showModal('passwdReset');
}

/**
 * 重置密码
 * 
 * @param id
 * @returns
 */
function resetPasswd() {
	var userId = $("#userId").val();
	var reNewPswd = $("#reNewPswd").val();
	var newPswd = $('#newPswd').val();
	if (newPswd != reNewPswd) {
		return layer.msg('密码输入不一致！');
	}

	var index = layer.confirm("确定重置该用户的密码？", function() {
		$.post('resetPasswd', {
			id : userId,
			newPswd : newPswd
		}, function(result) {
			if (result) {
				layer.msg(result.message);
			}
			hideModal('passwdReset');
		}, 'json');
	});
}

/**
 * 保存添加的用户
 * 
 * @returns
 */
function addUser() {
	var username = $("#userName").val();
	var password = $("#passWord").val();
	var userenable = $("#userEnable").val();

	$.post('addUser', {
		userName : username,
		passWord : password,
		userEnable : userenable
	}, function(result) {
		if (result) {
			layer.msg(result.message);
		}
		refreshPage(tableName);
		hideModal("showAddUser")
	}, 'json');
}

/**
 * 激活 | 禁止用户登录
 * 
 * @param status
 * @param id
 * @returns
 */
function activeUserByStatusAndId(userEnable, id) {
	var text = userEnable ? '激活' : '禁止';
	var index = layer.confirm("确定" + text + "这个用户？", function() {
		$.post('activeUserByStatusAndId', {
			userEnable : userEnable,
			id : id
		}, function(result) {
			if (result) {
				layer.msg(result.message);
			}
			refreshPage(tableName);
		}, 'json');
	});
}