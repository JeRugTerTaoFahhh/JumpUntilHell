package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public abstract class BaseElement implements Element {

    protected int x, y, w, h;
    protected BufferedImage img;

    public BaseElement(int x, int y, int w, int h, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.img = img;
    }

    @Override
    public void update(double dt) {}

    @Override
    public void draw(Graphics2D g2) {
        if (img != null) {
            g2.drawImage(img, x, y, w, h, null);
        } else {

            g2.fillRect(x, y, w, h);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
}
