function sleep(millis) {
	var njf1 = njen(this, arguments, "millis");
	nj: while (1) {
		try {
			switch (njf1.cp) {
			case 0:
				njf1._notifier = NjsRuntime.createNotifier();
				setTimeout(njf1._notifier, njf1._millis);
				njf1.cp = 1;
				njf1._notifier.wait(njf1);
				return;
			case 1:
				break nj;
			}
		} catch (ex) {
			if (!njf1.except(ex, 1))
				return;
		}
	}
	njf1.pf();
}

/**
 * 将form转为AJAX提交，默认是json格式
 * 
 * @param formName
 * @param functionName
 * @returns
 */
function ajaxSubmit(formName, functionName) {
	ajaxSubmit(formName, functionName, "json");
}

/**
 * 将form转为AJAX提交
 * 
 * @param formName
 * @param functionName
 * @param dataType
 * @returns
 */
function ajaxSubmit(formName, functionName, dataType) {
	var dataPara = getFormJson(formName);
	$.ajax({
		url : $(formName).attr('action'),
		type : $(formName).method,
		data : dataPara,
		dataType : dataType,
		async : false,// 异步
		success : function(data) {
			functionName(formName, data);
		}
	});
}

/**
 * 将form中的值转换为键值对
 * 
 * @param formName
 * @returns
 */
function getFormJson(formName) {
	var o = {};
	var a = $(formName).serializeArray();
	$.each(a, function() {
		if (this.name == "password") {
			this.value = $.md5(this.value)
		}
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}
/**
 * 加载数据
 * 
 * @param formName
 * @param functionName
 * @param data
 * @returns
 */
function loadPagedData(formName, functionName, data) {
	/**
	 * 遍历返回的数据
	 */
	functionName(data);

	var pageCount = data.totalPage; // 取到pageCount的值
	var currentPage = data.currentPage; // 得到currentPage
	var options = {
		bootstrapMajorVersion : 3, // 版本
		currentPage : currentPage, // 当前页数
		totalPages : pageCount, // 总页数
		numberOfPages : 5,// 默认显示的页面数
		itemTexts : function(type, page, current) {
			switch (type) {
			case "first":
				return "首页";
			case "prev":
				return "上一页";
			case "next":
				return "下一页";
			case "last":
				return "末页";
			case "page":
				return page;
			}
		},
		/**
		 * 点击事件，用于通过Ajax来刷新整个list列表
		 */
		onPageClicked : function(event, originalEvent, type, page) {
			$.ajax({
				url : $(formName).attr("action"),
				type : "Post",
				dataType : "json",
				data : "curPage=" + page,
				success : function(data) {
					/**
					 * 遍历返回的数据
					 */
					functionName(data);
				}
			});
		}
	};
	$('#pagintor').bootstrapPaginator(options);
}