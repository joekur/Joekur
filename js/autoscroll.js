

$(document).ready(function() {
	$('#playAgain').click(function() {
	    $('html, body').animate({ scrollTop: 0 }, 'slow', function() {
	    	// blink nav links
	    	var navLinks = $('#menu a');
	    	console.log('nav links selected');
	    	//navLinks.css('color','#FF4800');
	    	navLinks.stop().css('color', 'white').animate({'color' : '#FF4800'}, 1000, function() {
	    		navLinks.animate({'color' : 'white'}, 1000);
	    	});
	    });
	});
});