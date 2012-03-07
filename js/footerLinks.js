function hoverLiLink() {
	$(this).parent('li').css("list-style-type", "square");
}

function unhoverLiLink() {
	$(this).parent('li').css("list-style-type", "none");
}


$('.leftHalf a, .rightHalf a').hover(hoverLiLink, unhoverLiLink);