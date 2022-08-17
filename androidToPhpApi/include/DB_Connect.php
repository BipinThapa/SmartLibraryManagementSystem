<?php
 
/**
 * Database config variables
 */
define("DB_HOST", "localhost");
define("DB_USER", "root");
define("DB_PASSWORD", "");
define("DB_DATABASE", "smartlib_db");

class DB_Connect {
  private $conn;

  // Connecting to database
  public function connect() {
       
      // Connecting to mysql database
      $this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
       
      // return database handler
      return $this->conn;
  }
}
?>