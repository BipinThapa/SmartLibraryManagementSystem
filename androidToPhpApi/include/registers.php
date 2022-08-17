<?php

require_once 'DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);
if (isset($_POST['firstname']) && isset($_POST['lastname']) && isset($_POST['course_id']) && isset($_POST['email']) && isset($_POST['password'])) {

    // receiving the post params
    $firstname = $_POST['firstname'];
    $lastname = $_POST['lastname'];
    $course_id = $_POST['course_id'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($firstname, $lastname, $course_id, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["uid"] = $user["student_id"];
            $response["user"]["firstname"] = $user["firstname"];
            $response["user"]["lastname"] = $user["lastname"];
            $response["user"]["course_id"] = $user["course_id"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["created_at"] = $user["created_at"];
            $response["user"]["updated_at"] = $user["updated_at"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>