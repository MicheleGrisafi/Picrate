<?php
	require("../connection.php");
	
	$query = ("SELECT c.IDChallenge, description, title, c.image as cImage, IDSession, s.image as sImage, expiration, stato FROM ChallengeSession s, Challenge c WHERE stato = 0 AND s.IDChallenge = c.IDChallenge");

	if ($result = $mysqli->query($query)) {
		while($row = $result->fetch_array(MYSQL_ASSOC)) {
				$myArray[] = $row;
		}
		echo json_encode($myArray);
	}
	
	$mysqli->close();
?>