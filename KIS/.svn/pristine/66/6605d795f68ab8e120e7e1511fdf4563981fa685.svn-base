$(function() {
	$("[class=gradeB]").on("mouseenter",function() {
		var _this = this;
		$(this).popover({
			trigger : 'manual',
			placement : 'bottom', //placement of the popover. also can use top, bottom, left or right
			title : title_custom(this),
			html : 'true', //needed to show html of course
			content : content_custom(this),
			animation : false
		});
		$(this).popover("show");
		$(this).siblings(".popover").on("mouseleave",
			function() {
				$(_this).popover('hide');
			});
	}).on("mouseleave", function() {
		var _this = this;
		setTimeout(function() {
			if (!$(".popover:hover").length) {
				$(_this).popover("hide")
			}
		}, 100);
	});
	//设置title
	function title_custom(obj){
		var title = "分类："+$(obj)[0].id;
		return '<div style="text-align:center;font-size:12px;"> '+title+'</div>';
	}
	//设置图片
	function content_custom(obj){
		var imageURL = 'http://10.12.9.34/imgs/'+$(obj)[0].innerText+'.png';
		var content='<div id="popOverBox"><img src='+encodeURI(imageURL)+' width="251" height="201" /></div>';
		return content;
	}
});