<?php
    require("../connection.php");
    $username = null;
	if(isset($_POST["username"]))
		$username = $_POST["username"];

    
 	
    if (!is_null($username)){
    	$result = $mysqli->query("SELECT * FROM Utente WHERE username = '".$username."'");
    }else{
    	die("0");
    }
    if($result->num_rows > 0)
        echo "1";
    else
        echo "0";
	$mysqli->close();
?>