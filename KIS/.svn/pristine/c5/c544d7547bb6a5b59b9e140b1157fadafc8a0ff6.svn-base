var tableName="resourceListTable4Allocation";
//server分页
$(function () {
    var t = $("#"+tableName+"").bootstrapTable({
        url: '/resource/pageList4Allocation',
        method: 'get',
        dataType: "json",
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        striped: true,//设置隔行变色效果
        clickToSelect: true,
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
        sidePagination: "server", //服务端处理分页
        queryParamsType:'',//默认值为'limit',在默认情况下传给服务端的参数为：offset,limit,sort;设置为'',在这种情况下传给服务器的参数为：pageSize,pageNumber
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
                title: '角色名称',
                field: 'roleDesc',
                align: 'left'
            },
            {
                title: '角色类型',
                field: 'roleType',
                align: 'left'
            },
            {
                title: '拥有的权限',
                field: 'resourceNames',
                align: 'left'
            },
            {
                title: '操作',
                align: 'center',
                width:200,
                formatter: function (value, row, index) {//自定义显示，也可以写标签
                	operate=
                	'<shiro:hasPermission name="resource/grantresource2role">'+
					'<a class="btn btn-info btn-sm" onclick="javascript:selectResourceById('+row.id+');"><span class="glyphicon glyphicon-cog"></span> 选择权限</a>'+
					'</shiro:hasPermission>'
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
 * 清空所选角色的所有权限
 * 
 * @returns
 */
function deleteSelected(){
	var idArray=getSelections(tableName);
	if (idArray.length == 0) {
		layer.msg("请先选择要清空角色的用户!");
	}else{
		console.log(idArray)
		var index = layer.confirm("确定清空这"+ idArray.length +"个用户的所有角色？",function(){
			$.post('clearRoleResourceRelationshipByRoleIds',{
				roleIds:idArray.join(',')
			},function(result){
				if (result) {
					layer.msg(result.message);
				}
				refreshPage(tableName);
			},'json');
		});
	}
}

/**
 * 选择权限后保存
 * 
 * @returns
 */
function selectResource(){
	var checked = $("#boxRoleForm  :checked");
	var ids=[],names=[];
	$.each(checked,function(){
		ids.push(this.id);
		names.push($.trim($(this).attr('name')));
	});
	roleId=$('#selectedResourceId').val();
	var index = layer.confirm("确定操作？",function(){
		$.post('addResource2Role',{ids:ids.join(','),roleId:roleId},function(result){
			if(result){
				layer.msg(result.message);
			}
			refreshPage(tableName);
			hideModal("selectResource");
		},'json');
	});
}
/**
 * 根据角色ID选择权限，分配权限操作
 */
function selectResourceById(id){
	var load = layer.load();
	$.post("selectResourceByRoleId",{id:id},function(result){
		layer.close(load);
		if(result && result.length){
			var html =[];
			html.push('<div class="checkbox"><label><input type="checkbox"  selectAllBox="">全选</label></div>');
			$.each(result,function(){
				html.push("<div class='checkbox'><label>");
				html.push("<input type='checkbox' selectBox='' id='");
				html.push(this.id);
				html.push("'");
				if(this.check){
					html.push(" checked='checked'");
				}
				html.push("name='");
				html.push(this.resourceName);
				html.push("'/>");
				html.push(this.resourceName);
				html.push('</label></div>');
			});
			// 初始化全选
			return so.id('boxRoleForm').html(html.join('')),
			so.checkBoxInit('[selectAllBox]','[selectBox]'),
			$('#selectResource').modal({backdrop: false,keyboard: true}),$('#selectedResourceId').val(id);
		}else{
			return layer.msg('没有获取到权限数据，请先添加权限数据!');
		}
	},'json');
}