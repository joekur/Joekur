boolean paused = false;
int pausedTime;
boolean spacePressed = false, spaceClicked = false;

void keyPressed() {
  
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
      fill(#FF3705, 200);
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

void keyReleased() {
  if (key==' ') {
    spacePressed = false;
    spaceClicked = false;
  }
}


void adjustTimers(int delta) {
  timer += delta;
  phaseTimer += delta;
  dyingTimer += delta;
  deadGhostTimer += delta;
  readyTimer += delta;
  frightenedTimer += delta;
}


void seeTextBoxes(boolean b) {
  commentBox.setVisible(b);
  commentBox.setEnabled(b);
  nameBox.setVisible(b);
  nameBox.setEnabled(b);
}
