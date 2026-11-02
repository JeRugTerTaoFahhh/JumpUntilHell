package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall {

    private final int x, y, w, h;
    private final BufferedImage img;
    private boolean active = false;
    private boolean temporary = false;
    private double activeTime = 0;
    private double timer = 0;

    public Wall(int x, int y, int w, int h, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.img = img;
    }

    public void draw(Graphics2D g2d) {
        if (!active) {
            return;
        }
        if (img != null) {
            g2d.drawImage(img, x, y, w, h, null);
        } else {
            g2d.setColor(Color.RED);
            g2d.fillRect(x, y, w, h);
        }
    }

    public Rectangle getBounds() {
        if (!active) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(x, y, w, h);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setTemporary(double durationSeconds) {
        this.temporary = true;
        this.activeTime = durationSeconds;
    }

    public void update(double dt) {
        if (active && temporary) {
            timer += dt;
            if (timer >= activeTime) {
                active = false;
                timer = 0;
            }
        }
    }
}
