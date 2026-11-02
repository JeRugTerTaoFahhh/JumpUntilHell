package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;



/**
 * Minion enemy (Boss summon attack)
 */
public class Minion {
    private int x;
    private int y;
    private int width = 40;
    private int height = 40;
    private int speed = 2;
    private boolean movingLeft = true;
    
    private int health = 1; // One hit to kill
    private boolean alive = true;
    private final Random random = new Random();
    
    // Animation
    private int animFrame = 0;
    private int animDelay = 0;
    private BufferedImage leftImage,rightImage,minionImage;
    private boolean useImage = false;
    
    
    /**
     * Constructor
     */
    public Minion(int x, int y) {
        this.speed = speed;
        this.x = x;
        this.y = 575;
        leftImage = Utilities.loadSingleImage(GameConstants.FILE_MINION_LEFT);
        rightImage = Utilities.loadSingleImage(GameConstants.FILE_MINION_RIGHT);
        //minionImage = Utilities.loadSingleImage(GameConstants.FILE_FLOOR_MID_HELL);
    }
    
    /**
     * Update minion
     */
    public void update(ArrayList<Minion> allMinions)  {
        if (!alive) return;
        
        // Move left and right
        if (movingLeft) {
            minionImage = leftImage;
            x -= speed;
            if (x < 0) {
                movingLeft = false;
            }
        } else {
            minionImage = rightImage;
            x += speed;
            if (x > 1280 - width) {
                movingLeft = true;
            }
        }
        
        for (Minion other : allMinions) {
        if (other == this || !other.isAlive()) continue;

        int dx = other.x - this.x;
        if (Math.abs(dx) < this.width) {
            // เด้งออกจากกัน
            if (dx > 0) {
                this.x -= 2;
                other.x += 2;
            } else {
                this.x += 2;
                other.x -= 2;
            }
           
            this.movingLeft = !this.movingLeft;
            other.movingLeft = !other.movingLeft;
        }
    }

        
        // Animate
        animDelay++;
        if (animDelay > 10) {
            animDelay = 0;
            animFrame = (animFrame + 1) % 2;
        }
    }
    
    /**
     * Draw minion
     */
    public void draw(Graphics2D g2d) {
        if (!alive) return;
        
        if (minionImage != null) {
        g2d.drawImage(minionImage, x, y, width, height, null);
    } else{
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Body
        g2d.setColor(new Color(100, 50, 150)); // Purple
        g2d.fillOval(x, y, width, height);
        
        // Eyes
        g2d.setColor(Color.RED);
        int eyeOffset = animFrame * 2;
        g2d.fillOval(x + 10, y + 12 + eyeOffset, 8, 8);
        g2d.fillOval(x + 22, y + 12 + eyeOffset, 8, 8);
        
        // Mouth
        g2d.setColor(Color.BLACK);
        g2d.drawArc(x + 12, y + 22, 16, 10, 0, -180);
        
        // Health indicator
        if (health > 0) {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(x + 5, y - 5, 30, 3);
        }
        }
    }
    
    /**
     * Check if player stomped on minion's head
     */
    public boolean checkStompCollision(int playerX, int playerY, int playerWidth, int playerHeight, double velocityY) {
        if (!alive) return false;
        
        // Player must be falling down and hit from above
        if (velocityY > 0) {
            int playerBottom = playerY + playerHeight;
            int minionTop = y;
            int minionHead = y + height/3; // Upper third is "head"
            
            return playerX + playerWidth > x &&
                   playerX < x + width &&
                   playerBottom >= minionTop &&
                   playerBottom <= minionHead;
        }
        return false;
    }
    
    /**
     * Check collision with player (damage player)
     */
    public boolean checkCollision(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (!alive) return false;
        
        return playerX + playerWidth > x &&
               playerX < x + width &&
               playerY + playerHeight > y &&
               playerY < y + height;
    }
    
    /**
     * Take damage from stomp
     */
    public void takeDamage() {
        health--;
        if (health <= 0) {
            alive = false;
        }
    }
    
    // Getters
    public boolean isAlive() { return alive; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}