<?php
	require("../connection.php");
	
	$username = null;
	
	if(isset($_POST["username"]))
		$username = $_POST["username"];
	

	$query = $mysqli->prepare("SELECT * FROM utente WHERE username = ?");
	$query->bind_param('s',$username);
	$result = $query->execute();
	
	
	if (!$result) {
		echo "null";
		die();
	}else{
		$myArray = array();
		while($row = $result->fetch_array(MYSQL_ASSOC)) {
			$myArray[] = $row;
		}
		echo json_encode($myArray);
	}

	$mysqli->close();
?>