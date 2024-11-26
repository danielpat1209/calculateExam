import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static final String PATH = "https://app.seker.live/fm1/answer-file";
    public static final String MAGIC = "Hd5GB0C";
    public static final String DATA = "C:\\Users\\danie\\Downloads\\ws_2024_a_helper-main (13)\\ws_2024_a_helper-main\\data.csv";

    public static void main(String[] args) {


        List<Person> lines = readFile();
        //מהו ההפרש בין סכום הרכישה הממוצע של גברים לסכום הרכישה הממוצע של נשים?
        // ממוצע רכישה לגברים
        double maleAverage = lines.stream()
                .filter(person -> person.getGender().equalsIgnoreCase("Male"))
                .mapToDouble(Person::getPurchase)
                .average()
                .orElse(0);

        // ממוצע רכישה לנשים
        double femaleAverage = lines.stream()
                .filter(person -> person.getGender().equalsIgnoreCase("Female"))
                .mapToDouble(Person::getPurchase)
                .average()
                .orElse(0);
        // חישוב ההפרש
        double difference = maleAverage - femaleAverage;
        System.out.println("הפרש בין ממוצע הרכישות של גברים לנשים: " + difference);

        sendAnswer(MAGIC,"1","0.7613252866734683");

        // יצירת ראשי התיבות לכל לקוח
        Map<String, Long> initialsCount = lines.stream()
                .map(person -> (person.getFirstName().substring(0, 1) + person.getLastName().substring(0, 1)).toUpperCase())
                .collect(Collectors.groupingBy(initials -> initials, Collectors.counting()));

        // מציאת ראשי התיבות הנפוצים ביותר
        long maxCount = initialsCount.values().stream().max(Long::compare).orElse(0L);
        List<String> mostCommonInitials = initialsCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // הדפסת התוצאה
        System.out.println("ראשי התיבות הנפוצים ביותר: " + mostCommonInitials);
        sendAnswer(MAGIC,"2","MS");

        // ספירת מספר הנשים שהשם הפרטי ושם המשפחה מתחילים באותה האות
        long count = lines.stream()
                .filter(person -> person.getGender().equalsIgnoreCase("female")) // סינון נשים
                .filter(person -> person.getFirstName().substring(0, 1).equalsIgnoreCase(person.getLastName().substring(0, 1))) // בדיקת אות זהה
                .count();
        // הדפסת התוצאה
        System.out.println("מספר הנשים שהשם הפרטי ושם המשפחה מתחילים באותה האות: " + count);
        sendAnswer(MAGIC,"3","511");

        // קיבוץ לפי ערים וחישוב סכום הרכישות בכל עיר
        List<Map.Entry<String, Double>> topCities = lines.stream()
                .collect(Collectors.groupingBy(
                        Person::getCityLiving,
                        Collectors.summingDouble(Person::getPurchase)
                ))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // מיון בסדר יורד לפי סכום הרכישות
                .collect(Collectors.toList());

        // קבלת העיר השלישית
        if (topCities.size() >= 3) {
            String thirdCity = topCities.get(2).getKey(); // העיר השלישית ברשימה
            System.out.println("העיר השלישית בסכום הרכישה הכי גבוה היא: " + thirdCity);
        } else {
            System.out.println("אין מספיק ערים ברשימה.");
        }
        sendAnswer(MAGIC,"4","Faymouth");
        // קיבוץ לפי ערים וחישוב ממוצע שנת הלידה בכל עיר
        Optional<Map.Entry<String, Double>> oldestCity = lines.stream()
                .collect(Collectors.groupingBy(
                        Person::getCityLiving,
                        Collectors.averagingInt(Person::getBirth)
                ))
                .entrySet()
                .stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue)); // מחפש את שנת הלידה הממוצעת הנמוכה ביותר

        // הדפסת התוצאה
        if (oldestCity.isPresent()) {
            System.out.println("העיר שבה הלקוחות המבוגרים ביותר היא: " + oldestCity.get().getKey());
        } else {
            System.out.println("אין נתונים.");
        }
        sendAnswer(MAGIC,"5","East Aniballand");
        // שלב 1: חישוב העיר עם שנת הלידה הממוצעת הגבוהה ביותר
        Optional<Map.Entry<String, Double>> youngestCity = lines.stream()
                .collect(Collectors.groupingBy(
                        Person::getCityLiving,
                        Collectors.averagingInt(Person::getBirth) // ממוצע שנה של לידה כדי למצוא את העיר הצעירה ביותר
                ))
                .entrySet()
                .stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue)); // מינימום כי אנחנו רוצים את העיר עם הגיל הצעיר ביותר

        if (youngestCity.isEmpty()) {
            System.out.println("לא נמצאה עיר צעירה ביותר.");
            return;
        }

        String city = youngestCity.get().getKey();


// שלב 2: סינון הלקוחות מהעיר הצעירה ביותר
        List<Person> personsFromYoungestCity = lines.stream()
                .filter(person -> person.getCityLiving().equals(city))
                .collect(Collectors.toList());

// שלב 3: חישוב התו הנפוץ ביותר
        Map<Character, Long> charFrequency = personsFromYoungestCity.stream()
                .flatMap(person -> (person.getFirstName() + person.getLastName()).chars().mapToObj(c -> (char) c)) // מחברים את השם הפרטי עם שם המשפחה
                .collect(Collectors.groupingBy(c -> c, Collectors.counting())); // סופרים את התדירות של כל תו

        Optional<Map.Entry<Character, Long>> mostCommonChar = charFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue()); // מוצאים את התו עם התדירות הגבוהה ביותר

        if (mostCommonChar.isPresent()) {
            System.out.println("התו הנפוץ ביותר בקרב הלקוחות מהעיר הצעירה ביותר הוא: " + mostCommonChar.get().getKey());
        } else {
            System.out.println("לא נמצא תו נפוץ.");
        }
      sendAnswer(MAGIC,"6","h");

    }

    //Write your code here!


    public static List<Person> readFile() {
        List<Person> lines = new ArrayList<>();
        try {
            File file = new File("C:\\Users\\danie\\Downloads\\ws_2024_a_helper-main (13)\\ws_2024_a_helper-main\\data.csv");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] tokens = line.split(",");
                    Person person = new Person(Integer.parseInt(tokens[0]), tokens[1],
                            tokens[2], tokens[3], Integer.parseInt(tokens[4]), tokens[5], tokens[6],
                            Double.parseDouble(tokens[7]));
                    lines.add(person);
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    public static void sendAnswer(String magic, String question, String answer) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URI uri = new URIBuilder(PATH)
                    .setParameter("magic", magic)
                    .setParameter("question",question)
                    .setParameter("answer", answer)
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
