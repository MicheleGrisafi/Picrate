<?php
	$db_name = "rasp0gris";
	$mysql_username = "grisafi";
	$mysql_password = "michele";
	$server_name = "www.xoft.cloud";
	define('MYSQL_ASSOC',MYSQLI_ASSOC);
	$mysqli = new mysqli($server_name,$mysql_username,$mysql_password,$db_name);
	define ('SITE_ROOT', realpath(dirname(__FILE__)));
	$mysqli->set_charset("utf8");
?>