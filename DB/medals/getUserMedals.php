<?php
	require("../connection.php");
	
	$user = null;
	if(isset($_POST["user"])){
		$user = $_POST["user"];
	}
	
	$query = "SELECT m.position, p.IDPhoto,p.latitudine,p.longitudine, m.IDMedal, s.expiration, s.IDSession, c.title, c.description, c.IDChallenge FROM Medal m, 	Utente 	u, Photo p, ChallengeSession s, Challenge c WHERE u.IDUser = ".$user." AND	u.IDUser = p.owner AND p.challenge = s.IDSession AND s.IDChallenge = c.IDChallenge AND p.IDPhoto = m.IDPhoto ORDER BY m.position;";

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