<?php
	include 'includes/session.php';

	if(isset($_POST['delete'])){
		$id = $_POST['id'];
		$sql = "DELETE FROM books WHERE id = '$id' and status= 0";
		$conn->query($sql);
		if($conn->affected_rows){
			$_SESSION['success'] = 'Book deleted successfully';
		}
		else{
			$_SESSION['error'] = $conn->error . "Cannot Delete Book- Book Not Returned Yet";
		}
	}
	else{
		$_SESSION['error'] = 'Select item to delete first';
	}

	header('location: book.php');
	
?>