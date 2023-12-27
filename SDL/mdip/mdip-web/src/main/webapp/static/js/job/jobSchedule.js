$(document).ready(function() {
	$("#txtJobName").focus();
});

/**
 * 查询job schedule
 */
function searchJobSchedule(flag) {
	if (flag) {
		var originalValue_txtJobName = $("#txtJobName").val();
		var originalValue_txtJobType = $("#txtJobType").val();
		var originalValue_txtRunStatus = $("#txtRunStatus").val();
	}
	$.ajax({
		type : "post",
		url : $('#kettleForm').attr('action') + "getJobsByConditions",
		data : {
			jobName : $("#txtJobName").val(),
			jobType : $("#txtJobType").val(),
			runStatus : $("#txtRunStatus").val()
		},
		beforeSend : function() {
		},
		success : function(data) {
			$('#index-main-content').html(data);

			$("#txtJobName").val(originalValue_txtJobName);
			$("#txtJobType").val(originalValue_txtJobType);
			$("#txtRunStatus").val(originalValue_txtRunStatus);

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
		},
		error : function(request) {
			$("#txtJobName").val(originalValue_txtJobName);
			$("#txtJobType").val(originalValue_txtJobType);
			$("#txtRunStatus").val(originalValue_txtRunStatus);

			alert("作业条件查询请求失败！");

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
	});
}

/**
 * 文件上传
 */
function uploadFile() {
	if ($("#inputJobName").val() == "" || $("#inputKettleFile").val() == "") {
		swal("作业名和作业", "不能为空，作业名称不能有中文、空格！", "warning");
	} else {
		var uploadForm = $('#uploadForm');
		uploadForm.ajaxSubmit({
			type : 'post',
			url : uploadForm.attr('action'),
			contentType : "multipart/form-data",
			beforeSend : function() {
			},
			success : function(data) {
				swal("Good job!", "作业文件上传成功!", "success");
				searchJobSchedule(false);
			},
			error : function(request) {
				swal("Bad job!", "作业文件上传失败!", "failed");
			}
		});
	}
}

/**
 * 作业信息加载
 */
function loadJobInfo() {
	var checkNum = $("input[name='checkbox']:checked").length;
	var obj = $("#jobInfoModifyHref");// 获取按钮对象
	if (checkNum > 1) {
		swal("只能对单个作业", "进行修改！", "warning");
		obj.setAttribute("href", "");// 置空链接
	} else if (checkNum < 1) {
		swal("请选择一条作业", "进行修改！", "info");
		obj.setAttribute("href", "");// 置空链接
	} else {
		$("input[name='checkbox']:checkbox:checked").each(function() {
			jobId = $(this).parent().next().text();
		});
		$.ajax({
			type : "post",
			url : obj.attr("toURL"),
			data : {
				jobId : jobId
			},
			dataType : 'text',
			beforeSend : function() {
			},
			success : function(data) {
				$('#propModifyDiv').replaceWith(data);
			},
			error : function(request) {
				swal("Oops...", "属性修改页面加载失败！", "error");
			}
		});
		obj.attr("href", "#jobInfoModifyModel");
	}
};

/**
 * 作业信息修改
 */
function updateJobInfo() {
	var paramSetForm = $('#jobInfoUpdateForm');
	var url = paramSetForm.attr("action");

	$.ajax({
		type : "post",
		url : url,
		data : {
			jobId : jobId,
			jobDescription : $('#jobDescription').val(),
			jobType : $('#jobType').val(),
			remarks : $('#remarks').val()
		},
		dataType : 'text',
		beforeSend : function() {
		},
		success : function(data) {
			searchJobSchedule(true);
			swal("OK", "属性修改成功！", "success");
		},
		error : function(request) {
			swal("Failure", "属性修改失败！", "error");
		}
	});
}

/**
 * 参数加载
 */
function loadParams() {
	var checkNum = $("input[name='checkbox']:checked").length;
	var obj = $("#paramSetHref");
	if (checkNum > 1) {
		swal("只能对单个作业", "进行参数设置！", "warning");
		obj.setAttribute("href", "");
	} else if (checkNum < 1) {
		swal("请选择一条作业", "进行参数设置！", "info");
		obj.setAttribute("href", "");
	} else {
		$("input[name='checkbox']:checkbox:checked").each(function() {
			jobId = $(this).parent().next().text();
		});
		jobFileDirId = document.getElementById(jobId).value;
		$.ajax({
			type : "post",
			url : obj.attr("toURL"),
			data : {
				jobFileDir : jobFileDirId
			},
			dataType : 'text',
			beforeSend : function() {
			},
			success : function(data) {
				$('#paramSetDiv').replaceWith(data);
			},
			error : function(request) {
				swal("Oops...", "参数加载页面加载失败！", "error");
			}
		});
		obj.attr("href", "#mySetModal");
	}
};

/**
 * 参数修改
 * 
 * @returns
 */
function setParams() {
	var paramSetForm = $('#paramSetForm');
	var url = paramSetForm.attr("action");

	var jobFileDirId = document.getElementById(jobId).value;// 配置文件路径
	var tableInfo = GetInfoFromTable("loadParamsTable");// table 所有值

	paramSetForm.ajaxSubmit({
		type : 'POST',
		url : url,
		data : {
			strParams : tableInfo,
			jobFileDirId : jobFileDirId
		},
		dataType : 'text',
		success : function(data) {
			swal("OK", "属性修改成功！", "success");
		},
		error : function(request) {
			swal("Failure", "属性修改失败！", "error");
		}
	});
}

// 获取某个table所有元素及值
// 具体的表格，我使用的是2列表格
function GetInfoFromTable(tableid) {
	var tableInfo = "";
	var innerText = "";
	var inputValue = "";
	var tableObj = document.getElementById(tableid);// 获取表格对象
	// 遍历Table的所有Row，因为我要去掉表头，所以i从1开始，即从第二行开始
	for (var i = 1; i < tableObj.rows.length; i++) {
		// 遍历Row中的每一列
		for (var j = 0; j < tableObj.rows[i].cells.length; j++) {
			if (j == 0) {
				innerText = tableObj.rows[i].cells[j].innerText;
				tableInfo += innerText + "::";
			}
			if (j == 1) {
				inputValue = tableObj.rows[i].cells[j]
						.getElementsByTagName("input")[0].value;
				tableInfo += inputValue;
			}
		}
		tableInfo += ";;";
	}
	return tableInfo;
}

// 复选框事件,全选、取消全选的事件
function selectAll() {
	if ($("#SelectAll").attr("checked")) {
		$(":checkbox").attr("checked", true);
	} else {
		$(":checkbox").attr("checked", false);
	}
}

// 子复选框的事件
function setSelectAll() {
	// 当没有选中某个子复选框时，SelectAll取消选中
	if (!$("#subcheck").checked) {
		$("#SelectAll").attr("checked", false);
	}
	var chsub = $("input[type='checkbox'][id='subcheck']").length; // 获取subcheck的个数
	var checkedsub = $("input[type='checkbox'][id='subcheck']:checked").length; // 获取选中的subcheck的个数
	if (checkedsub == chsub) {
		$("#SelectAll").attr("checked", true);
	}
}

/**
 * 启动作业
 * 
 * @returns
 */
function startJob() {
	var checkNum = $('input:checked').length;
	var status = "";// 存储选中作业的状态
	if (checkNum != 0) {
		$("input[name='checkbox']:checkbox:checked").each(
				function() {
					status += $(this).parent().next().next().next().next()
							.next().text()
							+ "&";
				});
		// 判断作业运行状态
		if (status.indexOf("运行中") != -1) {// !=-1含有
			swal("作业运行中...", "存在已运行状态的作业！", "warning")
		} else {
			swal({
				title : "确定启动该作业?",
				text : "作业启动后，Kettle作业将进入运行状态!",
				type : "info",
				showLoaderOnConfirm : true,
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "启动",
				cancelButtonText : '取消',
				closeOnConfirm : false
			}, function() {
				var jobs = "";
				$("input[name='checkbox']:checkbox:checked").each(function() {
					jobs += $(this).parent().next().text() + "&";
				});

				jobs = jobs.substring(0, jobs.length - 1);

				var url = $("#hrStart").attr("toURL");

				$.ajax({
					type : "post",
					url : url,
					data : {
						jobId : jobs
					},
					dataType : 'text',
					beforeSend : function() {
					},
					success : function(data) {
						// searchJobSchedule();
						$("input[name='checkbox']:checkbox:checked").each(
								function() {
									$(this).parent().next().next().next()
											.next().next().text("运行中");
								});
						swal("Started!", "作业启动成功！", "success");
						return true;
					}
				});
			});
		}
	} else {
		swal("请至少选择一个作业", "进行启动！", "info")
	}
	return false;
};

/**
 * 停止作业
 */
function stopJob() {
	var checkNum = $('input:checked').length;
	var status = "";// 存储选中作业的状态
	if (checkNum != 0) {
		$("input[name='checkbox']:checkbox:checked").each(
				function() {
					status += $(this).parent().next().next().next().next()
							.next().text()
							+ "&";
				});

		// 判断作业运行状态
		if (status.indexOf("停止的") != -1) {// !=-1含有
			swal("存在处于停止状态的作业", "请勿勾选停止作业！", "warning")
		} else {
			swal({
				title : "确定停止该作业?",
				text : "作业停止后，Kettle作业将停止运行!",
				type : "info",
				showLoaderOnConfirm : true,
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "停止",
				cancelButtonText : '取消',
				closeOnConfirm : false
			}, function() {
				var jobs = "";
				$("input[name='checkbox']:checkbox:checked").each(function() {
					jobs += $(this).parent().next().text() + "&";
				});
				jobs = jobs.substring(0, jobs.length - 1);
				var url = $("#hrStop").attr("toURL");
				$.ajax({
					type : "post",
					url : url,
					data : {
						jobId : jobs
					},
					dataType : 'text',
					beforeSend : function() {

					},
					success : function(data) {
						// searchJobSchedule();
						$("input[name='checkbox']:checkbox:checked").each(
								function() {
									$(this).parent().next().next().next()
											.next().next().text("停止的");
								});
						swal("Stopped!", "作业停止成功！", "success");
						return true;
					}
				});
			});
		}
	} else {
		swal("请至少选择一个作业", "进行停止！", "info")
	}
	return false;
};

/**
 * 删除作业
 * 
 * @returns
 */
function deleteJob() {
	var checkNum = $('input:checked').length;
	var status = "";// 存储选中作业的状态
	if (checkNum != 0) {
		$("input[name='checkbox']:checkbox:checked").each(
				function() {
					status += $(this).parent().next().next().next().next()
							.next().text()
							+ "&";
				});

		// 判断作业运行状态
		if (status.indexOf("启动中") != -1 || status.indexOf("已完成") != -1
				|| status.indexOf("运行中") != -1) {// !=-1含有
			swal("作业运行中...", "请先停止作业再删除！", "warning")
		} else {
			swal({
				title : "确定删除该作业?",
				text : "作业删除后，数据库相关信息和作业文件会同时被清除!",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "删除!",
				cancelButtonText : '取消',
				closeOnConfirm : false
			}, function() {
				var jobIds = "";
				var jobFileDirs = "";
				$("input[name='checkbox']:checkbox:checked").each(
						function() {
							jobId = $(this).parent().next().text();
							jobIds += jobId + "&";// job id以&分割
							jobFileDirs += jobId + "::"
									+ document.getElementById(jobId).value
									+ ";;";// job目录以::分割
						});
				jobIds = jobIds.substring(0, jobIds.length - 1);
				var url = $("#hrDelete").attr("toURL");
				$.ajax({
					type : "post",
					url : url,
					data : {
						jobIds : jobIds,
						jobFileDirs : jobFileDirs
					},
					dataType : 'text',
					success : function(data) {
						searchJobSchedule(true);
					}
				});
				swal("Deleted!", "作业文件删除成功！", "success");
			});
		}
	} else {
		swal("请至少选择一个作业", "进行删除！", "info")
	}
}

// 设置只读
function setInputReadOnly(id) {
	var flag = document.getElementById(id).readOnly;
	if (flag == false) {
		document.getElementById(id).readOnly = true;
	} else {
		document.getElementById(id).readOnly = false;
	}
}