class FruitSystem {
  boolean fruitActive;
  PVector pos;
  PImage img;
  int dotLimit;
  boolean fruitGeneratedThisLevel;
  
  FruitSystem() {
    fruitActive = false;
    PVector pos = new PVector(0,0);
    fruitGeneratedThisLevel = false;
  }
  
  void update() {
    if (!fruitGeneratedThisLevel && curLevel >= 5) {
      // check if its time to create the fruit
      if (dotsEaten >= this.dotLimit) {
               
        // find suitable location
        boolean posFound = false;
        int posX, posY;
        int tries = 0;
        while(!posFound && tries < 1000) {
          posX = int(random(1,mapW-1)+.5);
          posY = int(random(1,mapH-1)+.5);
          PVector fruitV = new PVector(posX,posY);
          float distToPac = PVector.dist(fruitV, pac.loc);
          if (level[posY][posX].passable && level[posY][posX].eaten && distToPac > 1.5) {
            this.pos = new PVector(posX, posY);
            posFound = true;
            fruitActive = true;
          }
          tries++;
        }
        fruitGeneratedThisLevel = true;
      }
    }
    
    if (!fruitActive) {
      return;
    }
    
    this.drawFruit();
    
    int pacx = round(pac.loc.x);
    int pacy = round(pac.loc.y);
    
    if (pacx==this.pos.x && pacy==this.pos.y) {
      // eat fruit
      this.eatFruit();
    }
  }
  
  void eatFruit() {
    // called when fruit is eaten
    println("fruit eaten!");
    fruitActive = false;
    superPower.ammoPickup();
  }
  
  void drawFruit() {
    PVector mapPos = getLoc(this.pos.y, this.pos.x);
    
    pushMatrix();
    translate(mapPos.x, mapPos.y);
    imageMode(CENTER);
    image(this.img,0,0,18,18);
    popMatrix();
  }
  
  
  void prepareForLevel() {
    fruitGeneratedThisLevel = false;
    
    // decide which fruit
    int whichImage = int(random(4) + 0.5);
    switch (whichImage) {
      case 1:
        this.img = strawberry;
        break;
      case 2:
        this.img = cherry;
        break;
      case 3:
        this.img = orange;
        break;
      default:
        this.img = apple;
        break;
    }
    
    // decide after how many dots
    this.dotLimit = int( random(totalDots - 70, totalDots - 15) );
    //this.dotLimit = int( random(5, 6) );
  }
  
}
