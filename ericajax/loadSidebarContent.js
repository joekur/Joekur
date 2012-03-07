var lastActivityTime;
var hiscores;

function grabStats() {
	statsDiv = $('#statsInner');
	statsDiv.load('getGlobalStats.php', function() {
		$('#statsInner .loadGif').remove();
		$(this).hide().fadeIn(3000);
	});
}

function updateRecActivity() {
	$.getJSON('updateRecentActivity.php?' + new Date().getTime(), {lastUpdate : lastActivityTime}, function(data) {
		if (data[0].newActivity) {
			lastActivityTime = data[0].latestActivity;
			$('#rec_activity_inner').prepend('<div class="tempContainer"></div>');
			$('#rec_activity_inner .tempContainer').hide().css("opacity", "0.25").html(data[0].content).slideDown(1000).animate({opacity : 1}, 2000, function() {
				$('#rec_activity_inner .tempContainer').removeClass('tempContainer');
			});
			
			setTimeout(updateHiscores, 1500);
		}
		
	});
	
	setTimeout(updateRecActivity, 5000);
}

function initialLoadRecActivity() {
	recDiv = $('#rec_activity_inner');
	$.getJSON('getTenRecentActivities.php?' + new Date().getTime(), function(data) {
				
		lastActivityTime = data[0].latestActivity;
		$('#rec_activity_inner').hide().html(data[0].content).slideDown(1000);
	});
	
	setTimeout(updateRecActivity, 5000);
}

function updateHiscores() {
	$.getJSON('updateHiscores.php?' + new Date().getTime(), {lastScores : hiscores}, function(data) {
		if (data[0].newActivity) {
			hiscores = data[0].scores;
			
			var myDiv = $('#highscores_inner');
			myDiv.slideUp(1500, function() {
				// once the old scores have sliden up...
				myDiv.html(data[0].content).slideDown(1500);
			});
		}
		
	});
}

function initialLoadHiscores() {
	hiscoreDiv = $('#highscores_inner');
	$.getJSON('getInitialHiscores.php?' + new Date().getTime(), function(data) {
				
		hiscores = data[0].scores;
		$('#highscores_inner').hide().html(data[0].content).slideDown(1000);
	});
}

function startAni() {
	statsDiv = $('#statsInner');
	statsDiv.html('<div class="loadGif"></div>');
	$('#statsInner .loadGif').css("margin-top", "40px");
	setTimeout(grabStats, 2000);
	
	recDiv = $('#rec_activity_inner');
	recDiv.html('<div class="loadGif"></div>');
	$('#rec_activity_inner .loadGif').css("margin-top", "40px");
	setTimeout(initialLoadRecActivity, 4000);
	
	hiscoreDiv = $('#highscores_inner');
	hiscoreDiv.html('<div class="loadGif"></div>');
	$('#highscores_inner .loadGif').css("margin-top", "40px");
	setTimeout(initialLoadHiscores, 3000);
}


$(document).ready(startAni);