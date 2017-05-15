<?php
	require("../connection.php");
	$username = null;
	$id = null;
	
	if(isset($_POST["id"]))
		$id = $_POST["id"];
	if(isset($_POST["username"]))
		$username = $_POST["username"];
	
	$query = $mysqli->prepare("UPDATE Utente SET username = ? WHERE IDUser = ?");
	$query->bind_param('si',$username,$id);
	$result = $query->execute();
	
	
	if (!$result) {
		echo "null";
		die();
	}else{
		echo $mysqli->affected_rows;
	}
	
	
	$mysqli->close();
?>