package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


//Platform that can shake and fall
public class Platform implements Element{
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage image;
    
    // Platform states
    private boolean isShaking = false;
    private boolean isFalling = false;
    private boolean isGone = false;
    
    // Animation
    private int shakeTimer = 0;
    private int shakeDuration = 60; // frames
    private int shakeIntensity = 5;
    private int fallSpeed = 8;
    
    // Respawn
    private int respawnTimer = 0;
    private final int respawnDelay = 300; // 3 seconds
    private final int originalY;
    
    
    //Constructor
    public Platform(int x, int y, int width, int height, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.originalY = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }
    
    
    //Start shaking platform (Boss axe attack)
    public void startShaking() {
        if (!isGone && !isFalling) {
            isShaking = true;
            shakeTimer = shakeDuration;
        }
    }
    
   
   
    //Respawn platform 
    private void respawn() {
        isGone = false;
        isFalling = false;
        isShaking = false;
        y = originalY;
        respawnTimer = 0;
    }
    
    
    //Draw platform
    public void draw(Graphics2D g2d) {
        if (isGone) return;
        
        int drawX = x;
        int drawY = y;
        
        // Apply shake effect
        if (isShaking) {
            int shakeX = (int) (Math.random() * shakeIntensity * 2 - shakeIntensity);
            int shakeY = (int) (Math.random() * shakeIntensity * 2 - shakeIntensity);
            drawX += shakeX;
            drawY += shakeY;
        }
        
        if (image != null) {
            g2d.drawImage(image, drawX, drawY, width, height, null);
        } else {
            // Draw placeholder platform
            g2d.setColor(new Color(139, 69, 19)); // Brown
            g2d.fillRect(drawX, drawY, width, height);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(drawX, drawY, width, height);
        }
        
        // Show warning when shaking
        if (isShaking) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(drawX - 2, drawY - 2, width + 4, height + 4);
        }
    }
    
    
    //Check if player is on this platform 
    public boolean isPlayerOn(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (isGone) return false;
        
        int playerBottom = playerY + playerHeight;
        int platformTop = y;
        
        return playerX + playerWidth > x &&
               playerX < x + width &&
               playerY + playerHeight >= y &&
               playerY + playerHeight <= y + 15; // Allow some tolerance
    }
    
    
    //Check collision with player
    public boolean checkCollision(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (isGone) return false;
        
        return playerX < x + width &&
               playerX + playerWidth > x &&
               playerY < y + height &&
               playerY + playerHeight > y;
    }
    
    // check if character head hit platform
    public boolean isHitHead(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (isGone) return false;

        int playerTop    = playerY;
        int playerBottom = playerY + playerHeight;
        int playerCenterX = playerX + playerWidth / 2;

        int platformBottom = y + height;
        int platformLeft   = x;
        int platformRight  = x + width;

        boolean centerInside =
                playerCenterX > platformLeft &&
                playerCenterX < platformRight;

        if (centerInside &&
            playerTop <= platformBottom &&
            playerBottom > platformBottom) {
            return true;
        }
        return false;
    }

    public int getBottomY() {
        return y + height;
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isGone() { return isGone; }
    public boolean isShaking() { return isShaking; }
    public int getTopY() { return y; }


public void update(double dt) {
    if (isShaking) {
        shakeTimer--;
        if (shakeTimer <= 0) {
            isShaking = false;
            isFalling = true;
        }
    }
    
    if (isFalling) {
        y += fallSpeed;
        if (y > 800) {
            isGone = true;
            isFalling = false;
            respawnTimer = respawnDelay;
        }
    }
    
    if (isGone) {
        respawnTimer--;
        if (respawnTimer <= 0) {
            respawn();
        }
    }
}

    @Override
public Rectangle getBounds() {
    if (isGone) {
        return new Rectangle(0, 0, 0, 0);
    }
    return new Rectangle(x, y, width, height);
}
}

