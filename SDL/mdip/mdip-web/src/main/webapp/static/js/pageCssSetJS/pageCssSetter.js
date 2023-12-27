/**
 * /left/sidebar.html 左侧导航点击查询jobs列表
 * 
 * @author yonggang
 */
$(document).ready(function() {
	$('#indexPage').click(function() {
		$(this).siblings('li').removeClass('nav-active'); // 删除其他兄弟元素的样式
		$(this).attr('class', 'menu-list nav-active'); // 添加当前元素的样式
		showDashboard();
	});

	$('#jobSchedule').click(function() {
		clearInterval(dashboardInterval);
		$('#jobMgt').siblings('li').removeClass('nav-active'); // 删除其他兄弟元素的样式
		$('#jobMgt').attr('class', 'menu-list nav-active'); // 添加当前元素的样式
		$(this).siblings('li').removeClass('active'); // 删除其他兄弟元素的样式
		$(this).attr('class', 'active'); // 添加当前元素的样式
		showJobSchedule("");
		$("#txtJobName").focus();
		// Sort immediately with columns 0 and 1
		$('#dynamic-table').dataTable({
			"oLanguage" : {
				"sSearch" : "搜索：",
				"sLengthMenu" : "显示 _MENU_ 项结果",
				"sInfo" : "显示第 _START_ 至第 _END_ 项结果,共 _TOTAL_ 项",
				"oPaginate" : {
					"sPrevious" : "上一页",
					"sNext" : "下一页"
				}
			},
			"aaSorting" : [ [ 2, 'asc' ] ]
		});
	});
	$('#jobLog').click(function() {
		clearInterval(dashboardInterval);
		$('#jobMgt').siblings('li').removeClass('nav-active'); // 删除其他兄弟元素的样式
		$('#jobMgt').attr('class', 'menu-list nav-active'); // 添加当前元素的样式
		$(this).siblings('li').removeClass('active'); // 删除其他兄弟元素的样式
		$(this).attr('class', 'active'); // 添加当前元素的样式
		showJobLog();
		$("#txtJobName").focus();
		// Sort immediately with columns 0 and 1
		$('#dynamic-table').dataTable({
			"oLanguage" : {
				"sSearch" : "搜索：",
				"sLengthMenu" : "显示 _MENU_ 项结果",
				"sInfo" : "显示第 _START_ 至第 _END_ 项结果,共 _TOTAL_ 项",
				"oPaginate" : {
					"sPrevious" : "上一页",
					"sNext" : "下一页"
				}
			},
			"aaSorting" : [ [ 1, 'asc' ] ]
		});
		$(".form_datetime").datetimepicker({
			format : 'yyyy-mm-dd hh:ii'
		});
	});
	$('#dataTableMgt').click(function() {
		clearInterval(dashboardInterval);
		$('#dataMgt').siblings('li').removeClass('nav-active'); // 删除其他兄弟元素的样式
		$('#dataMgt').attr('class', 'menu-list nav-active'); // 添加当前元素的样式
		$(this).siblings('li').removeClass('active'); // 删除其他兄弟元素的样式
		$(this).attr('class', 'active'); // 添加当前元素的样式
		showData();
	});
	$('#indexPage').click();
});

var dashboardInterval;

/**
 * 显示dashboard
 */
function showDashboard() {
	$.ajax({
		type : "post",
		url : $('#dashboardForm').attr('action') + "dashboard",
		async : false,
		beforeSend : function() {
		},
		success : function(data) {
			$('#index-main-content').html(data);
			fetchEchartsUsedObj();// 抓取echarts展示用的数据
			getCPUUsage();// 获取CPU使用量
			getMemUsage();// 获取Memory使用量
			getDiskUsage();// 获取硬盘使用量
			dashboardInterval = setInterval(function() {
				fetchEchartsUsedObj();// 抓取echarts展示用的数据
				getCPUUsage();// 获取CPU使用量
				getMemUsage();// 获取Memory使用量
				getDiskUsage();// 获取硬盘使用量
			}, 10000);
		},
		error : function(request) {
			alert("dashboard请求失败！");
		}
	});
}

/**
 * 抓取echarts展示用的数据
 * 
 * @returns
 */
function fetchEchartsUsedObj() {
	$.ajax({
		type : "post",
		url : $('#dashboardForm').attr('action') + "echartsFetch",
		async : false,
		beforeSend : function() {
		},
		success : function(data) {
			setDashboardEcharts(data);// 设置echarts显示
		},
		error : function(request) {
			alert("dashboard请求失败！");
		}
	});
}

/**
 * 获取硬盘使用量
 * 
 * @returns
 */
function getDiskUsage() {
	$.ajax({
		type : "post",
		url : $('#dashboardForm').attr('action') + "getDiskUsage",
		async : false,
		beforeSend : function() {
		},
		success : function(data) {
			setDiskBoard(data);// 设置echarts显示
		},
		error : function(request) {
			alert("dashboard请求失败！");
		}
	});
}

/**
 * 获取CPU使用量
 * 
 * @returns
 */
function getCPUUsage() {
	$.ajax({
		type : "post",
		url : $('#dashboardForm').attr('action') + "getCPUUsage",
		async : false,
		beforeSend : function() {
		},
		success : function(data) {
			setCPUBoard(data);// 设置echarts显示
		},
		error : function(request) {
			alert("dashboard请求失败！");
		}
	});
}

/**
 * 获取内存使用率
 * 
 * @returns
 */
function getMemUsage() {
	$.ajax({
		type : "post",
		url : $('#dashboardForm').attr('action') + "getMemUsage",
		async : false,
		beforeSend : function() {
		},
		success : function(data) {
			setMemBoard(data);// 设置echarts显示
		},
		error : function(request) {
			alert("dashboard请求失败！");
		}
	});
}

/**
 * 设置日志大小柱状图
 * 
 * @param data
 * @returns
 */
function setDashboardEcharts(data) {
	var jobNames = new Array();
	for (var i = 0; i < data.length; i++) {
		jobNames.push(data[i].jobName);
	}

	var jobDescs = new Array();
	for (var i = 0; i < data.length; i++) {
		jobDescs.push(data[i].jobDescription);
	}

	var logFolderSizes = new Array();
	for (var i = 0; i < data.length; i++) {
		logFolderSizes.push(data[i].logFolderSize);
	}

	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('dashboardEcharts'));
	// 指定图表的配置项和数据
	var option = {
		title : {
			text : '作业日志文件监控',
			subtext : '总日志大小'
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '日志文件总大小' ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		grid : {
			left : '15%',
			bottom : '30%'
		},
		xAxis : [ {
			type : 'category',
			data : jobDescs,
			axisLabel : {
				interval : 0,
				rotate : 40
			}
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			name : '日志文件总大小',
			type : 'bar',
			data : logFolderSizes
		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	// myChart.clear();
	myChart.setOption(option, true);
}

/**
 * 设置磁盘使用量仪表盘
 * 
 * @param data
 * @returns
 */
function setDiskBoard(data) {
	var myChart = echarts.init(document.getElementById('diskBoard'));// 获取磁盘仪表盘div
	// 指定图表的配置项和数据
	var option = {
		title : {
			text : '磁盘监控', // 标题文本内容
		},
		toolbox : { // 可视化的工具箱
			show : true,
			feature : {
				restore : { // 重置
					show : true
				},
				saveAsImage : {// 保存图片
					show : true
				}
			}
		},
		tooltip : { // 弹窗组件
			formatter : "{a} <br/>{b} : {c}%"
		},
		series : [ {
			name : '业务指标',
			type : 'gauge',
			detail : {
				formatter : '{value}%'
			},
			data : [ {
				value : data,
				name : ''
			} ]
		} ]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}
/**
 * 设置CPU使用量仪表盘
 * 
 * @param data
 * @returns
 */
function setCPUBoard(data) {
	var myChart = echarts.init(document.getElementById('cpuBoard'));// 获取磁盘仪表盘div
	// 指定图表的配置项和数据
	var option = {
		title : {
			text : 'CPU监控', // 标题文本内容
		},
		toolbox : { // 可视化的工具箱
			show : true,
			feature : {
				restore : { // 重置
					show : true
				},
				saveAsImage : {// 保存图片
					show : true
				}
			}
		},
		tooltip : { // 弹窗组件
			formatter : "{a} <br/>{b} : {c}%"
		},
		series : [ {
			name : '业务指标',
			type : 'gauge',
			detail : {
				formatter : '{value}%'
			},
			data : [ {
				value : data,
				name : ''
			} ]
		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

/**
 * 设置Memory使用量仪表盘
 * 
 * @param data
 * @returns
 */
function setMemBoard(data) {
	var myChart = echarts.init(document.getElementById('memBoard'));// 获取磁盘仪表盘div
	// 指定图表的配置项和数据
	var option = {
		title : {
			text : 'Memory监控', // 标题文本内容
		},
		toolbox : { // 可视化的工具箱
			show : true,
			feature : {
				restore : { // 重置
					show : true
				},
				saveAsImage : {// 保存图片
					show : true
				}
			}
		},
		tooltip : { // 弹窗组件
			formatter : "{a} <br/>{b} : {c}%"
		},
		series : [ {
			name : '业务指标',
			type : 'gauge',
			detail : {
				formatter : '{value}%'
			},
			data : [ {
				value : data,
				name : ''
			} ]
		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}
//
///**
// * 显示硬盘用量仪表盘
// * 
// * @returns
// */
//function showDiskBoard() {
//	var myChart = echarts.init(document.getElementById('diskBoard'));
//	// 指定图表的配置项和数据
//	var option = {
//		title : {
//			text : '业务指标', // 标题文本内容
//		},
//		toolbox : { // 可视化的工具箱
//			show : true,
//			feature : {
//				restore : { // 重置
//					show : true
//				},
//				saveAsImage : {// 保存图片
//					show : true
//				}
//			}
//		},
//		tooltip : { // 弹窗组件
//			formatter : "{a} <br/>{b} : {c}%"
//		},
//		series : [ {
//			name : '业务指标',
//			type : 'gauge',
//			detail : {
//				formatter : '{value}%'
//			},
//			data : [ {
//				value : 45,
//				name : '完成率'
//			} ]
//		} ]
//
//	};
//
//	// 使用刚指定的配置项和数据显示图表。
//	myChart.setOption(option);
//}

// 显示作业调度表
function showJobSchedule(runStatus) {
	$.ajax({
		type : "post",
		url : $('#kettleForm').attr('action') + "list",
		data : {
			runStatus : runStatus
		},
		async : false,
		beforeSend : function() {
		},
		success : function(data) {
			$('#index-main-content').html(data);
		},
		error : function(request) {
			alert("kettle作业列表请求失败！");
		}
	});
}

// 显示日志表
function showJobLog() {
	$.ajax({
		type : "post",
		url : $('#joblogForm').attr('action') + "list",
		async : false,
		beforeSend : function() {
		},
		success : function(data) {
			$('#index-main-content').html(data);
		},
		error : function(request) {
			alert("作业监控页面请求失败！");
		}
	});
}

// 显示数据管理表
function showData() {
	$.ajax({
		type : "post",
		url : $('#tableDataForm').attr('action') + "getList",
		async : false,
		beforeSend : function() {
		},
		success : function(data) {
			$('#index-main-content').html(data);
			search();// 查询data数据
		},
		error : function(request) {
			alert("requesting failure！");
		}
	});
}

/**
 * dashboard跳转作业管理页面
 * 
 * @param Status
 *            作业状态
 * @returns
 */
function runningJobDashboardClick(Status) {
	$('#jobMgt').siblings('li').removeClass('nav-active'); // 删除其他兄弟元素的样式
	$('#jobMgt').attr('class', 'menu-list nav-active'); // 添加当前元素的样式
	$(this).siblings('li').removeClass('active'); // 删除其他兄弟元素的样式
	$(this).attr('class', 'active'); // 添加当前元素的样式
	showJobSchedule(Status);
	$("#txtJobName").focus();
	$("#txtRunStatus").val(Status);
	// Sort immediately with columns 0 and 1
	$('#dynamic-table').dataTable({
		"oLanguage" : {
			"sSearch" : "搜索：",
			"sLengthMenu" : "显示 _MENU_ 项结果",
			"sInfo" : "显示第 _START_ 至第 _END_ 项结果,共 _TOTAL_ 项",
			"oPaginate" : {
				"sPrevious" : "上一页",
				"sNext" : "下一页"
			}
		},
		"aaSorting" : [ [ 2, 'asc' ] ]
	});
}
