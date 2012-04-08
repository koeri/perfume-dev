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
