package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.*;
import java.awt.image.BufferedImage;

public class Portal {
    private int x, y, w, h;
    private BufferedImage img;

    public Portal(int x, int y, int w, int h, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.img = img;
    }

    public void draw(Graphics2D g2) {
        if (img != null) {
            g2.drawImage(img, x, y, w, h, null);
        } else {
            g2.setColor(Color.MAGENTA);
            g2.fillRect(x, y, w, h);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getW() { return w; }
    public int getH() { return h; }
}
