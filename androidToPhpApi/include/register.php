<?php
require_once 'DB_Functions.php';
$db = new DB_Functions();
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "smartlib_db";
$response = array("error" => FALSE);

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if($_SERVER['REQUEST_METHOD']=='POST'){
        //creating studentid
        $letters = '';
        $numbers = '';
        foreach (range('A', 'Z') as $char) {
            $letters .= $char;
        }
        for($i = 0; $i < 10; $i++){
            $numbers .= $i;
        }
      $student_id = substr(str_shuffle($letters), 0, 3).substr(str_shuffle($numbers), 0, 9);
      $firstname = $_POST['firstname'];
      $lastname = $_POST['lastname'];
      $course= $_POST['course_id'];
      $course_id=0;
      if ($course!=="Select a Course"){
          $course_id=$db->getCourseId($course);
      }else{
          $response["error"] = TRUE;
          $response["error_msg"] = "Please Select Course";
          echo json_encode($response);
      }



      $email = $_POST['email'];
      $password = $_POST['password'];
        // $uuid = uniqid('', true);
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt



    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    }else {
        $sql = "INSERT INTO students(student_id, firstname,lastname,course_id, email, encrypted_password, salt, created_at) 
                            VALUES('$student_id','$firstname','$lastname','$course_id','$email','$encrypted_password','$salt', NOW())";

        if ($conn->query($sql) === TRUE) {
            $response["error"] = FALSE;
            $response["sid"] = $student_id;
            $response["user"]["firstname"] = $firstname;
            $response["user"]["lastname"] = $lastname;
            $response["user"]["course_id"] = $course_id;
            $response["user"]["email"] = $email;
            $response["user"]["created_at"] = date('d-m-y h:i:s');
            $response["user"]["updated_at"] = date('d-m-y h:i:s');
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }

    }


}else{
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}

    $conn->close();



?>