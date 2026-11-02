package Project3_6713225;

/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.sound.sampled.Clip;


public abstract class GameStage extends JPanel implements ActionListener, KeyListener {

    // Core components
    protected Character character;
    protected BufferedImage background;
    protected ArrayList<Platform> platforms;
    protected List<Element> elements;

    // Game state
    protected boolean exitedStage = false;
    protected int deathCount = 0;
    protected boolean dCount = false;
    protected boolean goRestart = false;
    protected boolean goMenu = false;
    private boolean hasRecorded = false;

    // Timer
    protected Timer timer;
    protected Timer countTimer;
    protected long startTime;
    protected double elapsedSeconds;

    // Obstacles
    protected ArrayList<Spike> spikes = new ArrayList<>();
    protected ArrayList<SpikeTrigger> spikeTriggers = new ArrayList<>();
    protected ArrayList<Wall> walls = new ArrayList<>();
    protected ArrayList<WallTrigger> wallTriggers = new ArrayList<>();
    protected ArrayList<Portal> portals = new ArrayList<>();
    protected ArrayList<Warp> warps = new ArrayList<>();
    protected ArrayList<Key> keys = new ArrayList<>();
    protected ArrayList<MovingElement> movingElements = new ArrayList<>();
    protected ArrayList<MovingTrigger> movingTriggers = new ArrayList<>();
    protected ArrayList<KeyAttackTrigger> keyAttackTriggers = new ArrayList<>();

    // Settings
    private SettingsOverlay settingsOverlay;
    private boolean isSettingsOpen = false;

    // Sound
    protected int savedAlbumIndex;
    protected int savedLevelIndex;
    private Clip soundEffect;

    // Background Thread Components
    private ExecutorService backgroundExecutor;
    private BlockingQueue<BackgroundTask> taskQueue;
    private volatile boolean isShuttingDown = false;

    protected boolean isVictory = false;
    
    
    //Constructor
     
    public GameStage(BufferedImage background, ArrayList<Platform> platforms, List<Element> elements) {
        this.background = background;
        this.platforms = platforms != null ? platforms : new ArrayList<>();
        this.elements = elements != null ? elements : new ArrayList<>();
        this.character = new Character();

        setPreferredSize(new Dimension(GameConstants.FRAME_WIDTH, GameConstants.FRAME_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        savedAlbumIndex = Utilities.getCurrentAlbumIndex();
        savedLevelIndex = Utilities.getCurrentLevelIndex();

        // Initialize background thread
        initBackgroundThread();

        timer = new Timer(GameConstants.TIMER_DELAY, this);
        timer.start();
        startMapTimer();
    }

    private void initBackgroundThread() {
        taskQueue = new LinkedBlockingQueue<>();
        backgroundExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "GameStage-Background");
            t.setDaemon(true);
            return t;
        });

        // Start background worker
        backgroundExecutor.submit(() -> {
            while (!isShuttingDown) {
                try {
                    BackgroundTask task = taskQueue.poll(100, TimeUnit.MILLISECONDS);
                    if (task != null) {
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("Background task error: " + e.getMessage());
                }
            }
        });
    }

    
    protected void submitBackgroundTask(BackgroundTask task) {
        if (!isShuttingDown) {
            taskQueue.offer(task);
        }
    }

    protected void shutdownBackgroundThread() {
        isShuttingDown = true;
        if (backgroundExecutor != null) {
            backgroundExecutor.shutdown();
            try {
                if (!backgroundExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                    backgroundExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                backgroundExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    
    protected void startMapTimer() {
        startTime = System.currentTimeMillis();
        countTimer = new Timer(100, e -> updateMapTime());
        elapsedSeconds = 0;
        countTimer.start();
    }

    protected void updateMapTime() {
        long currentTime = System.currentTimeMillis();
        elapsedSeconds = (currentTime - startTime) / 1000.0;
    }

    public void stopMapTimer() {
        if (countTimer != null) {
            countTimer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        if (background != null) {
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }

        // Draw platforms
        for (Platform p : platforms) {
            p.draw(g2d);
        }

        // Draw game objects
        drawGameObjects(g2d);

        // Draw character
        character.draw(g2d);

        // Draw UI
        drawUI(g2d);

        // Draw game over screen
        if (character.isDead() && character.isDeathAnimationComplete()) {
            drawGameOverScreen(g2d, "GameOver");
        }
        if (isVictory) {
            drawGameOverScreen(g2d,"Victory");
    }
    }

    protected void drawGameObjects(Graphics2D g2d) {
        for (Portal p : portals) {
            p.draw(g2d);
        }
        for (Spike s : spikes) {
            s.draw(g2d);
        }
        for (Wall w : walls) {
            w.draw(g2d);
        }
        for (Key k : keys) {
            k.draw(g2d);
        }
        for (MovingElement m : movingElements) {
            if (!character.isDead) {
                m.draw(g2d);
            }
        }
    }

    protected void drawUI(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.BOLD, 24));
        g2d.setColor(new Color(64, 224, 208));
        String timeText = String.format("Time: %.1fs", elapsedSeconds);
        g2d.drawString(timeText, 13, 33);
    }

    protected void drawGameOverScreen(Graphics2D g2d, String endGameText) {
        
        goRestart = false;
        goMenu = false;
        String timeText = String.format("Time: %.1fs", elapsedSeconds);

        // Dark overlay
        g2d.setColor(new Color(0, 0, 0, 200));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Title text
        g2d.setColor(endGameText.equals("Victory") ? Color.GREEN : Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 72));
        FontMetrics fmGameOver = g2d.getFontMetrics();
        int gameOverX = (getWidth() - fmGameOver.stringWidth(endGameText)) / 2;
        int gameOverY = getHeight() / 2 - 50;
        g2d.drawString(endGameText, gameOverX, gameOverY);

        // Time text
        g2d.setColor(new Color(64, 224, 208));
        g2d.setFont(new Font("Monospaced", Font.BOLD, 36));
        FontMetrics fmStats = g2d.getFontMetrics();
        int timeX = (getWidth() - fmStats.stringWidth(timeText)) / 2;
        int timeY = gameOverY + 80;
        g2d.drawString(timeText, timeX, timeY);

        // Death count text
        int totalDeaths = StageStatistics.getInstance().getTotalSessionDeaths();
    String totalDeathText = String.format("You died %d times.", totalDeaths);

    int totalDeathX = (getWidth() - fmStats.stringWidth(totalDeathText)) / 2;
    int totalDeathY = timeY + 50;
    g2d.drawString(totalDeathText, totalDeathX, totalDeathY);
       
        
         
    g2d.drawString(totalDeathText, totalDeathX, totalDeathY);
    
        // Instructions
        g2d.setFont(new Font("Arial", Font.PLAIN, 24));
        g2d.setColor(Color.WHITE);
        FontMetrics fmHint = g2d.getFontMetrics();
        int hintY = totalDeathY + 80;

        if (endGameText.equals("GameOver")) {
            String hintText = "Press R to Restart";
            int hintX = (getWidth() - fmHint.stringWidth(hintText)) / 2;
            g2d.drawString(hintText, hintX, hintY);
            goRestart = true;
        } else if (endGameText.equals("Victory")) {
            String hintText = "Press Esc to Main menu";
            int hintX = (getWidth() - fmHint.stringWidth(hintText)) / 2;
            g2d.drawString(hintText, hintX, hintY);
            goMenu = true;
        }
    }

    /**
     * Action performed - main game loop
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isSettingsOpen) {
            repaint();
            return;
        }
        if (isVictory) {
        repaint();
        return;
    }
        // Handle death animation
        if (character.isDead()) {
            if (!character.isDeathAnimationComplete()) {
                stopMapTimer();
                character.setDeathVelocityY(character.getDeathVelocityY() + character.GRAVITY);
                character.setY(character.getY() + (int) character.getDeathVelocityY());

                if (character.getY() > getHeight()) {
                    character.setDeathAnimationComplete(true);

                }
            }
            repaint();
            return;
        }

        // Update game state
        updateGameState();

        // Handle physics and movement
        handlePhysics();
        handleMovement();

        repaint();
    }

   

    /**
     * Update game state - to be overridden by subclasses
     */
    protected void updateGameState() {
        double dt = 0.016;

        // Update platforms
        for (Platform platform : platforms) {
            platform.update(dt);
        }

        Rectangle playerBounds = character.getBounds();

        // Check spike triggers
        for (SpikeTrigger trigger : spikeTriggers) {
            if (!trigger.activated && playerBounds.intersects(trigger.area)) {
                if (trigger.requireKey && !character.hasKey()) {
                    continue;
                }
                trigger.activated = true;
                for (Spike s : trigger.spikesToActivate) {
                    s.setActive(true);
                }
            }
        }

        // Update spikes
        for (Spike s : spikes) {
            s.update(dt);
            if (s.isActive() && playerBounds.intersects(s.getBounds())) {
                die();
                break;
            }
        }

        // Check wall triggers
        for (WallTrigger trigger : wallTriggers) {
            if (!trigger.activated && playerBounds.intersects(trigger.area)) {
                if (trigger.requireKey && !character.hasKey()) {
                    continue;
                }
                trigger.activated = true;
                for (Wall w : trigger.wallsToActivate) {
                    w.setActive(true);
                }
            }
        }

        // Update walls
        for (Wall w : walls) {
            w.update(dt);
        }

        // Check warps
        for (Warp wz : warps) {
            if (playerBounds.intersects(wz.area)) {
                if (wz.requireKey && !character.hasKey()) {
                    System.out.println("Door locked. Need key first.");
                    continue;
                }
                if (wz.onWarp != null) {
                    wz.onWarp.run();
                } else {
                    character.setX(wz.targetX);
                    character.setY(wz.targetY);
                    character.setVelocityY(0);
                    character.setJumping(false);
                    character.setFalling(true);
                }
                break;
            }
        }

        // Update keys
        for (Key k : keys) {
            k.update(dt);
        }

        // Check key attack triggers
        for (KeyAttackTrigger t : keyAttackTriggers) {
            if (!t.used && playerBounds.intersects(t.area)) {
                t.used = true;
                t.key.startAttack();
            }
        }

        // Check key attacks player
        for (Key k : keys) {
            if (k.isInAttackMode() && playerBounds.intersects(k.getBounds())) {
                die();
                break;
            }
        }

        // Check key collection
        for (Key k : keys) {
            if (!k.isCollected() && !k.isInAttackMode() && playerBounds.intersects(k.getBounds())) {
                // Get key position before collecting
                Rectangle keyBounds = k.getBounds();
                final int keyX = keyBounds.x;
                final int keyY = keyBounds.y;

                k.collect();
                character.setHasKey(true);
                character.setCarriedKey(k);

               
                break;
            }
        }

        // Check moving element triggers
        for (MovingTrigger t : movingTriggers) {
            if (!t.activated && playerBounds.intersects(t.area)) {
                if (t.requireKey && !character.hasKey()) {
                    continue;
                }
                t.activated = true;
                for (MovingElement m : t.list) {
                    m.activate();
                }
            }
        }

        // Update moving elements
        for (MovingElement m : movingElements) {
            m.update(dt);
            if (m.isActive() && playerBounds.intersects(m.getBounds())) {
                die();
                break;
            }
        }
    }

    

    /**
     * Handle physics (jumping, falling, collisions)
     */
    protected void handlePhysics() {
        if (character.isJumping() || character.isFalling()) {
            character.applyGravity();

            // Check head collision with platforms
            if (character.getVelocityY() < 0) {
                for (Platform p : platforms) {
                    if (p.isGone()) {
                        continue;
                    }
                    if (p.isHitHead(character.getX(), character.getY(),
                            character.getWidth(), character.getHeight())) {
                        character.setY(p.getBottomY());
                        character.setVelocityY(0);
                        character.setJumping(false);
                        character.setFalling(true);
                        break;
                    }
                }
            }

            // Check platform landing
            boolean onPlatform = false;
            if (character.getVelocityY() >= 0) {
                for (Platform platform : platforms) {
                    if (!platform.isGone()
                            && platform.isPlayerOn(character.getX(), character.getY(),
                                    character.getWidth(), character.getHeight())) {
                        character.setY(platform.getTopY() - character.getHeight());
                        character.setVelocityY(0);
                        character.setJumping(false);
                        character.setFalling(false);
                        onPlatform = true;
                        break;
                    }
                }
            }

            // Check if fell off screen
            if (!onPlatform) {
                character.setFalling(true);
                if (character.getY() > getHeight()) {
                    die();
                }
            }
        } else {
            // Check if should fall
            boolean onPlatform = false;
            for (Platform platform : platforms) {
                if (!platform.isGone()
                        && platform.isPlayerOn(character.getX(), character.getY(),
                                character.getWidth(), character.getHeight())) {
                    onPlatform = true;
                    break;
                }
            }

            if (!onPlatform) {
                character.setFalling(true);
            }
        }

        // Check boundaries
        if (character.getX() < 0 || character.getX() >= getWidth() - character.getWidth()) {
            die();
        }
    }

    /**
     * Handle horizontal movement
     */
    protected void handleMovement() {
        int nextX = character.getX();

        if (character.isMovingLeft() && character.isMovingRight()) {
            character.resetAnimation();
        } else if (character.isMovingLeft() && character.getX() >= 0) {
            character.setFacingLeft(true);
            nextX -= character.getSpeed();
            if (!character.isJumping() && !character.isFalling()) {
                character.animateWalk();
            }
        } else if (character.isMovingRight() && character.getX() <= getWidth() - character.getWidth()) {
            character.setFacingLeft(false);
            nextX += character.getSpeed();
            if (!character.isJumping() && !character.isFalling()) {
                character.animateWalk();
            }
        } else {
            character.resetAnimation();
        }

        // Check wall collisions
        if (!walls.isEmpty()) {
            Rectangle currentBounds = character.getBounds();
            Rectangle nextBounds = new Rectangle(nextX, character.getY(),
                    character.getWidth(), character.getHeight());

            for (Wall w : walls) {
                if (!w.isActive()) {
                    continue;
                }

                Rectangle wallBounds = w.getBounds();
                if (!currentBounds.intersects(wallBounds) && nextBounds.intersects(wallBounds)) {
                    nextX = character.getX();
                    break;
                }
            }
        }

        character.setX(nextX);
    }

    /**
     * Player dies
     */
    protected void die() {
        if (exitedStage || character.isDead()) {
            return;
        }

        character.die();

        if (!dCount) {
            dCount = true;
            deathCount++;
             StageStatistics stats = StageStatistics.getInstance();
stats.addOneDeath();

        submitBackgroundTask(() -> {
            stats.save();
            int totalDeaths = stats.getTotalSessionDeaths();
            System.out.printf("Total Session Deaths: %d | Time: %.1fs%n", 
                totalDeaths, elapsedSeconds);
        });
        }

        savedAlbumIndex = Utilities.getCurrentAlbumIndex();
        savedLevelIndex = Utilities.getCurrentLevelIndex();
    }

    /**
     * Restart game
     */
    protected void restart() {
        character.reset();
        dCount = false;
        hasRecorded = false;

        // Reset obstacles
        for (Spike s : spikes) {
            s.setActive(false);
        }
        for (SpikeTrigger trigger : spikeTriggers) {
            trigger.activated = false;
        }
        for (Wall w : walls) {
            w.setActive(false);
        }
        for (WallTrigger trigger : wallTriggers) {
            trigger.activated = false;
        }
        for (MovingElement m : movingElements) {
            m.reset();
        }
        for (MovingTrigger trigger : movingTriggers) {
            trigger.activated = false;
        }
        for (Key k : keys) {
            k.reset();
        }
        for (KeyAttackTrigger t : keyAttackTriggers) {
            t.used = false;
        }

        // Resume timer
        if (countTimer != null && !countTimer.isRunning()) {
            startTime = System.currentTimeMillis() - (long) (elapsedSeconds * 1000);
            countTimer.start();
        }

        // Handle music
        int currentAlbum = Utilities.getCurrentAlbumIndex();
        int currentLevel = Utilities.getCurrentLevelIndex();

        if (currentAlbum != savedAlbumIndex) {
            Utilities.updateThemeSong(currentAlbum, currentLevel);
            savedAlbumIndex = currentAlbum;
            savedLevelIndex = currentLevel;
        } else {
            if (!Utilities.isGlobalMuted()) {
                Utilities.unmuteTheme();
            }
        }
    }

    

    /**
     * Open settings overlay
     */
    private void openSettings() {
        if (settingsOverlay == null) {
            settingsOverlay = new SettingsOverlay(() -> closeSettings());
        }
        stopMapTimer();
        isSettingsOpen = true;
        setLayout(null);
        add(settingsOverlay);
        settingsOverlay.setBounds(0, 0, getWidth(), getHeight());
        settingsOverlay.requestFocusInWindow();
        revalidate();
        repaint();
    }

    /**
     * Close settings overlay
     */
    private void closeSettings() {
        if (settingsOverlay != null) {
            remove(settingsOverlay);
            isSettingsOpen = false;

            if (countTimer != null && !countTimer.isRunning()) {
                startTime = System.currentTimeMillis() - (long) (elapsedSeconds * 1000);
                countTimer.start();
            }

            requestFocusInWindow();
            revalidate();
            repaint();
        }
    }

    // Helper methods for adding obstacles
    public Spike addSpike(int spikeX, int spikeY, int spikeW, int spikeH, BufferedImage spikeImg,
            int triggerX, int triggerY, int triggerW, int triggerH) {
        return addSpike(spikeX, spikeY, spikeW, spikeH, spikeImg,
                triggerX, triggerY, triggerW, triggerH, false);
    }

    public Spike addSpike(int spikeX, int spikeY, int spikeW, int spikeH, BufferedImage spikeImg,
            int triggerX, int triggerY, int triggerW, int triggerH, boolean requireKey) {
        Spike spike = new Spike(spikeX, spikeY, spikeW, spikeH, spikeImg);
        spikes.add(spike);

        SpikeTrigger trigger = new SpikeTrigger();
        trigger.area = new Rectangle(triggerX, triggerY, triggerW, triggerH);
        trigger.spikesToActivate.add(spike);
        trigger.requireKey = requireKey;
        spikeTriggers.add(trigger);

        return spike;
    }

    public Wall addWall(int wallX, int wallY, int wallW, int wallH, BufferedImage wallImg,
            int triggerX, int triggerY, int triggerW, int triggerH) {
        return addWall(wallX, wallY, wallW, wallH, wallImg,
                triggerX, triggerY, triggerW, triggerH, false);
    }

    public Wall addWall(int wallX, int wallY, int wallW, int wallH, BufferedImage wallImg,
            int triggerX, int triggerY, int triggerW, int triggerH, boolean requireKey) {
        Wall wall = new Wall(wallX, wallY, wallW, wallH, wallImg);
        walls.add(wall);

        WallTrigger trigger = new WallTrigger();
        trigger.area = new Rectangle(triggerX, triggerY, triggerW, triggerH);
        trigger.wallsToActivate.add(wall);
        trigger.requireKey = requireKey;
        wallTriggers.add(trigger);

        return wall;
    }

    public void addWarp(int portalX, int portalY, int portalW, int portalH, BufferedImage portalImg,
            int triggerX, int triggerY, int triggerW, int triggerH,
            int targetX, int targetY, boolean requireKey, Runnable onWarp) {
        Portal portal = new Portal(portalX, portalY, portalW, portalH, portalImg);
        portals.add(portal);

        Warp wz = new Warp();
        wz.area = new Rectangle(triggerX, triggerY, triggerW, triggerH);
        wz.portal = portal;
        wz.targetX = targetX;
        wz.targetY = targetY;
        wz.requireKey = requireKey;
        wz.onWarp = onWarp;
        warps.add(wz);
    }

    public Key addKey(int keyX, int keyY, int keyW, int keyH, BufferedImage keyImg) {
        Key key = new Key(keyX, keyY, keyW, keyH, keyImg);
        keys.add(key);
        return key;
    }

    public void addKeyAttack(Key key, int triggerX, int triggerY, int triggerW, int triggerH) {
        KeyAttackTrigger t = new KeyAttackTrigger();
        t.area = new Rectangle(triggerX, triggerY, triggerW, triggerH);
        t.key = key;
        keyAttackTriggers.add(t);
    }

    public MovingElement addMovingElement(int x, int y, int w, int h, BufferedImage img,
            int targetX, int targetY, double speed,
            int triggerX, int triggerY, int triggerW, int triggerH,
            boolean requireKey) {
        MovingElement m = new MovingElement(x, y, w, h, img, targetX, targetY, speed);
        movingElements.add(m);

        MovingTrigger t = new MovingTrigger();
        t.area = new Rectangle(triggerX, triggerY, triggerW, triggerH);
        t.list.add(m);
        t.requireKey = requireKey;

        movingTriggers.add(t);

        return m;
    }

    // Key listener implementation
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            character.setMovingLeft(true);
        }
        if (key == KeyEvent.VK_D) {
            character.setMovingRight(true);
        }
        if (key == KeyEvent.VK_W) {
            character.jump();
        }
        if (key == KeyEvent.VK_R && character.isDead() && goRestart) {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            restart();
        }
        if (key == KeyEvent.VK_ESCAPE && character.isDead() && goMenu) {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            handleEscapeToMenu();
        }
        if (key == KeyEvent.VK_ESCAPE && !character.isDead()) {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            openSettings();
        }
    }

    /**
     * Handle escape to menu - can be overridden
     */
    protected void handleEscapeToMenu() {
        // Save final statistics in background before exit
        final int deaths = deathCount;
        final double time = elapsedSeconds;
       

        // Shutdown background thread
        shutdownBackgroundThread();

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
        new Menu();
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
    public void keyTyped(KeyEvent e) {
    }

    // Inner classes for obstacles
    protected static class SpikeTrigger {

        Rectangle area;
        boolean activated = false;
        boolean requireKey = false;
        ArrayList<Spike> spikesToActivate = new ArrayList<>();
    }

    protected static class WallTrigger {

        Rectangle area;
        boolean activated = false;
        boolean requireKey = false;
        ArrayList<Wall> wallsToActivate = new ArrayList<>();
    }

    protected static class Warp {

        Rectangle area;
        Portal portal;
        int targetX;
        int targetY;
        boolean requireKey;
        Runnable onWarp;
    }

    protected static class MovingTrigger {

        Rectangle area;
        boolean activated = false;
        boolean requireKey = false;
        ArrayList<MovingElement> list = new ArrayList<>();
    }

    protected static class KeyAttackTrigger {

        Rectangle area;
        Key key;
        boolean used = false;
    }

    /**
     * Functional interface
     */
    @FunctionalInterface
    protected interface BackgroundTask {

        void run();
    }

    // Getters
    public Character getCharacter() {
        return character;
    }

    public void exitStage() {
        exitedStage = true;
        shutdownBackgroundThread();
    }
}
