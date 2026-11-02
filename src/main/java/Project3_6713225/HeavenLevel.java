package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class HeavenLevel extends BaseLevel {

    private final BufferedImage platformImg_1;
    private final BufferedImage platformImg_2;
    private final BufferedImage platformImg_3;
    private final BufferedImage platformImg_4;

    public HeavenLevel() {
        Utilities.updateThemeSong(Utilities.getCurrentAlbumIndex(), 1);
        
        platformImg_1 = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_P1);
        platformImg_2 = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_P2);
        platformImg_3 = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_P3);
        platformImg_4 = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_P4);
        background = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_BACKGROUND);
        buildLevel();
    }

    private void buildLevel() {
        Platform base = new Platform(0, 626, 312, 43, platformImg_1);
        elements.add(base);
        Block.add(base.getBounds());
        
        Platform p1 = new Platform(191, 497, 206, 43, platformImg_3);
        elements.add(p1);
        Block.add(p1.getBounds());
        
        Platform p2 = new Platform(451, 368, 206, 43, platformImg_3);
        elements.add(p2);
        Block.add(p2.getBounds());
        
        Platform p3 = new Platform(743, 435, 206, 43, platformImg_3);
        elements.add(p3);
        Block.add(p3.getBounds());
        
        Platform p4 = new Platform(191, 241, 206, 43, platformImg_3);
        elements.add(p4);
        Block.add(p4.getBounds());
        
        Platform p5 = new Platform(1020, 516, 260, 44, platformImg_2);
        elements.add(p5);
        Block.add(p5.getBounds());
        
        Platform p6 = new Platform(513, 157, 351, 44, platformImg_4);
        elements.add(p6);
        Block.add(p6.getBounds());
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
    }
    
    /**
     * Inner class: HeavenStage 
     */
    public static class HeavenStage extends GameStage {
        
        public HeavenStage(HeavenLevel level) {
            super(level.getBackground(), level.getPlatforms(), level.getElements());
            setupSpikes();
            setupWalls();
            setupMovingElement();
        }
        
        /**
         * Setup spikes for Heaven level
         */
        private void setupSpikes() {
            BufferedImage spikeImg = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_S1);

        Spike s1 = addSpike(
                513, 131, 332, 36,
                spikeImg,
                463, 17, 51, 135, false
        );
        s1.setTemporary(1.8);

        Spike s2 = addSpike(
                462, 342, 208, 36,
                spikeImg,
                703, 230, 46, 196, false
        );
        s2.setTemporary(1.8);
        }
        
        private void setupWalls() {
            BufferedImage wallImg = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_WALL);

        addWall(
                739, 358, 31, 90,
                wallImg,
                703, 230, 46, 196, false
        );

        addWall(
                739, 268, 31, 90,
                wallImg,
                703, 230, 46, 196, false
        );

        addWall(
                739, 208, 31, 60,
                wallImg,
                703, 230, 46, 196, false
        );

        addWall(
                921, 358, 31, 90,
                wallImg,
                942, 203, 50, 216, true
        );

        addWall(
                921, 268, 31, 90,
                wallImg,
                942, 203, 50, 216, true
        );

        addWall(
                921, 208, 31, 60,
                wallImg,
                942, 203, 50, 216, true
        );
        }
        
        /**
         * Setup warps and key for Heaven level
         */
        public void setupWarpsAndKey(GameFrame gameFrame, Menu app, Runnable onComplete) {

        BufferedImage keyImg = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_KEY);
        BufferedImage portalImg = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_PORTAL);
        BufferedImage portalImg_F = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_PORTALF);
        BufferedImage doorImg = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_DOOR);

        addWarp(
                224, 397, 95, 103, portalImg,
                245, 442, 52, 67,
                578, 559, false, null
        );

        addWarp(
                461, 519, 103, 139, portalImg_F,
                488, 555, 53, 67,
                332, 432, false, null
        );

        addWarp(
                1184, 428, 95, 103, portalImg,
                1208, 462, 53, 67,
                318, 151, false, null
        );

        addWarp(
                196, 146, 95, 103, portalImg,
                219, 181, 53, 67,
                1123, 429, false, null
        );

        addKey(
                1090, 422, 67, 97, keyImg
        );

        addWarp(795, 344, 87, 103,
                doorImg,
                812, 360, 52, 67,
                0, 0,
                true, () -> {
                   
    exitStage();
    stopMapTimer();
    timer.stop();

    if (onComplete != null) {
        onComplete.run();
    }

    SwingUtilities.invokeLater(() -> {
        gameFrame.dispose();
        app.showCard("LEVEL");
        app.setVisible(true);
    });
                });
    }
        public void setupMovingElement() {

        BufferedImage cloundImg = Utilities.loadSingleImage(GameConstants.FILE_HEAVEN_CLOUD);

        MovingElement m = addMovingElement(
                0, 89, 144, 95,
                cloundImg,
                473, 255, 900,
                473, 288, 194, 70, false
        );
        m.setupWaypoint(564, 350,2);
    }
    }
}