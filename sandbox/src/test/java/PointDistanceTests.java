import org.testng.Assert;
import org.testng.annotations.Test;

public class PointDistanceTests {

  @Test
  public void testDistance(){
    Point p1 = new Point(12,10 );
    Point p2 = new Point(15,-20 );
    Assert.assertEquals(p1.distancePointMethod(p2),30.14962686336267 );
  }

  @Test
  public void testDistanceNegative(){
    Point p1 = new Point(0,1);
    Point p2 = new Point(2,3);
    Assert.assertNotEquals(p2.distancePointMethod(p2),0);

  }
}
