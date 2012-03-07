var options, a;
var queryBox, queryTitle;
var translationText = "Spanish translation!";

jQuery(function(){
	options = { 
		serviceUrl: 'service/autocomplete.php',
		maxHeight: 400,
		width: 478
	};
	a = $('#query').autocomplete(options);
	
	queryBox = $('#query');
	queryTitle = queryBox.attr('title');
	queryBox.css("color","gray").val(queryTitle);
	
	queryBox.focus( function() {
		if (queryBox.val() === queryTitle) {
			queryBox.css("color","black").val("");
		}
	});
	
	queryBox.blur( function() {
		if (queryBox.val() === "") {
			queryBox.css("color", "gray").val(queryTitle);
		}
	});
	
});

/*,
  	width: 500,
  	zIndex: 9999 */








// LANGUAGE SELECTION
$('a.in').click( function () {
	myButton = $(this);
	$('a.in').removeClass("active");
	myButton.addClass("active");
	
	scriptKnown = false;
});

$('a.out').click( function () {
	myButton = $(this);
	$('a.out').removeClass("active");
	myButton.addClass("active");
});

// CHECK INPUT LANGUAGE DYNAMICALLY VIA CHARACTER CODE
var scriptKnown = false;
$('#query').keyup( function() {
	console.log('text change function');
	
	var text = $('#query').val();
	var lnButtons = $('a.in');
	
	console.log('text is: ' + text);
	
	if (!text) {
		scriptKnown = false;
	}
	if (!scriptKnown && text) {
		// detect script
		var code = text.charCodeAt(0);
		if (code >= 0x400 && code <= 0x04FF) {
			// cyrillic (russian)
			lnButtons.removeClass('active');
			$('#RU_inB').addClass('active');
		} else if (code >= 0x4E00 && code <= 0x9FBB) {
			// chinese
			lnButtons.removeClass('active');
			$('#CN_inB').addClass('active');
		} else {
			// latin (english spanish)
			var EN_button = $('#EN_inB');
			var ES_button = $('#ES_inB');
			if (!EN_button.hasClass('active') && !ES_button.hasClass('active')) {
				lnButtons.removeClass('active');
				EN_button.addClass('active'); // english by default
			}
		}
		
		scriptKnown = true;
	}
});


// TRANSLATE ACTION
function translate() {
	$('span#translation').text(translationText);
}

$('.arrow').click( function() {
	translate();
});

$('.arrow').hover( 
	function() {
		$('.arrow-bod').css({"border-right-color" : "#e19d7c", "border-top-color" : "#e19d7c"});
		$('.arrow-down').css({"border-top-color" : "#e19d7c"});
	}, 
	function() {
		$('.arrow-bod').css({"border-right-color" : "#E17640", "border-top-color" : "#E17640"});
		$('.arrow-down').css({"border-top-color" : "#E17640"});
	});

