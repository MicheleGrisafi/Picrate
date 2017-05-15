<?php
	require("../connection.php");
	
	$query = "SELECT NOW() as date";
	
	$response = array(); 
	
	if ($result = $mysqli->query($query)) {
		while($row = $result->fetch_array(MYSQL_ASSOC)) {
				$response["date"] = $row["date"];
		}
	}else{
		$response["error"]=true;
		$response["message"] = "";
	}
	echo json_encode($response);
	
	$mysqli->close();
?>