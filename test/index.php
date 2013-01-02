<?php

require('../lib/partial.class.php');

$title = 'First title';
$body = 'bla bla bal';
include('_partial.html.php');

$title = 'Second title';
$body = 'second body';
include('_partial.html.php');

/*
Partial::render('_partial.html.php', array(
	'title' => 'First title',
	'body' => 'bla bla bla yo'
));

echo Partial::inline('_partial.html.php', array(
	'title' => 'Second title',
	'body' => 'woooooooo ohhhh yeah'
));*/

?>