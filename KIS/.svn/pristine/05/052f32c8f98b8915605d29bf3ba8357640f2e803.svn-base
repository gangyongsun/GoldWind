$(function() {
	termTypeSummary();//术语分类统计饼图
	termTypeClickSummary(2);//术语点击量环形图
});

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
 * 术语点击量环形图
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
			init_ring(keyArray, keyValueMapJsonArray,"echarts-funnel-chart");//初始化漏斗图
		},
		error : function(data) {
			console.log("点击量环形图初始化失败!");
		}
	});
}

/**
 * 初始化饼图
 */
function init_pieChart(keyData, displayData) {
	var pieChart = echarts.init(document.getElementById("echarts-pie-chart"));
	var pieoption = {
		title : {
			text : '术语比例',
			subtext : '点击查看术语列表',
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
			center : [ '50%', '70%' ],
			data : displayData
		} ]
	};
	pieChart.setOption(pieoption);
	$(window).resize(pieChart.resize);
	pieChart.on('click', function (param) {
		var obj = {value:param.name};
		searchTerminology(obj);
	});
}

/**
 * 环形图
 * 
 * @param keyData
 * @param displayData
 * @returns
 */
function init_ring(keyData, displayData){
	var myChart = echarts.init(document.getElementById("echarts-funnel-chart"));
	// 指定图表的配置项和数据
	var option = {
		title : {// 标题组件
			text : '术语点击量',
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
			center : [ '50%', '70%' ], // 设置饼的原心坐标 不设置就会默认在中心的位置
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
	myChart.on('click', function (param) {
		var obj = {value:param.name};
		searchTerminology(obj);
	});
}