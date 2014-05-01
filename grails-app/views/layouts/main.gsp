<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title><g:layoutTitle default="Grails"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
	<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
	<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'menu.css')}" type="text/css">
	<g:layoutHead/>

	<jq:resources/>
	<jqui:resources/>
	<jqval:resources/>
	<jqvalui:resources/>

	%{--<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>--}%
	%{--<script src="https://raw.github.com/danpalmer/jquery.complexify.js/master/jquery.complexify.js"></script>--}%
	<g:javascript library="application"/>
	%{--<g:javascript library="migrate"/>--}%
	<g:javascript library="raty"/>

	<r:layoutResources/>
	<script>
		$.datepicker.setDefaults({
			showOn: "both",
			buttonImageOnly: true,
			buttonImage: "images/skin/calendar.gif",
			buttonText: "Calendar",
			showAnim: "slideDown",
			changeMonth: true,
			changeYear: true,
			dateFormat: "MM dd, yy"
		});
	</script>
</head>

<body id="mainBody">
<div id="grailsLogo" role="banner">
	<img src="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}" alt="Grails"/>
	<span style="float: right;text-align: left; vertical-align: bottom">
		<g:if test="${session?.user}">
			<br><br><br>
			Welcome <strong>${session?.user?.userName}</strong>
			<br>
			Last Login Time: <ls:dateFormatter date="${session?.user?.lastLoginTime}" format="dd-MM-yyyy HH:mm">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ls:dateFormatter>
		</g:if>
	</span>
</div>
<g:layoutBody/>

<div id="mainFooter">
	<div style='height:70px; width: 100%; background: url("${resource(dir: 'images', file: 'footer.png')}") #ffffff'></div>
	<div id="spinner" class="spinner" style="display:none;">
		<g:message code="spinner.alt" default="Loading&hellip;"/>
	</div>
</div>
<r:layoutResources/>
</body>
</html>