var projImages;

function hoverImage() {
	// show child description
	var desc = $(this).children('.projDesc');
	desc.stop(true).animate({opacity : 1}, 400);
}

function unhoverImage() {
	// hide child description
	var desc = $(this).children('.projDesc');
	desc.stop(true).animate({opacity : 0}, 400);
}


$(document).ready( function() {
	projImages = $('.projImage');
	projImages.children('.projDesc').css("opacity", "0");
	projImages.hover(hoverImage, unhoverImage);
});





