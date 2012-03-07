import java.net.URLEncoder;
import java.net.URLDecoder;

void loadHighScore() {
  
  String url = "http://joekur.com/pacman/getHiscore.php";
  String html[] = loadStrings(url);
  
  try {
    println("length: " + html.length);
    println(html);
    if (html.length > 0) {
      String temp = getLine(html[0]);
      temp = trim(temp);
      int[] scores = int(split(temp,','));
      
      hiscore = scores[0];
      println("found high score = " + hiscore);
      
      if (scores.length > 1) {
        hiscore_5th = scores[1];
        println("found 5th place = " + hiscore_5th);
      } else {
        hiscore_5th = 0;
      }
    }
    
  } catch (Exception e) {
    println("error retrieving highscore");
    enableHiscore = false;
  }
  
  
}


boolean submitHighScore(String name, String comment, int score) {
  comment = removeHtml(comment);
  comment = comment.replaceAll(" ", "%20");
  comment = comment.replaceAll("\n", "<br>");
  
  name = removeHtml(name);
  name = name.replaceAll(" ", "%20");
  
  String url = "http://joekur.com/pacman/submitScore.php?score=" + score 
    + "&name=" + name + "&skill=" + pac.powerClass + "&comment=" + comment;
    
  println("url is: " + url);
  
  
  String html[] = loadStrings(url);
  
  if (html != null) {
    return true;
  } else {
    println("error submitting high score");
    return false;
  }
  
  
}


void sendStats() {
  String url = "http://joekur.com/pacman/submitStats.php?"
    + "dots=" + globalDotCount + "&ghosts=" + globalGhostKillCount;
    
  String html[] = loadStrings(url);
  if (html == null) {
    println("error sending stats");
  }
}

String getLine(String s) {
  // returns line of html, without the extra stuff
  int first = s.indexOf("\"");
  String ans;
  ans = s.substring(first+1, s.length());
  return ans;
}

String removeHtml(String s) {
  s = s.replaceAll("<", "[");
  s = s.replaceAll(">", "]");
  return s;
}
