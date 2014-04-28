if (typeof jQuery !== 'undefined') {
	(function ($) {
		$('#spinner').ajaxStart(function () {
			$(this).fadeIn();
		}).ajaxStop(function () {
			$(this).fadeOut();
		});
	})(jQuery);
}

function markItemAsRead(itemId, fetchCount) {
	var request = $.ajax({
		url: "user/changeItemState",
		type: "POST",
		data: { id: itemId, count: fetchCount },
		dataType: "html"
	});
//	request.complete(function (XMLHttpRequest, textStatus) {
//		console.log('complete...')
//		console.log(XMLHttpRequest)
//		console.log(textStatus)
//	});
//	request.done(function (msg) {
//		console.log('done...')
//		console.log(msg)
//	});
	request.success(function (data, textStatus) {
		console.log('success...')
		$('#unreadItems').html(data);
		console.log(textStatus)
	});
	request.error(function (XMLHttpRequest, textStatus, errorThrown) {
		alert('Error occurred while processing your request! Error Code: ' + XMLHttpRequest.status);
		console.log('error...')
		console.log(XMLHttpRequest)
		console.log(textStatus)
		console.log(errorThrown)
	});
}


function updateSubscription(topicId, subscribed) {
	var myVar = document.getElementById('href_'+topicId)
	var request = $.ajax({
		url: "invitation/updateSubscription",
		type: "POST",
		data: { id: topicId, subscribed: subscribed },
		dataType: "html"
	});
//	request.complete(function (XMLHttpRequest, textStatus) {
//		console.log('complete...')
//		console.log(XMLHttpRequest)
//		console.log(textStatus)
//	});
//	request.done(function (msg) {
//		console.log('done...')
//		console.log(msg)
//	});
	request.success(function (data, textStatus) {
		console.log('success...')
		var html = "<a href='javascript:void(0)' id='href_'"+topicId;
		html += "onclick='return updateSubscription("+topicId+", "+subscribed+")'>";
		if(data == "1"){
			html += "<img src='images/skin/subscribe.gif' alt='Subscribe' height=16 width=16> </a>"
		}else{
			html += "<img src='images/skin/unsubscribe.gif' alt='Unsubscribe' height=16 width=16> </a>"
		}
		$(myVar).html(html);
		console.log(textStatus)
	});
	request.error(function (XMLHttpRequest, textStatus, errorThrown) {
		alert('Error occurred while processing your request! Error Code: ' + XMLHttpRequest.status);
		console.log('error...')
		console.log(XMLHttpRequest)
		console.log(textStatus)
		console.log(errorThrown)
	});
}

function searchAndDisplayTopic(obj, evt) {

//	evt ? (evt.which ? evt.which : evt.keyCode) : event.keyCode;
	var keyCode = +(evt.which || evt.keyCode || event.keyCode);
	if (keyCode !== 13) {
		return true;
	}

	var request = $.ajax({
		url: "user/searchTopic",
		type: "POST",
		data: { searchString: obj.value, scope: 'Public' },
		dataType: "html"
	});
//	request.complete(function (XMLHttpRequest, textStatus) {
//		console.log('complete...')
//		console.log(XMLHttpRequest)
//		console.log(textStatus)
//	});
//	request.done(function (msg) {
//		console.log('done...')
//		console.log(msg)
//	});
	request.success(function (data, textStatus) {
		console.log('success...')
		$('#dashboardTabs').html(data);
		console.log(textStatus)
	});
	request.error(function (XMLHttpRequest, textStatus, errorThrown) {
		alert('Error occurred while processing your request! Error Code: ' + XMLHttpRequest.status);
		console.log('error...')
		console.log(XMLHttpRequest)
		console.log(textStatus)
		console.log(errorThrown)
	});
	return false;
}

function updateRating(id, rating) {

	var request = $.ajax({
		url: "user/updateRatings",
		type: "POST",
		data: { id: id, rating: rating*2 },
		dataType: "html"
	});
//	request.complete(function (XMLHttpRequest, textStatus) {
//		console.log('complete...')
//		console.log(XMLHttpRequest)
//		console.log(textStatus)
//	});
//	request.done(function (msg) {
//		console.log('done...')
//		console.log(msg)
//	});
	request.success(function (data, textStatus) {
		console.log('success...')
//		$('#dashboardTabs').html(data);
		console.log(textStatus)
	});
	request.error(function (XMLHttpRequest, textStatus, errorThrown) {
		alert('Error occurred while processing your request! Error Code: ' + XMLHttpRequest.status);
		console.log('error...')
		console.log(XMLHttpRequest)
		console.log(textStatus)
		console.log(errorThrown)
	});
	return false;
}