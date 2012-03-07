var triOffset = 16;

function prepareTriangle() {
	var menuPosition = $('#menu').position();
	var navTri = $('#navTri');
	var thisPage = $('li.active a:first');
	var activePosition = thisPage.position();
	
	/*var activePage;
	if( $('#home').hasClass('active') ) {
		activePage = 0;
	} else if () {*/
	
	
	var leftPosition = activePosition.left + Math.round(thisPage.width() / 2) + triOffset;
	
	//alert("leftpos = " + activePosition.left + " offset = " + Math.round(thisPage.width() / 2));
	//alert("hi" + parseInt(activePosition.css("margin-left")));
	
	navTri.css({"visibility" : "visible", "left" : leftPosition});
	$('#navTri #base').css("visibility", "visible");
}

function getDuration(distance) {
	if (distance > 300) {
		return( 600 * (distance / 300) );
	} else {
		return 600;
	}
}

function hoverNav() {	
	var thisNav = $(this);
	var activeNav = $('li.active a:first');
	var navTri = $('#navTri');
	
	navTri.stop();
	
	var thisPosition = thisNav.position().left + Math.round(thisNav.width() / 2) + triOffset;
	var distance = Math.abs( thisPosition - navTri.position().left );
	var duration = getDuration(distance);
	
	navTri.animate({left : thisPosition}, duration);
}

function unhoverNav() {
	var thisNav = $(this);
	var activeNav = $('li.active a:first');
	var navTri = $('#navTri');
	
	var activePosition = activeNav.position().left + Math.round(activeNav.width() / 2) + triOffset;
	var distance = Math.abs( activePosition - navTri.position().left );
	var duration = getDuration(distance);
	
	navTri.stop();
	navTri.animate({left : activePosition}, duration);
}



$(document).ready(prepareTriangle);

$('#menu a').hover(hoverNav, unhoverNav);