<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- PACMAN - ERICA'S 22ND BIRTHDAY! -->

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

  <head>
    <!-- charset must remain utf-8 to be handled properly by Processing -->
    <meta http-equiv="content-type" content="text/html; charset=windows-1252" />


    <title>Happy Bday Erica</title>
    
    <link rel="icon" href="favicon_pinky.ico" type="image/x-icon"> 
    <link rel="shortcut icon" href="favicon_pinky.ico" type="image/x-icon"> 
    <link rel="image_src" href="/PacmanSnapshot.jpg">
    
    <LINK REL=StyleSheet HREF="style.css?3" TYPE="text/css">
    
    
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



<?
require_once('../pacman/db_connect_pacman.php');
?>

  </head>


<body>
  
  <div class="topdiv" style="width:1120px; padding:20px; margin:auto;">
  
  <div class="header"></div>
  
  
    <div class="content" id="content" style="width:680px;float:left;">
    
    <div class="pacman_container" id="pacman_container" style="width:680px;height:780px;background:#B8F7FC">
    
      <div id="pacman" style="position:relative;left:44px;top:50px;">


	<!-- This version plays nicer with older browsers, 
	     but requires JavaScript to be enabled. 
	     http://java.sun.com/javase/6/docs/technotes/guides/jweb/deployment_advice.html
	     http://download.oracle.com/javase/tutorial/deployment/deploymentInDepth/ -->
	<script type="text/javascript"
		src="http://www.java.com/js/deployJava.js"></script>
	<script type="text/javascript">
	  /* <![CDATA[ */

	  var attributes = { 
            code: 'pacman.class',
            archive: 'http://www.joekur.com/pacman/pacman.jar,http://www.joekur.com/pacman/guicomponents.jar,http://www.joekur.com/pacman/core.jar',
            width: 580, 
            height: 580,
          };

          var parameters = { 
            image: 'http://www.joekur.com/pacman/loading.gif',
            centerimage: 'true',
          };

          var version = '1.5';

          deployJava.runApplet(attributes, parameters, version);

          /* ]]> */

        </script>

	<noscript> <div>
	  <!--[if !IE]> -->
	  <object classid="java:pacman.class" 
            	  type="application/x-java-applet"
            	  	archive="http://www.joekur.com/pacman/pacman.jar,http://www.joekur.com/pacman/guicomponents.jar,http://www.joekur.com/pacman/core.jar"
            	  width="580" height="580"
            	  standby="Loading Processing software..." >

	    <param name="archive" value="http://www.joekur.com/pacman/pacman.jar,http://www.joekur.com/pacman/guicomponents.jar,http://www.joekur.com/pacman/core.jar" />

	    <param name="mayscript" value="true" />
	    <param name="scriptable" value="true" />

	    <param name="image" value="http://www.joekur.com/pacman/loading.gif" />
	    <param name="boxmessage" value="Loading Processing software..." />
	    <param name="boxbgcolor" value="#FFFFFF" />

	  <!--<![endif]-->

	    <!-- For more instructions on deployment, 
		 or to update the CAB file listed here, see:
		 http://java.sun.com/javase/6/webnotes/family-clsid.html
		 http://java.sun.com/javase/6/webnotes/install/jre/autodownload.html -->

	    <object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
		    codebase="http://java.sun.com/update/1.6.0/jinstall-6u20-windows-i586.cab"
		    width="580" height="580"
		    standby="Loading Processing software..."  >

	      <param name="code" value="pacman" />

	      <param name="archive" value="http://www.joekur.com/pacman/pacman.jar,http://www.joekur.com/pacman/guicomponents.jar,http://www.joekur.com/pacman/core.jar" />

	      <param name="mayscript" value="true" />
	      <param name="scriptable" value="true" />
	      <param name="image" value="http://www.joekur.com/pacman/loading.gif" />
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

      </div> <!-- end pacman div -->
      
      <div id="game_text" style="width:300px;position:relative;left:50px;top:56px;float:left" >
      
      <b>I<3Erica Pacman</b><br><br>
      Controls:<br>
      arrow keys to move<br>
      space for superpower<br>
      p to pause
      <br><br>
      <!--Beat the high score to prove your stuff and be placed in the hall of fame! It's
      a great way to show Erica Garcia you really care.-->
      
      </div>
      
      <div style="float:right;margin-top:55px;margin-right:55px;text-align:right;">
      Created by Joe Kurleto<br>
      Built with <a href="http://www.processing.org">processing</a>
      </div>
      


</div> <!-- end pacman_container div -->

<div class="game_description">

I<3Erica Pacman was created to mark the 22nd birthday of the lovely Erica Garcia. So
have some fun, snag a highscore, and be sure to wish Erica a very happy birthday!

</div>




<!-- begin htmlcommentbox.com -->
 <div id="HCB_comment_box"><a href="http://www.htmlcommentbox.com">HTML Comment Box</a> is loading comments...</div>
 <link rel="stylesheet" type="text/css" href="http://www.htmlcommentbox.com/static/skins/simple/skin.css" />
 <script type="text/javascript" language="javascript" id="hcb"> /*<!--*/ if(!window.hcb_user){hcb_user={  };} (function(){s=document.createElement("script");s.setAttribute("type","text/javascript");s.setAttribute("src", "http://www.htmlcommentbox.com/jread?page="+escape((window.hcb_user && hcb_user.PAGE)||(""+window.location)).replace("+","%2B")+"&mod=%241%24wq1rdBcg%24xBfqxi0umnb7PMqrNNHM90"+"&opts=415&num=10");if (typeof s!="undefined") document.getElementsByTagName("head")[0].appendChild(s);})(); /*-->*/ </script>
<!-- end htmlcommentbox.com -->


</div> <!-- end content div (left column) -->
    
    
    
    <!--margin-->
    <div style="width:30px;height:900px;float:left;"></div>
    
    
    
	<div class="right_col" id="right_col">  
	    
	  
	    <!-- stats -->
	    <div class="hx">
	    GLOBAL STATS
	    </div>
	
	
	    <div class="right_col_content" id="stats">
	    	
	    	<div id="statsInner"></div>
	    	
	    </div>
	    
	    
	    
	    <!-- hall of fame column -->
	    <div class="hx">
	    HALL OF FAME
	    </div>
	    
	    <div id="highscores" class="right_col_content">
	    
	    	<div id="highscores_inner"></div>
	    
	    </div>
	    
	    
	    <!-- recent activity -->
	    <div class="hx">
	    RECENT ACTIVITY
	    </div>
	    
	    <div id="rec_activity" class="right_col_content">
	    
			<div id="rec_activity_inner"></div>
	    
	    </div>
	
	    
	</div> <!-- end right column div -->


</div> <!-- end topdiv -->

<script src="/jquery.js"></script>
<script src="loadSidebarContent.js?2"></script>


  </body>
</html>
