package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Boss stage - extends GameStage
 */
public class BossStage extends GameStage {
    private final BossStageController bossStageController;
    private final BufferedImage lavaTile, backgroundImage;
    private Clip themeClip;
    protected Clip victoryClip;
    private boolean bossDefeated = false;
    
    private SettingsOverlay settingsOverlay;
    private boolean isSettingsOpen = false;
    //constructor
    public BossStage() {
        super(null, new ArrayList<>(), new ArrayList<>());
        backgroundImage =  Utilities.loadSingleImage(GameConstants.FILE_HELL_BACKGROUND);
        lavaTile = Utilities.loadSingleImage(GameConstants.FILE_FLOOR_MID_HELL);
        generateBossPlatforms();
        
        bossStageController = new BossStageController(GameConstants.FRAME_WIDTH, GameConstants.FRAME_HEIGHT);
        bossStageController.setPlatforms(platforms);
        
        Utilities.updateThemeSong(0,3);
    }
    
    
    private void generateBossPlatforms() {
        platforms = new ArrayList<>();
        int tileWidth = 128;
        int y = 600;
        Random rand = new Random();
        
        for (int i = 0; i < 10; i++) {
            if (rand.nextDouble() > 0.2) {
                Platform platform = new Platform(i * tileWidth, y, tileWidth, 64, lavaTile);
                platforms.add(platform);
            }
        }
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw background
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        
        // Draw boss
        if (bossStageController != null && bossStageController.getBoss() != null) {
            bossStageController.getBoss().draw(g2d);
        }
        
        
        
        // Draw platforms
        for (Platform platform : platforms) {
            platform.draw(g2d);
        }
        
        // Draw fireballs
        for (Fireball fireball : bossStageController.getFireballs()) {
            fireball.draw(g2d);
        }
        
        // Draw minions
        for (Minion minion : bossStageController.getMinions()) {
            minion.draw(g2d);
        }
        
        // Draw character
        character.draw(g2d);
        
        // Draw UI
        drawUI(g2d);
        
        // Draw game over or victory screen
        if (!bossDefeated && character.isDead() && character.isDeathAnimationComplete()) {
            drawGameOverScreen(g2d, "GameOver");
        }
        
        if (bossDefeated && character.isDead() && character.isDeathAnimationComplete()) {
            drawGameOverScreen(g2d, "Victory");
        }
    }
    
    
    //Override action performed
     
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isSettingsOpen) {
            repaint();
            return;
        }
        
        if (character.isDead()) {
            if (!character.isDeathAnimationComplete()) {
                stopMapTimer();
                character.setDeathVelocityY(character.getDeathVelocityY() + character.GRAVITY);
                character.setY(character.getY() + (int)character.getDeathVelocityY());
                
                if (character.getY() > getHeight()) {
                    character.setDeathAnimationComplete(true);
                }
            }
            repaint();
            return;
        }
        
        // Update boss controller
        if (bossStageController != null) {
            bossStageController.updatePlayerPosition(
                character.getX(), 
                character.getY(), 
                character.getWidth(), 
                character.getHeight()
            );
            
            bossStageController.update(0.016);
            
            // Check minion stomp
            double bounceVelocity = bossStageController.checkMinionStomp(
                character.getX(), 
                character.getY(), 
                character.getWidth(), 
                character.getHeight(), 
                character.getVelocityY()
            );
            
            if (bounceVelocity != 0) {
                character.setVelocityY(bounceVelocity);
            } else if (bossStageController.checkPlayerCollisions(
                character.getX(), 
                character.getY(), 
                character.getWidth(), 
                character.getHeight(), 
                character.getVelocityY()
            )) {
                die();
            }
        }
        
        // Check boss defeated
        if (!bossDefeated && bossStageController.getBoss().isBossDead()) {
            bossDefeated = true;
            character.die();
            character.setDeathAnimationComplete(true);
            
            if (themeClip != null) {
                Utilities.stopLoopSong(themeClip);
            }
            victoryClip = Utilities.playLoopSong(GameConstants.FILE_VICTORY_SOUND);
            stopMapTimer();
        }
        
        // Handle physics and movement
        handlePhysics();
        handleMovement();
        
        repaint();
    }
    
    
    //Override die to stop boss theme
    @Override
    protected void die() {
        super.die();
        
        if (themeClip != null) {
            Utilities.stopLoopSong(themeClip);
        }
    }
    
    
    //Override restart
    @Override
    protected void restart() {
        super.restart();
        
        if (bossStageController != null) {
            bossStageController.reset();
        }
        
        generateBossPlatforms();
        bossStageController.setPlatforms(platforms);
        bossDefeated = false;
        
        Utilities.updateThemeSong(0, 3);
    }
    
    //Override escape to menu
    @Override
    protected void handleEscapeToMenu() {
        if (victoryClip != null) {
            Utilities.stopLoopSong(victoryClip);
        }
        super.handleEscapeToMenu();
    }
    
    private void openSettings() {
        if (settingsOverlay == null) {
            settingsOverlay = new SettingsOverlay(() -> closeSettings());
        }
        stopMapTimer();
        isSettingsOpen = true;
        
        setLayout(null);
        
        add(settingsOverlay);
        settingsOverlay.setBounds(0, 0, getWidth(), getHeight());
        
        setComponentZOrder(settingsOverlay, 0);
        
        // ให้ settingsOverlay รับ focus
        settingsOverlay.setVisible(true);
        settingsOverlay.requestFocusInWindow();
        
        revalidate();
        repaint();
        
        System.out.println("Settings opened in Boss Stage");
    }
    
    private void closeSettings() {
        if (settingsOverlay != null) {
            remove(settingsOverlay);
            isSettingsOpen = false;
            
            // Resume timer
            if (!character.isDead() && countTimer != null && !countTimer.isRunning()) {
                startTime = System.currentTimeMillis() - (long)(elapsedSeconds * 1000);
                countTimer.start();
            }
            
            requestFocusInWindow();
            revalidate();
            repaint();
            
            System.out.println("Settings closed in Boss Stage");
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        System.out.println("BossStage keyPressed: " + KeyEvent.getKeyText(key) + 
                         ", isDead=" + character.isDead() + 
                         ", isSettingsOpen=" + isSettingsOpen);
        
        if (isSettingsOpen) {
            return;
        }
        
        if (key == KeyEvent.VK_ESCAPE && !character.isDead()) {
            openSettings();
            e.consume(); 
            return;
        }
        if (key == KeyEvent.VK_ESCAPE && character.isDead() && goMenu) {
            handleEscapeToMenu();
            return;
        }
        
        if (key == KeyEvent.VK_R && character.isDead() && goRestart) {
            restart();
            return;
        }
        
        if (key == KeyEvent.VK_A) {
            character.setMovingLeft(true);
        }
        if (key == KeyEvent.VK_D) {
            character.setMovingRight(true);
        }
        if (key == KeyEvent.VK_W) {
            character.jump();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (isSettingsOpen) {
            return;
        }
        
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            character.setMovingLeft(false);
        }
        if (key == KeyEvent.VK_D) {
            character.setMovingRight(false);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }
    
}