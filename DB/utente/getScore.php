<?php
	require("../connection.php");
	$id = "NULL";
	$score = "NULL";
	
	if(isset($_POST["id"]))
		$id = $_POST["id"];

	$query = $mysqli->prepare("SELECT score FROM utente WHERE IDuser = ?");
	$query->bind_param('i',$id);
	$result = $query->execute();
	
	if (!$result) {
		echo "null";
		die();
	}else{
		$row = $result->fetch_assoc();
		echo $row['score']);
	}

	$mysqli->close();
?>