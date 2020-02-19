import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main { // hi maxx

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            //add code.
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
}
