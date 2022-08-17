<?php

require_once 'DB_Functions.php';
$db = new DB_Functions();


$id=$db->getCourseId($_POST['course']);
echo json_encode($id);

?>