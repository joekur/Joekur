import processing.core.*; 
import processing.xml.*; 

import guicomponents.*; 
import java.net.URLEncoder; 
import java.net.URLDecoder; 

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

public class pacman extends PApplet {


G4P g = new G4P();
GTextField commentBox, nameBox;

Pac pac;
Blinky blinky;
Pinky pinky;
Inky inky;
Clyde clyde;
Ghost[] ghosts = new Ghost[4];
int yellow = 0xffFFF708;
boolean levelStarted = false;
int timer, phaseTimer;
int phaseTimes[] = {6000,14000,6000,14000,4000,999999};
int phaseModes[] = {0,1,0,1,0,1};
int curLevel = 1, curPhase = 0;
float PAC_SPEED = 4.5f/60.0f;
int inkyDots, clydeDots;
PFont font,font2;
int score = 0, hiscore = 0, hiscore_5th = 0;
boolean animation = true;
boolean gameStarted = false;
boolean invincible = false;
boolean enableHiscore = true;

int globalDotCount, globalGhostKillCount;
FruitSystem fruitSystem;
SuperPower superPower;

public void setup() {
  size(580,580);
  frameRate(60);
  smooth();
  readMapFile();
  
  // FONTS AND BUTTONS
  font = loadFont("GungsuhChe-20.vlw");
  font2 = loadFont("ARDESTINE-50.vlw");
  playButton = new Button(width/2, height - 35, 150, 40, "Play", color(0), color(0), color(255), yellow, font2);
  speedClassButton = new Button(width/2-96, 280, 150, 70, "", color(0), color(0), color(255), yellow, font2);
  laserClassButton = new Button(width/2-96, 200, 150, 70, "", color(0), color(0), color(255), yellow, font2);
  goButton = new Button(width/2, height-90, 200, 50, "GO!", color(0), color(0), color(255), yellow, font2);
  
  if (enableHiscore) {
    GComponent.globalFont = GFont.getFont(this, "Arial", 13);
    commentBox = new GTextField(this, "", width/2 - 350/2, 250, 350, 130, true);
    nameBox = new GTextField(this, "", width/2 - 350/2, 200, 350, 20, false);
    submit = new Button(width/2-60, 470, 100, 50, "Submit", color(0), color(0), color(255), yellow, font2);
    cancelSubmit = new Button(width/2+60, 470, 100, 50, "Cancel", color(0), color(0), color(255), yellow, font2);
    seeTextBoxes(false);
    
    loadHighScore();
  }
  
  setupGame();
}

public void setupGame() {
  pac = new Pac();
  blinky = new Blinky();
  pinky = new Pinky();
  inky = new Inky();
  clyde = new Clyde();
  ghosts[0] = blinky; ghosts[1] = pinky; ghosts[2] = inky; ghosts[3] = clyde;
  
  score = 0;
  curLevel = 0;
  
  fruitSystem = new FruitSystem();
  
  setupLevel();
  
  globalDotCount = 0;
  globalGhostKillCount = 0;
  
  hiScoreSubmit = false;
  scoreSubmit = false;
}

//-------------//
// SETUP LEVEL //
//-------------//
public void setupLevel() {
  curPhase = 0;
  curLevel++;
  
  initializeDots();
  dotsEaten = 0;
  
  fruitSystem.prepareForLevel();
  
  // set level parameters
  if (curLevel < 2) {
    curPacSpeed = .8f*PAC_SPEED;
    curGhostSpeed = .7f*PAC_SPEED;
    frightenedTime = 9000;
    inkyDots = 90;
    clydeDots = 130;
    
  } else if (curLevel < 5) {
    
    curPacSpeed = .9f*PAC_SPEED;
    curGhostSpeed = .8f*PAC_SPEED;
    frightenedTime = 8000;
    inkyDots = 80;
    clydeDots = 120;
    
  } else if (curLevel < 10) {
    
    blinky.homeBase = new PVector(-2,-2);
    pinky.homeBase = new PVector(mapW+1,-1);
    inky.homeBase = new PVector(-1,mapH+2);
    clyde.homeBase = new PVector(mapW+2,mapH+6);
    
    curPacSpeed = .9f*PAC_SPEED;
    curGhostSpeed = .83f*PAC_SPEED;
    frightenedTime = 7500;
    inkyDots = 70;
    clydeDots = 110;
    
  } else if (curLevel < 20) {
    
    blinky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    pinky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    inky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    clyde.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    
    curPacSpeed = PAC_SPEED;
    curGhostSpeed = .95f*PAC_SPEED;
    frightenedTime = 5800;
    inkyDots = 50;
    clydeDots = 90;
    
    phaseTimes[0] = 5000;
    phaseTimes[1] = 20000;
    phaseTimes[2] = 5000;
    phaseTimes[3] = 20000;
    phaseTimes[4] = 5000;
    phaseTimes[5] = 999999;
    
  } else if (curLevel < 25) {
    
    blinky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    pinky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    inky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    clyde.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    
    curPacSpeed = .9f*PAC_SPEED;
    curGhostSpeed = .95f*PAC_SPEED;
    frightenedTime = 3000;
    inkyDots = 30;
    clydeDots = 70;
    
    
  } else {
    
    blinky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    pinky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    inky.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    clyde.homeBase = new PVector(PApplet.parseInt(random(-5,mapW+5)), PApplet.parseInt(random(-5,mapH+5)));
    
    curPacSpeed = PAC_SPEED;
    curGhostSpeed = 1.06f*PAC_SPEED;
    frightenedTime = 1500;
    inkyDots = 25;
    clydeDots = 55;
  }
  
  
  reset();
  
}


public void draw() {
  background(0);
  
  drawLevel();
  drawHeader();
  
  if (!animation)
    runGame();
  else
    runAnimation();
}

public void runGame() {
  
  if (levelStarted) {
    pac.update();
    
    blinky.updateCondition();
    pinky.updateCondition();
    inky.updateCondition();
    clyde.updateCondition();
    
    superPower.update();
    
  } else {
    textFont(font,20);
    fill(yellow);
    text("Get ready!", width/2, 78);
    
    if (millis() - readyTimer > 2000) {
      startLevel();
    }
  }
  
  fruitSystem.update();
  pac.drawPac();
  
  blinky.drawGhost();
  pinky.drawGhost();
  inky.drawGhost();
  clyde.drawGhost();
  
  drawTunnel();
  superPower.drawHUD();
  
  gameUpdate();
  
}

public void gameUpdate() {
  if (levelStarted) {
    if (millis() - phaseTimer > phaseTimes[curPhase] && curPhase < 5) {
      // change phase
      phaseTimer = millis();
      curPhase++;
      println("new mode: " + phaseModes[curPhase]);
      blinky.mode = phaseModes[curPhase];
      blinky.reverseGhost = true;
      pinky.mode = phaseModes[curPhase];
      pinky.reverseGhost = true;
      inky.mode = phaseModes[curPhase];
      inky.reverseGhost = true;
      clyde.mode = phaseModes[curPhase];
      clyde.reverseGhost = true;
    }
    
    if (millis() - timer > 1000) {
      // release pinky
      pinky.moving = true;
    }
    if (dotsEaten >= inkyDots && millis() - timer > 1000) {
      // release inky
      inky.moving = true;
    }
    if (dotsEaten >= clydeDots && millis() - timer > 1000) {
      // release clyde
      clyde.moving = true;
    }
    
    // did we finish the level??
    if (dotsEaten >= totalDots) {
//    if (dotsEaten >= 3) {
      animation = true;
      levelCompleteAnimation = true;
      levelCompleteAngle = 0;
    }
    
  }
  
}


public void drawHeader() {
  fill(textC);
  textFont(font,30);
  textAlign(CENTER);
  
  // score
  text("SCORE", 50, 30);
  textFont(font,25);
  text(score,50,62);

  // level
  textFont(font,30);
  text ("LEVEL " + curLevel, width/2, 30);
  
  // lives
  text("LIVES", width - 50, 30);
  int offSet = 0;
  for (int i=0; i<pac.lives; i++) {
    // yellow circle
    fill(0xffFFF708);
    noStroke();
    arc(width-22-offSet, 55, 20, 20, PI/4, 2*PI-PI/4);
    offSet += 30;
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
  PFont font;
  
  Button (float centerx_, float centery_, float bwidth_, float bheight_, String butText_, int normFill_, int hoverFill_, int normStroke_, int hoverStroke_, PFont font_) {
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
    font = font_;
    
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
    noFill();
    strokeWeight(4);
    rect(centerx, centery, bwidth, bheight);
    
    textAlign(CENTER,CENTER);
    textFont(font, bheight*.5f);
    
    if (hovered) {
      fill(hoverStroke);
    } else {
      fill(normStroke);
    }
    
    text(butText, centerx, textY-3);
  }
  
  public void update() {
    hovered = (mouseX > centerx-bwidth/2 && mouseX < centerx+bwidth/2) && (mouseY > centery-bheight/2 && mouseY < centery+bheight/2);
    drawButton();
  }
  
}
boolean paused = false;
int pausedTime;
boolean spacePressed = false, spaceClicked = false;

public void keyPressed() {
  
  if (key == ESC) {
    key = 0;  // Fools! don't let them escape!
  }
  
  if (key==' ') {
    if (!spacePressed) {
      spaceClicked = true;
      if (levelStarted && !paused) {
        superPower.activate();
      }
    } else {
      spaceClicked = false;
    }
    spacePressed = true;
  }
  
  if ((key == 'p' || key=='P') && gameStarted) {
    // pause or unpase
    paused = !paused;
    if (paused) {
      pausedTime = millis();
      
      pushMatrix();
      noStroke();
      fill(0xffFF3705, 200);
      rotate(-PI/4);
      rect(0,150,500,80);
      
      fill(yellow);
      textFont(font,23);
      textAlign(CENTER);
      text("Paused!", 5, 143);
      textFont(font,17);
      text("press [p] to resume", 0, 170);
      
      popMatrix();
      
      noLoop();
    } else {
      adjustTimers(millis()-pausedTime);
      loop();
    }
  }
  
  if (pac.moving) {
    if (key == CODED) {
      if (keyCode == UP) {
        pac.moveRequested = 1;
      } else if (keyCode == RIGHT) {
        pac.moveRequested = 2;
      } else if (keyCode == DOWN) {
        pac.moveRequested = 3;
      } else if (keyCode == LEFT) {
        pac.moveRequested = 4;
      }
    }
    
  }
  
  
}

public void keyReleased() {
  if (key==' ') {
    spacePressed = false;
    spaceClicked = false;
  }
}


public void adjustTimers(int delta) {
  timer += delta;
  phaseTimer += delta;
  dyingTimer += delta;
  deadGhostTimer += delta;
  readyTimer += delta;
  frightenedTimer += delta;
}


public void seeTextBoxes(boolean b) {
  commentBox.setVisible(b);
  commentBox.setEnabled(b);
  nameBox.setVisible(b);
  nameBox.setEnabled(b);
}
//color textC = #F6FF00; // yellow
int textC = 0xffFF035F; // pink
boolean dyingAnimation = false;
int dyingTimer;
float dyingState, dyingAngle;
boolean levelCompleteAnimation = false;
float levelCompleteAngle = 0;
boolean gameOver = false;
boolean classSelectScreen = false;

boolean deadGhostAnimation = false;
int deadGhostTimer, ghostScore;
PVector ghostScoreLoc;
Ghost deadGhost;

int readyTimer;
Button playButton;

public void addScore(int d) {
  int lastScore = score;
  score += d;
  if ((score % 50000) < (lastScore % 50000)) {
    pac.lives++;
  }
}


public void startLevel() {
  levelStarted = true;
  timer = millis();
  phaseTimer = timer;
  pac.moving = true;
  blinky.moving = true;
}

public void reset() {
  
  ghostMult = 1;
  pulse = 1;
  
  curPhase = 0;
  timer = millis();
  phaseTimer = millis();
  
  readyTimer = millis();
  levelStarted = false;
  
  // when new level starts or pacman dies
  pac.initialize();
  blinky.initialize();
  pinky.initialize();
  inky.initialize();
  clyde.initialize();
  
  if (gameStarted) {
    superPower.reset();
  }
  
}

public void pacDie() {
  if (!invincible) {
    pac.lives--;
    dyingState = pac.mouthState;
    dyingAngle = pac.mouthAngle;
    dyingTimer = millis();
    animation = true;
    dyingAnimation = true;
  }
}

int ghostMult = 1;
public void killGhost(Ghost g) {
  int bonus = ghostMult * 200;
  ghostMult *= 2;
  ghostScore = bonus;
  addScore(bonus);
  deadGhost = g;
  
  PVector drawLoc = getLoc(pac.loc.y,pac.loc.x);
  float ran = random(0,1);
  int offSetx = round(ran);
  if (offSetx == 0) { offSetx = -1; }
  ran = random(0,1);
  int offSety = round(ran);
  if (offSety == 0) { offSety = -1; }
  
  ghostScoreLoc = new PVector(drawLoc.x + 20*offSetx, drawLoc.y + 20*offSety);
  
  deadGhostAnimation = true;
  animation = true;
  deadGhostTimer = millis();
  
}

//************//
// ANIMATIONS //
//************//

public void runAnimation() {
  if (gameOver) {
    runGameOver();
  } else if (classSelectScreen) {
    runClassSelectionScreen();
  } else if (!gameStarted) {
    runTitleScreen();
  } else if (levelCompleteAnimation) {
    runLevelCompleteAnimation();
  } else if (dyingAnimation) {
    runDyingAnimation();
  } else if (deadGhostAnimation) {
    killGhostAnimation();
  } else if (laserAnimationRunning) {
    runLaserAnimation();
  }
}

public void killGhostAnimation() {
  
  fruitSystem.update();
  pac.drawPac();
  if (deadGhost != blinky)
    blinky.drawGhost();
  if (deadGhost != pinky)
    pinky.drawGhost();
  if (deadGhost != inky)
    inky.drawGhost();
  if (deadGhost != clyde)
    clyde.drawGhost();
    
  superPower.drawHUD();
  drawTunnel();
  
  fill(255);
  stroke(0);
  textFont(font, 16);
  text(ghostScore, ghostScoreLoc.x, ghostScoreLoc.y);
  
  if (millis() - deadGhostTimer > 1000) {
    // end animation
    frightenedTimer += 1000; // must add a second back to the timer
    animation = false;
    deadGhostAnimation = false;
    deadGhost.dead = true;
    deadGhost.frightened = false;
    
    // reset multiplier if we've killed all possible ghosts
    if (!blinky.frightened && !pinky.frightened && !inky.frightened && !clyde.frightened) {
      ghostMult = 1;
    }
  }
}

public void runTitleScreen() {
  playButton.update();
  strokeWeight(1);
  
  if ((mousePressed==true && playButton.hovered) || (keyPressed && key==ENTER)) {
    classSelectScreen = true;
    gameOver = false; // for restarting gameplay
    enterReleasedClassSelect = false;
  }
}


Button speedClassButton, laserClassButton, goButton;
boolean speedSelected = false;
int selectColorBorder = 0xff00A8FF;
String speedName = "The Speedster";
String laserName = "The Bleedster";
String speedDesc = "Put the pedal to the metal and leave those ghosts in a cloud of dust.";
String laserDesc = "Hit the trigger to spew a molten-hot laser from pacman's mouth and burn your foes to a crisp.";
String className = laserName, classDesc = laserDesc;
boolean enterReleasedClassSelect = false;
public void runClassSelectionScreen() {
  stroke(255);
  strokeWeight(4);
  fill(0);
  rectMode(CENTER);
  rect(width/2, height/2, 400, 500);
  
  fill(255);
  textFont(font2, 30);
  text("FIGHT OR FLIGHT?", width/2, 100);
  textFont(font, 18);
  text("Choose your skill...", width/2, 130);
  String instruction = "This time around, pac's bringing some SKILL. Use it to get a leg up on the ghosts, but try not to rely on it! The meter at the bottom will show how much power you have left. Replenish by grabbing fruit starting on lvl 5.";
  //String instruction = "The meter at the bottom will show how much power you have left. We'll start you out with a little. Get more by grabbing fruit starting on lvl 5.";
  textFont(font,15);
  textAlign(LEFT);
  text(instruction, width/2+10, 458, 320, 250);
  
  //images
  imageMode(CENTER);
  image(pacLaserImg, width/2-96, 200, 150, 70);
  image(pacSpeedImg, width/2-96, 280, 150, 70);
  
  speedClassButton.update();
  laserClassButton.update();
  goButton.update();
  
  // placeholder
  noFill(); stroke(255);
  rectMode(CORNER);
  rect(298,164,160,152);
  stroke(0);
  fill(yellow);
  textFont(font2,21);
  textAlign(CENTER);
  text(className, 298+80, 193);
  textFont(font,13);
  fill(255);
  textAlign(LEFT);
  text(classDesc, 317, 202, 130, 120);
  rectMode(CENTER);
  
  if (mousePressed && laserClassButton.hovered) {
    speedSelected = false;
  }  
  if (mousePressed && speedClassButton.hovered) {
    speedSelected = true;
  }
  
  if (speedSelected) {
    laserClassButton.normStroke = color(255);
    speedClassButton.normStroke = selectColorBorder;
    className = speedName;
    classDesc = speedDesc;
  } else {
    laserClassButton.normStroke = selectColorBorder;
    speedClassButton.normStroke = color(255);
    className = laserName;
    classDesc = laserDesc;
  }
  
  // user must release enter to use it again
  if(!keyPressed) {
    enterReleasedClassSelect = true;
  }
  
  if ((mousePressed==true && goButton.hovered) || (keyPressed && key==ENTER && enterReleasedClassSelect)) {
    animation = false;
    classSelectScreen = false;
    setupGame();
    strokeWeight(1);
    
    if (speedSelected) {
      superPower = new SpeedPower();
      pac.powerClass = SPEEDSTER;
    } else {
      superPower = new LaserPower();
      pac.powerClass = FIGHTER;
    }
    
    gameStarted = true;
    
  }
}


public void runLevelCompleteAnimation() {
  
  fruitSystem.update();
  pac.drawPac();
  blinky.drawGhost();
  pinky.drawGhost();
  inky.drawGhost();
  clyde.drawGhost();
  superPower.drawHUD();
  drawTunnel();
  
  levelCompleteAngle += PI/12;
  
  pushMatrix();
    
  float angle = levelCompleteAngle;  
  if (angle > 4*2*PI) {
    angle = 4*2*PI;
  }
  int tSize = PApplet.parseInt(angle*50/(8*PI));
  
  translate(width/2, height/2);
  rotate(angle);
  
  textFont(font2, tSize);
  fill(255);
  stroke(255);
  text("Level Complete", 0, 0);
  
  popMatrix();
  
  if (levelCompleteAngle > 6*2*PI) {
    animation = false;
    levelCompleteAnimation = false;
    // level complete earns bonus
    if (curLevel < 10) {
      addScore(500);
    } else if (curLevel < 20) {
      addScore(2000);
    } else {
      addScore(4000);
    }
    setupLevel();
  }
}

public void runDyingAnimation() {
  
  fruitSystem.update();
  blinky.drawGhost();
  pinky.drawGhost();
  inky.drawGhost();
  clyde.drawGhost();
  superPower.drawHUD();
  drawTunnel();
  
  // draw dying pacman
  
  PVector drawLoc = getLoc(pac.loc.y, pac.loc.x);
  ellipseMode(CENTER);
  stroke(0);
  fill(yellow);
  
  arc(drawLoc.x, drawLoc.y, pacDiameter, pacDiameter, dyingAngle+PI*dyingState/4, dyingAngle+2*PI-PI*dyingState/4);
  
  if (millis() - dyingTimer > 600)
    dyingState += 0.04f;
  
  if (dyingState >= 4) {
    // animation complete
    dyingAnimation = false;
    animation = false;
    
    if (pac.lives >= 0) {
      reset();
    } else {
      // GAME OVER :(
      setupGameOver();
    }
  }
  
}

//*****LASER ANIMATION****//
public void runLaserAnimation() {
   
  fruitSystem.update();
  blinky.drawGhost();
  pinky.drawGhost();
  inky.drawGhost();
  clyde.drawGhost();
  superPower.drawHUD();
  
  laserAnimation.run();
  
  pac.drawPac();
  drawTunnel();
  
}

//*****GAME OVER******//

Button submit, cancelSubmit;
int hiscoreBoxLoc;
boolean hiScoreSubmit = false;
boolean scoreSubmit = false;
float curHue;
boolean formReady;
boolean serverError = false;

public void setupGameOver() {
  gameOver = true;
  animation = true;
  gameStarted = false;
  
  loadHighScore();
  
  // setup high score submission
  if (enableHiscore && score > hiscore_5th) {
    hiScoreSubmit = true;
    
    colorMode(HSB, 255);
  }
  scoreSubmit = true;
  hiscoreBoxLoc = -200;
  formReady = false;
  serverError = false;
  
}

public void runGameOver() {
  rectMode(CENTER);
  fill(0,150);
  stroke(255);
  strokeWeight(4);
  rect(width/2, height/2, 400, 60);
  strokeWeight(1);
  
  textFont(font2, 50);
  fill(0xffFF0000);
  text("GAME OVER", width/2, height/2+17);
  
  if (scoreSubmit) {
    hiscoreBoxLoc += 3;
    if (hiscoreBoxLoc > width/2) {
      hiscoreBoxLoc = width/2;
      formReady = true;
    }
    
    curHue += 0.5f;
    if (curHue > 255)
      curHue = 0;
    
    fill(0);
    stroke(255);
    rectMode(CENTER);
    rect(hiscoreBoxLoc, height/2, 400, 500);
    
    if (formReady) {
      // rect has moved into place
      
      // show text boxes
      seeTextBoxes(true);
      
      if (hiScoreSubmit) {
        fill(255);
        textFont(font, 20);
        textAlign(CENTER);
        if (score > hiscore) {
          text("1st place.\nWe bow to you...",width/2,100);
        } else {
          text("Congratulations!\nYou have set a new highscore!",width/2,100);
        }
        
        textFont(font,30);
        fill(color(curHue,255,255));
        text("Score = " + score, width/2, 175);
        
      } else {
        fill(255);
        textFont(font, 20);
        textAlign(CENTER);
        text("Submit your score?",width/2,100);
        
        textFont(font,30);
        text("Score = " + score, width/2, 175);
      }
      
      fill(255);
      textFont(font,14);
      textAlign(LEFT);
      text("Name", width/2-350/2, 195);
      text("Comment", width/2-350/2, 245);
      
      // error text
      if (serverError) {
        rectMode(CENTER);
        text("Error contacting server. Confirm your connection and try again.", width/2, 435, 300, 100);
      }
      
      submit.update();
      cancelSubmit.update();
      
      // allow user to tab between boxes
      if (nameBox.hasFocus() && keyPressed && key==TAB) {
        commentBox.setFocus(true);
      }
      
      if (submit.hovered && mousePressed) {
        // submit info
        String nameEntry = trim(nameBox.getText());
        String commentEntry = trim(commentBox.getText());
        println(nameEntry);
        println(commentEntry);
        boolean submitSuccess = submitHighScore(nameEntry,commentEntry,score);
        
        if (submitSuccess) {
          println("submit success");
          scoreSubmit = false;
          hiScoreSubmit = false;
          colorMode(RGB, 255);
          seeTextBoxes(false);
          
          // send stats to database
          sendStats();
        } else {
          // activate error message
          println("server error encountered");
          serverError = true;
        }
      } else if (cancelSubmit.hovered && mousePressed) {
        // dont send score
        println("score submission cancelled");
        scoreSubmit = false;
        hiScoreSubmit = false;
        seeTextBoxes(false);
        
        // send stats to database
        if (!serverError) {
          sendStats();
        }
      }
      
    }
    
  } else {
  
    // allow user to restart game
    runTitleScreen();
    
  }
}
class FruitSystem {
  boolean fruitActive;
  PVector pos;
  PImage img;
  int dotLimit;
  boolean fruitGeneratedThisLevel;
  
  FruitSystem() {
    fruitActive = false;
    PVector pos = new PVector(0,0);
    fruitGeneratedThisLevel = false;
  }
  
  public void update() {
    if (!fruitGeneratedThisLevel && curLevel >= 5) {
      // check if its time to create the fruit
      if (dotsEaten >= this.dotLimit) {
               
        // find suitable location
        boolean posFound = false;
        int posX, posY;
        int tries = 0;
        while(!posFound && tries < 1000) {
          posX = PApplet.parseInt(random(1,mapW-1)+.5f);
          posY = PApplet.parseInt(random(1,mapH-1)+.5f);
          PVector fruitV = new PVector(posX,posY);
          float distToPac = PVector.dist(fruitV, pac.loc);
          if (level[posY][posX].passable && level[posY][posX].eaten && distToPac > 1.5f) {
            this.pos = new PVector(posX, posY);
            posFound = true;
            fruitActive = true;
          }
          tries++;
        }
        fruitGeneratedThisLevel = true;
      }
    }
    
    if (!fruitActive) {
      return;
    }
    
    this.drawFruit();
    
    int pacx = round(pac.loc.x);
    int pacy = round(pac.loc.y);
    
    if (pacx==this.pos.x && pacy==this.pos.y) {
      // eat fruit
      this.eatFruit();
    }
  }
  
  public void eatFruit() {
    // called when fruit is eaten
    println("fruit eaten!");
    fruitActive = false;
    superPower.ammoPickup();
  }
  
  public void drawFruit() {
    PVector mapPos = getLoc(this.pos.y, this.pos.x);
    
    pushMatrix();
    translate(mapPos.x, mapPos.y);
    imageMode(CENTER);
    image(this.img,0,0,18,18);
    popMatrix();
  }
  
  
  public void prepareForLevel() {
    fruitGeneratedThisLevel = false;
    
    // decide which fruit
    int whichImage = PApplet.parseInt(random(4) + 0.5f);
    switch (whichImage) {
      case 1:
        this.img = strawberry;
        break;
      case 2:
        this.img = cherry;
        break;
      case 3:
        this.img = orange;
        break;
      default:
        this.img = apple;
        break;
    }
    
    // decide after how many dots
    this.dotLimit = PApplet.parseInt( random(totalDots - 70, totalDots - 15) );
    //this.dotLimit = int( random(5, 6) );
  }
  
}
PVector ghostStartLoc = new PVector(10,13,0);
PVector ghostBoxMid = new PVector(10,12,0);
PVector ghostBoxLeft = new PVector(9,12,0);
PVector ghostBoxRight = new PVector(11,12,0);
float curGhostSpeed;
float FRIGHTENED_SPEED = PAC_SPEED*.30f;
float TUNNEL_SPEED = PAC_SPEED*.55f;
float DEAD_SPEED = 4.0f/60.0f;
int frightenedC = 0xff0032F2;
int frightenedTime;
int frightenedTimer = 0;


class Clyde extends Ghost {
  
  Clyde() {
    fillC = 0xffFF7300;
    homeBase = new PVector(-2,-2); // upper left
    ghostBox = ghostBoxRight;
    initialize();
  }
  
  public void initialize() {
    ghostInitialize();
    loc.set(ghostBox);
    moving = false;
    inBox = true;
    norm_speed = curGhostSpeed;
  }
  
  public void determineChaseTarget() {
    // if less than 8 tiles from pacman, target hometile
    // else same as blinky
    int targetX, targetY;
    int distance = 0;
    distance += abs(loc.x - pac.loc.x);
    distance += abs(loc.y - pac.loc.y);
    if (distance < 6) {
      target.set(homeBase);
    } else {
      target.set(blinky.target);
    }
    
  }
  
}


class Inky extends Ghost {
  
  Inky() {
    fillC = 0xff00CEFF;
    homeBase = new PVector(mapW+2,mapH+6); // lower right
    ghostBox = ghostBoxLeft;
    initialize();
  }
  
  public void initialize() {
    ghostInitialize();
    loc.set(ghostBox);
    moving = false;
    inBox = true;
    norm_speed = curGhostSpeed;
  }
  
  public void determineChaseTarget() {
    // target is 4 squares ahead of pac
    int targetX, targetY;
    int pacTileX = PApplet.parseInt(pac.loc.x);
    int pacTileY = PApplet.parseInt(pac.loc.y);
    pacTileX += pac.direction.x*2;
    pacTileY += pac.direction.y*2;
    
    targetX = PApplet.parseInt(2*(pacTileX - blinky.loc.x));
    targetY = PApplet.parseInt(2*(pacTileY - blinky.loc.y));
    
    target.set(PApplet.parseFloat(targetX),PApplet.parseFloat(targetY),0.0f);
  }
  
}


class Pinky extends Ghost {
  
  Pinky() {
    fillC = 0xffFF74B7;
    homeBase = new PVector(-1,mapH+2); // bottom left
    ghostBox = ghostBoxMid;
    initialize();
  }
  
  public void initialize() {
    ghostInitialize();
    loc.set(ghostBox);
    moving = false; // start right away
    inBox = true;
    norm_speed = curGhostSpeed;
    //moveRequested = 3; // SOUTH
  }
  
  public void determineChaseTarget() {
    // target is 4 squares ahead of pac
    int targetX = PApplet.parseInt(pac.loc.x);
    int targetY = PApplet.parseInt(pac.loc.y);
    targetX += pac.direction.x*4;
    targetY += pac.direction.y*4;
    
    target.set(PApplet.parseFloat(targetX),PApplet.parseFloat(targetY),0.0f);
    
  }
  
}


class Blinky extends Ghost {
  
  Blinky() {
    initialize();
    fillC = 0xffF20000;
    homeBase = new PVector(mapW+1,-1); // upper right
    ghostBox = new PVector(10,14);
  }
  
  public void initialize() {
    ghostInitialize();
    loc.set(ghostStartLoc);
    moving = false; // start right away
    inBox = false;
    moveRequested = 4;
    norm_speed = curGhostSpeed;
  }
  
  public void determineChaseTarget() {
    // target is pac's location
    target.set(pac.loc);
  }
  
}


class Ghost extends Man {
  PVector target;
  PVector homeBase;
  PVector ghostBox;
  int mode; // scatter-0, chase-1, frightened-2
  boolean frightened;
  boolean dead;
  int fillC, curFill;
  float norm_speed;
  //int frightenedTimer = 0;
  boolean reverseGhost;
  
  Ghost() {
    ghostInitialize();
    isGhost = true;
  }
  
  public void ghostInitialize() {
    moveRequested = 0;
    moving = false;
    mode = phaseModes[curPhase];
    dead = false;
    target = new PVector(0,0,0);
    loc = new PVector(0,0,0);
    //direction = new PVector(-1,0); // edited
    direction = new PVector(0,0);
    frightened = false;
    reverseGhost = false;
  }
  
  public void drawGhost() {
    PVector drawLoc = getLoc(loc.y,loc.x);
    
    if (!dead) {
      ellipseMode(CENTER);
      stroke(0);
      if (!frightened)
        fill(fillC);
      else
        fill(curFill);
      ellipse(drawLoc.x, drawLoc.y, pacDiameter-2, pacDiameter-2);
      
      rectMode(CENTER);
      noStroke();
      rect(drawLoc.x, drawLoc.y+pacDiameter/4, pacDiameter-2, pacDiameter/2);
    }
    
    drawEyes(drawLoc);
  }
  
  public void drawEyes(PVector drawLoc) {
    fill(255);
    noStroke();
    ellipseMode(CENTER);
    // draw eyes
    ellipse(drawLoc.x-3, drawLoc.y, 5, 8); // left
    ellipse(drawLoc.x+3, drawLoc.y, 5, 8); // right
    
    // draw pupils
    if (!frightened) {
      fill(0);
      ellipse(drawLoc.x-3+2*direction.x, drawLoc.y+3*direction.y, 2, 2); // left
      ellipse(drawLoc.x+3+2*direction.x, drawLoc.y+3*direction.y, 2, 2); // right
    }
  }
  
  public void updateCondition() {
    
    updateMove();
    
    if (!inBox)
      updateMoveDecision();
    
    // handle speed
    if (dead) {
      speed = DEAD_SPEED;
    } else if (frightened) {
      speed = FRIGHTENED_SPEED;
    } else {
      if (inTunnel) {
        speed = TUNNEL_SPEED;
      } else {
        speed = norm_speed;
      }
    }
    
    // see if we need to stop frightened
    if (frightened) {
      if (millis() - frightenedTimer > frightenedTime) {
        frightened = false;
        ghostMult = 1;
      } else if (frightenedTime - (millis() - frightenedTimer) < 2000) {
        float fraction = PApplet.parseFloat((frightenedTime - (millis() - frightenedTimer)))/2000.0f;
        curFill = avgColor(frightenedC, fillC, 1-fraction);
      }
    }
    
    // if we are in the box we need to move out
    if (inBox && moving && !dead) {
      if (abs(PVector.dist(loc,ghostBoxLeft)) < 2*speed && direction.x==0 && direction.y==0) {
        moveRequested = 2;
      } else if (abs(PVector.dist(loc,ghostBoxRight)) < 2*speed && direction.x==0 && direction.y==0) {
        moveRequested = 4;
      } else if (abs(PVector.dist(loc,ghostBoxMid)) < 2*speed && direction.y==0) {
        moveRequested = 3;
      } else if (abs(PVector.dist(loc,ghostStartLoc)) < 2*speed) {
        // move out of the box
        loc.x = round(loc.x);
        loc.y = round(loc.y);
        inBox = false;
        //direction.x = 0; direction.y = 0;
        moveRequested = 4;
      }
    }
    
    // check for pacman collision
    if (!dead) {
      float pacDist = loc.dist(pac.loc);
      if (pacDist <= 8*PAC_SPEED) {
        
        if (frightened) {
          // ghost die!
          killGhost(this);
          globalGhostKillCount++;
        } else {
          // pacman die!
          pacDie();
        }
        
      }
    }
    
    // move dead ghost back to home
    if (dead) {
      if (loc.dist(ghostBoxMid) < 2*PAC_SPEED) {
        // if close enough to home, make alive again! rawr payback!
        direction.y = 1;
        dead = false;
        loc.set(ghostBoxMid);
      } else if (loc.dist(ghostStartLoc) < 2*PAC_SPEED) {
        inBox = true;
        direction.y = -1;
        direction.x = 0;
        loc.x = ghostStartLoc.x;
      }
    }
    
    
    
  }
  
  public void frighten() {
    if (moving && !dead) {
      frightened = true;
      reverseGhost = true;
      frightenedTimer = millis();
      curFill = frightenedC;
    }
  }
  
  public void kill() {
    dead = true;
    target.set(ghostBox);
  }
    
  
  public void determineChaseTarget() {
    // empty - defined in subclass
  }
  
  public void determineTarget() {
    if (frightened) {
      // frightened
      target.set(PApplet.parseInt(random(0,mapW)), PApplet.parseInt(random(0,mapH)), 0.0f);
    } else {
      if (mode == 0) {
        // SCATTER
        target.set(homeBase);
      } else if (mode == 1) {
        // CHASE
        determineChaseTarget(); // to be filled by subclass
      }
    }
  }
  
  public void updateMoveDecision() {
        
    if (!inTunnel && !dead) {
      determineTarget();
      
      if (reverseGhost) {
        if (abs(loc.x-PApplet.parseInt(loc.x)) < 2*speed && abs(loc.y-PApplet.parseInt(loc.y)) < 2*speed) {
          if (lastMove == 1)
            moveRequested = 3;
          else if (lastMove == 2)
            moveRequested = 4;
          else if (lastMove == 3)
            moveRequested = 1;
          else
            moveRequested = 2;
          reverseGhost = false;
          return;
        } else {
          return;
        }
      }
      
      inTunnel = loc.x < 0 || loc.x >= mapW;
      
      if (moveRequested == 0 && !inTunnel && !dead) {
        // no decision made for a move - update moveRequest
        int curX = round(loc.x);
        int curY = round(loc.y);
        PVector nextTile = new PVector(curX+direction.x, curY+direction.y, 0.0f);
        int nextX = PApplet.parseInt(nextTile.x);
        int nextY = PApplet.parseInt(nextTile.y);
        
        if (nextY != tunnel1.y || (nextX > tunnel1.x && nextX < tunnel2.x)) {
        
          if (level[nextY][nextX].numNeighbors > 2) {
            // make decision based on target tile
            int bestMoveInd=0; float minDist = 9999;
            for (int i=0; i<level[nextY][nextX].numNeighbors; i++) {
              // choose best move based on shortest euclidean distance to 
              // target tile
              PVector thisTile = resultTile(nextTile, level[nextY][nextX].neighbor[i]);
              if (curX != round(thisTile.x) || curY != round(thisTile.y)) {
                float distance = PVector.dist(thisTile, target);
                
                // distance thru tunnel is not the same
                if (thisTile.dist(tunnel1) < .5f) {
                  if (pac.inTunnel)
                    distance = 0;
                  else
                    distance = PVector.dist(target, tunnel2);
                } else if(thisTile.dist(tunnel2) < .5f) {
                  if (pac.inTunnel)
                    distance = 0;
                  else
                    distance = PVector.dist(target, tunnel1);
                }
                
                if (distance < minDist) {
                  minDist = distance;
                  bestMoveInd = i;
                }
              }
            }
            int bestMove = level[nextY][nextX].neighbor[bestMoveInd];
            moveRequested = bestMove;
            
          } else if (level[nextY][nextX].numNeighbors == 2) {
            // take the option that doesnt reverse you
            PVector thisTile = resultTile(nextTile, level[nextY][nextX].neighbor[0]);
            if (curX == round(thisTile.x) && curY == round(thisTile.y)) {
              // if this is current tile, use 2nd option
              moveRequested = level[nextY][nextX].neighbor[1];
            } else {
              // otherwise use first option
              moveRequested = level[nextY][nextX].neighbor[0];
            }
            
          } else {
            // get out of a dead end
            if (direction.x == 0 && direction.y == 0)
              moveRequested = level[nextY][nextX].neighbor[0];
          }
          
        } // end if nextTile != either tunnel
        
      }// end if move requested = 0
      
      
    }// end if not inTunnel and not dead
    else if (dead) {
      // move ghost back to home
      moveRequested = ghostDirections[PApplet.parseInt(loc.y)][PApplet.parseInt(loc.x)];
    }
  }// end void update
  
}// end class Ghost


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



public void loadHighScore() {
  
  String url = "http://joekur.com/pacman/getHiscore.php";
  String html[] = loadStrings(url);
  
  try {
    println("length: " + html.length);
    println(html);
    if (html.length > 0) {
      String temp = getLine(html[0]);
      temp = trim(temp);
      int[] scores = PApplet.parseInt(split(temp,','));
      
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


public boolean submitHighScore(String name, String comment, int score) {
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


public void sendStats() {
  String url = "http://joekur.com/pacman/submitStats.php?"
    + "dots=" + globalDotCount + "&ghosts=" + globalGhostKillCount;
    
  String html[] = loadStrings(url);
  if (html == null) {
    println("error sending stats");
  }
}

public String getLine(String s) {
  // returns line of html, without the extra stuff
  int first = s.indexOf("\"");
  String ans;
  ans = s.substring(first+1, s.length());
  return ans;
}

public String removeHtml(String s) {
  s = s.replaceAll("<", "[");
  s = s.replaceAll(">", "]");
  return s;
}
class Man {
  PVector loc;
  int moveRequested; // 1-N, 2-E, 3-S, 4-W
  int lastMove;
  PVector direction;
  float speed;
  boolean moving;
  boolean inTunnel;
  boolean inBox; // only may be true for a ghost
  boolean cornering;
  int corneringTimer;
  boolean isGhost;
  
  public void updateMove() {
    if (moving) {
      // check if change of direction is required
      if (PApplet.parseBoolean(moveRequested)) {
        if (moveRequested == 1 || moveRequested == 3) {
          // NORTH or SOUTH - x val must be near integer
          if ( abs(loc.x - PApplet.parseInt(loc.x)) < 2*speed && validMove(round(loc.x), round(loc.y), moveRequested).valid || inBox) {
            loc.x = round(loc.x);
            if (direction.x != 0 && isGhost) {
              cornering = true;
              corneringTimer = millis();
            }
            if (moveRequested == 1) {
              // NORTH
              direction.x = 0; direction.y = -1;
            } else {
              // SOUTH
              direction.x = 0; direction.y = 1;
            }
            lastMove = moveRequested;
            moveRequested = 0;
          }
        } else if (moveRequested == 2 || moveRequested == 4) {
          // EAST or WEST - y val must be near integer
          if ( abs(loc.y - PApplet.parseInt(loc.y)) < 2*speed && (validMove(round(loc.x), round(loc.y), moveRequested).valid || inBox)) {
            loc.y = round(loc.y);
            if (direction.y != 0 && isGhost) {
              cornering = true;
              corneringTimer = millis();
            }
            if (moveRequested == 2) {
              // EAST
              direction.x = 1; direction.y = 0;
            } else {
              // WEST
              direction.x = -1; direction.y = 0;
            }
            lastMove = moveRequested;
            moveRequested = 0;
          }
        }
      }
      
      // account for ghost cornering
      float curSpeed=0;
      if (isGhost) {
        if (cornering) {
          curSpeed = speed*.6f;
        } else {
          curSpeed = speed;
        }
        if (cornering && millis() - corneringTimer > 1000.0f*8.0f/60.0f) {
          cornering = false;
        }
      } else {
        curSpeed = speed;
      }
      
      // calculate next position
      PVector nextLoc = new PVector(0,0);
      nextLoc.x = loc.x + curSpeed*direction.x;
      nextLoc.y = loc.y + curSpeed*direction.y;
      // check if we're passing a tile midpoint in this timestep
      if (abs(floor(nextLoc.x)-floor(loc.x)) > 0.5f || abs(floor(nextLoc.y)-floor(loc.y)) > 0.5f) {
        int nextMove;
        if (nextLoc.y < loc.y) {
          nextMove = 1; // NORTH
        } else if (nextLoc.y > loc.y) {
          nextMove = 3; // SOUTH
        } else if (nextLoc.x > loc.x) {
          nextMove = 2; // EAST
        } else {
          nextMove = 4; // WEST
        }
        MoveResult moveResult = validMove(round(nextLoc.x),round(nextLoc.y),nextMove);
        boolean valid = moveResult.valid;
        inTunnel = moveResult.tunnel;
        
        if (valid || inBox) {
          // continue moving
          loc = nextLoc;
        } else {
          // stop
          loc.x = round(loc.x);
          loc.y = round(loc.y);
          direction.set(0.0f,0.0f,0.0f);
        }
        
        
      } else {
        // NOT passing midpoint - continue moving
        loc.x = loc.x + curSpeed*direction.x;
        loc.y = loc.y + curSpeed*direction.y;
      }
      
      // check if its in the tunnel
      if (inTunnel) {
        if (loc.x > tunnel2.x+1) {
          loc.x = tunnel1.x-1;
        } else if (loc.x < tunnel1.x-1) {
          loc.x = tunnel2.x+1;
        }
      }
      
//      if (PVector.dist(loc,tunnel1) < speed*2 && lastMove == 4) {
//        inTunnel = true;
//        // left tunnel
//        direction.set(-1.0,0.0,0.0);
//        loc = PVector.add(loc, PVector.mult(direction,speed));
//        if (loc.x <= -1.0 + speed*2) {
//          // move to next tunnel
//          loc.x = tunnel2.x;
//        }
//      }
//      if (PVector.dist(loc,tunnel2) < speed*2 && lastMove == 2) {
//        inTunnel = true;
//        // right tunnel
//        direction.set(1.0,0.0,0.0);
//        loc = PVector.add(loc, PVector.mult(direction,speed));
//        println("loc " + loc.x);
//        println(tunnel2.x + 1 - speed*2);
//        if (loc.x >= tunnel2.x+.5) {
//          // move to next tunnel
//          println("warped");
//          loc.x = tunnel1.x;
//        }
//      }
          
      
    }  // end if moving
  } // end void updateMove()
}
float TILE_SIZE = 20;
int mapW, mapH;
String fileIN[];
Tile level[][]; // [y][x]
boolean isPassable[][];
PVector levelLoc = new PVector(90,95); // where to shift level in viewing screen
PImage cherry, apple, strawberry, orange, pacSpeedImg, pacLaserImg;
int totalDots = 0, dotsEaten = 0;
PVector tunnel1 = new PVector(0,11);
PVector tunnel2 = new PVector(20,11);
int ghostDirections[][];

class MoveResult {
  boolean valid;
  boolean tunnel;
}

class Tile {
  boolean passable;
  char type;
  boolean eaten;
  int dotType; // 0-none, 1-normal, 2-superdot
  int numNeighbors;
  int neighbor[]; // describes the move: 1N,2E,3S,4W
  
  Tile() {
    dotType = 0;
    eaten = false;
    passable = false;
    numNeighbors = 0;
    neighbor = new int[4];
  }
}

public void readMapFile() {
  fileIN = loadStrings("pacman_map.txt");
  mapW = PApplet.parseInt(fileIN[0]);
  mapH = PApplet.parseInt(fileIN[1]);
  
  level = new Tile[mapH][mapW];
  for (int i=2; i<fileIN.length; i++) {
    String thisLine = fileIN[i];
    for (int j=0; j<mapW; j++) {
      level[i-2][j] = new Tile();
      level[i-2][j].type = thisLine.charAt(j);
      if (thisLine.charAt(j) == 'A' || thisLine.charAt(j) == 'B' || thisLine.charAt(j) == 'a') {
        level[i-2][j].passable = true;
      }
      if (thisLine.charAt(j) == 'A') {
        // normal dot
        level[i-2][j].dotType = 1;
        totalDots++;
      } else if (thisLine.charAt(j) == 'a') {
        // super dot
        level[i-2][j].dotType = 2;
        totalDots++;
      }
    }
  }
  // bug???
  totalDots--;
  
  // count number of neighbors
  for (int i=0; i<mapH; i++) {
    for (int j=0; j<mapW; j++) {
      if (level[i][j].passable) {
        int numNeighbor = 0;
        // top neighbor
        if (i>0) {
          if (level[i-1][j].passable) {
            level[i][j].neighbor[numNeighbor] = 1;
            numNeighbor++;
          }
        }
        // bottom neighbor
        if (i<mapH-1) {
          if (level[i+1][j].passable) {
            level[i][j].neighbor[numNeighbor] = 3;
            numNeighbor++;
          }
        }
        // left neighbor
        if (j>0) {
          if (level[i][j-1].passable) {
            level[i][j].neighbor[numNeighbor] = 4;
            numNeighbor++;
          }
        }
        // right neighbor
        if (j<mapW-1) {
          if (level[i][j+1].passable) {
            level[i][j].neighbor[numNeighbor] = 2;
            numNeighbor++;
          }
        }
        level[i][j].numNeighbors = numNeighbor;
      }
    }
  }
  
  // load images into memory
  loadImages();
  // read in ghost directions for returning to home
  readGhostDirections();
}


float pulse = 1;
int direction = 1;

public void drawLevel() {
  
  strokeWeight(1);
  
  // draw walls
  for (int i=0; i<mapH; i++) {
    for (int j=0; j<mapW; j++) {
      if (level[i][j].type == 'W' || level[i][j].type == 'w') {
        pushMatrix();
        
        PVector loc = getLoc(i,j);
        translate(loc.x, loc.y);
        rectMode(CENTER);
        stroke(0);
        if (level[i][j].type == 'W') {
          if (curLevel >= 25) {
            fill(avgColor(0xffFF035F, color(255), pulse));
            pulse += 0.0001f*direction;
            if (pulse < 0 || pulse > 1) {
              direction *= -1;
              constrain(pulse,0,1);
            }
          } else {
            fill(0xffFF035F);
          }
        } else {
          fill(0xff0046FF);
        }
        rect(0,0,TILE_SIZE,TILE_SIZE);
                
        popMatrix();
      } else if (level[i][j].type == 'A' || level[i][j].type == 'a') {
        
        if (!level[i][j].eaten) {
          // draw dot
          pushMatrix();
          PVector loc = getLoc(i,j);
          translate(loc.x, loc.y);
          ellipseMode(CENTER);
          fill(0xffFFF708);
          int circleRad;
          if (level[i][j].dotType == 1) {
            circleRad = 5;
            ellipse(0,0,circleRad,circleRad);
          } else {
            circleRad = 10;
            beginShape();
            float angle = TWO_PI/(2*5);
            for (int v=0; v<2*5; v++) {
              float rad;
              if (v % 2 == 1) {
                rad = 4;
              } else {
                rad = 8;
              }
              vertex(rad*cos(angle*v), rad*sin(angle*v));
            }
            endShape(CLOSE);
          }
          
          popMatrix();
        }
      }
    }
  }
  
  // draw ghost box
  pushMatrix();
  PVector loc = getLoc(12,10);
  translate(loc.x, loc.y);
  noFill();
  stroke(0xffFF035F);
  rectMode(CENTER);
  rect(0,0,TILE_SIZE*3,TILE_SIZE);
  // draw door
  fill(255);
  rect(0,TILE_SIZE/2,TILE_SIZE,3);
  popMatrix();
  
  // draw cherry
  pushMatrix();
  loc = getLoc(7,17);
  translate(loc.x, loc.y);
  imageMode(CENTER);
  image(cherry,0,0,18,18);
  popMatrix();
  
  
}


public void initializeDots() {
  for (int i=0; i<mapH; i++) {
    for (int j=0; j<mapW; j++) {
      level[i][j].eaten = false;
    }
  }
}

public void drawTunnel() {
  // draw tunnel "curtains"
  fill(0);
  noStroke();
  
  pushMatrix();
  PVector loc = getLoc(tunnel1.y,tunnel1.x-1);
  translate(loc.x, loc.y);
  rect(0,0,TILE_SIZE,TILE_SIZE);
  popMatrix();
  pushMatrix();
  loc = getLoc(tunnel2.y,tunnel2.x+1);
  translate(loc.x, loc.y);
  rect(0,0,TILE_SIZE,TILE_SIZE);
  popMatrix();
}


public PVector resultTile(PVector curTile, int moveType) {
  PVector ans = new PVector(0.0f,0.0f,0.0f);
  switch(moveType) {
    case 1: // NORTH
      ans.x = curTile.x;
      ans.y = curTile.y - 1;
      break;
    case 2: // EAST
      ans.x = curTile.x + 1;
      ans.y = curTile.y;
      break;
    case 3: // SOUTH
      ans.x = curTile.x;
      ans.y = curTile.y + 1;
      break;
    case 4: // WEST
      ans.x = curTile.x - 1;
      ans.y = curTile.y;
      break;
    default:
      break;    
  }
  return ans;
}




// returns true if tile to move to is passable
public MoveResult validMove(int locx, int locy, int moveRequested) {
  int next_locx=-1, next_locy=-1;
  boolean valid = false;
  boolean tunnel = false;
  MoveResult ans = new MoveResult();
  
  PVector temp = new PVector(locx,locy,0);
  PVector next_loc = resultTile(temp, moveRequested);
  next_locx = PApplet.parseInt(next_loc.x);
  next_locy = PApplet.parseInt(next_loc.y);
  
  if (next_locx >= 0 && next_locx < mapW) {
    if (next_locy >= 0 && next_locy < mapH) {
      if (level[next_locy][next_locx].passable) {
        valid = true;
      }
    }
  }
  
  // what if its the tunnel?
  if (next_locy == tunnel1.y) {
    if ((next_locx <= tunnel1.x || next_locx >= tunnel2.x) && (moveRequested == 2 || moveRequested == 4)) {
      valid = true;
      tunnel = true;
    }
  }
  
  ans.valid = valid;
  ans.tunnel = tunnel;
  return ans;
}


public PVector getLoc(float row, float col) {
  PVector ans = new PVector(0,0);
  ans.x = levelLoc.x + col*TILE_SIZE;
  ans.y = levelLoc.y + row*TILE_SIZE;
  return ans;
}

public void loadImages() {
  strawberry = loadImage("strawberry.gif");
  cherry = loadImage("cherry.gif");
  apple = loadImage("apple.gif");
  orange = loadImage("orange.gif");
  pacSpeedImg = loadImage("pacSpeed.png");
  pacLaserImg = loadImage("pacLaser.png");
}

public void readGhostDirections() {
  String file[] = loadStrings("ghostDirections.txt");
  ghostDirections = new int[mapH][mapW];
  
  for (int i=0; i<mapH; i++) {
    String thisLine = file[i];
    for (int j=0; j<mapW; j++) {
      char thisChar = thisLine.charAt(j);
      switch (thisChar) {
        case 'u':
          ghostDirections[i][j] = 1;
          break;
        case 'r':
          ghostDirections[i][j] = 2;
          break;
        case 'd':
          ghostDirections[i][j] = 3;
          break;
        case 'l':
          ghostDirections[i][j] = 4;
          break;
        default:
          ghostDirections[i][j] = 0;
          break;
      }
    }
  }  
}
float pacDiameter = TILE_SIZE*.9f;
float curPacSpeed = PAC_SPEED;

class Pac extends Man {
  int lives;
  float mouthState;
  int mouthDirection;
  float mouthAngle;
  int powerClass;
  circBuffer posBuffer;

  Pac() {
    initialize();
    lives = 3;
    inBox = false;
    isGhost = false;
  }
  
  public void initialize() {
    loc = new PVector(10.0f,7.0f);
    direction = new PVector(1,0);
    moveRequested = 0;
    lastMove = 2;
    moving = false;
    speed = curPacSpeed;
    mouthState = 0.0f;
    mouthDirection = 1;
    inTunnel = false;
    posBuffer = new circBuffer(15);
  }
  
  public void update() {
    boolean dotEatenThisFrame = false;
    
    posBuffer.insert(this.loc);
    
    if (!inTunnel) {
      int curX = round(loc.x);
      int curY = round(loc.y);
      if (!level[curY][curX].eaten && (level[curY][curX].type == 'a' || level[curY][curX].type == 'A')) {
        dotEatenThisFrame = true;
        level[curY][curX].eaten = true;
        dotsEaten++;
        addScore(10);
        globalDotCount++;
        if (level[curY][curX].dotType == 2) {
          ghostMult = 1;
          blinky.frighten();
          pinky.frighten();
          inky.frighten();
          clyde.frighten();
          phaseTimer += frightenedTime;
        }
      }
    }
    
    if (!dotEatenThisFrame)
      updateMove();
      
      
    // special classes
    if (this.powerClass == SPEEDSTER) {
      
    }
  }
  
  public void drawPac() {
    PVector drawLoc = getLoc(loc.y,loc.x);
    ellipseMode(CENTER);
    stroke(0);
    fill(0xffFFF708);
    
    // move mouth
    if (lastMove==3) {
      // SOUTH
      mouthAngle = PI/2;
    } else if (lastMove==1) {
      // NORTH
      mouthAngle = 3*PI/2;
    } else if (lastMove==2) {
      // EAST
      mouthAngle = 0;
    } else {
      // WEST
      mouthAngle = PI;
    }
    
    if (mouthState >= 1) {
      mouthDirection = -1;
    } else if (mouthState <= 0) {
      mouthDirection = 1;
    }
    mouthState += 0.04f*mouthDirection;
    
    arc(drawLoc.x, drawLoc.y, pacDiameter, pacDiameter, mouthAngle+PI*mouthState/4, mouthAngle+2*PI-PI*mouthState/4);
    
    if (this.powerClass == SPEEDSTER) {
      if (superPower.getBoostActive() && this.loc.x>0 && this.loc.x<mapW) {
        PVector oneBefore = this.loc.get();
        for (int i=posBuffer.numContent-1; i>0; i--) {
          PVector prevPos = posBuffer.getVal(i);
          PVector drawLoc1 = getLoc(prevPos.y,prevPos.x);
          
          PVector midPos = PVector.add(prevPos,oneBefore);
          midPos.mult(0.5f);
          PVector drawLoc2 = getLoc(midPos.y,midPos.x);
          
          PVector pos3 = PVector.add(prevPos,midPos);
          pos3.mult(0.5f);
          PVector drawLoc3 = getLoc(pos3.y,pos3.x);
          
          PVector pos4 = PVector.add(oneBefore,midPos);
          pos4.mult(0.5f);
          PVector drawLoc4 = getLoc(pos4.y,pos4.x);
          
          oneBefore.set(prevPos.x,prevPos.y,0);
          
          float a = 20.0f*PApplet.parseFloat(i)/PApplet.parseFloat(posBuffer.numContent);
          fill(yellow, a);
          noStroke();
          arc(drawLoc1.x, drawLoc1.y, pacDiameter, pacDiameter, mouthAngle+PI*mouthState/4, mouthAngle+2*PI-PI*mouthState/4);
          arc(drawLoc2.x, drawLoc2.y, pacDiameter, pacDiameter, mouthAngle+PI*mouthState/4, mouthAngle+2*PI-PI*mouthState/4);
          arc(drawLoc3.x, drawLoc3.y, pacDiameter, pacDiameter, mouthAngle+PI*mouthState/4, mouthAngle+2*PI-PI*mouthState/4);
          arc(drawLoc4.x, drawLoc4.y, pacDiameter, pacDiameter, mouthAngle+PI*mouthState/4, mouthAngle+2*PI-PI*mouthState/4);
        }
        
      }
    }
    
    
  }
  
  

}



/////////////////////
// CIRCULAR BUFFER //
/////////////////////
class circBuffer {
  int sizeArray;
  PVector[] buffer;
  int head, tail;
  int numContent;
  
  circBuffer(int size_) {
    sizeArray = size_;
    buffer = new PVector[sizeArray];
    head = 0;
    tail = 1;
    numContent = 0;
  }
  
  public void insert(PVector i) {
    buffer[tail] = new PVector(0,0);
    buffer[tail].x = i.x;
    buffer[tail].y = i.y;
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
  
  public PVector getVal(int ind) {
    int i = ind + head;
    if (i > sizeArray - 1) {
      i -= sizeArray;
    }
    return buffer[i].get();
  }
  
}
int redC = 0xffFF0A0A;
final int SPEEDSTER = 0;
final int FIGHTER = 1;
final int MAGE = 2;

class SuperPower {
  int type;
  
  SuperPower() {
    type = SPEEDSTER;
  }
  
  public void reset() {}
  public void update() {}
  public void drawHUD() {}
  public void ammoPickup() {}
  public void activate() {}
  public boolean getBoostActive() {
    return false;
  }
  
}


///////////////
// SPEEDSTER //
///////////////
class SpeedPower extends SuperPower {
  float maxBoost;
  float boostLeft;
  boolean boostActive;
  float lossPerSec = 10.0f;
  
  SpeedPower() {
    maxBoost = 100.0f;
    boostLeft = 40;
    boostActive = false;
  }
  
  public void reset() {
    boostActive = false;
  }
  
  public boolean getBoostActive() {
    return boostActive;
  }
  
  public void ammoPickup() {
    boostLeft += maxBoost / 5;
    boostLeft = constrain(boostLeft, 0, maxBoost);
  }
  
  public void activate() {
    if (boostActive) {
      boostActive = false;
    } else if (!boostActive && boostLeft > 0) {
      boostActive = true;
    }
  }
  
  public void update() {
    
    // handle pac speed
    if (boostActive) {
      pac.speed = curPacSpeed * 2.0f;
      boostLeft -= lossPerSec / frameRate;
      if (boostLeft <= 0) {
        boostActive = false;
        boostLeft = 0;
      } 
    } else {
      pac.speed = curPacSpeed;
    }
    
    drawHUD();
    
  }
  
  public void drawHUD() {
    float offsetX = width/2 - 50;
    float offsetY = height - 65;
    
    rectMode(CORNER);
    stroke(255);
    fill(0);
    rect(offsetX, offsetY, 100, 20);
    noStroke();
    fill(redC);
    rect(offsetX+1,offsetY+1, 99.0f*boostLeft/maxBoost, 18);
    rectMode(CENTER);
  }
}


/////////////
// FIGHTER //
/////////////
class LaserPower extends SuperPower {
  int shotsLeft;
  int maxShots;
  int lastShotTime;
  int coolDown = 1000 * 4;
  
  LaserPower() {
    shotsLeft = 2;
    maxShots = 5;
    lastShotTime = 0;
  }
  
  public void reset() {}
  
  public void update() {}
  
  public void ammoPickup() {
    shotsLeft++;
    if (shotsLeft > maxShots) {
      shotsLeft = maxShots;
    }
  }
  
  public void activate() {
    if (millis() - coolDown > lastShotTime && shotsLeft > 0) {
           
      
      PVector startTile, endTile;
      if (pac.loc.y == tunnel1.y && pac.direction.y == 0) {
        // laser whole row
        startTile = new PVector(-1, tunnel1.y);
        endTile = new PVector(mapW, tunnel1.y);
      } else {
      
        // determine tiles that laser will affect
        startTile = pac.loc.get();
        startTile.x = round(startTile.x);
        startTile.y = round(startTile.y);
        // if pacman near next tile, move start tile up one
        PVector nextTile = new PVector(0,0);
        nextTile.x = startTile.x + pac.direction.x;
        nextTile.y = startTile.y + pac.direction.y;
        float distToNextTile = PVector.dist(nextTile, pac.loc);
        if (distToNextTile < 0.85f) {
          startTile = nextTile.get();
        }
        
        // check if pac is against a wall
        if (pac.direction.x == 0 && pac.direction.y == 0) {
          return;
        }
        
        PVector curTile = startTile.get();
        while (level[PApplet.parseInt(curTile.y + pac.direction.y)][PApplet.parseInt(curTile.x + pac.direction.x)].passable) {
          int constrainedX = constrain(PApplet.parseInt(curTile.x + pac.direction.x), 1, mapW-2);
          int constrainedY = constrain(PApplet.parseInt(curTile.y + pac.direction.y), 1, mapH-2);
          if (constrainedX != PApplet.parseInt(curTile.x + pac.direction.x) || constrainedY != PApplet.parseInt(curTile.y + pac.direction.y)) {
            break;
          }
          if (!level[PApplet.parseInt(curTile.y + pac.direction.y)][PApplet.parseInt(curTile.x + pac.direction.x)].passable) {
            break;
          }
          
          curTile.x += pac.direction.x;
          curTile.y += pac.direction.y;
        }
        endTile = curTile.get();
      }
      
      
      lastShotTime = millis();
      shotsLeft--;
      laserAnimation = new LaserAnimation(startTile, endTile, pac.direction);
    
    }
    
  }
  
  public void drawHUD() {
    float offsetX = width/2 - 45;
    float offsetY = height - 65;
    int ammoOffset = 0;
    
    stroke(255);
    rectMode(CORNER);
    for (int i=0; i<maxShots; i++) {
      if (i<shotsLeft) {
        fill(0xffFF3E1C);
      } else {
        fill(0xffFF3E1C, 20);
      }
      rect(offsetX+ammoOffset, offsetY, 10, 20);
      ammoOffset += 20;
    }
    rectMode(CENTER);
  }
  
}

LaserAnimation laserAnimation;
boolean laserAnimationRunning = false;
class LaserAnimation {
  PVector start, end;
  boolean[] isDead;
  PVector direction;
  int numTiles;
  float progress;
  boolean increasing; // true while laser grows
  float lineWidth;
  int ghostsKilled;
  PVector scoreLoc;
  int bonusScore;
  
  LaserAnimation(PVector start_, PVector end_, PVector direction_) {
    start = start_.get();
    end = end_.get();
    direction = direction_.get();
    numTiles = PApplet.parseInt(abs(start.x-end.x) + abs(start.y-end.y) + 1);
    isDead = new boolean[ghosts.length];
    progress = 0;
    increasing = true;
    lineWidth = 0;
    ghostsKilled = 0;
    
    this.determineDeadGhosts();
    
    animation = true;
    laserAnimationRunning = true;
  }
  
  public void determineDeadGhosts() {
    for(int i=0; i<ghosts.length; i++) {
      Ghost g = ghosts[i];
      this.isDead[i] = false;
      
      for(int tileInd=0; tileInd<this.numTiles; tileInd++) {
        PVector curTile = new PVector(0,0);
        if (this.numTiles > 20) {
          // whole row
          curTile.x = start.x + tileInd;
          curTile.y = start.y;
        } else {
          curTile.x = start.x + this.direction.x * tileInd;
          curTile.y = start.y + this.direction.y * tileInd;
        }
        
        float distToLaser = PVector.dist(curTile,g.loc);
        if (distToLaser <= 0.6f) {
          this.isDead[i] = true;
          ghostsKilled++;
          break;
        }
      } 
    } // end loop thru ghosts
    
  } // end determineDeadGhosts()
  
  public void run() {
    if (progress < 0.5f) {
      pac.mouthState = progress * 2;
    } else {
      pac.mouthState = (1-progress) * 2;
    }
    
    this.progress += 0.5f / frameRate;
    if (progress > 0.5f && this.increasing) {
      this.increasing = false;
      if(progress - 0.5f / frameRate <= 0.5f) {
        // kill the ghosts here
        for (int i=0; i<ghosts.length; i++) {
          if (this.isDead[i]) {
            ghosts[i].dead = true;
            ghosts[i].frightened = false;
          }
        }
        if (ghostsKilled == 1) {
          this.bonusScore = 500;
        } else if (ghostsKilled == 2) {
          this.bonusScore = 1000;
        } else if (ghostsKilled == 3) {
          this.bonusScore = 2000;
        } else if (ghostsKilled == 4) {
          this.bonusScore = 3000;
        } else {
          this.bonusScore = 0;
        }
        addScore(bonusScore);
        
        // find score loc
        scoreLoc = getLoc(pac.loc.y,pac.loc.x);
        float ran = random(0,1);
        int ranOffX = round(ran);
        if (ranOffX == 0) { ranOffX = -1; }
        ran = random(0,1);
        int ranOffY = round(ran);
        if (ranOffY == 0) { ranOffY = -1; }
        scoreLoc.x += 20 * ranOffX;
        scoreLoc.y += 20 * ranOffY;
      }
    }
    if (increasing) {
      this.lineWidth += 12.0f / frameRate;
    } else {
      this.lineWidth -= 12.0f / frameRate;
      // draw score
      if (bonusScore > 0) {
        fill(255);
        textFont(font, 16);
        text(bonusScore, scoreLoc.x, scoreLoc.y);
      }
    }
    
    lineWidth = constrain(lineWidth, 0, 100);
    
    if (progress >= 1) {
      animation = false;
      laserAnimationRunning = false;
    }
    
    this.drawLaser();
    
  } // end run()
  
  public void drawLaser() {
    strokeWeight(lineWidth);
    stroke(0xffFF3E1C);
    PVector drawLoc1 = getLoc(this.start.y, this.start.x);
    PVector drawLoc2 = getLoc(this.end.y, this.end.x);
    line(drawLoc1.x, drawLoc1.y, drawLoc2.x, drawLoc2.y);
    
    strokeWeight(1);
  }
  
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "pacman" });
  }
}
