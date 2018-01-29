<?php
	require("../connection.php");
	
	$session = null;
	if(isset($_POST["session"])){
		$session = $_POST["session"];
	}
	
	$query = "SELECT m.position, p.IDPhoto, m.IDMedal, u.username, u.IDUser, u.score, p.latitudine, p.longitudine FROM Medal m, Utente u, Photo p, ChallengeSession s WHERE s.IDSession = ".$session." AND u.IDUser = p.owner AND	p.challenge = s.IDSession AND p.IDPhoto = m.IDPhoto ORDER BY m.position;";

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