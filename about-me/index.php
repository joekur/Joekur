<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- ABOUT ME -->


<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Joekur | About Me</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="robots" content="all" />
<?php include($_SERVER['DOCUMENT_ROOT']. "/globalHeader.php"); ?>
<link href="style.css" rel="stylesheet" type="text/css" media="screen" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
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

</head>

<body>

<?php include("nav.php"); ?>

<div id="bg_full">
	<div id="page">
	
		<div id="top_part">
	
			<div id="pictureframe">
				<div id="slides">
					<div class="slides_container">
						<img src="img/me1.png" width="380" height="480" alt="Slide 1" title="My girlfriend's camera really knows how to make me look good" />
						<img src="img/me2.png" width="380" height="480" alt="Slide 2" title="On the beach of great lake michigan" />
						<img src="img/me3.png" width="380" height="480" alt="Slide 3" title="Watch out, I'm dangerous" />
					</div>
					<!--<a href="#" class="prev"><img src="img/arrow-prev.png" width="24" height="43" alt="Arrow Prev"></a>
					<a href="#" class="next"><img src="img/arrow-next.png" width="24" height="43" alt="Arrow Next"></a>-->
				</div>
				<img src="img/frame.png" width="500" height="550" id="frame" />
			
			</div>
			
			<div id="bio">
				<h1>Joe Kurleto</h1>
				<br>
				
				<table cellspacing="10">
					<tr>
					<td><b>Age:</b></td>
					<td> 
					<?
						$year_diff  = date("Y") - 1990 - 1;
						$age = $year_diff;
						if (date("m") > 5 || (date("m") == 5 && date("d") >= 26)) {
							$age++;
						}
						echo $age;
					?>
					</td>
					</tr>
					<!--<tr>
					<td><b>Sex:</b></td> <td>Man</td>
					</tr>-->
					<tr>
					<td><b>Location:</b></td> <td>Ann Arbor, MI</td>
					</tr>
					<tr>
					<td valign="top"><b>Interests:</b> </td> <td>Engineering, programming, web design, technology, games, soccer, guitar, piano, sci-fi, bowling, laughter-inducing television</td>
					</tr>
					<tr>
					<td><b>Music:</b></td> <td>Rock 'n' roll of years past</td>
					</tr>
					<tr>
					<td valign="top"><b>Novels:</b></td> <td>Dune, Ender's Game, Foundation</td>
					</tr>
					<tr>
					<td><b>Slurpee:</b></td> <td>Boston cooler</td>
					</tr>	
					
				</table>
				
				<div id='networks'>
					<a href="http://www.linkedin.com/in/joekur"> <img src="img/linkedin-icon.png" alt="My LinkedIn" /> </a>
					<a href="http://facebook.com/joe.kurleto"> <img src="img/facebook-icon.png" alt="My Facebook" /> </a>
				</div>
			
			</div> <!-- end bio -->
			
		
		</div>


<?php include("../footer.html"); ?>

</body>
</html>