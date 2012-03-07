<?php

$host = "localhost";
$user = "joekurco";
$password = "wmscajr2";
$dbName = "joekurco_pacman";

mysql_connect($host, $user, $password);
mysql_select_db($dbName) or die(mysql_error());

?>