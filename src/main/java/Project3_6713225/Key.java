package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.*;
import java.awt.image.BufferedImage;

public class Key {

    private int x, y, w, h;
    private BufferedImage img;
     private final double startX, startY;
    private boolean collected = false;
    
    private boolean attackEnabled = false;
    private boolean attackActive = false;
    private boolean goingBack = false;

    private double attackTargetX, attackTargetY;
    private double attackSpeed;

    private boolean pausing = false;
    private double attackPauseDuration = 0.0;
    private double attackPauseTimer = 0.0;

    public Key(int x, int y, int w, int h, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.img = img;
        this.startX = x;
        this.startY = y;
    }
    
    public void enableAttack(double targetX, double targetY,
            double speed, double pauseSeconds) {
        this.attackEnabled = true;
        this.attackTargetX = targetX;
        this.attackTargetY = targetY;
        this.attackSpeed = speed;
        this.attackPauseDuration = pauseSeconds;
    }

    public void startAttack() {
        if (!attackEnabled) {
            return;
        }
        if (attackActive) {
            return;
        }

        attackActive = true;
        goingBack = false;
        pausing = false;
        attackPauseTimer = 0.0;
    }

    public boolean isInAttackMode() {
        return attackEnabled && attackActive;
    }
    
    public void draw(Graphics2D g2) {
        if (collected) {
            return;
        }

         g2.drawImage(img, x, y, w, h, null);
    }
    public void update(double dt) {
        if (!attackEnabled || !attackActive) {
            return;
        }

        if (pausing) {
            attackPauseTimer += dt;
            if (attackPauseTimer >= attackPauseDuration) {
                pausing = false;

                if (!goingBack) {
                    goingBack = true;
                }
            }
            return;
        }

        double destX = goingBack ? startX : attackTargetX;
        double destY = goingBack ? startY : attackTargetY;

        double dx = destX - x;
        double dy = destY - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < 1.0) {
            x = (int) destX;
            y = (int) destY;

            if (!goingBack) {
                if (attackPauseDuration > 0) {
                    pausing = true;
                    attackPauseTimer = 0.0;
                } else {
                    goingBack = true;
                }
            } else {
                attackActive = false;
                goingBack = false;
            }
            return;
        }

        double step = attackSpeed * dt;
        double vx = (dx / dist) * step;
        double vy = (dy / dist) * step;

        x += vx;
        y += vy;
    }
    

    public BufferedImage getImage() {
        return img;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }
    public void setPosition(int nx, int ny) {
        this.x = nx;
        this.y = ny;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    

    public Rectangle getBounds() {
        if (collected) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(x, y, w, h);
    }

    public void collect() {
        collected = true;
        attackActive = false;
        pausing = false;
    }

    public boolean isCollected() {
        return collected;
    }
    public void reset() {
        collected = false;
        x = (int) startX;
        y = (int) startY;

        attackActive = false;
        goingBack = false;
        pausing = false;
        attackPauseTimer = 0.0;
    }
}
