var tableName="onlineTable";
//server分页
$(function () {
    var t = $("#"+tableName+"").bootstrapTable({
        url: '/member/pageOnline',
        method: 'get',
        dataType: "json",
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        striped: true,//设置隔行变色效果
        undefinedText: "空",//当数据为undefined时显示的字符
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
        sidePagination: "server", //客户端分页
        queryParamsType:'',//默认值为'limit',在默认情况下传给服务端的参数为：offset,limit,sort;设置为'',在这种情况下传给服务器的参数为：pageSize,pageNumber
      	//idField: "id",//指定主键列
        columns: [
            {
                visible:false,
                field: 'sessionId',//json数据中rows数组中的属性名
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
                title: '在线状态',
                field: 'sessionStatus',
                align: 'left',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
                	 return row.sessionStatus == true ? "在线" : "不在线";
                }
            },
            {
                title: '会话创建时间',
                field: 'startTime',
                align: 'left'
            },
            {
                title: '会话最后活动时间',
                field: 'lastAccess',
                align: 'left'
            },
            {
                title: '操作',
                align: 'center',
                width:200,
                formatter: function (value, row, index) {
                	var detail_content=
                		'<shiro:hasPermission name="user/onlinedetail">'+
                		'<a class="btn btn-info btn-sm" onclick="onlineDetail(\''+ row.sessionId +'\')"><span class="glyphicon glyphicon-info-sign"></span> 详情</a>';//+
                		'</shiro:hasPermission>';
					var deactive_content=
						'<shiro:hasPermission name="user/onlinedeactive">'+
						'<a class="btn btn-primary btn-sm" onclick="changeSessionStatus(\''+row.sessionId +'\',\''+ false +'\')"><span class="glyphicon glyphicon-flash"></span> 踢出</a>';//+
						'</shiro:hasPermission>';
					var active_content=
						'<shiro:hasPermission name="user/onlineactive">'+
						'<a class="btn btn-danger btn-sm" onclick="changeSessionStatus(\''+row.sessionId +'\',\''+ true +'\')"><span class="glyphicon glyphicon-ok"></span> 激活</a>';//+
						'</shiro:hasPermission>';
					
					var operate=detail_content;
					
					if(row.sessionStatus == true){
						operate+=deactive_content;
					}else{
						operate+=active_content;
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
 * 在线用户详情
 * 
 * @param sessionId
 * @returns
 */
function onlineDetail(sessionId){
	console.log(sessionId);
	$.ajax({
		type : "POST",
		url : 'onlineDetail',
		data : {
			sessionId : sessionId
		},
		success : function(data) {
			console.log(data);
//			$("#myModal").html(data);
//			$('#myModal').modal('show');
		},
		error : function(data) {
			console.log("查看详情失败！");
		}
	});
}

/**
 * 改变状态
 * 
 * @param sessionIds
 * @param status
 * @returns
 */
function changeSessionStatus(sessionIds, status) {
	var text="确定踢出该用户？";
	if(status){
		text="确定激活该用户？";
	}
	
	var index = layer.confirm(text, function() {
		$.post("changeSessionStatus", {
			status : status,
			sessionIds : sessionIds
		}, function(result) {
			if (result) {
				layer.msg(result.message);
			}
			refreshPage(tableName);
		}, 'json');
	});
}