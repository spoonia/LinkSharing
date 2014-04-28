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
				Dear ${user.userName},<br><br>
				Your friend, ${user.userName} with email address <b>
				<a href="mailto:${user.email}"
				   target="_blank">${user.email}</a>
			</b> has asked you to subscribe to the below topic.<br>
				<strong>Topic</strong>:${topic.topicName}<br><br>
				<a href="${acceptLink}" target="_blank">
					Click here
				</a> to subscribe.<br><br>
				<a href="${rejectLink}" target="_blank">
					Click here
				</a> to reject the invitation.
				<br><br><br><br><i>This email has been generated automatically. Please, do not reply.</i>
			</div>
		</td>
	</tr>
</table>
</body>
