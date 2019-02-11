<!DOCTYPE html>

<html lang="en">

<body>
	Date: ${time?date}
	<br>
	Time: ${time?time}
	<br>
	Message: ${message}


	<ul>
		<li>ok 200, user 1：<a href="user/1">user 1</a></li>
		<li>Error 500, user 0：<a href="user/0">user 0</a></li>
		<li>Error 404, a.txt：<a href="a.txt">a.txt</a></li>
		<li>Error 401, admin：<a href="admin">admin</a></li>
		<li>hello jsp：<a href="hello">hello jsp</a></li>
	</ul>
</body>

</html>