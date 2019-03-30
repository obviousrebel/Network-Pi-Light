package NetPiLight;

import Jimbo.Boards.com.pimoroni.Blinkt;
import java.awt.Color;

/**
 *
 * @author Joshua Alexander
 */
public class SolidColor implements Command {

    private final Blinkt b;
    private final int rgb, r, g, bl;

    public SolidColor(Blinkt b, Color c) {
        this.b = b;
        rgb = c.getRGB();
        r = rgb >> 16 & 0xFF;
        g = rgb >> 8 & 0xFF;
        bl = rgb & 0XFF;
    }

    @Override
    public String execute() {
        b.set(0, r, g, bl, 30);
        b.set(1, r, g, bl, 30);
        b.set(2, r, g, bl, 30);
        b.set(3, r, g, bl, 30);
        b.set(4, r, g, bl, 30);
        b.set(5, r, g, bl, 30);
        b.set(6, r, g, bl, 30);
        b.set(7, r, g, bl, 30);
        b.show();

        return String.format("Running Solid Color - R: %s G: %s B: %s", r, g, bl);
    }
}