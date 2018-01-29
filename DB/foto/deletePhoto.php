<?php 
	require("../connection.php");
	
	//this is our upload folder 
	$upload_path = 'picturesUsers/';
	
	//Getting the server ip 
	$server_ip = gethostbyname(gethostname());
	
	//creating the upload url 
	$upload_url = 'http://'.$server_ip.'/'.$upload_path; 
	
	//response array 
	$response = array(); 
	$response['error']=false;

	$imageId = null;
	
	if(isset($_POST["id"]))
		$imageId = $_POST["id"];
	
	
	$query = "DELETE FROM Photo WHERE IDPhoto = ".$imageId;
	
	if (!$result = $mysqli->query($query)) {
		$response['error']=true;
		$response['message']=mysqli_error($mysqli);
	}elseif ($mysqli->affected_rows > 0){
		$response['idImage'] = $imageId;
		
        $url = explode('/', getcwd());
        array_pop($url);
		$file = implode('/', $url)."/".$upload_path.$imageId.".jpg";
		if (!unlink($file))
		{
			$response['error'] = true;
			$response['message'] = "invalid file";
		}
	}
	else{
		$response["error"] = true;
		$response["message"] = "nessuna riga modificata";
	}
	echo json_encode($response);
	$mysqli->close();
?>