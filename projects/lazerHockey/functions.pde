float curHue = 0;
void drawTable() {
  
  // table outline-------------------------------
  colorMode(HSB, 255);
  curHue += 0.5;
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
  color butOff = #400505;
  color butOn = #FF0000;
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
void keyPressed() {
  
  
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
void pause() {
  pausedTime = millis();
  
  rectMode(CENTER);
  fill(100,70);
  rect(width/2,height/2,.95*tableWidth,60);
  textSize(40);
  fill(255);
  textAlign(CENTER);
  text("Paused", width/2, height/2+15);
  
  noLoop();
}
void unpause() {
  timeStart += millis() - pausedTime;
  loop();
}


void keyReleased() {
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

color avgColor(color color1, color color2, float percent) {
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


void stop() {
  // always close Minim audio classes when you are done with them
  bumpSound.close();
  scoreSound.close();
  superSound.close();
  blastSound.close();
  minim.stop();
  
  super.stop();
  
}
