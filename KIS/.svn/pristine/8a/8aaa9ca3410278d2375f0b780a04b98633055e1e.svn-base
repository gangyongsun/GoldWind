$(function() {
	$("#period").change(function () {
		lineStackDataRequest($("#period").val());//折线数据图
    });
	
	termTypeSummary();//术语分类统计饼图
	termTypeClickSummary(3);//术语点击量环形图
	
	lineStackDataRequest("recent30");//折线数据图
	
//	depNameDescTablePage();//部门访问人数数据图
//	empDescTablePage();//个人访问次数记录分页

//	$("#deptTab2").click(function() {
//		deptAccessEmpNumDataRequest();//部门访问人数数据图
//	});

//	$("#empTab2").click(function() {
//		empAccessCountDataRequest();//个人访问次数数据图
//	});
	depNameDescPie();
	empDescPie();
})


/**
 * 部门访问人数pie
 * 
 * @returns
 */
function depNameDescPie() {
	$.ajax({
		type : "POST",
		url : '/console/depNameDescPie',
		success : function(data) {
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
			init_pieChart(keyArray, keyValueMapJsonArray,"deptDesc-pie-chart");//初始化饼图
		},
		error : function(data) {
			console.log("条形图初始化失败!");
		}
	});
}


/**
 * 访问人数玫瑰图
 * 
 * @returns
 */
function empDescPie(){
	$.ajax({
		type : "POST",
		url : '/console/empDescPie',
		success : function(data) {
//			console.log(data);
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
//			console.log(keyArray);
//			console.log(keyValueMapJsonArray);
			init_rose(keyArray, keyValueMapJsonArray,"empDesc-pie-chart");//初始化饼图
		},
		error : function(data) {
			console.log("empDesc饼图初始化失败!");
		}
	});
}

/**
 * 术语分类统计饼图
 * @returns
 */
function termTypeSummary(){
	$.ajax({
		type : "POST",
		url : '/kis/quantatySummary',
		success : function(data) {
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
			init_pieChart(keyArray, keyValueMapJsonArray,"echarts-pie-chart");//初始化饼图
		},
		error : function(data) {
			console.log("饼图初始化失败!");
		}
	});
}

/**
 * 术语点击量图
 * 
 * @returns
 */
function termTypeClickSummary(level){
	$.ajax({
		type : "POST",
		url : '/kis/accessSummary',
		data : {
			classificationLevel : level
		},
		success : function(data) {
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
//			init_ring(keyArray, keyValueMapJsonArray,"echarts-funnel-chart");//初始化漏斗图
			init_rose(keyArray, keyValueMapJsonArray,"echarts-funnel-chart");//初始化漏斗图
		},
		error : function(data) {
			console.log("点击量环形图初始化失败!");
		}
	});
}

/**
 * 个人访问次数数据图
 * 
 * @returns
 */
function empAccessCountDataRequest() {
	$.ajax({
		type : "POST",
		url : '/console/empDescList',
		success : function(data) {
			var empArray = [];
			var empAccessCountArray = [];
			for ( var i in data) {
				empArray.push(data[i].userName);
				empAccessCountArray.push(data[i].accessCount);
				if (i > 8)
					break;
			}
			init_columnchart(empArray, empAccessCountArray, "empDescBar");
		},
		error : function(data) {
			console.log("条形图初始化失败!");
		}
	});
}

/**
 * 部门访问人数数据图
 * 
 * @returns
 */
function deptAccessEmpNumDataRequest() {
	$.ajax({
		type : "POST",
		url : '/console/depNameDescList',
		success : function(data) {
			var deptArray = [];
			var deptEmpNumArray = [];
			for ( var i in data) {
				deptArray.push(data[i].deptName);
				deptEmpNumArray.push(data[i].empNum);
				if (i > 3)
					break;
			}
			init_barchart(deptArray, deptEmpNumArray, "depNameDescBar");
		},
		error : function(data) {
			console.log("条形图初始化失败!");
		}
	});
}

/**
 * 折线数据图
 * 
 * @returns
 */
function lineStackDataRequest(period) {
	$.ajax({
		type : "POST",
		url : '/console/accessTendency',
		data : {
			period : period
		},
		success : function(data) {
			var dateArray = [];
			var accessCountArray = [];
			var accessEmpCountArray = [];
			for ( var i in data) {
				dateArray.push(data[i].accessDate);
				accessCountArray.push(data[i].accessCount);
				accessEmpCountArray.push(data[i].accessEmpCount);
			}
			init_lineStack(dateArray, accessCountArray, accessEmpCountArray);// 初始化折线图
		},
		error : function(data) {
			console.log("折线图初始化失败!");
		}
	});
}

/**
 * 部门访问人数记录分页
 * 
 * @returns
 */
function depNameDescTablePage() {
	var t = $("#depNameDescTable");
	t.bootstrapTable('destroy');
	var t = t.bootstrapTable({
		url : '/console/depNameDescPage',
		method : 'get',
		dataType : "json",
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		undefinedText : "空",// 当数据为undefined时显示的字符
		pagination : true, // 启用分页
		pageNumber : 1,// 分页首页页码
		pageSize : 5,// 分页页面数据条数
		pageList : [ 5, 10, 20 ],// 设置可供选择的页面数据条数；设置为All则显示所有记录
		paginationFirstText : "首页",// 指定“首页”按钮的图标或文字
		paginationPreText : '上一页',// 指定“上一页”按钮的图标或文字
		paginationNextText : '下一页',// 指定“下一页”按钮的图标或文字
		paginationLastText : "末页",// 指定“末页”按钮的图标或文字
		paginationLoop : false,// 不循环
		data_local : "zh-US",// 表格汉化
		sidePagination : "server", // 服务端处理分页
		queryParamsType : '',// 默认值为'limit',在默认情况下传给服务端的参数为：offset,limit,sort;设置为'',在这种情况下传给服务器的参数为：pageSize,pageNumber
		idField : "id",// 指定主键列
		columns : [ {
			title : '二级部门名称',
			field : 'centerTxt',
			align : 'left',
			valign : 'middle'
		},{
			title : '三级部门名称',
			field : 'deptName',
			align : 'left',
			valign : 'middle'
		}, {
			title : '访问人数',
			field : 'empNum',
			align : 'left',
			valign : 'middle'
		} ]
	});
	t.on('load-success.bs.table', function(data) {// table加载成功后的监听函数
		console.log("depNameDescTable load console success");
		$(".pull-right").css("display", "block");
	});
}

/**
 * 个人访问次数记录分页
 * 
 * @returns
 */
function empDescTablePage() {
	var t = $("#empDescTable");
	t.bootstrapTable('destroy');
	var t = t.bootstrapTable({
		url : '/console/empDescPage',
		method : 'get',
		dataType : "json",
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		undefinedText : "空",// 当数据为undefined时显示的字符
		pagination : true, // 启用分页
		pageNumber : 1,// 分页首页页码
		pageSize : 5,// 分页页面数据条数
		pageList : [ 5, 10, 20 ],// 设置可供选择的页面数据条数；设置为All则显示所有记录
		paginationFirstText : "首页",// 指定“首页”按钮的图标或文字
		paginationPreText : '上一页',// 指定“上一页”按钮的图标或文字
		paginationNextText : '下一页',// 指定“下一页”按钮的图标或文字
		paginationLastText : "末页",// 指定“末页”按钮的图标或文字
		paginationLoop : false,// 不循环
		data_local : "zh-US",// 表格汉化
		sidePagination : "server", // 服务端处理分页
		queryParamsType : '',// 默认值为'limit',在默认情况下传给服务端的参数为：offset,limit,sort;设置为'',在这种情况下传给服务器的参数为：pageSize,pageNumber
		idField : "id",// 指定主键列
		columns : [ {
			title : '员工姓名',
			field : 'userName',
			align : 'left',
			valign : 'middle'
		}, {
			title : '访问次数',
			field : 'accessCount',
			align : 'left',
			valign : 'middle'
		} ]
	});
	t.on('load-success.bs.table', function(data) {// table加载成功后的监听函数
		console.log("empDescTable load console success");
		$(".pull-right").css("display", "block");
	});
}

/**
 * 折线图
 * 
 * @param dateArray
 * @param accessCountArray
 * @param accessEmpCountArray
 * @returns
 */
function init_lineStack(dateArray, accessCountArray, accessEmpCountArray) {
	var dom = document.getElementById("line-stack-daily");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title : {
			text : ''
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '访问次数', '访问人数' ]
		},
		toolbox : {
			show : true,
			orient : 'horizontal',
			left : 'right',
			top : 'center',
			feature : {
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					title:"切换",
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
			left : '1%',
			right : '1%',
			bottom : '1%',
			containLabel : true
		},
		xAxis : {
			type : 'category',
			boundaryGap : false,
			data : dateArray,
			axisLabel : {
				interval : 0,
				rotate : 45
			}
		},
		yAxis : {
			type : 'value'
		},
		series : [ {
			name : '访问次数',
			type : 'line',
			stack : '总量',
			data : accessCountArray
		}, {
			name : '访问人数',
			type : 'line',
			stack : '总量',
			data : accessEmpCountArray
		} ]
	};
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
}

/**
 * 数条形图
 * 
 * @param keyArray
 * @param dataArray
 * @returns
 */
function init_barchart(keyArray, dataArray, barName) {
	var dom = document.getElementById(barName);
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	app.title = '部门访问人数条形图';

	option = {
		title : {
			text : '',
			subtext : ''
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
			data : [ '总访人数' ]
		},
		toolbox : {
			show : true,
			orient : 'horizontal',
			left : 'right',
			top : 'top',
			feature : {
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					title:"切换",
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
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : {
			type : 'value',
			boundaryGap : [ 0, 0.1 ]
		},
		yAxis : {
			type : 'category',
			data : keyArray,
			axisLabel : {
				interval : 0,
				rotate : 0
			}
		},
		series : [ {
			name : '总访人数',
			type : 'bar',
			data : dataArray
		} ]
	};
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
}

/**
 * 柱状图
 * 
 * @param keyArray
 * @param valueArray
 * @param name
 * @returns
 */
function init_columnchart(keyArray, valueArray, name) {
	var dom = document.getElementById(name);
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		color : [ '#006699', '#4cabce', '#e5323e', '#003366' ],
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
			data : [ '总访问次数' ]
		},
		toolbox : {
			show : true,
			orient : 'horizontal',
			left : 'right',
			top : 'top',
			feature : {
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					title:"切换",
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
		xAxis : [ {
			type : 'category',
			data : keyArray,
			axisTick : {
				show : false
			},
			axisLabel : {
				interval : 0,
				rotate : 0
			}
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			name : '总访问次数',
			type : 'bar',
			barGap : 0,
			data : valueArray
		} ]
	};
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
}

/**
 * 饼图
 */
function init_pieChart(keyData, displayData,name) {
	var pieChart = echarts.init(document.getElementById(name));
	var pieoption = {
		title : {
//			text : '术语比例',
			subtext : '',
			x : 'center',
			textStyle : {
				color : "#07a2a4",
				fontSize : 14,
			},
			subtextStyle: {
	            color: '#2ec7c9',
	            fontSize: 11
	        },
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a}:{d}%"
		},
		color : [ "#2ec7c9", "#b6a2de", "#5ab1ef", "#ffb980", "#d87a80",  "#8d98b3", "#e5cf0d",  "#97b552", "#95706d",  "#dc69aa", "#07a2a4",  "#9a7fd1",  "#588dd5",  "#f5994e", "#c05050", "#59678c",  "#c9ab00",  "#7eb00a", "#6f5553", "#c14089"], // 手动设置每个图例的颜色
		legend : {
			orient : 'vertical',
			width : 40, // 图行例组件的宽度,默认自适应
			x : 'left',
			itemWidth : 10, // 图例标记的图形宽度
			itemHeight : 10, // 图例标记的图形高度
			data : keyData
		},
		//calculable : true,
		series : [ {
			name : '占比',
			type : 'pie',
			radius : '50%',
			center : [ '50%', '50%' ],
			data : displayData
		} ]
	};
	pieChart.setOption(pieoption);
	$(window).resize(pieChart.resize);
}

/**
 * 环形图
 * 
 * @param keyData
 * @param displayData
 * @returns
 */
function init_ring(keyData, displayData,name){
	var myChart = echarts.init(document.getElementById(name));
	// 指定图表的配置项和数据
	var option = {
		title : {// 标题组件
//			text : '术语点击量',
			x : 'center',// 标题的位置 默认是left，其余还有center、right属性
			textStyle : {
				color : "#07a2a4",
				fontSize : 14,
			}
		},
		tooltip : { // 提示框组件
			trigger : 'item', // 触发类型(饼状图片就是用这个)
			formatter : "{a}: {c} ({d}%)" // 提示框浮层内容格式器
		},
		color : [ "#2ec7c9", "#b6a2de", "#5ab1ef", "#ffb980", "#d87a80",  "#8d98b3", "#e5cf0d",  "#97b552", "#95706d",  "#dc69aa", "#07a2a4",  "#9a7fd1",  "#588dd5",  "#f5994e", "#c05050", "#59678c",  "#c9ab00",  "#7eb00a", "#6f5553", "#c14089"], // 手动设置每个图例的颜色
		legend : { // 图例组件
			// right:100, //图例组件离右边的距离
			orient : 'vertical', // 布局 纵向布局 图例标记居文字的左边 vertical则反之
			width : 40, // 图行例组件的宽度,默认自适应
			x : 'right', // 图例显示在右边
			itemWidth : 10, // 图例标记的图形宽度
			itemHeight : 10, // 图例标记的图形高度
			data : keyData
		},
		series : [ // 系列列表
		{
			name : '点击量', // 系列名称
			type : 'pie', // 类型 pie表示饼图
			center : [ '40%', '70%' ], // 设置饼的原心坐标 不设置就会默认在中心的位置
			radius : [ '40%', '55%' ], // 饼图的半径,第一项是内半径,第二项是外半径,内半径为0就是真的饼,不是环形
			itemStyle : { // 图形样式
				emphasis : { // normal 是图形在默认状态下的样式；emphasis是图形在高亮状态下的样式，比如在鼠标悬浮或者图例联动高亮时。
					label : { // 饼图图形上的文本标签
						show : false// 平常不显示
					},
					labelLine : { // 标签的视觉引导线样式
						show : false// 平常不显示
					}
				}
			},
			data :displayData
		}]
	};
	myChart.setOption(option);
	$(window).resize(myChart.resize);
}

/**
 * 玫瑰图
 * 
 * @returns
 */
function init_rose(keyData, displayData,name){
	var dom = document.getElementById(name);
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
//	    title : {
//	        text: '南丁格尔玫瑰图',
//	        subtext: '纯属虚构',
//	        x:'center'
//	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	    	orient : 'vertical', // 布局 纵向布局 图例标记居文字的左边 vertical则反之
	    	width : 40, // 图行例组件的宽度,默认自适应
			x : 'right', // 图例显示在右边
			itemWidth : 10, // 图例标记的图形宽度
			itemHeight : 10, // 图例标记的图形高度
	        y : 'bottom',
	        data:keyData
	    },
//	    toolbox: {
//	        show : true,
//	        feature : {
//	            dataView : {show: true, readOnly: false},
//	            magicType : {
//	                show: true,
//	                type: ['pie', 'funnel']
//	            },
//	            restore : {show: true},
//	            saveAsImage : {show: true}
//	        }
//	    },
	    calculable : true,
	    series : [
	        {
//	            name:'面积模式',
	            type:'pie',
	            radius : ['30%', '70%'],
	            center : ['35%', '50%'],
	            roseType : 'area',
	            data:displayData
	        }
	    ]
	};
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	}
}