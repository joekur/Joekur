<?php

require_once('db_connect_pacman.php');
 
$dots = $_GET['dots'];
$ghosts = $_GET['ghosts'];

$query = "SELECT * FROM Stats WHERE ID='1'";
$result = mysql_query($query) or die(mysql_error());
$row = mysql_fetch_array($result);

$newDots = $dots + $row['Dots'];
$newGhosts = $ghosts + $row['Ghosts'];
echo($newDots);
echo("<br>");
echo($newGhosts);
echo("<br>");

$newPlays = 1 + $row['Plays'];

$query = "UPDATE Stats SET Dots='$newDots', Ghosts='$newGhosts', Plays='$newPlays' WHERE ID='1' ";
$result = mysql_query($query) or die(mysql_error());

?>