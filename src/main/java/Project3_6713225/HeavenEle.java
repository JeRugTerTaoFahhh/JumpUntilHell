package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.image.BufferedImage;

public class HeavenEle extends BaseElement {

    private double t = 0;

    public HeavenEle(int x, int y, int w, int h, BufferedImage img) {
        super(x, y, w, h, img);
    }

    @Override
    public void update(double dt) {
        t += dt;
        y += Math.sin(t * 2.0) * 0.5;
    }
}
