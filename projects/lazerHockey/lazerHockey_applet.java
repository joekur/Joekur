import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class lazerHockey_applet extends PApplet {


Minim minim;
AudioSample bumpSound, scoreSound, superSound, blastSound;

boolean[] buttons;
int numLanes = 4;
//int numLanes = 8;
String bumpKeys = "asdfjkl;", bumpKeysUpper = "ASDFJKL;";
//String bumpKeys = "asdfjkl;qweruiop;", bumpKeysUpper = "ASDFJKL;QWERUIOP";
float tableWidth, tableHeight, laneWidth;
float[] lanePos = new float[numLanes];
float margin, sideMargin, goalTop, goalBot;
float puckStartSpeed, puckSpeedMax, puckSpeedMult = 1.2f;
float bumpSpeed, bumpRetSpeed, bumpLength;
int score1, score2, gameLength = 90;
int timeLeft;
long timeStart;
boolean gameBegin = false, displayControls = true;
Button button, modeButton, difficultyButton;
boolean startRequest = false, restartRequest = false, gameEnded = true;

final int MULTIPLAYER = 0;
final int PRACTICE = 1;

int mode = MULTIPLAYER; // 0-2player, 1-practice
int difficulty = 0; //0-easy, 1-med, 2-hard

int pucksLost = 0;
int numPlayers = 1;
boolean[] bumpKeyEnabled = new boolean[8];

AI glados;

Puck[] pucks;
Bumper[] bumpers;

public void setupGame() {
  
  switch(difficulty) {
    case 0: //EASY
      puckStartSpeed = tableHeight*1.5f/650.0f;
      puckSpeedMax = tableHeight*10.0f/650.0f;
      break;
    case 1: //MED
      puckStartSpeed = tableHeight*2.2f/650.0f;
      puckSpeedMax = tableHeight*13.0f/650.0f;
      break;
    case 2: //HARD
      puckStartSpeed = tableHeight*3.0f/650.0f;
      puckSpeedMax = tableHeight*16.0f/650.0f;
      break;
    default:
      break;
    
  }
  
  buttons = new boolean[2*numLanes];
  pucks = new Puck[numLanes];
  bumpers = new Bumper[2*numLanes]; // pl1 pl1 pl1 pl1 pl2 pl2 pl2 pl2
  
  glados = new AI(3);
  
  timeLeft = gameLength;
  
  score1 = 0;
  score2 = 0;
  
  pucksLost = 0;
  
  for(int i=0; i<numLanes; i++) {
    buttons[i] = false;
    lanePos[i] = (i+1) * tableWidth/(numLanes+1) + (width/2 - tableWidth/2);
    
    // enable bumpers
    bumpKeyEnabled[i] = true;
    bumpKeyEnabled[i+numLanes] = true;
    
    // add pucks
    int direction;
    if (i % 2 == 1) {
      direction = 1;
    } else {
      direction = -1;
    }
    pucks[i] = new Puck(i, direction);
    
    // add bumpers
    bumpers[i] = new Bumper(i, true);
    bumpers[i+numLanes] = new Bumper(i, false);
    
  }
}

public void setup() {
  size(550,600);
  smooth();
  tableWidth = width*4.0f/6.0f-50; tableHeight = height*6.5f/7.5f; laneWidth = tableWidth*30.0f/400.0f;
  
  
  bumpSpeed = tableHeight*9.0f/650.0f;
  bumpRetSpeed = tableHeight*3.0f/650.0f;
  bumpLength = tableHeight*200.0f/650.0f;
  
  margin = (height-tableHeight)/2;
  sideMargin = (width-tableWidth)/2;
  goalTop = margin;
  goalBot = height - margin;
  
  //setup audio
  minim = new Minim(this);
  bumpSound = minim.loadSample("photon.wav");
  scoreSound = minim.loadSample("drop.wav");
  superSound = minim.loadSample("charge.wav");
  blastSound = minim.loadSample("cannon.wav");
  
  //buttons
  button = new Button(width/2, 3*height/4+40, 350, 100, "Play", color(0), color(0), color(255), color(0xffFF4000));
  button.textY = button.textY - 15;
  modeButton = new Button(width/2, height/2-10, 250, 40, modeString, color(0), color(0), color(255), color(0xff00FF57));
  difficultyButton = new Button(width/2, height/2+40, 250, 40, difficultyString, color(0), color(0), color(255), color(0xff7600FF));
  modeButton.textY = modeButton.textY-13;
  difficultyButton.textY = difficultyButton.textY-13;
  
  //setupGame();
  
}

public void draw() {
  background(0);
  
  if (gameBegin) {
    // play game
    playGame();
  } else {
    // title menu
    runAnimations();
  }
  
}

public void playGame() {
  timeLeft = gameLength - PApplet.parseInt((millis()-timeStart)/1000);
  if (timeLeft < 0) { timeLeft = 0; }
    
  // draw table
  drawTable();
  
  for (int i=0; i<numLanes; i++) {    
    // handle bumpers
    bumpers[i].update();
    bumpers[i].drawBumper();
    bumpers[i+numLanes].update();
    bumpers[i+numLanes].drawBumper();
    
    // handle pucks
    pucks[i].update();
    pucks[i].drawPuck();
  }
  
  // ai
  if (mode==PRACTICE)
    glados.update();
//  glados.drawStress();
  
  if (timeLeft == 0) { 
    animation = true;
    gameEndAni = true;
    gameBegin = false;
  }
}




public void restartGame() {
  gameEnded = false;
  gameEndAni = false;
  titleScreen = true;
  animation = true;
  setupGame();
}
float MAX_FOCUS = 500;
int stressLife = 1000; // stress event lasts for 1 sec
int[] stressTimers = new int[500];
int numStresses;

class AI {
  int skill;
  final int EASY = 1, MEDIUM = 2, BRICK_WALL = 3;
  
  float focus;
  float rechargeRate = 1;
  
  float stress;
  
  AI(int skill_) {
    skill = BRICK_WALL;
    focus = MAX_FOCUS;
    stress = 0;
  }
  
  public void update() {
    
    // recharge focus
    //focus += rechargeRate;
    
    // update stress timers
    for (int i=0; i<500; i++) {
      if (millis() - stressTimers[i] > stressLife) {
        
      }
    }
    
    stress = 0;
    // loop through pucks
    for (int i=0; i<numLanes; i++) {
      
      if (pucks[i].direction==-1 && pucks[i].distToGoal(true)<tableHeight/3) {
        stress += 10*abs(pucks[i].speed);
      }
      
      
      
      switch (skill) {
        case EASY:
          break;
          
        case MEDIUM:
          break;
          
        case BRICK_WALL:
          float distance = pucks[i].distToGoal(true);
          if (distance < 55) {
            bumpers[i].bump();
          }
          break;
          
        default:
          break;
      }//end switch
      
      
    }//end loop thru pucks
  }
  
  public void drawStress() {
    rectMode(CORNER);
    noFill();
    stroke(255);
    
    int rectHeight = 100;
    rect(10,10,10,rectHeight);
    
    noStroke();
    fill(0xffFF0000);
    
    rect(11,11,8,(rectHeight-2)*(stress/500));
  }
  
}
float superProb = 0.06f;
int superLength_ms = 1000;
int normBumpColor = 0xffFF0000;
int superBumpColor = 0xffFFFFFF;

class Bumper {
  int lane;
  boolean player1; // true if belongs to player 1
  float ypos;
  float speed;
  boolean forward;
  boolean active;
  boolean superActive;
  int t_superActive; // millis() when super bump was activated
  float superTint; // between 0 and 1, starts at 1
  
  Bumper (int lane_, boolean player1_) {
    lane = lane_;
    player1 = player1_;
    
    ypos = 0.0f;
    speed = 0.0f;
    active = false;
    forward = true;
    superActive = false;
    t_superActive = 0;
    superTint = 1;
  }
  
  public void update() {
    
    if (active && !superActive) {
      // update position
      ypos = ypos + speed;
      
      int playerDirection;
      if (player1) {
        playerDirection = 1;
      } else {
        playerDirection = -1;
      }
      
      // check for puck collision
      boolean collision;
      float ranNum = random(1);
      if (player1) {
        collision = ypos >= pucks[lane].ypos - laneWidth/2;
      } else {
        collision = ypos <= pucks[lane].ypos + laneWidth/2;
      }
      if (collision && forward) {
        if (player1) {
          pucks[lane].ypos = ypos + laneWidth/2;
        } else {
          pucks[lane].ypos = ypos - laneWidth/2;
        }
      }
      if (collision && forward && ranNum <= superProb) {
        // initiate super bump
        superActive = true;
        t_superActive = millis();
        superTint = 1;
        superSound.trigger();
        pucks[lane].charging = true;
        
      } else if (collision && forward) {
        // normal bump
        pucks[lane].bump(player1);
        speed = -playerDirection * bumpSpeed;
        forward = false;
        
      } else {
      
        // check if weve reached extension limit
        if ((player1 && ypos >= goalTop + bumpLength) || (!player1 && ypos <= goalBot - bumpLength)) {
          speed = -playerDirection * bumpRetSpeed;
          forward = false;
        }
        
        // check if we've returned to home
        if ((player1 && ypos < goalTop) || (!player1 && ypos > goalBot)) {
          active = false;
          speed = 0.0f;
        }
        
      }
           
    } // end if (active)
    
    if (superActive) {
      superBumpUpdate();
    }
    
  }
  
  public void bump() {
    if (!active) {
      active = true;
      forward = true;
      if (player1) {
        speed = bumpSpeed;
        ypos = goalTop;
      } else {
        speed = -bumpSpeed;
        ypos = goalBot;
      }
      bumpSound.trigger();
    }
  }
  
  public void superBumpUpdate() {
    if (millis() - t_superActive >= superLength_ms) {
      // release!
      blastSound.trigger();
      int playerDirection;
      if (player1) {
        playerDirection = 1;
      } else {
        playerDirection = -1;
      }
      superActive = false;
      pucks[lane].charging = false;
      pucks[lane].bump(player1);
      pucks[lane].speed = pucks[lane].direction * puckSpeedMax;
      speed = -playerDirection * bumpSpeed;
      forward = false;
        
    } else {
      // charging up
      float phase = (millis() - PApplet.parseFloat(t_superActive))/PApplet.parseFloat(superLength_ms);
      superTint = sin(2*PI*7*phase*phase*phase);
    }
  }
  
  public void drawBumper() {
    if (active) {
        
      float bumpHeight, bumpMid;
      if (player1) {
        bumpHeight = ypos - goalTop-4;
        bumpMid = (ypos + goalTop+4)/2;
      } else {
        bumpHeight = goalBot-3 - ypos;
        bumpMid = (ypos + goalBot-3)/2;
      }
      
      noStroke();
      if (forward) {
        if (superActive) {
          fill(avgColor(normBumpColor,superBumpColor,superTint));
        } else {
          fill(normBumpColor);
        }
      } else {
        fill(0xffFFB8A0);
      }
      rectMode(CENTER);
      
      rect(lanePos[lane], bumpMid, laneWidth, bumpHeight);
      
    }
  }
  
}



class Button {
  boolean hovered;
  float centerx, centery;
  float bwidth;
  float bheight;
  float textY;
  int normFill, hoverFill;
  int normStroke, hoverStroke;
  String butText;
  
  Button (float centerx_, float centery_, float bwidth_, float bheight_, String butText_, int normFill_, int hoverFill_, int normStroke_, int hoverStroke_) {
    centerx = centerx_;
    centery = centery_;
    bwidth = bwidth_;
    bheight = bheight_;
    normFill = normFill_;
    hoverFill =  hoverFill_;
    normStroke = normStroke_;
    hoverStroke = hoverStroke_;
    butText = butText_;
    textY = centery_;
    
    hovered = false;
  }
  
  public void drawButton() {
    rectMode(CENTER);
    if (hovered) {
      fill(hoverFill);
      stroke(hoverStroke);
    } else {
      fill(normFill);
      stroke(normStroke);
    }
    
    rect(centerx, centery, bwidth, bheight);
    
    textAlign(CENTER,CENTER);
    textSize(bheight*.5f);
    strokeWeight(4);
    if (hovered) {
      fill(hoverStroke);
    } else {
      fill(normStroke);
    }
    
    text(butText, centerx, textY+10);
  }
  
  public void update() {
    hovered = (mouseX > centerx-bwidth/2 && mouseX < centerx+bwidth/2) && (mouseY > centery-bheight/2 && mouseY < centery+bheight/2);
    drawButton();
  }
  
}
int defFillColor = 0xffFF4000;
int defPtVal = 10, ptValInc = 5;

class Puck {
  int lane;
  float ypos;
  float speed;
  int direction; // 1 going down, -1 going up
  int ptValue;
  int fillColor;
  boolean charging;
  circBuffer posBuffer;
  PuckExplosion p1_explosion, p2_explosion;
  
  Puck (int lane_, int direction_) {
    lane = lane_;
    ypos = height/2;
    direction = direction_;
    speed = direction * puckStartSpeed;
    
    p1_explosion = new PuckExplosion(true, lane);
    p2_explosion = new PuckExplosion(false, lane);
    
    ptValue = defPtVal;
    fillColor = defFillColor;
    charging = false;
    posBuffer = new circBuffer(20);
  }
  
  public void update() {
    p1_explosion.update();
    p2_explosion.update();
    
    posBuffer.insert(ypos);
    
    if (!charging) {
      ypos = ypos + speed;
      
      if ((ypos >= goalBot-laneWidth/2) || (ypos <= goalTop+laneWidth/2)) {
             
        if (ypos >= goalBot-laneWidth/2) {
          // player 1 scores!
          p2_explosion.trigger();
          speed = -puckStartSpeed;
          direction = -1;
          score1 += ptValue;
          scoreSound.trigger();
          posBuffer = new circBuffer(20);
          pucksLost++;
        } else {
          // player 2 scores!
          p1_explosion.trigger();
          speed = puckStartSpeed;
          direction = 1;
          score2 += ptValue;
          scoreSound.trigger();
          posBuffer = new circBuffer(20);
          pucksLost++;
        }
        
        ypos = height/2;
        ptValue = defPtVal;
      }
    } else {
      // clear buffer
      posBuffer = new circBuffer(20);
    }
  }
  
  public float distToGoal(boolean player1) {
    // player 1's goal at top
    float ans;
    if (player1) {
      ans = ypos - (goalTop + laneWidth/2);
    } else {
      ans = (goalBot - laneWidth/2) - ypos;
    }
    return ans;
  }
  
  public void bump(boolean player) {
    if (player) {
      direction = 1;
    } else {
      direction = -1;
    }
    speed = direction * abs(speed) * puckSpeedMult;
    speed = constrain(speed, -puckSpeedMax, puckSpeedMax);
    ptValue = ptValue + ptValInc;
  }
  
  public void drawPuck() {
    drawPuckColor(defFillColor);
  }
  
  public void drawPuckColor(int fill_) {
    fill(fill_);
    noStroke();
    ellipseMode(CENTER);
    ellipse(lanePos[lane], ypos, laneWidth, laneWidth);
    
    if (!charging) {
      float oneBefore = ypos;
      for (int i=posBuffer.numContent-1; i>0; i--) {
        float prevPos = posBuffer.getVal(i);
        float midPos = (oneBefore + prevPos)/2;
        oneBefore = prevPos;

        float a = 90*(PApplet.parseFloat(i)/PApplet.parseFloat(posBuffer.numContent))*abs(speed)/puckSpeedMax;
        int thisFill = avgColor(fill_, 0xffFFCB3B, 1-PApplet.parseFloat(i)/PApplet.parseFloat(posBuffer.numContent));
        fill(thisFill, a);
      
        float thisDist = abs(midPos - prevPos);
        ellipse(lanePos[lane], prevPos, laneWidth, laneWidth);
        ellipse(lanePos[lane], midPos, laneWidth, laneWidth);
        ellipse(lanePos[lane], midPos-thisDist/2.0f, laneWidth, laneWidth);
        ellipse(lanePos[lane], midPos+thisDist/2.0f, laneWidth, laneWidth);
      }
    }
    
  }
  
  
}


class circBuffer {
  int sizeArray;
  float[] buffer;
  int head, tail;
  int numContent;
  
  circBuffer(int size_) {
    sizeArray = size_;
    buffer = new float[sizeArray];
    head = 0;
    tail = 1;
    numContent = 0;
  }
  
  public void insert(float i) {
    buffer[tail] = i;
    numContent++;
    if (numContent > sizeArray)
      numContent = sizeArray;
    
    if (tail==head) {
      head++;
      if (head > sizeArray - 1)
        head = 0;
    }
    
    tail++;
    if (tail > sizeArray - 1) {
      tail = 0;
    }
  }
  
  public float getVal(int ind) {
    int i = ind + head;
    if (i > sizeArray - 1) {
      i -= sizeArray;
    }
    return buffer[i];
  }
  
}




//////////////////
// PUCK EXPLOSIONS
//////////////////
class PuckExplosion {
  boolean active;
  boolean increasing;
  int radius;
  int lane;
  boolean player1;
  int c;
  
  PuckExplosion(boolean player1_, int lane_) {
    active = false;
    player1 = player1_;
    lane = lane_;
  }
  
  public void trigger() {
    active = true;
    increasing = true;
    radius = 1;
    
    colorMode(HSB,255);
    int thisHue = PApplet.parseInt(random(1,255));
    c = color(thisHue,255,255);
    colorMode(RGB,255);
  }
  
  public void update() {
    if (active) {
      
      if (increasing) {
        radius += 1;
      } else {
        radius -= 1;
      }
      if (radius > 2*laneWidth) {
        increasing = false;
      } else if (radius < 1) {
        // end explosion animation
        active = false;
      }
      
      drawExplosion();
      
    }
  }
  
  public void drawExplosion() {
    int xpos = PApplet.parseInt(lanePos[lane]);
    int ypos;
    if (player1) {
      ypos = PApplet.parseInt(goalTop + 3);
    } else {
      ypos = PApplet.parseInt(goalBot - 2);
    }
    for (int r = radius; r>0; r--) {
      fill(color(c,5));
      noStroke();
      if (player1) {
        arc(xpos,ypos,1.5f*r,5*r,0,PI);
      } else {
        arc(xpos,ypos,1.5f*r,5*r,PI,TWO_PI);
      }
    }
  }
  
}
boolean animation = true;
boolean titleScreen = true;
boolean runGameCountdown = false;
boolean gameCountdown = false;
boolean gameEndAni = false;

public void runAnimations() {
  if (titleScreen) {
    titlePage();
  } else if (gameCountdown) {
    runGameCountdown();
  } else if (gameEndAni) {
    gameEnd();
  }
}

// TITLE PAGE
String modeString = "Mode: 2-player";
String difficultyString = "Difficult: Easy";
boolean mouseRel = false;
boolean changeModeRequest = false; // these requests currently unused
boolean changeDifficultyRequest = false; //ditto
public void mouseReleased() {
  mouseRel = true;
}

public void titlePage() {
  fill(255);
  textSize(60);
  textAlign(CENTER);
  text("LAZER", width/2, height/4-40);
  text("HOCKEY", width/2, height/4 + 20);
  
  //buttons
  button.update();
  modeButton.update();
  difficultyButton.update();
  
  if (mouseRel && button.hovered || startRequest) {
    // start game countdown
    setupGame();
    gameCountdown = true;
    titleScreen = false;
    countdown = millis();
  } else if (mouseRel && modeButton.hovered) {
    changeMode();
  } else if (mouseRel && difficultyButton.hovered) {
    changeDifficulty();
  }
  mouseRel = false;
}

public void changeMode() {
  mode++;
  if (mode > 1) mode = 0;
  
  if (mode==0) {
    modeString = "Mode: 2-player";
  } else {
    modeString = "Mode: Practice";
  }
  
  modeButton.butText = modeString;
}

public void changeDifficulty() {
  difficulty++;
  if (difficulty>2) difficulty = 0;
  
  if (difficulty==0) {
    difficultyString = "Difficulty: Easy";
  } else if (difficulty==1) {
    difficultyString = "Difficulty: Medium";
  } else if (difficulty==2) {
    difficultyString = "Difficulty: Hard";
  }
  
  difficultyButton.butText = difficultyString;
}



int countdown;
public void runGameCountdown() {
  drawTable();
  
  String s = "";
  if (millis() - countdown < 700) {
    s = "3";
  } else if (millis() - countdown < 1400) {
    s = "2";
  } else if (millis() - countdown < 2100) {
    s = "1";
  } else if (millis() - countdown < 2800) {
    s = "GO!";
  } else {
    // start game
    gameBegin = true;
    gameCountdown = false;
    animation = false;
    
    timeStart = millis();
  }
  
  fill(255);
  textSize(30);
  text(s, width/2, height/2);
}


public void gameEnd() {
  
  drawTable();
  for (int i=0; i<numLanes; i++) {
    bumpers[i].drawBumper();
    bumpers[i+numLanes].drawBumper();
    pucks[i].drawPuck();
    pucks[i].p1_explosion.drawExplosion();
    pucks[i].p2_explosion.drawExplosion();
  }
  
  rectMode(CENTER);
  fill(100,70);
  rect(width/2,height/2,.95f*tableWidth,100);
  
  colorMode(HSB, 255);
  String endText = "";
  if (mode == MULTIPLAYER) {
    if (score1 > score2) {
      endText = "Player 1 Wins!";
    } else if (score2 > score1) {
      endText = "Player 2 Wins!";
    } else {
      endText = "Tie Game!";
    }
  } else if (mode == PRACTICE) {
    endText = "Game Over";
  }
  fill(color(curHue,255,255));
  textSize(40);
  textAlign(CENTER);
  text(endText, width/2, height/2-5);
  textSize(20);
  text("Press 'r' to restart", width/2, height/2 + 35);
  colorMode(RGB, 255);
  
  gameEnded = true;

}



float curHue = 0;
public void drawTable() {
  
  // table outline-------------------------------
  colorMode(HSB, 255);
  curHue += 0.5f;
  if (curHue > 255)
    curHue = 0;
  stroke(color(curHue,255,255));
  strokeWeight(3);
  noFill();
  rectMode(CENTER);
  rect(width/2, height/2, tableWidth, tableHeight);
  colorMode(RGB, 255);
  
//  fill(#313131);
//  noStroke();
//  for (int i=1; i<numLanes; i=i+2) {
//    rect(lanePos[i], height/2, laneWidth, tableHeight);
//  }
  //---------------------------------------------
  
  // draw buttons--------------------------------
  int butRad = 40;
  int butOff = 0xff400505;
  int butOn = 0xffFF0000;
  ellipseMode(CENTER);
  strokeWeight(1);
  
  textSize(12);
  
  for (int i=0; i<numLanes; i++) {
    stroke(255);
    
    if (buttons[i]) {
      fill(butOn);
    } else {
      fill(butOff);
    }
    ellipse(lanePos[i], margin/2, laneWidth, laneWidth);
    fill(255);
    if (mode == MULTIPLAYER)
      text(bumpKeys.charAt(i), lanePos[i], margin/2+6);
    
    
    if (buttons[i+numLanes]) {
      fill(butOn);
    } else {
      fill(butOff);
    }
    ellipse(lanePos[i], height-margin/2, laneWidth, laneWidth);
    fill(255);
    text(bumpKeys.charAt(i+numLanes), lanePos[i], height-margin/2+6);
  }
  //---------------------------------------------
  
  // draw score----------------------------------
  fill(255);
  textSize(18);
  textAlign(CENTER);
  if (mode==MULTIPLAYER) {
    text("Player 1", width-sideMargin/2, height/2-40-25);
    text(score1, width-sideMargin/2, height/2-40);
    text("Player 2", width-sideMargin/2, height/2+50);
    text(score2, width-sideMargin/2, height/2+50+25);
  } else if (mode==PRACTICE) {
    text("Pucks Lost", width-sideMargin/2+3, height/2);
    text(pucksLost, width-sideMargin/2, height/2+25);
  }
  //---------------------------------------------
  
  // draw time
  text("TIME", sideMargin/2, height/2-10);
  text(timeLeft, sideMargin/2, height/2+20);
  
} // end drawTable()




boolean paused = false;
boolean keyClick = false;
public void keyPressed() {
  
  
  for (int i=0; i<numLanes*2; i++) {
    if (bumpKeys.charAt(i) == key || bumpKeysUpper.charAt(i) == key) {
      if (bumpKeyEnabled[i]) {
        if (gameBegin && (mode==MULTIPLAYER || i >= numLanes))
        bumpers[i].bump();
        buttons[i] = true;
      }
    }
  }
  if (key == 'r' || key == 'R') {
    if (gameEnded) {
      restartRequest = true;
      restartGame();
    }
  }
  if (key == ENTER) {
    startRequest = true;
  }
  if ((key == 'p' || key =='P') && gameBegin) {
    paused = !paused;
    if (paused) pause();
    else unpause();
  }
  
  //titlescreen
  if (titleScreen && !keyClick) {
    if (key=='m' || key=='M') {
      changeMode();
    }
    if (key=='d' || key=='D') {
      changeDifficulty();
    }
  }
  
  keyClick = true;
}

int pausedTime;
public void pause() {
  pausedTime = millis();
  
  rectMode(CENTER);
  fill(100,70);
  rect(width/2,height/2,.95f*tableWidth,60);
  textSize(40);
  fill(255);
  textAlign(CENTER);
  text("Paused", width/2, height/2+15);
  
  noLoop();
}
public void unpause() {
  timeStart += millis() - pausedTime;
  loop();
}


public void keyReleased() {
  keyClick = false;
  if (gameBegin) {
    for (int i=0; i<numLanes*2; i++) {
      if (bumpKeys.charAt(i) == key) {
        buttons[i] = false;
      }
    }
  }
  if (key == 'r' || key == 'R') {
    restartRequest = false;
  }
  if (key == ENTER) {
    startRequest = false;
  }
}

public int avgColor(int color1, int color2, float percent) {
  float r1,g1,b1,r2,g2,b2,r3,g3,b3;
  r1 = red(color1);
  g1 = green(color1);
  b1 = blue(color1);
  r2 = red(color2);
  g2 = green(color2);
  b2 = blue(color2);
  r3 = percent*r2 + (1-percent)*r1;
  g3 = percent*g2 + (1-percent)*g1;
  b3 = percent*b2 + (1-percent)*b1;
  return color(r3,g3,b3);
}


public void stop() {
  // always close Minim audio classes when you are done with them
  bumpSound.close();
  scoreSound.close();
  superSound.close();
  blastSound.close();
  minim.stop();
  
  super.stop();
  
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "lazerHockey_applet" });
  }
}
