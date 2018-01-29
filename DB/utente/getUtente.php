<?php
	require("../connection.php");
	require("../googleAuth.php");
	$googleKey = null;
	$id = null;
    
	if(isset($_POST["googleKey"]))
		$googleKey = $_POST["googleKey"];
    if(isset($_POST["id"]))
		$id = $_POST["id"];
    
 	
    if (is_null($id)){
    	$auth = verifyToken($googleKey);
        if($auth == "error")
        	die("null");
    	$result = $mysqli->query("SELECT * FROM Utente WHERE googleKey = '".$auth."'");
    }else{
    	$result = $mysqli->query("SELECT * FROM Utente WHERE id = ".$id);
    }
	$array = array();
	if (!$result) {
		echo "null";
	}else{
        while($row = $result->fetch_assoc()){
			$array[] = $row;
		}
        echo json_encode($array);
	}
	$mysqli->close();
?>