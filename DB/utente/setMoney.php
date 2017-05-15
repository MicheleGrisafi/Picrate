<?php
	require("../connection.php");
	$id = "NULL";
	$money = "NULL";
	$increment = true;
	
	if(isset($_POST["id"]))
		$id = $_POST["id"];
	if(isset($_POST["money"]))
		$money = $_POST["money"];
	if(isset($_POST["increment"])){
		if ($_POST["increment"] == "true")
			$increment = true;
		else
			$increment = false;
	}
		
	
	
	if (increment)
		$query = $mysqli->prepare("UPDATE Utente SET money = money + ? WHERE IDUser = ?");
	else
		$query = $mysqli->prepare("UPDATE Utente SET money = ? WHERE IDUser = ?");
	
	$query->bind_param('ii',$money,$id);
	$result = $query->execute();
	
	if (!$result) {
		echo "null";
		die();
	}else{
		echo $mysqli->affected_rows;
	}
	
	$mysqli->close();
?>