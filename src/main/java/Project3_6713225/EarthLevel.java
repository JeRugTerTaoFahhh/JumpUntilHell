package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.Graphics2D;
import java.awt.image.*;

public class EarthLevel extends BaseLevel {

    private final BufferedImage platformImg_1, platformImg_2, platformImg_3, platformImg_4, platformImg_5, platformImg_6;

    public EarthLevel() {
        Utilities.updateThemeSong(Utilities.getCurrentAlbumIndex(), 0);
        
        platformImg_1 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_P1);
        platformImg_2 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_P2);
        platformImg_3 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_P3);
        platformImg_4 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_P4);
        platformImg_5 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_P5);
        platformImg_6 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_P6);
        background = Utilities.loadSingleImage(GameConstants.FILE_EARTH_BACKGROUND);
        buildLevel();
    }

    private void buildLevel() {
        Platform ground = new Platform(0, 626, 1277, 47, platformImg_1);
        elements.add(ground);
        Block.add(ground.getBounds());

        Platform p1 = new Platform(224, 497, 287, 43, platformImg_2);
        elements.add(p1);
        Block.add(p1.getBounds());
        
        Platform p2 = new Platform(631, 496, 81, 39, platformImg_3);
        elements.add(p2);
        Block.add(p2.getBounds());
        
        Platform p3 = new Platform(820, 495, 230, 45, platformImg_4);
        elements.add(p3);
        Block.add(p3.getBounds());
        
        Platform p4 = new Platform(1131, 419, 145, 44, platformImg_5);
        elements.add(p4);
        Block.add(p4.getBounds());
        
        Platform p5 = new Platform(827, 341, 235, 43, platformImg_4);
        elements.add(p5);
        Block.add(p5.getBounds());
        
        Platform p6 = new Platform(526, 298, 186, 43, platformImg_6);
        elements.add(p6);
        Block.add(p6.getBounds());
        
        Platform p7 = new Platform(163, 298, 181, 43, platformImg_6);
        elements.add(p7);
        Block.add(p7.getBounds());
        
        Platform p8 = new Platform(391, 206, 81, 39, platformImg_3);
        elements.add(p8);
        Block.add(p8.getBounds());
        
        Platform p9 = new Platform(158, 86, 186, 43, platformImg_6);
        elements.add(p9);
        Block.add(p9.getBounds()); 
    }
    
    @Override
    public void update(double dt) {
        super.update(dt);
    }
    
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2); 
    }
    
    public static class EarthStage extends GameStage {
        
        public EarthStage(EarthLevel level) {
            super(level.getBackground(), level.getPlatforms(), level.getElements());
            
            setupSpikes();
            setupWalls();
        }
        
        /**
         * Setup spikes for Earth level
         */
        private void setupSpikes() {
            BufferedImage spikeImg_1 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_S1);
            BufferedImage spikeImg_2 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_S2);
            BufferedImage spikeImg_3 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_S3);
            BufferedImage spikeImg_4 = Utilities.loadSingleImage(GameConstants.FILE_EARTH_S4);

            addSpike(217, 595, 313, 35, spikeImg_4, 198, 527, 17, 99);
            addSpike(693, 600, 166, 33, spikeImg_3, 759, 359, 68, 136);
            addSpike(530, 598, 166, 33, spikeImg_3, 472, 541, 199, 67);
            addSpike(983, 598, 313, 35, spikeImg_4, 1063, 515, 214, 95);
            
            Spike s3 = addSpike(870, 467, 166, 33, spikeImg_3, 827, 363, 34, 136);
            s3.setTemporary(1.2);

            Spike s4 = addSpike(603, 270, 109, 34, spikeImg_2, 712, 204, 45, 99);
            s4.setTemporary(1.2);

            Spike s5 = addSpike(404, 176, 56, 33, spikeImg_1, 472, 110, 24, 99);
            s5.setTemporary(1.2);

            Spike s6 = addSpike(224, 270, 109, 33, spikeImg_2, 231, 200, 93, 99);
            s6.setTemporary(1.2);
        }
        
        /**
         * Setup walls for Earth level
         */
        private void setupWalls() {
            BufferedImage wallImg = Utilities.loadSingleImage(GameConstants.FILE_EARTH_WALL);
            Wall w = addWall(814, 373, 30, 128, wallImg, 759, 359, 68, 136);
            w.setTemporary(1.2);
        }
        
        
        /**
         * Setup warps and key for Earth level
         */
        public void setupWarpsAndKey(GameFrame gameFrame, Menu app, Runnable onComplete) {
            BufferedImage keyImg = Utilities.loadSingleImage(GameConstants.FILE_EARTH_KEY);
            BufferedImage portalImg = Utilities.loadSingleImage(GameConstants.FILE_EARTH_PORTAL);
            BufferedImage doorImg = Utilities.loadSingleImage(GameConstants.FILE_EARTH_DOOR);
            
            // Portal 1: Right to Left
            addWarp(1212, 349, 65, 79, portalImg, 1212, 349, 65, 79, 254, 192, false, null);

            // Portal 2: Left to Right
            addWarp(165, 226, 65, 79, portalImg, 165, 226, 65, 79, 1140, 360, false, null);

            // Key
            addKey(156, 0, 49, 86, keyImg);

            // Exit door (requires key)
            addWarp(893, 250, 100, 99, doorImg, 893, 250, 100, 99, 0, 0, true, () -> {
                exitStage();
                
                if (onComplete != null) {
                    onComplete.run();
                }
                gameFrame.dispose();
                app.showCard("LEVEL");
                app.setVisible(true);
            });
        }
    }
}