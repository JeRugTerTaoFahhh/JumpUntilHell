package Project3_6713225;

/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseLevel implements Level {

    protected BufferedImage background;
    protected List<Element> elements = new ArrayList<>();
    protected List<Rectangle> Block = new ArrayList<>();
    protected List<Rectangle> Trap  = new ArrayList<>();

    @Override
    public void update(double dt) {
        for (Element e : elements) {
            e.update(dt);
        }

        Trap.clear();
        for (Element e : elements) {
            if (!(e instanceof Platform)) {
                Trap.add(e.getBounds());
            }
        }
    }
    
    @Override
    public BufferedImage getBackground() {
        return background;
    }

    
    public List<Element> getElements() {
        return elements;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (background != null) {
            g2.drawImage(background, 0, 0, null);
        }
        for (Element e : elements) {
            e.draw(g2);
        }
    }

    @Override
    public List<Rectangle> getBlock() {
        return Block;
    }

    @Override
    public List<Rectangle> getTrap() {
        return Trap;
    }
    
    public ArrayList<Platform> getPlatforms() {
    ArrayList<Platform> list = new ArrayList<>();
    for (Element e : elements) {
        if (e instanceof Platform platform) {
            list.add(platform);
        }
    }
    return list;
}
    
}
