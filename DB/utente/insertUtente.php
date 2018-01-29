<?php 
	require("../connection.php");
    require("../googleAuth.php");
	$username = null;
	$googleKey = null;
	$email = null;
	
	if(isset($_POST["email"]))
		$email = $_POST["email"];
	if(isset($_POST["googlekey"]))
		$googleKey = $_POST["googlekey"];
	if(isset($_POST["username"]))
		$username = $_POST["username"];
	$auth = verifyToken($googleKey);
	if($auth == "error")
    	die("null");
    $score = 0;
    $money = 100;
	$query = $mysqli->prepare("INSERT INTO Utente(username,googleKey,mail,score,money) VALUES(?,?,?,?,?)");
	$query->bind_param('sssii',$username,$auth,$email,$score,$money);
	$result = $query->execute();
	
	if (!$result) {
		die("null");
	}	
	if ($mysqli->affected_rows > 0){
		echo $mysqli->insert_id;
	}
	else{
		echo "null";
	}
	
	$mysqli->close();
?>