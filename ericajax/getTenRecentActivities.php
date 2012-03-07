<?
require_once($_SERVER['DOCUMENT_ROOT'] . '/pacman/db_connect_pacman.php');

$latest_activity;

$query = "SELECT * FROM Highscores ORDER BY Time DESC";
$result = mysql_query($query) or die(mysql_error());

$count = 0;
$html = '';
$row = mysql_fetch_array($result);
while($row && $count<10) {
	
	if ($count == 0) {
		$latest_activity = $row['Time'];
	}
	
    $html = $html . "<div class='hiscore'>";
    
    $html = $html . "<b>" . $row['Score'] . "</b> - ";
    if (!empty($row['Name'])) {
    	$html = $html . $row['Name'];
    } else {
    	$html = $html . "Anonymous";
    }
    
    $html = $html . "<div style='float:right;'>(" . date('h:ia m/d/Y', strtotime($row['Time'])) . ")</div>";
    
    if(!empty($row['Comment'])) {
    	$html = $html . "<br>" . $row['Comment'];
    }
    
    $html = $html . "</div>";
    $count = $count + 1;
    $row = mysql_fetch_array($result);
}

$json[] = array('latestActivity' => $latest_activity, 'content' => $html);
echo json_encode($json)
	
?>