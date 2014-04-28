<style type="text/css">
body, td {
	font-family: arial, sans-serif;
	font-size: 13px
}

a:link, a:active {
	color: #1155CC;
	text-decoration: none
}

a:hover {
	text-decoration: underline;
	cursor: pointer
}

a:visited {
	color: #6611CC
}

img {
	border: 0px
}

pre {
	white-space: pre;
	white-space: -moz-pre-wrap;
	white-space: -o-pre-wrap;
	white-space: pre-wrap;
	word-wrap: break-word;
	max-width: 800px;
	overflow: auto;
}
</style>

<body>
<table width=100% cellpadding=12 cellspacing=0 border=0>
	<tr>
		<td>
			<div style="overflow: hidden;">
				Hi ${user.userName},<br><br>
				You have requested to have your password reset for your account
				from [${clientIP}]<br><br>
				Please use below details to login to your account.<br>
				<strong>UserId</strong>-${user.userId}<br>
				<strong>Email-Id</strong>-${user.email}<br>
				<strong>Password</strong>-${user.plainPassword}<br>
				<br><br>
				Change your password once you login.<br><br><br><br>
				If you have not made this request,
				or if you think this is an unauthorized use of your email address,
				please forward this email to <b>
				<a href="mailto:sandeep.poonia@intelligrape.com" target="_blank">accounts@linkshare.com</a></b>.
				<br><br><br><br><i>This email has been generated automatically. Please, do not reply.</i>
			</div>
		</td>
	</tr>
</table>
</body>