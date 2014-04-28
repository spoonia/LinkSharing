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
				Your new user account (${user.userId}) with the email address <b>
				<a href="mailto:${user.email}" target="_blank">${user.email}</a>
			</b> is now set up.<br><br>
				Please use the link below to activate your account.<br>
				<a href="${hex}"
				   target="_blank">Click here</a><br><br>
				You will be able to change your parameters (password, ...) once your account is activated.<br><br><br><br>
				If you have not requested the creation of a LinkShare account,
				or if you think this is an unauthorized use of your email address,
				please forward this email to <b>
				<a href="mailto:sandeep.poonia@intelligrape.com" target="_blank">accounts@linkshare.com</a></b>.
				<br><br><br><br><i>This email has been generated automatically. Please, do not reply.</i>
			</div>
		</td>
	</tr>
</table>
</body>