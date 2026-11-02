package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.*;
import java.util.Scanner;
import javax.sound.sampled.Clip;
import javax.swing.*;
import static javax.swing.SwingUtilities.getWindowAncestor;

public class LevelSelect extends JPanel {

    private final JLabel drawpane;
    private final JButton home_button, earth_button, heaven_button, hell_button;
    private MyImageIcon homeButtonIcon, earthButtonIcon, heavenButtonIcon, heavenlockButtonIcon,
            hellButtonIcon, helllockButtonIcon, levelselect, background;
    private int heaven, hell;
    private String name;
    private String settingname = "guest";
    private Clip soundEffect;
    private final String inFilename = RuntimePaths.data("Data.txt");

    public LevelSelect(Menu app) {
        setLayout(new BorderLayout());
        setVisible(false);
        background = new MyImageIcon(GameConstants.FILE_BG).resize(1280, 720);
        levelselect = new MyImageIcon(GameConstants.FILE_LEVEL_SELECT);
        JLabel backgroundLayer = new JLabel(background);
        backgroundLayer.setLayout(null);
        drawpane = new JLabel(levelselect);
        drawpane.setBounds(406, 21, 467, 199);

        home_button = new JButton();
        homeButtonIcon = new MyImageIcon(GameConstants.FILE_HOME_BUTTON);
        home_button.addActionListener((ActionEvent e) -> {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            app.showCard("MAIN_MENU");
        });
        
         home_button.addMouseListener(new MouseAdapter(){
        
                 @Override
                 public void mouseEntered(MouseEvent e ){
                    homeButtonIcon = new MyImageIcon(GameConstants.FILE_HOME_BUTTON ).resize(GameConstants.HOME_BUTTON_WIDTH+10, GameConstants.HOME_BUTTON_HEIGHT+10);
                    home_button.setIcon(homeButtonIcon);   
                }
                
                 @Override
                public void mouseExited(MouseEvent e){
                    homeButtonIcon = new MyImageIcon(GameConstants.FILE_HOME_BUTTON ).resize(GameConstants.HOME_BUTTON_WIDTH, GameConstants.HOME_BUTTON_HEIGHT);
                     home_button.setIcon(homeButtonIcon);   
                }    
        });
        
        home_button.setIcon(homeButtonIcon);
        home_button.setBorderPainted(false);
        home_button.setContentAreaFilled(false);
        home_button.setFocusPainted(false);
        home_button.setText("");
        home_button.setBounds(23, 577, 97, 96);

        earth_button = new JButton();
        earthButtonIcon = new MyImageIcon(GameConstants.FILE_EARTH_BUTTON);
        earth_button.addActionListener((ActionEvent e) -> {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            app.setVisible(false);
            EarthLevel level = new EarthLevel();
            GameFrame gameFrame = new GameFrame(level);
            
            EarthLevel.EarthStage game = new EarthLevel.EarthStage(level);
            game.setupWarpsAndKey(gameFrame, app, () -> unlockLevel("heaven"));

            gameFrame.setContentPane(game);
            gameFrame.setVisible(true);
            game.requestFocusInWindow();
        });
        earth_button.addMouseListener(new MouseAdapter(){
        
                 @Override
                 public void mouseEntered(MouseEvent e ){
                    earthButtonIcon = new MyImageIcon(GameConstants.FILE_EARTH_BUTTON ).resize(GameConstants.EARTH_LEVEL_WIDTH+5, GameConstants.EARTH_LEVEL_HEIGHT+5);
                    earth_button.setIcon(earthButtonIcon);   
                }
                
                 @Override
                public void mouseExited(MouseEvent e){
                    earthButtonIcon = new MyImageIcon(GameConstants.FILE_EARTH_BUTTON ).resize(GameConstants.EARTH_LEVEL_WIDTH, GameConstants.EARTH_LEVEL_HEIGHT);
                     earth_button.setIcon(earthButtonIcon);   
                }    
        });
        
        earth_button.setIcon(earthButtonIcon);
        earth_button.setBorderPainted(false);
        earth_button.setContentAreaFilled(false);
        earth_button.setFocusPainted(false);
        earth_button.setText("");
        earth_button.setBounds(410, 232, 463, 143);

        heaven_button = new JButton();
        heaven_button.addActionListener((ActionEvent e) -> {
            if (heaven == 1) {
                soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
                app.setVisible(false);
                HeavenLevel level = new HeavenLevel();
                GameFrame gameFrame = new GameFrame(level);
                
                HeavenLevel.HeavenStage game = new HeavenLevel.HeavenStage(level);
                game.setupWarpsAndKey(gameFrame, app, () -> unlockLevel("hell"));
                game.setupMovingElement();

                gameFrame.setContentPane(game);
                gameFrame.setVisible(true);
                game.requestFocusInWindow();
            }
        });
        
        heaven_button.setBorderPainted(false);
        heaven_button.setContentAreaFilled(false);
        heaven_button.setFocusPainted(false);
        heaven_button.setText("");
        heaven_button.setBounds(410, 397, 465, 137);

        hell_button = new JButton();
        hell_button.addActionListener((ActionEvent e) -> {
            if (hell == 1) {
                soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
                app.setVisible(false);
                HellLevel level = new HellLevel();
                GameFrame gameFrame = new GameFrame(level);
                
                HellLevel.HellStage game = new HellLevel.HellStage(level);
                game.setupWarpsAndKey(gameFrame, app, null);
                game.setupMovingElement();

                gameFrame.setContentPane(game);
                gameFrame.setVisible(true);
                game.requestFocusInWindow();
            }
        });
        
        
        hell_button.setBorderPainted(false);
        hell_button.setContentAreaFilled(false);
        hell_button.setFocusPainted(false);
        hell_button.setText("");
        hell_button.setBounds(412, 537, 458, 139);

        backgroundLayer.add(hell_button);
        backgroundLayer.add(heaven_button);
        backgroundLayer.add(earth_button);
        backgroundLayer.add(home_button);
        backgroundLayer.add(drawpane);
        add(backgroundLayer, BorderLayout.CENTER);
    }

    public void getname(String n) {
        settingname = n;
    }

    public void levelupdate() {
        try {
            File inFile = new File(inFilename);
            try (Scanner fileScan = new Scanner(inFile)) {
                boolean found = false;
                
                while (fileScan.hasNext()) {
                    if (fileScan.hasNextInt()) {
                        fileScan.nextInt();
                        continue;
                    }
                    
                    name = fileScan.next();
                    if (name.equals(settingname)) {
                        heaven = fileScan.nextInt();
                        hell = fileScan.nextInt();
                        found = true;
                    }
                }
                
                if (!found) {
                    heaven = 0;
                    hell = 0;
                    try (PrintWriter fw = new PrintWriter(new FileWriter(inFile, true))) {
                        fw.printf(settingname + " 0 0\n");
                    } catch (Exception e) {
                    }
                }
            }
            
            
            // Update button icons
            if (heaven == 1) {
                heavenButtonIcon = new MyImageIcon(GameConstants.FILE_HEAVEN_BUTTON);             
                heaven_button.addMouseListener(new MouseAdapter(){      
                 @Override
                 public void mouseEntered(MouseEvent e ){
                     if(heaven == 1){
                    heavenButtonIcon = new MyImageIcon(GameConstants.FILE_HEAVEN_BUTTON ).resize(GameConstants.HEAVEN_LEVEL_WIDTH+10, GameConstants.HEAVEN_LEVEL_HEIGHT+10);
                    heaven_button.setIcon(heavenButtonIcon);   
                     }
                }
                 @Override
                public void mouseExited(MouseEvent e){
                    if(heaven == 1){
                    heavenButtonIcon = new MyImageIcon(GameConstants.FILE_HEAVEN_BUTTON ).resize(GameConstants.HEAVEN_LEVEL_WIDTH, GameConstants.HEAVEN_LEVEL_HEIGHT);
                     heaven_button.setIcon(heavenButtonIcon);   
                    }
                }    
                });
                
                heaven_button.setIcon(heavenButtonIcon);
                
            } else {
                heavenlockButtonIcon = new MyImageIcon(GameConstants.FILE_HEAVENLOCK_BUTTON);
                heaven_button.setIcon(heavenlockButtonIcon);
            }

            if (hell == 1) {
                hellButtonIcon = new MyImageIcon(GameConstants.FILE_HELL_BUTTON);
                
                
               hellButtonIcon = new MyImageIcon(GameConstants.FILE_HELL_BUTTON);             
                hell_button.addMouseListener(new MouseAdapter(){      
                 @Override
                 public void mouseEntered(MouseEvent e ){
                     if(hell == 1){
                    hellButtonIcon = new MyImageIcon(GameConstants.FILE_HELL_BUTTON ).resize(GameConstants.HELL_LEVEL_WIDTH+10, GameConstants.HELL_LEVEL_HEIGHT+10);
                    hell_button.setIcon(hellButtonIcon);   
                     }
                     }
                 @Override
                public void mouseExited(MouseEvent e){
                    if(hell == 1){
                    hellButtonIcon = new MyImageIcon(GameConstants.FILE_HELL_BUTTON ).resize(GameConstants.HELL_LEVEL_WIDTH, GameConstants.HELL_LEVEL_HEIGHT);
                     hell_button.setIcon(hellButtonIcon);   
                     }
                     }    
                });
                
                hell_button.setIcon(hellButtonIcon);
            } else {
                helllockButtonIcon = new MyImageIcon(GameConstants.FILE_HELLLOCK_BUTTON);
                hell_button.setIcon(helllockButtonIcon);
            }

        } catch (FileNotFoundException e) {
        }
    }

    public void unlockLevel(String levelName) {
        try {
            File inFile = new File(inFilename);
            File tempFile = new File(RuntimePaths.data("Data_temp.txt"));

            PrintWriter writer;
            try (Scanner scanner = new Scanner(inFile)) {
                writer = new PrintWriter(new FileWriter(tempFile));
                while (scanner.hasNext()) {
                    String currentName = scanner.next();
                    
                    if (scanner.hasNextInt()) {
                        int h = scanner.nextInt();
                        int he = scanner.nextInt();
                        
                        if (currentName.equals(settingname)) {
                            if (levelName.equals("heaven")) {
                                h = 1;
                            } else if (levelName.equals("hell")) {
                                he = 1;
                            }
                        }
                        
                        writer.println(currentName + " " + h + " " + he);
                    }
                }
            }
            writer.close();

            inFile.delete();
            tempFile.renameTo(inFile);

            levelupdate();

        } catch (IOException e) {
        }
    }
}