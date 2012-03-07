<?php

require_once('db_connect_pacman.php');
 
$score = $_GET['score'];
$comment = $_GET['comment'];
$name = $_GET['name'];
$class = $_GET['skill'];


echo $score;
echo('<br>');

$query = "INSERT INTO Highscores  VALUES('$name','$comment','$score','$class','',NOW()) ";
$result = mysql_query($query) or die(mysql_error());

?>