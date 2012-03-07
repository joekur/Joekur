class Button {
  boolean hovered;
  float centerx, centery;
  float bwidth;
  float bheight;
  float textY;
  color normFill, hoverFill;
  color normStroke, hoverStroke;
  String butText;
  
  Button (float centerx_, float centery_, float bwidth_, float bheight_, String butText_, color normFill_, color hoverFill_, color normStroke_, color hoverStroke_) {
    centerx = centerx_;
    centery = centery_;
    bwidth = bwidth_;
    bheight = bheight_;
    normFill = normFill_;
    hoverFill =  hoverFill_;
    normStroke = normStroke_;
    hoverStroke = hoverStroke_;
    butText = butText_;
    textY = centery_;
    
    hovered = false;
  }
  
  void drawButton() {
    rectMode(CENTER);
    if (hovered) {
      fill(hoverFill);
      stroke(hoverStroke);
    } else {
      fill(normFill);
      stroke(normStroke);
    }
    
    rect(centerx, centery, bwidth, bheight);
    
    textAlign(CENTER,CENTER);
    textSize(bheight*.5);
    strokeWeight(4);
    if (hovered) {
      fill(hoverStroke);
    } else {
      fill(normStroke);
    }
    
    text(butText, centerx, textY+10);
  }
  
  void update() {
    hovered = (mouseX > centerx-bwidth/2 && mouseX < centerx+bwidth/2) && (mouseY > centery-bheight/2 && mouseY < centery+bheight/2);
    drawButton();
  }
  
}
