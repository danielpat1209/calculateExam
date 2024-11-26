import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.*;

public class image {
    public static String PATH="https://app.seker.live/fm1/answer-image";

    public static void main(String[] args) throws IOException {
        // קריאת התמונה
        File imageFile = new File("C:\\Users\\danie\\Downloads\\ws_2024_a_helper-main (13)\\ws_2024_a_helper-main\\images\\1.png");
        BufferedImage image = ImageIO.read(imageFile);

        // חישוב הצבעים בדומיננטיות
        Map<Color, Integer> colorFrequency = new HashMap<>();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                // סינון הצבע הלבן
                if (isNotWhite(color)) {
                    colorFrequency.put(color, colorFrequency.getOrDefault(color, 0) + 1);
                }
            }
        }

        // מיון הצבעים לפי תדירות
        List<Map.Entry<Color, Integer>> sortedColors = new ArrayList<>(colorFrequency.entrySet());
        sortedColors.sort((entry1, entry2) -> entry2.getValue() - entry1.getValue());

        // קבלת הצבע השלישי בדומיננטיות
        if (sortedColors.size() >= 3) {
            Color thirdDominantColor = sortedColors.get(2).getKey();
            System.out.println("הצבע השלישי בדומיננטיות בתמונה הוא: RGB("
                    + thirdDominantColor.getRed() + ", "
                    + thirdDominantColor.getGreen() + ", "
                    + thirdDominantColor.getBlue() + ")");
            sendAnswer("Hd5GB0C",202,98,226);
        } else {
            System.out.println("לא נמצאו מספיק צבעים.");
        }
    }

    private static boolean isNotWhite(Color color) {
        // בודק אם הצבע אינו לבן
        return !(color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255);
    }

    public static void sendAnswer(String magic, int red, int green, int blue) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URI uri = new URIBuilder(PATH)
                    .setParameter("magic", magic)
                    .setParameter("red", String.valueOf(red))
                    .setParameter("green", String.valueOf(green))
                    .setParameter("blue", String.valueOf(blue))
                    .build();

            HttpPost post = new HttpPost(uri);
            post.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(post)) {
                int responseCode = response.getStatusLine().getStatusCode();
                System.out.println("Response Code: " + responseCode);

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response: " + responseBody);

                if (responseCode == 200) {
                    System.out.println("Answer sent successfully!");
                } else {
                    System.out.println("Error code: " + responseCode);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}




