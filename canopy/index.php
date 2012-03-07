<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
        
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<link href="style.css?1" rel="stylesheet" type="text/css" media="screen" />
	
</head>



<body>
	
	<div id="header">
		<div id="logo">
			<img src="img/canopyLogo.png" />
		</div>
	</div>
	
	<div id="container">
		
		
		
		<a class="lnBut in active" id="EN_inB">EN</a>
		<a class="lnBut in" id="CN_inB">CN</a>
		<a class="lnBut in" id="ES_inB">ES</a>
		<a class="lnBut in" id="RU_inB">RU</a>
		
		<div id="query_holder">
		<input type="text" name="q" id="query" title="Enter an English word or phrase..." />
		</div>
		<br><br><br>
		
		<div class="arrow">
			<div class="arrow-bod"></div>
			<div class="arrow-down"></div>
		</div>
		
		<br>
		
		<a class="lnBut out EN active">EN</a>
		<a class="lnBut out CN">CN</a>
		<a class="lnBut out ES">ES</a>
		<a class="lnBut out RU">RU</a>
		
		<div id="translation_holder">
			<span id="translation">
				
			</span>
		</div>
		
	</div>
	
	<div id="footer">
		
		<a href="http://languagemate.com">
			<img src="img/languagemateLogo.jpg" />
		</a>
		
	</div>
	
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="js/queryHandling.js"></script>
	
</body>


</html>