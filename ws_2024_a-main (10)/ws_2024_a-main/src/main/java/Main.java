import utils.Utils;

public class Main {

    //Google drive link: https://docs.google.com/document/d/16XKREqqzlJNLWHq5jZIminFVNuhuBTZHqwBKAsZnDFw/edit?usp=sharing

    public static void main(String[] args) {
        Utils.saveIdentifyCode();
        if (Utils.getCreds() != null) {
            System.out.println(Utils.getCreds());
            Window window = new Window();
        }
    }


}
