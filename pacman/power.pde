color redC = #FF0A0A;
final int SPEEDSTER = 0;
final int FIGHTER = 1;
final int MAGE = 2;

class SuperPower {
  int type;
  
  SuperPower() {
    type = SPEEDSTER;
  }
  
  void reset() {}
  void update() {}
  void drawHUD() {}
  void ammoPickup() {}
  void activate() {}
  boolean getBoostActive() {
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
  float lossPerSec = 10.0;
  
  SpeedPower() {
    maxBoost = 100.0;
    boostLeft = 40;
    boostActive = false;
  }
  
  void reset() {
    boostActive = false;
  }
  
  boolean getBoostActive() {
    return boostActive;
  }
  
  void ammoPickup() {
    boostLeft += maxBoost / 5;
    boostLeft = constrain(boostLeft, 0, maxBoost);
  }
  
  void activate() {
    if (boostActive) {
      boostActive = false;
    } else if (!boostActive && boostLeft > 0) {
      boostActive = true;
    }
  }
  
  void update() {
    
    // handle pac speed
    if (boostActive) {
      pac.speed = curPacSpeed * 2.0;
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
  
  void drawHUD() {
    float offsetX = width/2 - 50;
    float offsetY = height - 65;
    
    rectMode(CORNER);
    stroke(255);
    fill(0);
    rect(offsetX, offsetY, 100, 20);
    noStroke();
    fill(redC);
    rect(offsetX+1,offsetY+1, 99.0*boostLeft/maxBoost, 18);
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
  
  void reset() {}
  
  void update() {}
  
  void ammoPickup() {
    shotsLeft++;
    if (shotsLeft > maxShots) {
      shotsLeft = maxShots;
    }
  }
  
  void activate() {
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
        if (distToNextTile < 0.85) {
          startTile = nextTile.get();
        }
        
        // check if pac is against a wall
        if (pac.direction.x == 0 && pac.direction.y == 0) {
          return;
        }
        
        PVector curTile = startTile.get();
        while (level[int(curTile.y + pac.direction.y)][int(curTile.x + pac.direction.x)].passable) {
          int constrainedX = constrain(int(curTile.x + pac.direction.x), 1, mapW-2);
          int constrainedY = constrain(int(curTile.y + pac.direction.y), 1, mapH-2);
          if (constrainedX != int(curTile.x + pac.direction.x) || constrainedY != int(curTile.y + pac.direction.y)) {
            break;
          }
          if (!level[int(curTile.y + pac.direction.y)][int(curTile.x + pac.direction.x)].passable) {
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
  
  void drawHUD() {
    float offsetX = width/2 - 45;
    float offsetY = height - 65;
    int ammoOffset = 0;
    
    stroke(255);
    rectMode(CORNER);
    for (int i=0; i<maxShots; i++) {
      if (i<shotsLeft) {
        fill(#FF3E1C);
      } else {
        fill(#FF3E1C, 20);
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
    numTiles = int(abs(start.x-end.x) + abs(start.y-end.y) + 1);
    isDead = new boolean[ghosts.length];
    progress = 0;
    increasing = true;
    lineWidth = 0;
    ghostsKilled = 0;
    
    this.determineDeadGhosts();
    
    animation = true;
    laserAnimationRunning = true;
  }
  
  void determineDeadGhosts() {
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
        if (distToLaser <= 0.6) {
          this.isDead[i] = true;
          ghostsKilled++;
          break;
        }
      } 
    } // end loop thru ghosts
    
  } // end determineDeadGhosts()
  
  void run() {
    if (progress < 0.5) {
      pac.mouthState = progress * 2;
    } else {
      pac.mouthState = (1-progress) * 2;
    }
    
    this.progress += 0.5 / frameRate;
    if (progress > 0.5 && this.increasing) {
      this.increasing = false;
      if(progress - 0.5 / frameRate <= 0.5) {
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
      this.lineWidth += 12.0 / frameRate;
    } else {
      this.lineWidth -= 12.0 / frameRate;
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
  
  void drawLaser() {
    strokeWeight(lineWidth);
    stroke(#FF3E1C);
    PVector drawLoc1 = getLoc(this.start.y, this.start.x);
    PVector drawLoc2 = getLoc(this.end.y, this.end.x);
    line(drawLoc1.x, drawLoc1.y, drawLoc2.x, drawLoc2.y);
    
    strokeWeight(1);
  }
  
}
