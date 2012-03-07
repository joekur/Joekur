<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- DISCOTECTIVE -->

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>The Discotective</title>
	<meta name="keywords" content="discotective, joekur, joe kurleto, image processing, ocr, omr" />
	<meta name="description" content="Optical music recognition project." />
	<meta name="robots" content="all" />
	
	<link href="style.css" rel="stylesheet" type="text/css" media="screen" />
	
	<?php include($_SERVER['DOCUMENT_ROOT']. "/globalHeader.php"); ?>
	
	

</head>

<body>

	<?php include("../nav.php"); ?>
	
	<div id="bg_full">
		<div id="page">
			
			<div class="content">
				<div id="discoD"></div>
				
				<h1 class="smLineHt">The Discotective</h1>
				<h2 class="subtitle">an optical music recognition project</h2><br><br>
				
				<p>
					The discotective is a project resulting from a semester-long senior design lab in signal processing. 
					Basically, it was the final hurdle I had to jump to get my cap and gown. Though I suffered many sleepless 
					nights and runtime errors during the course of this project, the discotective really is the pride and joy 
					of my undergraduate studies.
				</p>
				
				<p>
					As technically as I can say it, the discotective is an "optical music recognition" device. If you haven't 
					heard of that before, it's kind of the same idea that modern scanners use to turn a scanned image into a 
					word document or pdf. These algorithms process an image character by character to recognize and extract 
					the captured text (some even work with handwritten text!). In our case, however, we're recognizing not words, 
					but rather musical notation. We give our algorithm an image of sheet music and it spits out the music in 
					audio form &ndash; for the user to hear.
				</p>
				
				<p>
					Our team's main motivation for such a project was it's usefulness for beginning music students. Learning to 
					read sheet music can be a difficult process (maybe you've tried it). What can make learning a new piece of 
					music much easier is knowing what the song is supposed to sound like before trying to hack away at all the 
					musical notation. That's why many music books come with a CD in the back. A device like the discotective would 
					allow students to hear a sheet of music, anywhere.
				</p>
				
				<p>
					Our algorithm was first prototyped within matlab and then ported to an embedded system. We chose an Altera DE2 
					FPGA to house the algorithm, because Terasic has a nice camera add-on for the Altera boards. The FPGA was 
					outfitted to run as a softcore processor, allowing us to write our application in embedded C.
				</p>
				
				<div id="sheetmusicframe">
					<div id="slides">
						<div class="slides_container">
							<img src="img/saria.gif" width="700" height="500" alt="Saria's Song" />
							<img src="img/tetris.gif" width="700" height="500" alt="Tetris Theme" />
							<img src="img/sun.gif" width="700" height="500" alt="Here Comes The Sun" />
						</div>
					</div>
				</div>
				
				<p>
					Above is pictured the three pieces of music we chose to demo our final result. You can see we have a taste 
					for video game tunes (as well as the Beatles of course). These are representative of the complexity of 
					musical notation our system was able to handle with near-perfect accuracy.
				</p>
				
				<p style="margin-bottom:10px">
					For a slightly more in-depth overview of the actual algorithm, check out our design expo poster and final
					presentation:
				</p>
				
				<ul class="downloads">
					<li><a href="downloads/discotective-poster.pdf">Poster (pdf)</a></li>
					<li><a href="downloads/discotective-slides.pptx">Presentation (pptx)</a></li>
				</ul>
			
			</div>
	
	<script src="http://gsgd.co.uk/sandbox/jquery/easing/jquery.easing.1.3.js"></script>
	<script src="/js/slides.min.jquery.js"></script>
	<script>
	$(function(){
		$('#slides').slides({
			preload: true,
			preloadImage: 'img/loading.gif',
			play: 7000,
			pause: 2500,
			hoverPause: true
		});
	});
	</script>
	
	<?php include($_SERVER['DOCUMENT_ROOT'] . "/footer.html"); ?>
