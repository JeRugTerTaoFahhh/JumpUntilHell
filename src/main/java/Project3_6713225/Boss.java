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


public class Boss {
    // Boss position
    private final int x = 297;
    private final int y = 90;
    private final int width = GameConstants.BOSS_WIDTH;
    private final int height = GameConstants.BOSS_HEIGHT;
    
    
    public enum AttackPattern {
        AXE_SWING, 
        FIREBALL_RAIN,
        SUMMON_MINIONS 
    }
    
    private AttackPattern currentPattern = AttackPattern.AXE_SWING;
    private boolean isAttacking = false;
    
    private ArrayList<BufferedImage> axeSwingFrames;
    private ArrayList<BufferedImage> fireballRainFrames;
    private ArrayList<BufferedImage> summonMinionsFrames;
    private ArrayList<BufferedImage> endFrames;
    
    private int currentFrame = 0;
    private int frameDelay = 0;
    private final int frameSpeed = GameConstants.BOSS_FRAME_SPEED;
    
    private boolean isDying = false;
    boolean isBossDead = false;
    private int deathFrame = 0;
    private int deathFrameDelay = 0;
    private final int deathFrameSpeed = 20; 
    
    private int attackCooldown = 0;
    private final int attackCooldownMax = GameConstants.BOSS_ATTACK_COOLDOWN;
    private final Random random = new Random();
    
    private final BufferedImage healthbar;
    private int health = GameConstants.BOSS_HEALTH;
    private final int maxHealth = GameConstants.BOSS_HEALTH;
    
    //Constructor
    public Boss() {
        loadAnimations();
        healthbar = Utilities.loadSingleImage(GameConstants.FILE_HEALTH_BAR);
    }
    
    private void loadAnimations() {
        axeSwingFrames = new ArrayList<>();
        String[] axePaths = {
            GameConstants.FILE_BOSS_AXE_1,
            GameConstants.FILE_BOSS_AXE_2,
            GameConstants.FILE_BOSS_AXE_3,
            GameConstants.FILE_BOSS_AXE_4
        };
        axeSwingFrames = Utilities.loadImages(axePaths);
        
        fireballRainFrames = new ArrayList<>();
        String[] fireballPaths = {
            GameConstants.FILE_BOSS_FIREBALL_1,
            GameConstants.FILE_BOSS_FIREBALL_2,
            GameConstants.FILE_BOSS_FIREBALL_3,
            GameConstants.FILE_BOSS_FIREBALL_4
        };
        fireballRainFrames = Utilities.loadImages(fireballPaths);
        
        summonMinionsFrames = new ArrayList<>();
        String[] summonPaths = {
            GameConstants.FILE_BOSS_SUMMON_1,
            GameConstants.FILE_BOSS_SUMMON_2,
            GameConstants.FILE_BOSS_SUMMON_3,
            GameConstants.FILE_BOSS_SUMMON_4
        };
        summonMinionsFrames = Utilities.loadImages(summonPaths);
        
        endFrames = new ArrayList<>();
            String[] boomPaths = {
            GameConstants.FILE_BOOM_1,
            GameConstants.FILE_BOOM_2,
            GameConstants.FILE_BOOM_3,
            GameConstants.FILE_BOOM_4
        };
        endFrames = Utilities.loadImages(boomPaths);
    }
    
    //update method /Handles attack cooldowns, initiating attacks, and animations
    public void update() {
        if (isBossDead) return;
        
        if (isDying) {
        animateDeath();
        return;
        }
    
        if (attackCooldown > 0) {
            attackCooldown--;
            return;
        }
        if (!isAttacking) {
            startRandomAttack();
        }
      
        if (isAttacking) {
            animateAttack();
        }
    }
    
    
    private void startRandomAttack() {
        int randomChoice = random.nextInt(3);
        
        switch (randomChoice) {
            case 0:
                currentPattern = AttackPattern.AXE_SWING;
                break;
            case 1:
                currentPattern = AttackPattern.FIREBALL_RAIN;
                break;
            case 2:
                currentPattern = AttackPattern.SUMMON_MINIONS;
                break;
        }
        
        // Reset animation
        isAttacking = true;
        currentFrame = 0;
        frameDelay = 0;
    }
    
    private void animateAttack() {
        frameDelay++;
        if (frameDelay >= frameSpeed) {
            frameDelay = 0;
            
            ArrayList<BufferedImage> currentFrames = getCurrentFrames();
            currentFrame++;
            
            if (currentFrame >= currentFrames.size()) {
                finishAttack();
            }
        }
    }
    
    private void finishAttack() {
        isAttacking = false;
        currentFrame = 0;
        attackCooldown = attackCooldownMax;
    }
    
    private ArrayList<BufferedImage> getCurrentFrames() {
        switch (currentPattern) {
            case AXE_SWING:
                return axeSwingFrames;
            case FIREBALL_RAIN:
                return fireballRainFrames;
            case SUMMON_MINIONS:
                return summonMinionsFrames;
            default:
                return axeSwingFrames;
        }
    }
    
    public void draw(Graphics2D g2d) {
        if (isDying) {
        if (deathFrame < endFrames.size()) {
            g2d.drawImage(endFrames.get(deathFrame), 245, -20, width, height, null);
        }
        drawHealthBar(g2d);
        return;
    }

    if (isBossDead) return;
    
        ArrayList<BufferedImage> currentFrames = getCurrentFrames();
        if (!currentFrames.isEmpty() && currentFrame < currentFrames.size()) {
            BufferedImage currentImage = currentFrames.get(currentFrame);
            g2d.drawImage(currentImage, x, y, width, height, null);
        } else if (!currentFrames.isEmpty()) {
            g2d.drawImage(currentFrames.get(0), x, y, width, height, null);
        }
        drawHealthBar(g2d);
    }
    
    private void drawHealthBar(Graphics2D g2d) {
        int barWidth = 350;
        int barHeight = 10;
        int barX = 425;
        int barY = 58;
        
        g2d.setColor(Color.ORANGE);
        int healthWidth = (int) ((health / (float) maxHealth) * barWidth);
        g2d.fillRect(barX, barY, healthWidth, barHeight);
        
        g2d.drawImage(healthbar,null, 360, 5);
    }
    
    public void takeDamage(int damage) {
        if (isDying || isBossDead) return;
                
        health -= damage;
        if (health < 0) {
            health = 0;
            startDeathAnimation();
        }
    }
    
    private void startDeathAnimation() {
        isAttacking = false;
        isDying = true;
        deathFrame = 0;
        deathFrameDelay = 0;
        Utilities.playSound(GameConstants.FILE_BOSS_BOOM_SOUND);
    }
    
    private void animateDeath() {
    deathFrameDelay++;

    if (deathFrameDelay >= deathFrameSpeed) {
        deathFrameDelay = 0;
        deathFrame++;

        if (deathFrame >= endFrames.size()) {
            isDying = false;
            isBossDead = true;
            
        }
    }
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getHealth() { return health; }
    public void heal() { health = GameConstants.BOSS_HEALTH; }
    public boolean isAlive() { return !isBossDead; }
    public AttackPattern getCurrentPattern() { return currentPattern; }
    public boolean isAttacking() { return isAttacking; }
    public boolean isBossDead() { return isBossDead; }
}