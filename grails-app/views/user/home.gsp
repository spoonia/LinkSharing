<%--
  Created by IntelliJ IDEA.
  User: sandeep
  Date: 18/4/14
  Time: 1:14 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<g:applyLayout name="home">
	<html>
	<head>
		<title>Homepage</title>
	</head>

	<body>
	<content tag="content">
		<div id="dashboardTabs" style="border: 0px; border-radius: 0px;">
			<ul>
				<li><a href="#fragment-1"><span>What's Popular</span></a></li>
				<li><a href="#fragment-2"><span>My Topics</span></a></li>
				<li><a href="#fragment-3"><span>Subscribed Topics</span></a></li>
				<li><a href="#fragment-4"><span>Read Items</span></a></li>
				<li><a href="#fragment-5"><span>Unread Items</span></a></li>
			</ul>

			<div id="fragment-1">
				<ul style="border: 1px solid #dddddd;background: #dddddd">
					<li><a href="#fragment-1_0"><span>Topics</span></a></li>
					<li><a href="#fragment-1_1"><span>Items</span></a></li>
				</ul>

				<div id="fragment-1_0">
					<ls:mostSubscribedTopics/>
				</div>

				<div id="fragment-1_1">
					<ls:mostReadItems/>
				</div>
			</div>

			<div id="fragment-2">
				<ul>
					<li><a href="#fragment-2_0"><span>Template Rendering</span></a></li>
					<li><a href="#fragment-2_1"><span>Using TagLib</span></a></li>
				</ul>

				<div id="fragment-2_0">
					<g:render template="/user/UserHomeTemplate"
					          model="[id: 0, Title: 'Owned Topic List', ownedSubscriptionsList: ownedSubscriptionsList]"/>
				</div>

				<div id="fragment-2_1">
					<ls:ownedTopics/>
				</div>
			</div>

			<div id="fragment-3">
				<ul>
					<li><a href="#fragment-3_0"><span>Template Rendering</span></a></li>
					<li><a href="#fragment-3_1"><span>Using TagLib</span></a></li>
				</ul>

				<div id="fragment-3_0">
					<g:render template="/user/UserHomeTemplate"
					          model="[id: 1, Title: 'Subscribed Topic List', subscriptionsList: subscriptionsList]"/>
				</div>

				<div id="fragment-3_1">
					<ls:subscribedTopics/>
				</div>
			</div>

			<div id="fragment-4">
				<ul>
					<li><a href="#fragment-4_0"><span>Read Item List</span></a></li>
				</ul>

				<div id="fragment-4_0">
					<g:render template="/user/UserHomeTemplate"
					          model="[id: 2, Title: 'Read Item List', readUserResourceMappingList: readUserResourceMappingList]"/>
				</div>
			</div>

			<div id="fragment-5">
				<ul>
					<li><a href="#fragment-5_0"><span>Template Rendering</span></a></li>
					<li><a href="#fragment-5_1"><span>Using TagLib</span></a></li>
				</ul>

				<div id="fragment-5_0">
					<g:render template="/user/UserHomeTemplate"
					          model="[id: 3, Title: 'Unread Item List', unreadUserResourceMappingList: unreadUserResourceMappingList]"/>
				</div>

				<div id="fragment-5_1">
					<ls:unreadItems count="5"/>
				</div>
			</div>
		</div>
		<input style="visibility: hidden" id="hint" value="" type="text"/>
		<script>
			$("#dashboardTabs ul").css(
					{
						background: 'repeat-x scroll 50% top',
						border: '0px'
					});
			$("#dashboardTabs").tabs();
			$("#fragment-1").tabs();
			$("#fragment-2").tabs();
			$("#fragment-3").tabs();
			$("#fragment-4").tabs();
			$("#fragment-5").tabs();

			$(function () {
				$(".subscriptionRating").each(function () {
					var rating = +$(this).text()
					$(this).text('')
					$(this).raty({
						score: (rating / 2),
						half: true,
						starHalf: 'images/star-half-mono.png',
						target: "#hint",
						targetType: 'number',
						targetKeep: true
					});
					$(this).click(function () {
						updateRating($(this).attr('id'), $('#hint').val());
					});
				});
			});
		</script>

	</content>
	</body>
	</html>
</g:applyLayout>