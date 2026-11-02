package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class MovingElement implements Element {

    private double x, y;
    private final int w, h;
    private final BufferedImage img;

    // waypoint 
    private boolean hasWaypoint = false;
    private double wpX, wpY;

    // pause at waypoint
    private boolean pausing = false;
    private double pauseDuration = 0.0;
    private double pauseTimer = 0.0;
    private boolean passedWaypoint = false;

    private final double startX;
    private final double startY;

    private final double targetX;
    private final double targetY;
    private final double speed;

    private boolean active = false;

    boolean requireKey = false;
    

    public MovingElement(int x, int y, int w, int h, BufferedImage img,
            int targetX, int targetY, double speed) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.img = img;

        this.startX = x;
        this.startY = y;

        this.targetX = targetX;
        this.targetY = targetY;
        this.speed = speed;
    }

    public void activate() {
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void update(double dt) {
        if (!active) {
            return;
        }

        // pause 
        if (pausing) {
            pauseTimer += dt;
            if (pauseTimer >= pauseDuration) {
                pausing = false;
            }
            return;
        }

        // waypoint
        double destX, destY;

        if (hasWaypoint && !passedWaypoint) {
            // if not arrived at waypoint target is waypoint
            destX = wpX;
            destY = wpY;
        } 
         else {
            destX = startX;
            destY = startY;
        }

        double dx = destX - x;
        double dy = destY - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < 0.5) {
            // arrived 
            if (hasWaypoint && !passedWaypoint) {
                // arrived at waypoint element will wait
                passedWaypoint = true;
                if (pauseDuration > 0) {
                    pausing = true;
                    pauseTimer = 0.0;
                    return;
                }
            } else {
                // arrived at target element stop moving
                active = false;
            }
            return;
        }

        double step = speed * dt;

        // prevent overshoot
        if (step >= dist) {
            x = destX;
            y = destY;

            if (hasWaypoint && !passedWaypoint) {
                passedWaypoint = true;
                if (pauseDuration > 0) {
                    pausing = true;
                    pauseTimer = 0.0;
                    return;
                }
            } else {
                active = false;
            }
            return;
        }

        double vx = (dx / dist) * step;
        double vy = (dy / dist) * step;

        x += vx;
        y += vy;
    }

    @Override
    public void draw(Graphics2D g2) {
        
        if (img != null) {
            g2.drawImage(img, (int) x, (int) y, w, h, null);
        } else {
            g2.fillRect((int) x, (int) y, w, h);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, w, h);
    }

    //element that stop at (x,y) and delay
    public void setupWaypoint(double wpX, double wpY, double pauseSeconds) {
        this.hasWaypoint = true;
        this.wpX = wpX;
        this.wpY = wpY;
        this.pauseDuration = pauseSeconds;
    }

    public void reset() {
        this.x = startX;
        this.y = startY;
        this.active = false;

        this.pausing = false;
        this.pauseTimer = 0.0;
        this.passedWaypoint = false;
    }
}
