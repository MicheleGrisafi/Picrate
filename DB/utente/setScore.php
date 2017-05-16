<?php
	require("../connection.php");
	$id = "NULL";
	$score = "NULL";
	$increment = true;
	
	if(isset($_POST["id"]))
		$id = $_POST["id"];
	if(isset($_POST["score"]))
		$score = $_POST["score"];
	if(isset($_POST["increment"])){
		if ($_POST["increment"] == "true")
			$increment = true;
		else
			$increment = false;
	}
		
	if (increment)
		$query = $mysqli->prepare("UPDATE Utente SET score = score + ? WHERE IDUser = ?");
	else
		$query = $mysqli->prepare("UPDATE Utente SET score = ? WHERE IDUser = ?");
	
	$query->bind_param('ii',$score,$id);
	$result = $query->execute();
	
	if (!$result) {
		echo "null";
		die();
	}else{
		echo $mysqli->affected_rows;
	}
	
	$mysqli->close();
?>