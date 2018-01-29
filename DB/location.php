<?php
 
// current directory
echo $_SERVER['DOCUMENT_ROOT'];
if(unlink(getcwd()."/picturesUsers/21.html")){
	echo "true";
}
else{
	echo "false";
}
 
?>