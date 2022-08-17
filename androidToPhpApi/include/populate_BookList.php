<?php
require_once 'DB_Connect.php';

$db = new Db_Connect();
$conn = $db->connect();

$sql = "SELECT b.id,b.isbn,b.title,c.name as subject,b.author,b.publisher,b.publish_date,YEAR(b.publish_date) as year FROM books as b INNER JOIN category as c on b.category_id=c.id where b.status=0";
$r = mysqli_query($conn,$sql);
if(!$conn->query($sql)){
    echo "Error in connecting to Database.";
}
else{
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        $return_arr['books'] = array();
        while($row = $result->fetch_array()){
            array_push($return_arr['books'], array(
                'id'=> $row['id'],
                'ISBN'=>$row['isbn'],
                'title'=> $row['title'],
                'subject'=> $row['subject'],
                'language'=> "English",
                'number_of_page'=> "200",
                'edition'=> "2",
                'author'=> $row['author'],
                'publisher'=> $row['publisher'],
                'publication_date'=> $row['publish_date'],
                'faculty'=> "",
                'year'=> $row['year'],
                'semester'=> "",
                'url'=> "https://images-na.ssl-images-amazon.com/images/I/51W-YaXNAVL._SX218_BO1,204,203,200_QL40_FMwebp_.jpg",
                'thumbnailUrl'=> "https://images-na.ssl-images-amazon.com/images/I/51W-YaXNAVL._SX218_BO1,204,203,200_QL40_FMwebp_.jpg"
            ));
        }
        echo json_encode($return_arr);
    }
}
?>