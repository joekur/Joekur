var options, a;

jQuery( function(){
  options = { 
  	serviceUrl: 'service/autocomplete.php',
  	width: 80,
  	zIndex: 9999
  	};
  a = $('#query').autocomplete(options);
});

$('#query').click( function() {
	alert("hello");
	$('#query').css({"color" : "black;"}).val("hello");
})
