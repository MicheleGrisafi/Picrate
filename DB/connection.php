<?php
	$db_name = "my_fotografandoapp";
	$mysql_username = "fotografandoapp";
	$mysql_password = "";
	$server_name = "localhost";
	define('MYSQL_ASSOC',MYSQLI_ASSOC);
	$mysqli = new mysqli($server_name,$mysql_username,$mysql_password,$db_name);
	define ('SITE_ROOT', realpath(dirname(__FILE__)));
	$mysqli->set_charset("utf8");
?>