package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.CardLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HellLevel extends BaseLevel {

    private final BufferedImage platformImg_1, platformImg_2, platformImg_3, platformImg_4,
            platformImg_5, platformImg_6, platformImg_7, platformImg_8;
    private JPanel cardPanel;
    private final CardLayout cardLayout =     new CardLayout(); 
    public HellLevel() {
         Utilities.updateThemeSong(Utilities.getCurrentAlbumIndex(), 2);
        background = Utilities.loadSingleImage(GameConstants.FILE_HELL_BACKGROUND);
        platformImg_1 = Utilities.loadSingleImage(GameConstants.FILE_HELL_P1);
        platformImg_2 = Utilities.loadSingleImage(GameConstants.FILE_HELL_P2);
        platformImg_3 = Utilities.loadSingleImage(GameConstants.FILE_HELL_P3);
        platformImg_4 = Utilities.loadSingleImage(GameConstants.FILE_HELL_P4);
        platformImg_5 = Utilities.loadSingleImage(GameConstants.FILE_HELL_P5);
        platformImg_6 = Utilities.loadSingleImage(GameConstants.FILE_HELL_P6);
        platformImg_7 = Utilities.loadSingleImage(GameConstants.FILE_HELL_P7);
        platformImg_8 = Utilities.loadSingleImage(GameConstants.FILE_HELL_P8);
        buildLevel();
    }

    private void buildLevel() {
        Platform ground = new Platform(0, 626, 1322, 43, platformImg_1);
        elements.add(ground);
        Block.add(ground.getBounds());

        Platform p1 = new Platform(279, 527, 102, 43, platformImg_4);
        elements.add(p1);
        Block.add(p1.getBounds());

        Platform p2 = new Platform(387, 455, 209, 44, platformImg_2);
        elements.add(p2);
        Block.add(p2.getBounds());

        Platform p3 = new Platform(810, 499, 205, 45, platformImg_3);
        elements.add(p3);
        Block.add(p3.getBounds());

        Platform p4 = new Platform(572, 370, 250, 44, platformImg_5);
        elements.add(p4);
        Block.add(p4.getBounds());

        Platform p5 = new Platform(222, 329, 159, 44, platformImg_6);
        elements.add(p5);
        Block.add(p5.getBounds());

        Platform p6 = new Platform(0, 207, 258, 44, platformImg_8);
        elements.add(p6);
        Block.add(p6.getBounds());

        Platform p7 = new Platform(917, 263, 363, 43, platformImg_7);
        elements.add(p7);
        Block.add(p7.getBounds());
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }
    
    /**
     * Inner class: HellStage - จัดการ gameplay สำหรับ Hell level
     */
    public static class HellStage extends GameStage {
         private int isBossStageCreated = 0;
        public HellStage(HellLevel level) {
            super(level.getBackground(), level.getPlatforms(), level.getElements());
            setupSpikes();
            setupWalls();
            setupMovingElement();
        }
        
        /**
         * Setup spikes for Hell level
         */
        private void setupSpikes() {
           BufferedImage spikeImg_1 = Utilities.loadSingleImage(GameConstants.FILE_HELL_S1);
        BufferedImage spikeImg_2 = Utilities.loadSingleImage(GameConstants.FILE_HELL_S2);
        BufferedImage upImg = Utilities.loadSingleImage(GameConstants.FILE_HELL_UP);
        BufferedImage downImg = Utilities.loadSingleImage(GameConstants.FILE_HELL_DOWN);

        Spike s1 = addSpike(
                151, 604, 208, 35,
                spikeImg_2,
                161, 406, 46, 221, false
        );
        s1.setTemporary(1.8);

        Spike s2 = addSpike(
                279, 510, 109, 34,
                spikeImg_1,
                237, 428, 40, 109, false
        );
        s2.setTemporary(1.8);

        Spike s3 = addSpike(
                902, 476, 113, 35,
                spikeImg_1,
                910, 382, 40, 85, false
        );
        s3.setTemporary(1.8);

        Spike s4 = addSpike(
                902, 476, 113, 35,
                spikeImg_1,
                904, 430, 55, 80, false
        );
        s4.setTemporary(1.8);

        Spike s5 = addSpike(
                1063, 601, 113, 35,
                spikeImg_1,
                1063, 511, 108, 80, false
        );
        s5.setTemporary(1.8);

        Spike s6 = addSpike(
                435, 467, 60, 177,
                downImg,
                439, 467, 60, 177, false
        );
        s6.setTemporary(1.8);
        
        Spike s7 = addSpike(
                139, 186, 110, 34,
                spikeImg_1,
                142, 72, 137, 144, true
        );
        s7.setTemporary(1.8);

        addSpike(
                620, 601, 99, 34,
                spikeImg_1,
                616, 478, 95, 118, false
        );

        Spike l1 = addSpike(
                110, 621, 36, 23,
                spikeImg_1,
                88, 417, 63, 221, false
        );
        l1.setVisible(false);

        Spike l2 = addSpike(
                448, 450, 36, 23,
                spikeImg_1,
                88, 417, 63, 221, false
        );
        l2.setVisible(false);

        Spike l3 = addSpike(
                673, 355, 36, 23,
                spikeImg_1,
                88, 417, 63, 221, false
        );
        l3.setVisible(false);

        addSpike(
                696, 240, 73, 133,
                upImg,
                696, 240, 73, 133, false
        );

        addSpike(
                696, 240, 73, 133,
                upImg,
                696, 240, 73, 133, true
        );
        }
        
        public void setupWalls() {
            BufferedImage wallImg = Utilities.loadSingleImage(GameConstants.FILE_HELL_WALL);

        Wall w1 = addWall(
                153, 371, 84, 265,
                wallImg,
                113, 373, 41, 245, false
        );
        w1.setTemporary(1.8);

        Wall w2 = addWall(
                731, 478, 52, 156,
                wallImg,
                650, 478, 81, 118, false
        );
        w2.setTemporary(1.8);

        Wall w3 = addWall(
                731, 478, 52, 156,
                wallImg,
                627, 477, 48, 117, true
        );
        w3.setTemporary(1.8);
        }
        
        
        public void setupWarpsAndKey(GameFrame gameFrame, Menu app, Runnable onComplete) {

        BufferedImage keyImg = Utilities.loadSingleImage(GameConstants.FILE_HELL_KEY);
        BufferedImage portalImg = Utilities.loadSingleImage(GameConstants.FILE_HELL_PORTAL);
        BufferedImage doorImg = Utilities.loadSingleImage(GameConstants.FILE_HELL_DOOR);

        addWarp(
                1168, 530, 96, 96, portalImg,
                1193, 545, 52, 67,
                316, 272, false, null
        );

        addWarp(
                202, 241, 96, 96, portalImg,
                227, 270, 52, 67,
                1096, 559, false, null
        );
        
        addWarp(3, 83, 85, 137,
                doorImg,
                19, 151, 52, 67,
                0, 0,
                true, () -> {
                    exitStage();
                if (onComplete != null) {
                    onComplete.run();
                }
                    isBossStageCreated +=1;
                    if(isBossStageCreated == 1){
                    gameFrame.dispose();
                    JFrame frame = new JFrame("Boss Stage");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            BossStage bossStage = new BossStage();
            
            frame.add(bossStage);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
            bossStage.requestFocusInWindow();}
                });
    }
        public void setupMovingElement() {

        BufferedImage spikeImg = Utilities.loadSingleImage(GameConstants.FILE_HELL_S3);
        BufferedImage spikeImg_1 = Utilities.loadSingleImage(GameConstants.FILE_HELL_S1);
        BufferedImage keyImg = Utilities.loadSingleImage(GameConstants.FILE_HELL_KEY);

         Key k = addKey(
                1230, 197, 29, 66, keyImg
        );
        
         k.enableAttack(
            940, 196, 200, 2         
    );
         
         addKeyAttack(
            k, 1117, 169, 57, 94    
    );

        MovingElement m = addMovingElement(
                -35, 397, 35, 106,
                spikeImg,
                1242, 397, 1200,
                528, 368, 57, 89, false
        );
        m.setupWaypoint(1242, 397, 1);
        
        MovingElement m1 = addMovingElement(
                1063, 720, 113, 35,
                spikeImg_1,
                1063, -50, 1200,
                1057, 311, 119, 267, true
        );
        m1.setupWaypoint(1063, -50, 1);
    }
    }
}