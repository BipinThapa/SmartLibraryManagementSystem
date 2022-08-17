<?php
	include 'includes/session.php';

	if(isset($_POST['edit'])){
		$id = $_POST['id'];
		$firstname = $_POST['firstname'];
		$lastname = $_POST['lastname'];
		$course = $_POST['course'];
		$status = isset($_POST['statusEditStudent'])?1:0;  //isset($_POST['statusEditStudent'])

		$sql = "UPDATE students SET firstname = '$firstname', lastname = '$lastname', course_id = '$course', status='$status' WHERE id = '$id'";
		if($conn->query($sql)){
			$_SESSION['success'] = 'Student updated successfully';
		}
		else{
			$_SESSION['error'] = $conn->error;
		}
	}
	else{
		$_SESSION['error'] = 'Fill up edit form first';
	}

	header('location:student.php');

?>