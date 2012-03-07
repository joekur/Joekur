boolean animation = true;
boolean titleScreen = true;
boolean runGameCountdown = false;
boolean gameCountdown = false;
boolean gameEndAni = false;

void runAnimations() {
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
void mouseReleased() {
  mouseRel = true;
}

void titlePage() {
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

void changeMode() {
  mode++;
  if (mode > 1) mode = 0;
  
  if (mode==0) {
    modeString = "Mode: 2-player";
  } else {
    modeString = "Mode: Practice";
  }
  
  modeButton.butText = modeString;
}

void changeDifficulty() {
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
void runGameCountdown() {
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


void gameEnd() {
  
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
  rect(width/2,height/2,.95*tableWidth,100);
  
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



