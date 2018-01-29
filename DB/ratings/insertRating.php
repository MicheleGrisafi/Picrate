<?php 
	require("../connection.php");
	
	

	if(isset($_POST["user"]))
		$user = $_POST["user"];
	if(isset($_POST["foto"]))
		$foto = $_POST["foto"];
	if(isset($_POST["voto"]))
		$voto = $_POST["voto"];
	if(isset($_POST["segnalazione"]))
		$segnalazione = $_POST["segnalazione"];
	if($segnalazione == "true")
    	$segnalazione = 1;
    else
    	$segnalazione = 0;
	
	$query = $mysqli->prepare("INSERT INTO Rating(IDPhoto,IDUser,Rating,segnalazione) VALUES(?,?,?,?)");
	$query->bind_param('iiii',$foto,$user,$voto,$segnalazione);
	$result = $query->execute();
	$response['error']=false;
	if (!$result) {
		$response['error']=true;
		$response['message']=mysqli_error($mysqli);
	}elseif ($mysqli->affected_rows == 0){
		$response["error"] = true;
		$response["message"] = "nessuna riga modificata";
	}
	echo json_encode($response);
	$mysqli->close();
?>