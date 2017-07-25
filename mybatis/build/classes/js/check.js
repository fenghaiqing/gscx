
	$(document)
			.ready(
					function() {
						//alert("check");
						$("#username")
								.blur(
										function() {
											var url = 'http://localhost:8080/Hibernate5/register/check.do';
											var params = {
												username : $("#username").val(),
												password1 : $("#password1")
														.val()
											};
											$
													.post(
															url,
															params,
															function callback(
																	data) {
																if (data.reg == "allowed") {
																	$(
																			'#checkinfo')
																			.html(
																					"可以注册!");
																	$(
																			'#checkinfo')
																			.attr(
																					"style",
																					"color: blue");
																} else if (data.reg == "username_existed") {
																	$(
																			'#checkinfo')
																			.html(
																					"用户已存在，请用其它名字注册!");
																	$(
																			'#checkinfo')
																			.attr(
																					"style",
																					"color: red");
																} else if (data.reg == "username_needed") {
																	$(
																			'#checkinfo')
																			.html(
																					"用户名不能为空!");
																	$(
																			'#checkinfo')
																			.attr(
																					"style",
																					"color: red");
																}
															}, 'json');
										});
						$('#regform')
								.submit(
										function() {
											//alert("asd");
											$
													.ajax({
														url : "http://localhost:8080/Hibernate5/register/ajax.do",

														type : "POST",
														dataType : 'json',
														data : $('#regform')
																.serialize(),
														success : function(data) {
															if (data.reg == "username_needed") {
																$('#reginfo')
																		.html(
																				"错误：用户名为空，无法注册!");
																$('#reginfo')
																		.attr(
																				"style",
																				"color: red");
															} else if (data.reg == "username_existed") {
																$('#reginfo')
																		.html(
																				"错误：用户名已存在，注册失败!");
																$('#reginfo')
																		.attr(
																				"style",
																				"color: red");
															} else {
																$('#reginfo')
																		.html(
																				"提示：用户["
																						+ data.username
																						+ "] "
																						+ " 注册成功!");
																$('#reginfo')
																		.attr(
																				"style",
																				"color: blue");
																$('#regform')[0]
																		.reset();
																$('#checkinfo')
																		.html(
																				"");
																$('#age').val(
																		"");
															}
														},
														error : function() {
															alert("网络连接出错！");
														}
													});
											return false;
										});
					});
