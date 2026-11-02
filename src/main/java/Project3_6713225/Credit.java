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

class Credit extends JPanel
{
    private final JLabel                  drawpane;
    private final JButton                 close_button;    
    private final MyImageIcon closeButtonIcon,Img,background;    
    private final int framewidth  = GameConstants.CREDIT_WIDTH;
    private final int frameheight = GameConstants.CREDIT_HEIGHT;
    private Clip soundEffect;
    
    
    public Credit(Menu app){
        setLayout(new BorderLayout());
        
        setVisible(false);
        background=new MyImageIcon(GameConstants.FILE_BG).resize(1280, 720);
        Img= new MyImageIcon(GameConstants.FILE_CREDIT_BG).resize(framewidth, frameheight);
        JLabel backgroundLayer = new JLabel(background);
        backgroundLayer.setLayout(null);
        drawpane = new JLabel(Img);
        drawpane.setBounds(98,91,framewidth, frameheight);
        
        close_button = new JButton();
        close_button.addActionListener((ActionEvent e) -> {
            soundEffect = Utilities.playSoundEffect(GameConstants.FILE_BUTTON_SOUND);
            app.showCard("MAIN_MENU");
        });       
        closeButtonIcon = new MyImageIcon(GameConstants.FILE_CLOSE_BUTTON ).resize(GameConstants.CLOSE_BUTTON_WIDTH, GameConstants.CLOSE_BUTTON_HEIGHT);
        close_button.setIcon(closeButtonIcon);   
        close_button.setBorderPainted(false);       
        close_button.setContentAreaFilled(false);  
        close_button.setFocusPainted(false);      
        close_button.setText("");                       
        close_button.setBounds(1098,72,GameConstants.CLOSE_BUTTON_WIDTH, GameConstants.CLOSE_BUTTON_HEIGHT); 
        
        backgroundLayer.add(close_button);
        backgroundLayer.add(drawpane);
        add(backgroundLayer, BorderLayout.CENTER);
        
    }
    
}
