<?php
	
    
    require("../connection.php");
	
	$stato = 1;
	if(isset($_POST["stato"])){
		$stato = $_POST["stato"];
	}
	$query = "SELECT c.IDChallenge, description, title, c.image as cImage, IDSession, s.image as sImage, expiration, stato FROM ChallengeSession s, Challenge c WHERE stato = ".$stato." AND s.IDChallenge = c.IDChallenge";
	if ($result = $mysqli->query($query)) {
        while($row =mysqli_fetch_assoc($result))
        {
            $emparray[] = $row;
        }
    	//print_r($emparray);
        echo json_encode($emparray);
	}
	
	$mysqli->close();
?>