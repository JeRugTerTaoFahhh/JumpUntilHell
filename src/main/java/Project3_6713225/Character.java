package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * animation, skin
 */
public class Character {
    // Position and movement
    protected int x = GameConstants.CHARACTER_START_X;
    protected int y = GameConstants.CHARACTER_START_Y;
    protected int speed = GameConstants.CHARACTER_SPEED;
    protected boolean movingLeft = false;
    protected boolean movingRight = false;
    protected boolean facingLeft = false;
    
    // Jump mechanics
    protected boolean isJumping = false;
    protected boolean isFalling = false;
    protected double velocityY = 0;
    private Clip soundEffect;
    protected final double GRAVITY = GameConstants.GRAVITY;
    protected final double JUMP_STRENGTH = GameConstants.JUMP_STRENGTH;
    
    // Animation
    protected ArrayList<BufferedImage> walkFrames;
    protected int currentFrame = 0;
    protected int frameDelay = 0;
    protected final int frameSpeed = GameConstants.FRAME_SPEED;
    protected final int imageWidth = GameConstants.CHARACTER_WIDTH;
    protected final int imageHeight = GameConstants.CHARACTER_HEIGHT;
    
    // Skin
    protected final Utilities.CharacterSkin[] availableSkins;
    protected int currentSkinIndex = 0;
    
    // Death state
    protected boolean isDead = false;
    protected boolean deathAnimationComplete = false;
    protected double deathVelocityY = 0;
    
    // Key carrying
    protected boolean hasKey = false;
    protected Key carriedKey = null;
    
    private boolean isVictory = false;
    
    public Character() {
        availableSkins = Utilities.getAvailableSkins();
        loadSelectedSkin();
        
        // Fallback: if walkFrames is still null, load default skin
        if (walkFrames == null || walkFrames.isEmpty()) {
            loadDefaultSkin();
        }
    }
    
    /**
     * Load skin from Utilities
     */
    protected void loadSelectedSkin() {
        Utilities.CharacterSkin skin = Utilities.getSelectedSkin();
        if (skin != null) {
            walkFrames = Utilities.loadImages(skin.imagePaths);
            if (walkFrames != null && !walkFrames.isEmpty()) {
                System.out.println("Loaded skin: " + skin.name);
            }
        }
    }
    
    /**
     * Load default skin as fallback
     */
    protected void loadDefaultSkin() {
        if (availableSkins != null && availableSkins.length > 0) {
            Utilities.CharacterSkin defaultSkin = availableSkins[0];
            walkFrames = Utilities.loadImages(defaultSkin.imagePaths);
            if (walkFrames != null && !walkFrames.isEmpty()) {
                System.out.println("Loaded default skin: " + defaultSkin.name);
            } else {
                // Last resort: create empty list to prevent null pointer
                walkFrames = new ArrayList<>();
                System.err.println("Warning: Could not load any character skin!");
            }
        } else {
            walkFrames = new ArrayList<>();
            System.err.println("Warning: No available skins found!");
        }
    }
    
    /**
     * Move character horizontally
     */
    public void move(int deltaX) {
        x += deltaX;
    }
    
    /**
     * Jump
     */
    public void jump() {
        if (!isJumping && !isFalling) {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_JUMP_SOUND);
            isJumping = true;
            velocityY = JUMP_STRENGTH;
        }
    }
    
    /**
     * Apply gravity
     */
    public void applyGravity() {
        velocityY += GRAVITY;
        y += velocityY;
    }
    
    /**
     * Animate walking
     */
    public void animateWalk() {
        if (walkFrames == null || walkFrames.isEmpty()) return;
        
        frameDelay++;
        if (frameDelay >= frameSpeed) {
            frameDelay = 0;
            currentFrame = (currentFrame + 1) % walkFrames.size();
        }
    }
    
    /**
     * Reset animation
     */
    public void resetAnimation() {
        currentFrame = 0;
        frameDelay = 0;
    }
    
    /**
     * Draw character
     */
    public void draw(Graphics2D g2d) {
        if (walkFrames != null && !walkFrames.isEmpty()) {
            BufferedImage currentImage = walkFrames.get(currentFrame);
            if (currentImage != null) {
                if (facingLeft) {
                    g2d.drawImage(currentImage, x + imageWidth, y, -imageWidth, imageHeight, null);
                } else {
                    g2d.drawImage(currentImage, x, y, imageWidth, imageHeight, null);
                }
            }
        }
        
        // Draw carried key
        if (hasKey && carriedKey != null) {
            BufferedImage keyImg = carriedKey.getImage();
            if (keyImg != null) {
                int keyW = carriedKey.getWidth();
                int keyH = carriedKey.getHeight();
                int drawX = x + imageWidth - keyW;
                int drawY = y - keyH;
                g2d.drawImage(keyImg, drawX, drawY, keyW, keyH, null);
            }
        }
    }
    
    /**
     * Die
     */
    public void die() {
        if (!isDead) {
            isDead = true;
            deathAnimationComplete = false;
            currentFrame = 0;
            frameDelay = 0;
            deathVelocityY = -8;
            hasKey = false;
            carriedKey = null;
            Utilities.muteTheme();
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_GAME_OVER_SOUND);
        }
    }
    
    /**
     * Reset character to starting position
     */
    public void reset() {
        isDead = false;
        x = GameConstants.CHARACTER_START_X;
        y = GameConstants.CHARACTER_START_Y;
        deathVelocityY = 0;
        velocityY = 0;
        movingLeft = false;
        movingRight = false;
        hasKey = false;
        carriedKey = null;
        isJumping = false;
        isFalling = false;
        currentFrame = 0;
        frameDelay = 0;
    }
    
    /**
     * Get character bounds for collision detection
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, imageWidth, imageHeight);
    }
    
    // Getters and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return imageWidth; }
    public int getHeight() { return imageHeight; }
    public double getVelocityY() { return velocityY; }
    public void setVelocityY(double velocity) { this.velocityY = velocity; }
    public boolean isDead() { return isDead; }
    public boolean isDeathAnimationComplete() { return deathAnimationComplete; }
    public void setDeathAnimationComplete(boolean complete) { this.deathAnimationComplete = complete; }
    public double getDeathVelocityY() { return deathVelocityY; }
    public void setDeathVelocityY(double velocity) { this.deathVelocityY = velocity; }
    public boolean isJumping() { return isJumping; }
    public boolean isFalling() { return isFalling; }
    public void setJumping(boolean jumping) { this.isJumping = jumping; }
    public void setFalling(boolean falling) { this.isFalling = falling; }
    public boolean isMovingLeft() { return movingLeft; }
    public boolean isMovingRight() { return movingRight; }
    public void setMovingLeft(boolean moving) { this.movingLeft = moving; }
    public void setMovingRight(boolean moving) { this.movingRight = moving; }
    public void setFacingLeft(boolean facing) { this.facingLeft = facing; }
    public boolean isFacingLeft() { return facingLeft; }
    public boolean hasKey() { return hasKey; }
    public void setHasKey(boolean hasKey) { this.hasKey = hasKey; }
    public void setCarriedKey(Key key) { this.carriedKey = key; }
    public Key getCarriedKey() { return carriedKey; }
    public int getSpeed() { return speed; }
    
}