package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame(Level startLevel) {
        GamePanel panel = new GamePanel(startLevel);
        setContentPane(panel);
        setResizable(false);

        Thread t = new Thread(panel);
        t.start();

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    
}
