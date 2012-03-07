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
  
  void update() {
    
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
  
  void drawStress() {
    rectMode(CORNER);
    noFill();
    stroke(255);
    
    int rectHeight = 100;
    rect(10,10,10,rectHeight);
    
    noStroke();
    fill(#FF0000);
    
    rect(11,11,8,(rectHeight-2)*(stress/500));
  }
  
}
