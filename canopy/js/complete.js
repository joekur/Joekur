var numSentences;
var sentenceID;
var dropWords;

$(function() {
	
	numSentences = $('.sentenceList').children().length;
	sentenceID = new Array(numSentences);
	dropWords = $('.droppableWord');
	
	// give id to each drop word
	var idnum = 0;
	dropWords.each( function() {
		$(this).attr("id", function () {
			idnum++;
  			return "dropWord" + idnum;
  		});
	});
	
	
	$('.droppableWord').draggable({
		revert: true,
		refreshPositions: true
	}).each(function() {
		this.onselectstart = function() { return false; };
	});
	
	$('.blankWord').droppable({
		hoverClass: "blankWordHovered",
		tolerance: 'touch',
		drop: function( event, ui ) {
			var thisWord = ui.draggable.text();
			ui.draggable.css('visibility','hidden');
			
			console.log($(this).attr('id'));
			if ($(this).attr('id')) {
				// already a word in here
				var wholeid = $(this).attr('id');
				var idnum = wholeid.substring(1,wholeid.length);
				$('#' + idnum)
					.css('visibility', 'visible');
				
			} else {
				$(this)
					.addClass('filledWord')
					.removeClass('blankWord')
			}
			
			$(this)
				.html(thisWord)
				.attr('id', 'p' + ui.draggable.attr('id') )
				.mousedown( onFilledWordClick );
		}
	});
	
	function onFilledWordClick() {
		var wholeid = $(this).attr('id');
		// this is id of dropWord
		console.log('onfilledwordclick id = ' + wholeid);
		var idnum = wholeid.substring(1,wholeid.length);
		
		$(this)
			.addClass('blankWord')
			.removeClass('filledWord')
			.html('')
			.attr('id','');
		
		$('#' + idnum)
			.css('visibility', 'visible');
	}
	
	
	
	
});
