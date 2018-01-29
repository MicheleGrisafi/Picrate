<?php 
	require("../connection.php");
	$username = null;
	$id = null;
	$money = null;
	$score = null;
	
	if(isset($_POST["id"]))
		$id = $_POST["id"];
	if(isset($_POST["username"]))
		$username = $_POST["username"];
	if(isset($_POST["money"]))
		$money = $_POST["money"];
	if(isset($_POST["score"]))
		$score = $_POST["score"];
        
    print_r($_POST);
	
	$query = $mysqli->prepare("UPDATE Utente SET username = ?, score = ?, money = ? WHERE IDUser = ?");
	$query->bind_param("siii",$username,$score,$money,$id);
	$result = $query->execute();
	if (!$result) {
		echo "null";
	}	
	$mysqli->close();
?>