<?php
	require("../connection.php");
	$id = "NULL";
	$money = "NULL";
	
	if(isset($_POST["id"]))
		$id = $_POST["id"];

	
	$query = $mysqli->prepare("SELECT money FROM utente WHERE IDuser = ?");
	$query->bind_param('i',$id);
	$result = $query->execute();
	
	if (!$result) {
		echo "null";
		die();
	}else{
		$row = $result->fetch_assoc();
		echo $row['money']);
	}

	$mysqli->close();
?>