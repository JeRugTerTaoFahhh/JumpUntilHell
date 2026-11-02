package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private Level currentLevel;
    private boolean running = true;

    public GamePanel(Level start) {
        this.currentLevel = start;
        setPreferredSize(new Dimension(1280, 720));
        setFocusable(true);
    }
    @Override
public void run() {
    long last = System.nanoTime();

    while (running) {
        long now = System.nanoTime();
        double dt = (now - last) / 1e9;   
        last = now;

        currentLevel.update(dt);

        //movement.update(dt, currentLevel.getColliders());

        //checkPortalByPosition();

        repaint();

        try {
            Thread.sleep(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //currentLevel.draw((Graphics2D) g);
    }
}
