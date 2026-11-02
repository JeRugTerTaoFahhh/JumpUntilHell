package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.Clip;


public class BossStageController {
    private final Boss boss;
    private final ArrayList<Fireball> fireballs;
    private final ArrayList<Minion> minions;
    private ArrayList<Platform> platforms;
    private final Random random;
    private Clip soundEffect;
    
    private Boss.AttackPattern lastAttackPattern = null;
    private boolean attackExecuted = false;
    
    private int playerX;
    private int playerY;
    private int playerWidth;
    private int playerHeight;
    
    private final int frameWidth;
    private final int frameHeight;
    
    public BossStageController(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.random = new Random();
        this.fireballs = new ArrayList<>();
        this.minions = new ArrayList<>();
        this.platforms = new ArrayList<>();
        this.boss = new Boss();
    }
    
    public void updatePlayerPosition(int x, int y, int width, int height) {
        this.playerX = x;
        this.playerY = y;
        this.playerWidth = width;
        this.playerHeight = height;
    }
    
    public void setPlatforms(ArrayList<Platform> platforms) {
        this.platforms = platforms;
    }
    
    public void update(double deltaTime) {
        if (boss != null && boss.isAlive()) {
            boss.update();
            handleBossAttacks(); 
        }
        for (Platform platform : platforms) {
            platform.update(deltaTime);
        }
        for (int i = fireballs.size() - 1; i >= 0; i--) {
            Fireball fireball = fireballs.get(i);
            fireball.update();
            if (!fireball.isActive()) {
                fireballs.remove(i);
            }
        }
        
        for (int i = minions.size() - 1; i >= 0; i--) {
            Minion minion = minions.get(i);
            minion.update(minions);
            
            if (!minion.isAlive()) {
                minions.remove(i);
            }
        }
    }
    public boolean checkPlayerCollisions(int x, int y, int width, int height, double velocityY) {
        for (Fireball fireball : fireballs) {
            if (fireball.checkCollision(x, y, width, height)) {
                return true;
            }
        }
        
        for (Minion minion : minions) {
            if (minion.checkStompCollision(x, y, width, height, velocityY)) {
                return false;
            }
            else if (minion.checkCollision(x, y, width, height)) {
                return true; 
            }
        }
        
        return false;
    }
    
   
    public double checkMinionStomp(int x, int y, int width, int height, double velocityY) {
        for (Minion minion : minions) {
            if (minion.checkStompCollision(x, y, width, height, velocityY)) {
                minion.takeDamage();
                soundEffect = Utilities.playSoundEffect(GameConstants.FILE_KILL_MINION_SOUND);
                if (!minion.isAlive()) {
                    boss.takeDamage(10);
                }
                return -8; 
            }
        }
        return 0;
    }
    
    private void handleBossAttacks() {
        Boss.AttackPattern currentPattern = boss.getCurrentPattern();
        
        if (boss.isAttacking() && currentPattern != lastAttackPattern) {
            lastAttackPattern = currentPattern;
            attackExecuted = false;
        }
        if (boss.isAttacking() && !attackExecuted) {
            attackExecuted = true;
            
            switch (currentPattern) {
                case AXE_SWING:
                    executeAxeSwingAttack(); 
                    break;
                case FIREBALL_RAIN:
                    executeFireballRainAttack(); 
                    break;
                case SUMMON_MINIONS:
                    executeSummonMinionsAttack(); 
                    break;
            }
        }
        if (!boss.isAttacking()) {
            lastAttackPattern = null;
            attackExecuted = false;
        }
    }
    
    private void executeAxeSwingAttack() {
        int numToShake = 2 + random.nextInt(2); 
        soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BOSS_AXE_SOUND);
        
        ArrayList<Platform> availablePlatforms = new ArrayList<>();
        for (Platform platform : platforms) {
            if (!platform.isGone() && !platform.isShaking()) {
                availablePlatforms.add(platform);
            }
        }
        for (int i = 0; i < Math.min(numToShake, availablePlatforms.size()); i++) {
            int index = random.nextInt(availablePlatforms.size());
            availablePlatforms.get(index).startShaking();
            availablePlatforms.remove(index);
        }
    }
    
    private void executeFireballRainAttack() {
        int numFireballs = 10 + random.nextInt(6); 
        soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BOSS_FIREBALL_SOUND);
        
        for (int i = 0; i < numFireballs; i++) {
            int fireballX = random.nextInt(frameWidth);
            fireballs.add(new Fireball(fireballX));
        }
    }
    
    private void executeSummonMinionsAttack() {
        int numMinions = 2 + random.nextInt(2);
        int safeDistance = 100;
        soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BOSS_SUMMON_SOUND);
        
        for (int i = 0; i < numMinions; i++) {
            int minionX;
            int attempts = 0;
            do {
                minionX = random.nextInt(frameWidth);
                attempts++;
            } while (Math.abs(minionX - playerX) < safeDistance && attempts < 50);
            
            int minionY = GameConstants.GROUND_Y - 40;
            minions.add(new Minion(minionX, minionY));
        }
    }
    public void reset() {
        fireballs.clear();
        minions.clear();
        if (boss != null) {
            boss.heal();
        }
        lastAttackPattern = null;
        attackExecuted = false;
    }
    
    public Boss getBoss() { return boss; }
    public ArrayList<Fireball> getFireballs() { return fireballs; }
    public ArrayList<Minion> getMinions() { return minions; }
}