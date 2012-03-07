<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- HOMEPAGE -->


<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Joekur</title>

<meta name="keywords" content="joekur, joe, kurleto, game, processing, java, resume" />
<meta name="description" content="JoeKur - Joe Kurleto's homepage. Play games made with processing." />
<meta name="robots" content="all" />


<?php include($_SERVER['DOCUMENT_ROOT']. "/globalHeader.php"); ?>

<script src="/jquery.js"></script>


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



<?php include("header.html"); ?>



<div id="bg_full">

	<div id="page">

		<div id="cont">

			<div id="about" class="post">

				<h1 class="title">Hello!</h1>

				<div class="entry">

					<p>

					This site is a place for you to get to know a bit about me and the projects I've worked on. Everything here is

					very brand new and I don't have a whole lot to show yet...but my goal is to really make an effort to create

					some interesting works. I'm an engineer and a programmer, and I'm currently interested in using processing

					(check out <a href="http://www.processing.org">processing.org</a>) to make simple games and graphic applications.

					<br><br>

					Right now you can check out my Lazer Hockey game inspired by a very addictive arcade game. Head to my 
					<a href="/projects">projects</a>
					tab to have a go. Hopefully in the very near future I'll have much more to show :)

					<div style="text-align:right">-Joe Kurleto </div>

					</p>

				</div>

			</div>

			<!-- end #about -->

		</div>

		<!-- end #content -->
		




<?php include("footer.html"); ?>


<script>
$("#home").addClass("active");
</script>



</body>

</html>

