//color textC = #F6FF00; // yellow
color textC = #FF035F; // pink
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

void addScore(int d) {
  int lastScore = score;
  score += d;
  if ((score % 50000) < (lastScore % 50000)) {
    pac.lives++;
  }
}


void startLevel() {
  levelStarted = true;
  timer = millis();
  phaseTimer = timer;
  pac.moving = true;
  blinky.moving = true;
}

void reset() {
  
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

void pacDie() {
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
void killGhost(Ghost g) {
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

void runAnimation() {
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

void killGhostAnimation() {
  
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

void runTitleScreen() {
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
color selectColorBorder = #00A8FF;
String speedName = "The Speedster";
String laserName = "The Bleedster";
String speedDesc = "Put the pedal to the metal and leave those ghosts in a cloud of dust.";
String laserDesc = "Hit the trigger to spew a molten-hot laser from pacman's mouth and burn your foes to a crisp.";
String className = laserName, classDesc = laserDesc;
boolean enterReleasedClassSelect = false;
void runClassSelectionScreen() {
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


void runLevelCompleteAnimation() {
  
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
  int tSize = int(angle*50/(8*PI));
  
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

void runDyingAnimation() {
  
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
    dyingState += 0.04;
  
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
void runLaserAnimation() {
   
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

void setupGameOver() {
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

void runGameOver() {
  rectMode(CENTER);
  fill(0,150);
  stroke(255);
  strokeWeight(4);
  rect(width/2, height/2, 400, 60);
  strokeWeight(1);
  
  textFont(font2, 50);
  fill(#FF0000);
  text("GAME OVER", width/2, height/2+17);
  
  if (scoreSubmit) {
    hiscoreBoxLoc += 3;
    if (hiscoreBoxLoc > width/2) {
      hiscoreBoxLoc = width/2;
      formReady = true;
    }
    
    curHue += 0.5;
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
