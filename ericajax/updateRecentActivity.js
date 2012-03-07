<?
require_once($_SERVER['DOCUMENT_ROOT'] . '/pacman/db_connect_pacman.php');

$lastUpdate = $_GET('lastUpdate');
$latest_activity; // to be sent back
$newActivity;

$query = "SELECT * FROM Highscores ORDER BY Time DESC";
$result = mysql_query($query) or die(mysql_error());
$num_results = mysql_num_rows($result); 

$count = 0;
$html = '';
$row = mysql_fetch_array($result);

if ($num_results < 1 || $row['Time'] <= lastUpdate) {
	// no new updates
	$newActivity = false;
	$latest_activity = $lastUpdate;
	
} else {}

	while($row && $count<10 && ($row['Time'] > $lastUpdate)) {
		
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
	
}

$json[] = array('newActivity' => $newActivity, 'latestActivity' => $latest_activity, 'content' => $html);
echo json_encode($json)
	
?>