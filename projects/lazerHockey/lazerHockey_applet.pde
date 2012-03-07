import ddf.minim.*;
Minim minim;
AudioSample bumpSound, scoreSound, superSound, blastSound;

boolean[] buttons;
int numLanes = 4;
//int numLanes = 8;
String bumpKeys = "asdfjkl;", bumpKeysUpper = "ASDFJKL;";
//String bumpKeys = "asdfjkl;qweruiop;", bumpKeysUpper = "ASDFJKL;QWERUIOP";
float tableWidth, tableHeight, laneWidth;
float[] lanePos = new float[numLanes];
float margin, sideMargin, goalTop, goalBot;
float puckStartSpeed, puckSpeedMax, puckSpeedMult = 1.2;
float bumpSpeed, bumpRetSpeed, bumpLength;
int score1, score2, gameLength = 90;
int timeLeft;
long timeStart;
boolean gameBegin = false, displayControls = true;
Button button, modeButton, difficultyButton;
boolean startRequest = false, restartRequest = false, gameEnded = true;

final int MULTIPLAYER = 0;
final int PRACTICE = 1;

int mode = MULTIPLAYER; // 0-2player, 1-practice
int difficulty = 0; //0-easy, 1-med, 2-hard

int pucksLost = 0;
int numPlayers = 1;
boolean[] bumpKeyEnabled = new boolean[8];

AI glados;

Puck[] pucks;
Bumper[] bumpers;

void setupGame() {
  
  switch(difficulty) {
    case 0: //EASY
      puckStartSpeed = tableHeight*1.5/650.0;
      puckSpeedMax = tableHeight*10.0/650.0;
      break;
    case 1: //MED
      puckStartSpeed = tableHeight*2.2/650.0;
      puckSpeedMax = tableHeight*13.0/650.0;
      break;
    case 2: //HARD
      puckStartSpeed = tableHeight*3.0/650.0;
      puckSpeedMax = tableHeight*16.0/650.0;
      break;
    default:
      break;
    
  }
  
  buttons = new boolean[2*numLanes];
  pucks = new Puck[numLanes];
  bumpers = new Bumper[2*numLanes]; // pl1 pl1 pl1 pl1 pl2 pl2 pl2 pl2
  
  glados = new AI(3);
  
  timeLeft = gameLength;
  
  score1 = 0;
  score2 = 0;
  
  pucksLost = 0;
  
  for(int i=0; i<numLanes; i++) {
    buttons[i] = false;
    lanePos[i] = (i+1) * tableWidth/(numLanes+1) + (width/2 - tableWidth/2);
    
    // enable bumpers
    bumpKeyEnabled[i] = true;
    bumpKeyEnabled[i+numLanes] = true;
    
    // add pucks
    int direction;
    if (i % 2 == 1) {
      direction = 1;
    } else {
      direction = -1;
    }
    pucks[i] = new Puck(i, direction);
    
    // add bumpers
    bumpers[i] = new Bumper(i, true);
    bumpers[i+numLanes] = new Bumper(i, false);
    
  }
}

void setup() {
  size(550,600);
  smooth();
  tableWidth = width*4.0/6.0-50; tableHeight = height*6.5/7.5; laneWidth = tableWidth*30.0/400.0;
  
  
  bumpSpeed = tableHeight*9.0/650.0;
  bumpRetSpeed = tableHeight*3.0/650.0;
  bumpLength = tableHeight*200.0/650.0;
  
  margin = (height-tableHeight)/2;
  sideMargin = (width-tableWidth)/2;
  goalTop = margin;
  goalBot = height - margin;
  
  //setup audio
  minim = new Minim(this);
  bumpSound = minim.loadSample("photon.wav");
  scoreSound = minim.loadSample("drop.wav");
  superSound = minim.loadSample("charge.wav");
  blastSound = minim.loadSample("cannon.wav");
  
  //buttons
  button = new Button(width/2, 3*height/4+40, 350, 100, "Play", color(0), color(0), color(255), color(#FF4000));
  button.textY = button.textY - 15;
  modeButton = new Button(width/2, height/2-10, 250, 40, modeString, color(0), color(0), color(255), color(#00FF57));
  difficultyButton = new Button(width/2, height/2+40, 250, 40, difficultyString, color(0), color(0), color(255), color(#7600FF));
  modeButton.textY = modeButton.textY-13;
  difficultyButton.textY = difficultyButton.textY-13;
  
  //setupGame();
  
}

void draw() {
  background(0);
  
  if (gameBegin) {
    // play game
    playGame();
  } else {
    // title menu
    runAnimations();
  }
  
}

void playGame() {
  timeLeft = gameLength - int((millis()-timeStart)/1000);
  if (timeLeft < 0) { timeLeft = 0; }
    
  // draw table
  drawTable();
  
  for (int i=0; i<numLanes; i++) {    
    // handle bumpers
    bumpers[i].update();
    bumpers[i].drawBumper();
    bumpers[i+numLanes].update();
    bumpers[i+numLanes].drawBumper();
    
    // handle pucks
    pucks[i].update();
    pucks[i].drawPuck();
  }
  
  // ai
  if (mode==PRACTICE)
    glados.update();
//  glados.drawStress();
  
  if (timeLeft == 0) { 
    animation = true;
    gameEndAni = true;
    gameBegin = false;
  }
}




void restartGame() {
  gameEnded = false;
  gameEndAni = false;
  titleScreen = true;
  animation = true;
  setupGame();
}
