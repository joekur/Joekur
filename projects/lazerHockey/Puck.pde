color defFillColor = #FF4000;
int defPtVal = 10, ptValInc = 5;

class Puck {
  int lane;
  float ypos;
  float speed;
  int direction; // 1 going down, -1 going up
  int ptValue;
  color fillColor;
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
  
  void update() {
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
  
  float distToGoal(boolean player1) {
    // player 1's goal at top
    float ans;
    if (player1) {
      ans = ypos - (goalTop + laneWidth/2);
    } else {
      ans = (goalBot - laneWidth/2) - ypos;
    }
    return ans;
  }
  
  void bump(boolean player) {
    if (player) {
      direction = 1;
    } else {
      direction = -1;
    }
    speed = direction * abs(speed) * puckSpeedMult;
    speed = constrain(speed, -puckSpeedMax, puckSpeedMax);
    ptValue = ptValue + ptValInc;
  }
  
  void drawPuck() {
    drawPuckColor(defFillColor);
  }
  
  void drawPuckColor(color fill_) {
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

        float a = 90*(float(i)/float(posBuffer.numContent))*abs(speed)/puckSpeedMax;
        color thisFill = avgColor(fill_, #FFCB3B, 1-float(i)/float(posBuffer.numContent));
        fill(thisFill, a);
      
        float thisDist = abs(midPos - prevPos);
        ellipse(lanePos[lane], prevPos, laneWidth, laneWidth);
        ellipse(lanePos[lane], midPos, laneWidth, laneWidth);
        ellipse(lanePos[lane], midPos-thisDist/2.0, laneWidth, laneWidth);
        ellipse(lanePos[lane], midPos+thisDist/2.0, laneWidth, laneWidth);
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
  
  void insert(float i) {
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
  
  float getVal(int ind) {
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
  color c;
  
  PuckExplosion(boolean player1_, int lane_) {
    active = false;
    player1 = player1_;
    lane = lane_;
  }
  
  void trigger() {
    active = true;
    increasing = true;
    radius = 1;
    
    colorMode(HSB,255);
    int thisHue = int(random(1,255));
    c = color(thisHue,255,255);
    colorMode(RGB,255);
  }
  
  void update() {
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
  
  void drawExplosion() {
    int xpos = int(lanePos[lane]);
    int ypos;
    if (player1) {
      ypos = int(goalTop + 3);
    } else {
      ypos = int(goalBot - 2);
    }
    for (int r = radius; r>0; r--) {
      fill(color(c,5));
      noStroke();
      if (player1) {
        arc(xpos,ypos,1.5*r,5*r,0,PI);
      } else {
        arc(xpos,ypos,1.5*r,5*r,PI,TWO_PI);
      }
    }
  }
  
}
