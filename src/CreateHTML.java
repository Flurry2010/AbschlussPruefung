import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Flurry on 08.06.2016.
 */
public class CreateHTML {

    public static boolean CREATE(ArrayList<Auto> auto) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("index.html"))) {


        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
