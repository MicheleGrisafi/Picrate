<?php
	require("../connection.php");
	

    $result = $mysqli->query("SELECT IDUser,username, score FROM Utente ORDER BY score DESC, money DESC");
	
	$myArray = array();
	if (!$result) {
		$mysqli->close();
        die("null");
	}else{
		while($row = $result->fetch_array(MYSQL_ASSOC)) {
			$myArray[] = $row;
		}
	}
	echo json_encode($myArray);
	$mysqli->close();
?>