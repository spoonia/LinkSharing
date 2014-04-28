<%--
  Created by IntelliJ IDEA.
  User: sandeep
  Date: 18/4/14
  Time: 1:14 PM
--%>
<html>
<head>
	<title><g:layoutTitle default="HomePage"/></title>
	<meta name="layout" content="main"/>
</head>

<body>
<g:if test="${session?.user}">
	<ul class="sky-mega-menu sky-mega-menu-anim-flip">
		<li>
			<g:link controller="user" action="dashboard" style="text-decoration:none">
				<g:img dir="images/skin" file="house.png">Home</g:img>
			</g:link>
		</li>
		<g:if test="${session.user.email.equalsIgnoreCase('admin@intelligrape.com')}">
			<li>
				<g:link controller="admin" action="stats" style="text-decoration:none">Statistics</g:link>
			</li>
		</g:if>

		<li aria-haspopup="true">
			<a href="#">
				<i class="fa"></i>Actions<i class="fa fa-indicator fa-chevron-down"></i>
			</a>

			<div class="grid-container3">
				<ul>
					<li>
						<g:link controller="topic" action="index" style="text-decoration:none">
							<g:img dir="images/skin" file="topic.png"/> &nbsp;My Topics
						</g:link>
					</li>

					<li aria-haspopup="true">
						<a href="#">
							<g:img dir="images/skin" file="resource.jpg"/>
							<i class="fa fa-indicator fa-chevron-right"></i>&nbsp;Resources
						</a>

						<div class="grid-container3">
							<ul>
								<li aria-haspopup="true">
									<a href="#">
										<g:img dir="images/skin" file="url.gif"/> &nbsp;Link Resource
									</a>
								</li>
								<li>
									<g:link controller="documentResource" action="index" style="text-decoration:none">
										<g:img dir="images/skin" file="resource.jpg"/> &nbsp;Document Resource
									</g:link>
								</li>
							</ul>
						</div>
					</li>

					<li>
						<g:link class="search" action="index" style="display: inline">
							<g:img dir="images/skin" file="search.png"/> &nbsp;Search Topics
						</g:link>
					</li>
					<li>
						<g:link controller="topic" action="index" style="text-decoration:none">
							<g:img dir="images/skin" file="subscribe.gif"/> &nbsp;Subscriptions
						</g:link>
					</li>
					<li aria-haspopup="true">
						<a href="#">
							<g:img dir="images/skin" file="invite.png"/>
							<i class="fa fa-indicator fa-chevron-right"></i>&nbsp;Invitations
						</a>

						<div class="grid-container3">
							<ul>
								<li aria-haspopup="true">
									<a href="#">
										<i class="fa"></i>Invitations Sent
									</a>
								</li>
								<li>
									<a href="#">
										<i class="fa"></i>Invitations Received
									</a>
								</li>
							</ul>
						</div>
					</li>
				</ul>
			</div>
		</li>

		<style>
		.searchField {
			text-decoration: none;
			margin: 0;
			padding: 0;
			background-image: linear-gradient(bottom, rgb(170, 170, 170) 11%, rgb(238, 238, 238) 77%);
			background-image: -o-linear-gradient(bottom, rgb(170, 170, 170) 11%, rgb(238, 238, 238) 77%);
			background-image: -moz-linear-gradient(bottom, rgb(170, 170, 170) 11%, rgb(238, 238, 238) 77%);
			background-image: -webkit-linear-gradient(bottom, rgb(170, 170, 170) 11%, rgb(238, 238, 238) 77%);
			background-image: -ms-linear-gradient(bottom, rgb(170, 170, 170) 11%, rgb(238, 238, 238) 77%);
			background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0.11, rgb(170, 170, 170)), color-stop(0.77, rgb(238, 238, 238)));
			border-radius: 5px;
			background: url(http://cssdeck.com/uploads/media/items/4/4yK4Jqj.jpg) 5px center no-repeat #FFF;
			border: 1px solid #AAA;
			color: #666;
			font-size: 11px;
			opacity: 0.6;
			outline: none;
			/*width: 80px;*/
			/* CSS3 */
			-webkit-transition: 0.3s;
			-moz-transition: 0.3s;
			-o-transition: 0.3s;
			transition: 0.3s;
		}

		.searchField :hover {
			opacity: 1
		}

		.searchField :focus {
			opacity: 1;
			width: 180px;
		}

		.searchField :focus ~ a {
			width: 95px
		}
		</style>

		<li class="right">
			<input type="text" class="searchField" style="margin: 0;padding: 0 0 0 20px;"
			       onkeypress="return searchAndDisplayTopic(this, event)" placeholder="Search Topic"
			       name="hello">
		</li>

		<li class="right">
			<g:link controller="user" action="forceChange" style="text-decoration:none">Change Password</g:link>
		</li>

		<li class="right">
			<g:link controller="login" action="logout" style="text-decoration:none">Logout</g:link>
		</li>
	</ul>

	<div style="line-height: 10px; ">&nbsp;</div>

		<g:pageProperty name="page.content"/>
</g:if>
</body>
</html>