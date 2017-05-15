<?php 
	require("../connection.php");
	$username = null;
	$googleKey = null;
	$email = null;
	$money = null;
	$score = null;
	
	if(isset($_POST["email"]))
		$email = $_POST["email"];
	if(isset($_POST["googlekey"]))
		$googleKey = $_POST["googlekey"];
	if(isset($_POST["username"]))
		$username = $_POST["username"];
	if(isset($_POST["money"]))
		$money = $_POST["money"];
	if(isset($_POST["score"]))
		$score = $_POST["score"];
	
	$query = $mysqli->prepare("INSERT INTO Utente(username,googleKey,mail,score,money) VALUES(?,?,?,?,?)");
	$query->bind_param('sssii',$username,$googleKey,$email,$score,$money);
	$result = $query->execute();
	
	if (!$result) {
		echo "null";
		die();
	}	
	if ($mysqli->affected_rows > 0){
		echo $mysqli->insert_id;
	}
	else{
		echo "null";
	}
	
	$mysqli->close();
?>