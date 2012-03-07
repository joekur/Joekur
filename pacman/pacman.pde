import guicomponents.*;
G4P g = new G4P();
GTextField commentBox, nameBox;

Pac pac;
Blinky blinky;
Pinky pinky;
Inky inky;
Clyde clyde;
Ghost[] ghosts = new Ghost[4];
color yellow = #FFF708;
boolean levelStarted = false;
int timer, phaseTimer;
int phaseTimes[] = {6000,14000,6000,14000,4000,999999};
int phaseModes[] = {0,1,0,1,0,1};
int curLevel = 1, curPhase = 0;
float PAC_SPEED = 4.5/60.0;
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

void setup() {
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

void setupGame() {
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
void setupLevel() {
  curPhase = 0;
  curLevel++;
  
  initializeDots();
  dotsEaten = 0;
  
  fruitSystem.prepareForLevel();
  
  // set level parameters
  if (curLevel < 2) {
    curPacSpeed = .8*PAC_SPEED;
    curGhostSpeed = .7*PAC_SPEED;
    frightenedTime = 9000;
    inkyDots = 90;
    clydeDots = 130;
    
  } else if (curLevel < 5) {
    
    curPacSpeed = .9*PAC_SPEED;
    curGhostSpeed = .8*PAC_SPEED;
    frightenedTime = 8000;
    inkyDots = 80;
    clydeDots = 120;
    
  } else if (curLevel < 10) {
    
    blinky.homeBase = new PVector(-2,-2);
    pinky.homeBase = new PVector(mapW+1,-1);
    inky.homeBase = new PVector(-1,mapH+2);
    clyde.homeBase = new PVector(mapW+2,mapH+6);
    
    curPacSpeed = .9*PAC_SPEED;
    curGhostSpeed = .83*PAC_SPEED;
    frightenedTime = 7500;
    inkyDots = 70;
    clydeDots = 110;
    
  } else if (curLevel < 20) {
    
    blinky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    pinky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    inky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    clyde.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    
    curPacSpeed = PAC_SPEED;
    curGhostSpeed = .95*PAC_SPEED;
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
    
    blinky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    pinky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    inky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    clyde.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    
    curPacSpeed = .9*PAC_SPEED;
    curGhostSpeed = .95*PAC_SPEED;
    frightenedTime = 3000;
    inkyDots = 30;
    clydeDots = 70;
    
    
  } else {
    
    blinky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    pinky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    inky.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    clyde.homeBase = new PVector(int(random(-5,mapW+5)), int(random(-5,mapH+5)));
    
    curPacSpeed = PAC_SPEED;
    curGhostSpeed = 1.06*PAC_SPEED;
    frightenedTime = 1500;
    inkyDots = 25;
    clydeDots = 55;
  }
  
  
  reset();
  
}


void draw() {
  background(0);
  
  drawLevel();
  drawHeader();
  
  if (!animation)
    runGame();
  else
    runAnimation();
}

void runGame() {
  
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

void gameUpdate() {
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


void drawHeader() {
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
    fill(#FFF708);
    noStroke();
    arc(width-22-offSet, 55, 20, 20, PI/4, 2*PI-PI/4);
    offSet += 30;
  }
}

