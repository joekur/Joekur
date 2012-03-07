<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- PROJECTS TOP PAGE -->


<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Joekur | Projects</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="robots" content="all" />
<?php include($_SERVER['DOCUMENT_ROOT']. "/globalHeader.php"); ?>
<link href="projectStyles.css?3" rel="stylesheet" type="text/css" media="screen" />


<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-26969545-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>


</head>

<body>

<?php include("nav.php"); ?>

<div id="bg_full">
	<div id="page">
	
	
		<div class="projCategory" id="games">
			
			<div class="categoryTitle"><h1>Games</h1></div>
			
			<a id="lazerHockeyImg" class="projImage" href="lazerHockey">
				<div class="projDesc">
					<div class="descContainer"></div>
					<h2>Lazer Hockey</h2>
					<p class="description">
						2-player button-mashing game<br>
						Made with processing
					</p>
				</div>
			</a>
			
			<a id="pacmanImg" class="projImage" href="http://joekur.com/erica22">
				<div class="projDesc">
					<div class="descContainer"></div>
					<h2>I&lt;3Erica Pacman</h2>
					<p class="description">
						Small twist on an old classic<br>
						Made with processing
					</p>
				</div>
			</a>
			
			
		</div>
		<!-- end #games -->
		
		<div class="projCategory" id="engineering">
			
			<div class="categoryTitle"><h1>Engineering</h1></div>
			
			
			<a id="discoImg" class="projImage" href="discotective">
				<div class="projDesc">
					<div class="descContainer"></div>
					<h2>The Discotective</h2>
					<p class="description">
						Music recognition system<br>
						Senior design lab
					</p>
				</div>
			</a>
			
		</div>
		<!-- end engineering -->
		
		<div style="clear: both; height: 50px;">&nbsp;</div>
		

<script src="/js/projImg.js"></script>

<?php include("../footer.html"); ?>

