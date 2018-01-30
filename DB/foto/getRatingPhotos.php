<?php
	require("../connection.php");
	
	$user = null;
	if(isset($_POST["user"])){
		$user = $_POST["user"];
	}
	
	$query = "SELECT p.IDPhoto, c.IDSession, g.title, g.description, c.expiration FROM Photo p INNER JOIN ChallengeSession c ON p.challenge = c.IDSession AND c.stato = 1 AND p.owner <> {$user} AND p.IDPhoto not in (SELECT IDPhoto FROM Rating WHERE IDUser = {$user}) INNER JOIN Challenge g ON c.IDChallenge = g.IDChallenge LEFT OUTER JOIN Rating r ON r.IDPhoto = p.IDPhoto GROUP BY p.IDPhoto ORDER BY COUNT(r.IDPhoto)";

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