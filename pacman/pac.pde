float pacDiameter = TILE_SIZE*.9;
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
  
  void initialize() {
    loc = new PVector(10.0,7.0);
    direction = new PVector(1,0);
    moveRequested = 0;
    lastMove = 2;
    moving = false;
    speed = curPacSpeed;
    mouthState = 0.0;
    mouthDirection = 1;
    inTunnel = false;
    posBuffer = new circBuffer(15);
  }
  
  void update() {
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
  
  void drawPac() {
    PVector drawLoc = getLoc(loc.y,loc.x);
    ellipseMode(CENTER);
    stroke(0);
    fill(#FFF708);
    
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
    mouthState += 0.04*mouthDirection;
    
    arc(drawLoc.x, drawLoc.y, pacDiameter, pacDiameter, mouthAngle+PI*mouthState/4, mouthAngle+2*PI-PI*mouthState/4);
    
    if (this.powerClass == SPEEDSTER) {
      if (superPower.getBoostActive() && this.loc.x>0 && this.loc.x<mapW) {
        PVector oneBefore = this.loc.get();
        for (int i=posBuffer.numContent-1; i>0; i--) {
          PVector prevPos = posBuffer.getVal(i);
          PVector drawLoc1 = getLoc(prevPos.y,prevPos.x);
          
          PVector midPos = PVector.add(prevPos,oneBefore);
          midPos.mult(0.5);
          PVector drawLoc2 = getLoc(midPos.y,midPos.x);
          
          PVector pos3 = PVector.add(prevPos,midPos);
          pos3.mult(0.5);
          PVector drawLoc3 = getLoc(pos3.y,pos3.x);
          
          PVector pos4 = PVector.add(oneBefore,midPos);
          pos4.mult(0.5);
          PVector drawLoc4 = getLoc(pos4.y,pos4.x);
          
          oneBefore.set(prevPos.x,prevPos.y,0);
          
          float a = 20.0*float(i)/float(posBuffer.numContent);
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
  
  void insert(PVector i) {
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
  
  PVector getVal(int ind) {
    int i = ind + head;
    if (i > sizeArray - 1) {
      i -= sizeArray;
    }
    return buffer[i].get();
  }
  
}
