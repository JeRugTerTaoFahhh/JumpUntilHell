package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Fireball that falls from sky (Boss fireball attack)
 */
public class Fireball {
    private final int x;
    private int y;
    private final int size = 40;
    private final int speed;
    private boolean active = true;
    
    private BufferedImage fireballImage;
    /**
     * Constructor - starts at top of screen
     * @param x
     */
    public Fireball(int x) {
        this.x = x;
        this.y = -size; // Start above screen
        this.speed = 4 + (int)(Math.random() * 6);
        fireballImage = Utilities.loadSingleImage(GameConstants.FILE_FIREBALL);
    }
    
    /**
     * Update fireball position
     */
    public void update() {
        if (active) {
            y += speed;
            
            // Deactivate if off screen
            if (y > 750) {
                active = false;
            }
        }
    }
    
    /**
     * Draw fireball
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        if (!active) return;
        // Draw fire effect
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (fireballImage != null) {
        g2d.drawImage(fireballImage, x - size/2, y, size, size, null);
        } else {
        
        
        // Outer glow
        g2d.setColor(new Color(255, 100, 0, 100));
        g2d.fillOval(x - size/2 - 5, y - 5, size + 10, size + 10);
        
        // Main fireball
        g2d.setColor(new Color(255, 140, 0));
        g2d.fillOval(x - size/2, y, size, size);
        
        // Inner core
        g2d.setColor(new Color(255, 200, 0));
        g2d.fillOval(x - size/4, y + size/4, size/2, size/2);
        
        // Flame trail
        for (int i = 0; i < 3; i++) {
            int trailY = y - (i + 1) * 10;
            int trailSize = size - i * 5;
            int alpha = 150 - i * 50;
            g2d.setColor(new Color(255, 100, 0, alpha));
            g2d.fillOval(x - trailSize/2, trailY, trailSize, trailSize);
        }
    }
    }
    
    /**
     * Check collision with player
     */

    public boolean checkCollision(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (!active) return false;
        
        // Circle collision detection
        int centerX = x;
        int centerY = y + size/2;
        int radius = size/2;
        
        int playerCenterX = playerX + playerWidth/2;
        int playerCenterY = playerY + playerHeight/2;
        
        int dx = centerX - playerCenterX;
        int dy = centerY - playerCenterY;
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        
        return distance < radius + Math.min(playerWidth, playerHeight)/3;
    }
    public boolean hitGround(int groundY) {
        return y + size >= groundY;
    }
    
    // Getters
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
    
    public void setFireballImage(BufferedImage image) {
        this.fireballImage = image;
    }
}