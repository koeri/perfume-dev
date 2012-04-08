import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 

import com.rhizomatiks.bvh.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class p5f_sample extends PApplet {



Minim minim;
AudioPlayer player;

BvhParser parserA = new BvhParser();
PBvh bvh1, bvh2, bvh3;
	
public void setup()
{
  size( 1280, 720, P3D );
  background( 0 );
  noStroke();
  frameRate( 30 );
  
  bvh1 = new PBvh( loadStrings( "aachan.bvh" ) );
  bvh2 = new PBvh( loadStrings( "kashiyuka.bvh" ) );
  bvh3 = new PBvh( loadStrings( "nocchi.bvh" ) );

  minim = new Minim(this);
  player = minim.loadFile("Perfume_globalsite_sound.wav");
  player.play();

  loop();
}

public void draw()
{
  background( 0 );
  
  //camera
  //float _cos = cos(millis() / 5000.f);
  //float _sin = sin(millis() / 5000.f);
  //camera(width/4.f + width/4.f * _cos +200, height/2.0f-100, 550 + 150 * _sin,width/2.0f, height/2.0f, -400, 0, 1, 0);
  camera((float) mouseX, (float) mouseY, 100.f, (float) (width/2.f), (float) (height/2.f), 0.f, 0.f, 1.f, 0.f);
  
  //ground 
  fill( color( 255 ));
  stroke(127);
  line(width/2.0f, height/2.0f, -30, width/2.0f, height/2.0f, 30);
  stroke(127);
  line(width/2.0f-30, height/2.0f, 0, width/2.0f + 30, height/2.0f, 0);
  stroke(255);
  pushMatrix();
  translate( width/2, height/2-10, 0);
  scale(-1, -1, -1);
  
  //model
  bvh1.update( millis() );
  bvh2.update( millis() );
  bvh3.update( millis() );
  bvh1.draw();
  bvh2.draw();
  bvh3.draw();
  popMatrix();
}
public class PBvh
{
  public BvhParser parser;  

  public PBvh(String[] data)
  {
    parser = new BvhParser();
    parser.init();
    parser.parse( data );
  }
  
  public void update( int ms )
  {
    parser.moveMsTo( ms );//30-sec loop 
    parser.update();
  }
  
  public void draw()
  {
    fill(color(random(100,255),random(100,255),random(100,255)));
    
    for( BvhBone b : parser.getBones())
    {
      pushMatrix();
      translate(b.absPos.x, b.absPos.y, b.absPos.z);
      rect(0, 0, 2, 2);
      popMatrix();
      if (!b.hasChildren())
      {
        pushMatrix();
        translate( b.absEndPos.x, b.absEndPos.y, b.absEndPos.z);
        rect(0, 0, 10, 10);
        popMatrix();
      }
        
    }
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "p5f_sample" });
  }
}
