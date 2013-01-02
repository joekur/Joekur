<?php

$host = "localhost";
$user = "joekurco";
$password = "oats_2122";
$dbName = "joekurco_pacman";

mysql_connect($host, $user, $password);
mysql_select_db($dbName) or die(mysql_error());

?>