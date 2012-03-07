<?php


require_once($_SERVER['DOCUMENT_ROOT'] . '/pacman/db_connect_pacman.php');

$query = "SELECT * FROM Stats WHERE ID='1'";
$result = mysql_query($query) or die(mysql_error());
$row = mysql_fetch_array($result);

echo($row['Plays'] . " games played");
echo("<br>");
echo($row['Dots'] . " dots eaten");
echo("<br>");
echo($row['Ghosts'] . " ghosts eaten");


?>