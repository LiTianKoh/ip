import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Bob {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line;

        String logo =
                " ______   _______   ______ \n"
                        + "|  __  \\ |  ___  | |  __  \\ \n"
                        + "| |__)  )| |   | | | |__)  )\n"
                        + "|  __  ( | |   | | |  __  ( \n"
                        + "| |__)  )| |___| | | |__)  )\n"
                        + "|______/ |_______| |______/ \n";
        System.out.println("Hello from\n" + logo);
        System.out.println("    ___________________________");
        System.out.println("    Hello! I'm BOB");
        System.out.println("    What can I do for you?");
        System.out.println("    ___________________________");

        //Word recognition
        Pattern pattern = Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);

        do {
            line = in.nextLine();
            System.out.println("    ___________________________");

            //Check if it's not Bye
            if (!line.equalsIgnoreCase("Bye")) {
                Matcher matcher = pattern.matcher(line);

                //Check if the line input consist of handsome or beautiful
                if (matcher.find()) {
                    System.out.println("    Nonono, you are ;)");
                    System.out.println("    ___________________________");
                } else {
                    System.out.println("    " + line);
                    System.out.println("    ___________________________");
                }
            }
        } while (!line.toLowerCase().contains("bye")); //Lines containing bye
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ___________________________");

        in.close();
    }
}