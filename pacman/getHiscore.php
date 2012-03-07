 <?
 
 require_once('db_connect_pacman.php');
 
 $query = "SELECT * FROM Highscores ORDER BY Score DESC";
 $result = mysql_query($query) or die(mysql_error());
 
 // print high score and then 5th highest score, if there is one
 $rownum = 1;
 while ($row = mysql_fetch_array($result)) {
 	if ($rownum == 1 || $rownum == 5) {
 		echo $row['Score'];
 		echo ",";
 		//echo('<br>');
 	}
 	$rownum = $rownum + 1;
 }

 
 ?>