<?php
	require("../connection.php");
	
	$session = null;
	$user = null;
	if(isset($_POST["session"])){
		$session = $_POST["session"];
	}
	if(isset($_POST["utente"])){
		$user = $_POST["utente"];
	}
	
	$query = "SELECT * FROM Photo WHERE challenge = ".$session." AND owner = ".$user;

	if ($result = $mysqli->query($query)) {
		while($row = $result->fetch_array(MYSQL_ASSOC)) {
				$myArray[] = $row;
		}
		echo json_encode($myArray);
	}else{
		echo "null";
	}
	
	$mysqli->close();
?>