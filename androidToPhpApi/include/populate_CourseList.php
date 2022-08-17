<?php
require_once 'DB_Connect.php';

$db = new Db_Connect();
$conn = $db->connect();

$sql = "select * from course";
$r = mysqli_query($conn,$sql);
if(!$conn->query($sql)){
    echo "Error in connecting to Database.";
}
else{
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        $return_arr['courses'] = array();
        while($row = $result->fetch_array()){
            array_push($return_arr['courses'], array(
                'id'=>$row['id'],
                'title'=>$row['title']
            ));
        }
        echo json_encode($return_arr);
    }
}
?>