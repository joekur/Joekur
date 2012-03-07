<?php

include($_SERVER['DOCUMENT_ROOT']. "/header.html"); 

?>

<script src="/jquery.js"></script>

<script>

var url = window.location.href
var parts = url.split("?")
var pageName = part[1]

$("#" + pageName).addClass("active");
</script>