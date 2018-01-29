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

	$longitudine = null;
	$latitudine = null;
	$owner = null;
	$challenge = null;
	
	if(isset($_POST["challenge"]))
		$challenge = $_POST["challenge"];
	if(isset($_POST["longitudine"]))
		$longitudine = $_POST["longitudine"];
	if(isset($_POST["latitudine"]))
		$latitudine = $_POST["latitudine"];
	if(isset($_POST["owner"]))
		$owner = $_POST["owner"];


	$query = $mysqli->prepare("INSERT INTO Photo(latitudine,longitudine,owner,challenge) VALUES(?,?,?,?)");
	$query->bind_param('ddii',$latitudine,$longitudine,$owner,$challenge);
	$result = $query->execute();
	if (!$result) {
		$response['error']=true;
		$response['message']=mysqli_error($mysqli);
	}elseif ($mysqli->affected_rows > 0){
		
		$imageId = $mysqli->insert_id;
		
		$response['idImage'] = $imageId;
		
		$name = $imageId;
		
		//getting file info from the request 
		$fileinfo = pathinfo($_FILES['image']['name']);
		
		//getting the file extension 
		$extension = $fileinfo['extension'];
		
		//file url to store in the database 
		$file_url = $upload_url . $name . '.' . $extension;
		
		//file path to upload in the server 
		$file_path = SITE_ROOT."/".$upload_path . $name . '.'. $extension; 
		try{
			move_uploaded_file($_FILES['image']['tmp_name'],$file_path);

			
			$message = 'Error uploading file';
			switch( $_FILES['image']['error'] ) {
				case UPLOAD_ERR_OK:
					$message = false;;
					break;
				case UPLOAD_ERR_INI_SIZE:
				case UPLOAD_ERR_FORM_SIZE:
					$message .= ' - file too large (limit of '.ini_get("upload_max_filesize").' bytes).';
					break;
				case UPLOAD_ERR_PARTIAL:
					$message .= ' - file upload was not completed.';
					break;
				case UPLOAD_ERR_NO_FILE:
					$message .= ' - zero-length file uploaded.';
					break;
				default:
					$message .= ' - internal error #'.$_FILES['image']['error'];
					break;
			}
			$response["error_uploading"] = $message;	
			$response["url"] =  "http://fotografandoapp.altervista.org/picturesUsers/".$name.'.'.$extension;
		}catch(Exception $e){
			$response['error']=true;
			$response['message']=$e->getMessage();
		}		
	}
	else{
		$response["error"] = true;
		$response["message"] = "nessuna riga modificata";
	}
	echo json_encode($response);
	
	$mysqli->close();
?>