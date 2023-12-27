$(function() {
	$.ajax({
		type : "POST",
		url : '/kis/generateAutofillData',
		success : function(data) {
			var data = {
				english : data[0],
				chinese : data[1]
			};

			typeof $.typeahead === 'function' && $.typeahead({
				input : ".js-typeahead",
				minLength : 1,
				maxItem : 15,
				order : "asc",
				hint : true,
				group : {
					template : "{{group}} beers!"
				},
				maxItemPerGroup : 5,
				backdrop : {
					"background-color" : "#fff"
				},
				href : "/beers/{{group}}/{{display}}/",
				dropdownFilter : "所有分类",
				emptyTemplate : 'No result for "{{query}}"',
				source : {
					"English" : {
						data : data.english
					},
					"中文" : {
						data : data.chinese
					},
				},
				callback : {
					onClickAfter : function(node, a, item, event) {
						event.preventDefault();
					}
				},
				debug : true
			});
		},
		error : function(data) {
			console.log("获取generateAutofillData失败！");
		}
	});

});