<?php

$str = 'www.joekur.com';
$pattern = "/(?i)\b((?:https?:\/\/|www\d{0,3}[.]|[a-z0-9.\-]+[.][a-z]{2,4}\/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))/";
$replace = "<a href=\"\\0\" rel=\"nofollow\">\\0</a>";

/*$pattern_http = "/(?i)\b((?:https?:\/\/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))/";
$pattern_www = "/(?i)\b((www\d{0,3}[.])(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))/";
*/
$pattern_http = "/^((?:https?:\/\/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))/";
$pattern_www = "/^((www\d{0,3}[.])(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))/";
$replace_http = "<a href=\"\\0\" rel=\"nofollow\">\\0</a>";
$replace_www = "<a href=\"http://\\0\" rel=\"nofollow\">\\0</a>";

echo preg_match($pattern_http, $str);
echo preg_match($pattern_www, $str);
echo preg_match($pattern, $str);

echo '<br>';
$str = preg_replace($pattern_http, $replace_http, $str);
$str = preg_replace($pattern_www, $replace_www, $str);
echo $str;

?>