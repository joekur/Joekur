<?
require_once($_SERVER['DOCUMENT_ROOT'] . '/pacman/db_connect_pacman.php');

$lastScores[] = $_GET['lastScores'];

$scores[] = array(0,0,0,0,0); // new queried values
$newActivity = false;
$html = '';

$query = "SELECT * FROM Highscores ORDER BY Score DESC";
$result = mysql_query($query) or die(mysql_error());

$count = 0;
$row = mysql_fetch_array($result);
while($row && $count<5) {
	$scores[$count] = $row['Score'];
	
	if ($scores[$count] != $lastScores[0][$count]) {
		// there is a difference!
		$newActivity = true;
	}
	
    $html = $html . "<div class='hiscore'>" . "<div class='classImg'>";
	
    if ($row['Class'] == 0) {
    	$html = $html . "<img src='pacSpeedSm.png' width='61' height='27' />";
    } else {
    	$html = $html . "<img src='pacLaserSm.png' width='61' height='27' />";
    }
    $html = $html . "</div>";
    
    $html = $html . "<b>" . $row['Score'] . "</b> - ";
    if (!empty($row['Name'])) {
    	$html = $html . $row['Name'];
    } else {
    	$html = $html . "Anonymous";
    }
    
    $html = $html . "<br>";
    if(!empty($row['Comment'])) {
    	
    	$html = $html . $row['Comment'];
    }
    
    $html = $html . "</div>";
    
    $count = $count + 1;
    $row = mysql_fetch_array($result);
}



$json[] = array('newActivity' => $newActivity, 'scores' => $scores, 'content' => $html);
echo json_encode($json)
	
?>