package Project3_6713225;
/*6713225 Chatchawal Labkim
6713232 Thanapon Rojanabenjakul
6713234 Thanawat Mekwattanawanit
6713241 Piyangkun Onsee
*/
import java.awt.image.BufferedImage;

public class HellEle extends BaseElement {

    private double t = 0;

    public HellEle(int x, int y, int w, int h, BufferedImage img) {
        super(x, y, w, h, img);
    }

    @Override
    public void update(double dt) {
        t += dt;
        x += Math.sin(t * 3.0) * 0.8;
    }
}
