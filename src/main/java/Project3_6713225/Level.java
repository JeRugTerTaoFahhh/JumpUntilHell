package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public interface Level {
    void update(double dt);
    void draw(Graphics2D g2);

    BufferedImage getBackground();
    List<Element> getElements();
    ArrayList<Platform> getPlatforms();
    List<Rectangle> getBlock(); 
    List<Rectangle> getTrap();   
}
