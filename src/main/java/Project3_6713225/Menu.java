package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.Clip;
import javax.swing.*;

public final class Menu extends JFrame {
    private final JPanel                  contentpane;
    private JLabel                  drawpane,titlelabel;
    private JButton                 start_button,credit,howtoplay,settings_button, skin_button, exit_button;
    private  MyImageIcon                  backgroundImg,startButtonIcon,settingsButtonIcon,creditButtonIcon,HTPButtonIcon,skinButtonIcon,exitButtonIcon,titleIcon;    
    private Clip           themeSound,soundEffect;
    private final Menu         currentFrame;
    private LevelSelect                 LevelPanel;
    private Credit                      CreditPanel;
    private Skin     skinPanel;
    private final CardLayout cardLayout =     new CardLayout(); 
    private JPanel cardPanel;
    
    private HowtoPlay                    HTPPanel;
    private Settings                     SettingsPanel;
    private BossStage                    BossPanel;
    
    private final int framewidth  = GameConstants.FRAME_WIDTH;
    private final int frameheight = GameConstants.FRAME_HEIGHT;
   
    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }
    public void addBossStage(BossStage bossStage) {
        // ลบ BossStage เก่าออกก่อน (ถ้ามี)
        if (BossPanel != null) {
            cardPanel.remove(BossPanel);
        }
        
        // เก็บ reference ใหม่
        BossPanel = bossStage;
        
        // เพิ่มใน CardLayout
        cardPanel.add(BossPanel, "BOSS");
        cardPanel.revalidate();
        cardPanel.repaint();
        
        System.out.println("BossStage added to Menu");
    }
    
    /**
     * แสดง BossStage
     */
    public void showBossStage() {
        cardLayout.show(cardPanel, "BOSS");
        
        // ให้ BossStage focus
        if (BossPanel != null) {
            BossPanel.requestFocusInWindow();
            System.out.println("Showing BossStage");
        }
    }
    
    public Menu()
    {
        Utilities.updateThemeSong(0, 0);
        setTitle("Jump Until Hell");
	setSize(framewidth, frameheight); 
        setResizable(false);
        setLocationRelativeTo(null);
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        currentFrame = this;
        contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new BorderLayout() );        
        AddComponents();
       

    }
    public void AddComponents(){
        backgroundImg  = new  MyImageIcon(GameConstants.FILE_BG).resize(framewidth, frameheight);
	drawpane = new JLabel();
	drawpane.setIcon(backgroundImg);
        cardPanel = new JPanel(cardLayout);
        
        
        LevelPanel=new LevelSelect(this);
        CreditPanel= new Credit(this);
        CreditPanel.setBounds(0, 0, framewidth, frameheight);
        drawpane.setLayout(null);
        
        skinPanel = new Skin(this);
        HTPPanel= new HowtoPlay(this);
        SettingsPanel= new Settings(this);    
        titleIcon= new MyImageIcon(GameConstants.FILE_TITLE).resize(GameConstants.TITLE_WIDTH, GameConstants.TITLE_HEIGHT);
        titlelabel = new JLabel(titleIcon);      
        titlelabel.setIcon(titleIcon);
        titlelabel.setBounds(348, 55, GameConstants.TITLE_WIDTH,GameConstants.TITLE_HEIGHT);
        
        //Start button
        start_button = new JButton();
        startButtonIcon = new MyImageIcon(GameConstants.FILE_START_BUTTON).resize(GameConstants.START_BUTTON_WIDTH, GameConstants.START_BUTTON_HEIGHT);
         
        start_button.addActionListener((ActionEvent e) -> {
            String savename = SettingsPanel.givename();
            LevelPanel.getname(savename);
            LevelPanel.levelupdate();
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            showCard("LEVEL"); 
        });       
        start_button.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e ){
                    startButtonIcon = new MyImageIcon(GameConstants.FILE_START_BUTTON).resize(GameConstants.START_BUTTON_WIDTH+10, GameConstants.START_BUTTON_HEIGHT+10);
                    start_button.setIcon(startButtonIcon);
                }
                @Override
                 public void mouseExited(MouseEvent e){
                     startButtonIcon = new MyImageIcon(GameConstants.FILE_START_BUTTON).resize(GameConstants.START_BUTTON_WIDTH, GameConstants.START_BUTTON_HEIGHT);
                     start_button.setIcon(startButtonIcon); 
                }
        
        });
        
        start_button.setIcon(startButtonIcon);   
        start_button.setBorderPainted(false);       
        start_button.setContentAreaFilled(false);  
        start_button.setFocusPainted(false);      
        start_button.setText("");                       
        start_button.setBounds(409,309,GameConstants.START_BUTTON_WIDTH, GameConstants.START_BUTTON_HEIGHT);
        
    
        //Skin button
        skin_button = new JButton();
        skinButtonIcon = new  MyImageIcon(GameConstants.FILE_SKIN_BUTTON).resize(GameConstants.SKINEXIT_BUTTON_WIDTH, GameConstants.SKINEXIT_BUTTON_HEIGHT);
        skin_button.addActionListener((ActionEvent e) -> {
        soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            showCard("SKIN");
        });     
         skin_button.addMouseListener(new MouseAdapter(){
                
                @Override
                public void mouseEntered(MouseEvent e ){ 
                    skinButtonIcon = new  MyImageIcon(GameConstants.FILE_SKIN_BUTTON).resize(GameConstants.SKINEXIT_BUTTON_WIDTH+10, GameConstants.SKINEXIT_BUTTON_HEIGHT+10);
                    skin_button.setIcon(skinButtonIcon);   
                }
                @Override
                public void mouseExited(MouseEvent e){
                    skinButtonIcon = new  MyImageIcon(GameConstants.FILE_SKIN_BUTTON).resize(GameConstants.SKINEXIT_BUTTON_WIDTH, GameConstants.SKINEXIT_BUTTON_HEIGHT);
                    skin_button.setIcon(skinButtonIcon);   
                }

     
        });       
        
        skin_button.setIcon(skinButtonIcon);   
        skin_button.setBorderPainted(false);       
        skin_button.setContentAreaFilled(false);  
        skin_button.setFocusPainted(false);      
        skin_button.setText("");                       
        skin_button.setBounds(484,472,GameConstants.SKINEXIT_BUTTON_WIDTH, GameConstants.SKINEXIT_BUTTON_HEIGHT);
    
        
        
        //Settings button
        settings_button = new JButton();
        settingsButtonIcon = new MyImageIcon(GameConstants.FILE_SETTINGS_BUTTON).resize(GameConstants.SETTINGS_BUTTON_WIDTH, GameConstants.SETTINGS_BUTTON_HEIGHT);
        settings_button.addActionListener((ActionEvent e) -> {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            showCard("SETTINGS");
        });  
        settings_button.addMouseListener(new MouseAdapter(){
                
                @Override
                public void mouseEntered(MouseEvent e ){ 
                    settingsButtonIcon = new MyImageIcon(GameConstants.FILE_SETTINGS_BUTTON).resize(GameConstants.SETTINGS_BUTTON_WIDTH+10, GameConstants.SETTINGS_BUTTON_HEIGHT+10);
                    settings_button.setIcon(settingsButtonIcon);   
                }
                @Override
                public void mouseExited(MouseEvent e){
                    settingsButtonIcon = new MyImageIcon(GameConstants.FILE_SETTINGS_BUTTON).resize(GameConstants.SETTINGS_BUTTON_WIDTH, GameConstants.SETTINGS_BUTTON_HEIGHT);
                    settings_button.setIcon(settingsButtonIcon);   
                }

     
        });   
        
        settings_button.setIcon(settingsButtonIcon);   
        settings_button.setBorderPainted(false);       
        settings_button.setContentAreaFilled(false);  
        settings_button.setFocusPainted(false);      
        settings_button.setText("");                       
        settings_button.setBounds(33,520,GameConstants.SETTINGS_BUTTON_WIDTH, GameConstants.SETTINGS_BUTTON_HEIGHT);
        
        
        
        //Credit button
        credit = new JButton();
        creditButtonIcon = new  MyImageIcon(GameConstants.FILE_CREDIT_BUTTON).resize(GameConstants.CREDIT_BUTTON_WIDTH, GameConstants.CREDIT_BUTTON_HEIGHT);
        credit.addActionListener((ActionEvent e) -> {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            showCard("CREDIT");
        });   
        credit.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e ){
                    creditButtonIcon = new  MyImageIcon(GameConstants.FILE_CREDIT_BUTTON).resize(GameConstants.CREDIT_BUTTON_WIDTH+10, GameConstants.CREDIT_BUTTON_HEIGHT+10);
                    credit.setIcon(creditButtonIcon);  
                }
                @Override
                public void mouseExited(MouseEvent e){
                    creditButtonIcon = new  MyImageIcon(GameConstants.FILE_CREDIT_BUTTON).resize(GameConstants.CREDIT_BUTTON_WIDTH, GameConstants.CREDIT_BUTTON_HEIGHT);
                    credit.setIcon(creditButtonIcon);  
                }      
        
        });
        
        credit.setIcon(creditButtonIcon);   
        credit.setBorderPainted(false);       
        credit.setContentAreaFilled(false);  
        credit.setFocusPainted(false);      
        credit.setText("");                       
        credit.setBounds(33,55,GameConstants.CREDIT_BUTTON_WIDTH, GameConstants.CREDIT_BUTTON_HEIGHT);   
        
        //HowtoPlay button
        howtoplay = new JButton();
        HTPButtonIcon = new  MyImageIcon(GameConstants.FILE_HTP_BUTTON).resize(GameConstants.HTP_BUTTON_WIDTH, GameConstants.HTP_BUTTON_HEIGHT);
        howtoplay.addActionListener((ActionEvent e) -> {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            showCard("HOW_TO_PLAY");
        }); 
        howtoplay.addMouseListener(new MouseAdapter(){
        
                @Override
                public void mouseEntered(MouseEvent e ){
                    HTPButtonIcon = new  MyImageIcon(GameConstants.FILE_HTP_BUTTON).resize(GameConstants.HTP_BUTTON_WIDTH+10, GameConstants.HTP_BUTTON_HEIGHT+10);
                    howtoplay.setIcon(HTPButtonIcon); 
                }
                
                @Override
                public void mouseExited(MouseEvent e){
                    HTPButtonIcon = new  MyImageIcon(GameConstants.FILE_HTP_BUTTON).resize(GameConstants.HTP_BUTTON_WIDTH, GameConstants.HTP_BUTTON_HEIGHT);
                    howtoplay.setIcon(HTPButtonIcon); 
                }
        
        
        
        });
        
        howtoplay.setIcon(HTPButtonIcon);   
        howtoplay.setBorderPainted(false);       
        howtoplay.setContentAreaFilled(false);  
        howtoplay.setFocusPainted(false);      
        howtoplay.setText("");                       
        howtoplay.setBounds(1099,507,GameConstants.HTP_BUTTON_WIDTH, GameConstants.HTP_BUTTON_HEIGHT); 
        
        
        //Exit button
        exit_button = new JButton();
        exitButtonIcon = new MyImageIcon(GameConstants.FILE_EXIT_BUTTON).resize(GameConstants.SKINEXIT_BUTTON_WIDTH, GameConstants.SKINEXIT_BUTTON_HEIGHT);
        exit_button.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
         exit_button.addMouseListener(new MouseAdapter(){
        
                 @Override
                 public void mouseEntered(MouseEvent e ){
                    exitButtonIcon = new MyImageIcon(GameConstants.FILE_EXIT_BUTTON).resize(GameConstants.SKINEXIT_BUTTON_WIDTH+10, GameConstants.SKINEXIT_BUTTON_HEIGHT+10);
                    exit_button.setIcon(exitButtonIcon); 
                }
                
                 @Override
                public void mouseExited(MouseEvent e){
                    exitButtonIcon = new MyImageIcon(GameConstants.FILE_EXIT_BUTTON).resize(GameConstants.SKINEXIT_BUTTON_WIDTH, GameConstants.SKINEXIT_BUTTON_HEIGHT);
                    exit_button.setIcon(exitButtonIcon);  
                }    
        });
        
        exit_button.setIcon(exitButtonIcon);   
        exit_button.setBorderPainted(false);       
        exit_button.setContentAreaFilled(false);  
        exit_button.setFocusPainted(false);      
        exit_button.setText("");                       
        exit_button.setBounds(485,568,GameConstants.SKINEXIT_BUTTON_WIDTH, GameConstants.SKINEXIT_BUTTON_HEIGHT); 
        
        
        
        
        
        drawpane.add(titlelabel);  
        drawpane.add(start_button);
        drawpane.add(skin_button);
        drawpane.add(settings_button);
        drawpane.add(credit);
        drawpane.add(howtoplay);
        drawpane.add(exit_button);
       
        
        
        cardPanel.add(drawpane, "MAIN_MENU");
        cardPanel.add(CreditPanel, "CREDIT");
        cardPanel.add(skinPanel, "SKIN");
        cardPanel.add(HTPPanel, "HOW_TO_PLAY");
        cardPanel.add(SettingsPanel, "SETTINGS");
        cardPanel.add(LevelPanel, "LEVEL");
        
        contentpane.add(cardPanel, BorderLayout.CENTER); 
        validate();
    }   
    
    public Settings getSettings() {
        return SettingsPanel;
    }
}
