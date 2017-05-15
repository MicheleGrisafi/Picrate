<?php
	$db_name = "fotografandodb";
	$mysql_username = "root";
	$mysql_password = "";
	$server_name = "localhost";
	define('MYSQL_ASSOC',MYSQLI_ASSOC);
	$mysqli = new mysqli($server_name,$mysql_username,$mysql_password,$db_name);
	define ('SITE_ROOT', realpath(dirname(__FILE__)));
?>