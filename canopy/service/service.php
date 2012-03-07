<?php

$suggestions[] = array('Joe','Joseph','Jonathon');

$json = array('query' => 'Jo', 'suggestions' => $suggestions);
echo json_encode($json);

?>