<?php
	require_once 'google/google-api-php-client-2.2.0/vendor/autoload.php';
    function verifyToken($id_token){
    	$client = new Google_Client(['client_id' => $CLIENT_ID]);
        $payload = $client->verifyIdToken($id_token);
        if ($payload) {
          $userid = $payload['sub'];
          return $userid;
        } else {
          return "error";
        }
    }
?>
