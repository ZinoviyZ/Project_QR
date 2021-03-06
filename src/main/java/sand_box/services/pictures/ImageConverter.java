package sand_box.services.pictures;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zinoviy on 9/13/16.
 */
public class ImageConverter {
    private BufferedImage img;
    private String name;
    private String path;
    private static final int SMALL = 300;
    private static final int MIDDLE = 900;
    private static final int BIG = 2000;
    private static String FORMAT = "png";


    public ImageConverter(String path, InputStream stream, String name) throws IOException {
        this.img = ImageIO.read(stream);
        this.name = name;
        this.path = path;
    }

    public int converting() {
        int n = 1;
        ExecutorService service = Executors.newCachedThreadPool();
        int MAX = Math.max(img.getHeight(),img.getWidth());
        service.execute(new Thread(new Converter(img,SMALL,path +"/"+SMALL,name,FORMAT)));
        if(MAX>=(MIDDLE/1.5)) {
            service.execute(new Thread(new Converter(img, MIDDLE, path + "/" + MIDDLE, name, FORMAT)));
            n++;
        }
        if(MAX>=(BIG/1.5)) {
            service.execute(new Thread(new Converter(img, BIG, path + "/" + BIG, name, FORMAT)));
            n++;
        }
        service.shutdown();
        return n;
    }
}
