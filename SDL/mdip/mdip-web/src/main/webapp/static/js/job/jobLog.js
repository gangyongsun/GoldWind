var timer=null;
var logId=null;
var logName=null;
var interval=null;

$(document).ready(function() {
	$("#txtJobName").focus();
		
	$("#dynamic-table tr td:nth-child(2)").live("click",function(e){
		$("#refreshButton").text("关闭自动刷新");
		$("#myModal").modal();		
		logId = $(this).attr("value");
		getLogInfoById();
		
		var myChart = echarts.init(document.getElementById('echarts'));
		myChart.clear();
    });
	
	$("#refreshButton").live("click",function(e){
		if($("#refreshButton").text()=="关闭自动刷新"){
			if(interval!=null){
				clearInterval(interval);
			}
			$("#refreshButton").text("自动刷新");
		}else{
			getLogInfoById();
			$("#refreshButton").text("关闭自动刷新");
		}
	});
	
	$("#myModal").on("shown.bs.modal",function(){		
		var myChart = echarts.init(document.getElementById('echarts'));
	    myChart.resize();
	});
	
	$("#myModal").on("hide.bs.modal",function(){
		//监听模态框隐藏事件做一些动作
		if(interval!=null){
			clearInterval(interval);
		}		
	});
	
	$("#btnSearchLog").live("click",function(){
		searchJobLog();
	});
	
});

function searchJobLog() {
	var originalValue_jobName = $("#txtJobName").val();
	var originalValue_jobStatus = $("#jobStatus").val();
	var originalValue_startDatetime = $("#startDatetime").val();
	var originalValue_endDatetime = $("#endDatetime").val();

	$.ajax({
		type : "post",
		url : $("#jobLogForm").attr('action') + "/getJobLogsByConditions",
		data : {
			jobName : $("#txtJobName").val(),
			jobStatus : $("#jobStatus").val(),
			startDatetime : $("#startDatetime").val(),
			endDatetime : $("#endDatetime").val()
		},
		beforeSend : function() {
		},
		success : function(data) {
			$('#index-main-content').html(data);

			$("#txtJobName").val(originalValue_jobName);
			$("#jobStatus").val(originalValue_jobStatus);
			$("#startDatetime").val(originalValue_startDatetime);
			$("#endDatetime").val(originalValue_endDatetime);

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
			
			setEchartsData(data);
		},
		error : function(request) {
			$("#txtJobName").val(originalValue_jobName);
			$("#jobStatus").val(originalValue_jobStatus);
			$("#startDatetime").val(originalValue_startDatetime);
			$("#endDatetime").val(originalValue_endDatetime);
			alert("根据条件查询作业日志失败！");

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
		}
	});
}

function getLogInfoById(){
	getData();
	clearInterval(interval);
	interval=setInterval("getData()",5000);
}

function getData(){
	$.ajax({
		type : "post",
		url : $("#jobLogForm").attr('action') + "/getLogInfoById",
		data : {
			logId : logId
		},
		beforeSend : function() {
		},
		success : function(data) {
			logName = data.jobName;
			setEchartsData(data);
		},
		error : function(request) {
			alert(logId + "请求失败")
		}
	});
}

function setEchartsData(data){
		
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('echarts'));
    
    $("#echartsTitle").html("作业" +logName+"文件即时监控");
  
    //统计时间的间隔是5秒，所以速度要除以5秒
    var realkafkaSpeed = Math.ceil(data.realOutCount/5);

    var historykafkaSpeed = Math.ceil(data.historyOutCount/5);

    var dbSpeed = Math.ceil(data.dbOutCount/5);
    
    // 指定图表的配置项和数据
    var option = {
    	    title : {
    	        text: ''
    	    },
    	    tooltip : {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['文件总数','移入文件','移出文件','实时KafKa处理速度','历史KafKa处理速度']
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            mark : {show: true},
    	            dataView : {show: true, readOnly: false},
    	            magicType : {show: true, type: ['line', 'bar']},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    label:{
    	    	normal:{show: true,position: 'top'}
    	    },
    	    calculable : true,
    	    xAxis : [
    	        {
    	            type : 'category',
    	            data : ['实时文件目录','历史文件目录','完成文件目录']
    	        }
    	    ],
    	    yAxis : [
    	        {
    	            type : 'value',
    	            name : '文件数',
    	            axisLabel : {
    	                formatter: '{value} 个'
    	            }
    	        },
    	        {
    	        	type : 'value',
    	        	name : '速度',
    	        	axisLabel : {
    	        		formatter: '{value} 个/秒'
    	        	}
    	        }
    	    ],
    	    series : [
    	    	{
    	            name:'文件总数',
    	            type:'bar',
    	            data:[data.realSum,data.historySum,data.successFilesNum]    	           
    	        },
    	        {
    	            name:'移入文件',
    	            type:'bar',
    	            data:[data.realInCount,data.historyInCount,data.successFilesNum]    	           
    	        },    	        
    	        {
    	            name:'移出文件',
    	            type:'bar',
    	            data:[data.realOutCount,data.historyOutCount,0]    	           
    	        },    	        
    	        {
    	            name:'实时KafKa处理速度',
    	            type:'line',
    	            yAxisIndex: 1,
    	            symbol:'none',
    	            data:[realkafkaSpeed,realkafkaSpeed,realkafkaSpeed]    	           
    	        },    	        
    	        {
    	            name:'历史KafKa处理速度',
    	            type:'line',
    	            yAxisIndex: 1,
    	            symbol:'none',
    	            data:[historykafkaSpeed,historykafkaSpeed,historykafkaSpeed]    	           
    	        }
    	    ]
    	};
 
    // 使用刚指定的配置项和数据显示图表。
//    myChart.clear();
//    myChart.setOption(option,true);
    myChart.setOption(option);
}