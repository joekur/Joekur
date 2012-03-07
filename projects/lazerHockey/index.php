<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!--LAZER HOCKEY-->


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
	<!-- charset must remain utf-8 to be handled properly by Processing -->
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />

	<title>Lazer Hockey</title>
	<?php include($_SERVER['DOCUMENT_ROOT']. "/globalHeader.php"); ?>
  </head>

  <body>
  
  <?php include("../nav.php"); ?>
  
  <div id="bg_full">
	<div id="page">
		<div id="cont">
  
      <div id="lazerHockey_container" style="padding:none;width:550px;height:600px;margin-top:60px;margin-left:30px;margin-bottom:30px;background:white;">

	<!-- This version plays nicer with older browsers, 
	     but requires JavaScript to be enabled. 
	     http://java.sun.com/javase/6/docs/technotes/guides/jweb/deployment_advice.html
	     http://download.oracle.com/javase/tutorial/deployment/deploymentInDepth/ -->
	<script type="text/javascript"
		src="http://www.java.com/js/deployJava.js"></script>
	<script type="text/javascript">
	  /* <![CDATA[ */

	  var attributes = { 
            code: 'lazerHockey_applet.class',
            archive: 'lazerHockey_applet.jar,jl1.0.jar,jsminim.jar,minim-spi.jar,minim.jar,mp3spi1.9.4.jar,tritonus_aos.jar,tritonus_share.jar,core.jar',
            width: 550, 
            height: 600,
          };
          var parameters = { 
            image: 'loading.gif',
            centerimage: 'true',
          };
          var version = '1.5';
          deployJava.runApplet(attributes, parameters, version);

          /* ]]> */
        </script>
        
	<noscript> <div>
	  <!--[if !IE]> -->
	  <object classid="java:lazerHockey_applet.class" 
            	  type="application/x-java-applet"
            	  archive="lazerHockey_applet.jar,jl1.0.jar,jsminim.jar,minim-spi.jar,minim.jar,mp3spi1.9.4.jar,tritonus_aos.jar,tritonus_share.jar,core.jar"
            	  width="550" height="600"
            	  standby="Loading Processing software..." >
            
	    <param name="archive" value="lazerHockey_applet.jar,jl1.0.jar,jsminim.jar,minim-spi.jar,minim.jar,mp3spi1.9.4.jar,tritonus_aos.jar,tritonus_share.jar,core.jar" />
	    
	    <param name="mayscript" value="true" />
	    <param name="scriptable" value="true" />
	    
	    <param name="image" value="loading.gif" />
	    <param name="boxmessage" value="Loading Processing software..." />
	    <param name="boxbgcolor" value="#FFFFFF" />
	  <!--<![endif]-->

	    <!-- For more instructions on deployment, 
		 or to update the CAB file listed here, see:
		 http://java.sun.com/javase/6/webnotes/family-clsid.html
		 http://java.sun.com/javase/6/webnotes/install/jre/autodownload.html -->
	    <object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
		    codebase="http://java.sun.com/update/1.6.0/jinstall-6u20-windows-i586.cab"
		    width="550" height="600"
		    standby="Loading Processing software..."  >
	      
	      <param name="code" value="lazerHockey_applet" />
	      <param name="archive" value="lazerHockey_applet.jar,jl1.0.jar,jsminim.jar,minim-spi.jar,minim.jar,mp3spi1.9.4.jar,tritonus_aos.jar,tritonus_share.jar,core.jar" />
	      
	      <param name="mayscript" value="true" />
	      <param name="scriptable" value="true" />
	      
	      <param name="image" value="loading.gif" />
	      <param name="boxmessage" value="Loading Processing software..." />
	      <param name="boxbgcolor" value="#FFFFFF" />
	      
	      <p>
		<strong>
		  This browser does not have a Java Plug-in.
		  <br />
		  <a href="http://www.java.com/getjava" title="Download Java Plug-in">
		    Get the latest Java Plug-in here.
		  </a>
		</strong>
	      </p>
	      
	    </object>
	    
	  <!--[if !IE]> -->
	  </object>
	  <!--<![endif]-->

	</div> </noscript>

	
	</div>
	<!-- end lazerhockey container -->
	
			
			<div id="description" class="post">
			
			<h1 class="title">Lazer Hockey</h1>
			<div class="entry">
				<p>
				<em>Controls:</em><br>
				a,s,d,f to control top player<br>
				j,k,l,; to control bottom player (1-player)<br>
				p to pause
				</p>
				
				<p>
				'Lazer Hockey' is a 2-player game that a group of my friends discovered at a local arcade we have
				here in Ann Arbor called Pinball Pete's. The idea is
				very much like air hockey, except way crazier. I won't try to describe it too much, just have a go and you'll
				catch on. Unfortunately currently you can only play with a buddy (although yes, you can <em>try</em>
				playing against yourself). Soon though I hope to develop a computer AI to help you hone your skills while
				no one is around.
				</p>
				
				<p>
				UPDATE: There is now a 'practice' mode. This is akin to playing tennis against a wall - don't expect to win. Also, like 
				the original, you have the option to choose your difficulty. But if you play on anything but the hardest setting, you
				won't be taken seriously.
				</p>
				
				<p>This game was built with <a href="http://www.processing.org">processing</a>.</p><br>
				
				<!-- begin htmlcommentbox.com -->
				 <div id="HCB_comment_box"><a href="http://www.htmlcommentbox.com">HTML Comment Box</a> is loading comments...</div>
				 <link rel="stylesheet" type="text/css" href="http://www.htmlcommentbox.com/static/skins/simple/skin.css" />
				 <script type="text/javascript" language="javascript" id="hcb"> /*<!--*/ if(!window.hcb_user){hcb_user={  };} (function(){s=document.createElement("script");s.setAttribute("type","text/javascript");s.setAttribute("src", "http://www.htmlcommentbox.com/jread?page="+escape((window.hcb_user && hcb_user.PAGE)||(""+window.location)).replace("+","%2B")+"&mod=%241%24wq1rdBcg%24xBfqxi0umnb7PMqrNNHM90"+"&opts=16798&num=10");if (typeof s!="undefined") document.getElementsByTagName("head")[0].appendChild(s);})(); /*-->*/ </script>
				<!-- end htmlcommentbox.com -->
			
			</div>
			
			</div>
			
			
			
	
		
      
      		</div>
		<!-- end #content -->
		
		<div style="clear: both; height: 20px;">&nbsp;</div>


<?php include("../../footer.html"); ?>
      
      
 
  </body>
</html>
