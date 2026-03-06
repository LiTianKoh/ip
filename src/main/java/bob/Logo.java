package bob;

public class Logo {
    private static final String SEPARATOR = "    ___________________________";
    private static final String INDENT = "    ";

    private static final String LOGO =
            " ______   _______   ______ \n" +
                    "|  __  \\ |  ___  | |  __  \\ \n" +
                    "| |__)  )| |   | | | |__)  )\n" +
                    "|  __  ( | |   | | |  __  ( \n" +
                    "| |__)  )| |___| | | |__)  )\n" +
                    "|______/ |_______| |______/ \n";

    public static void printBob() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println(SEPARATOR);
        System.out.println(INDENT + "Hello! I'm BOB");
        System.out.println(INDENT + "What can I do for you?");
        System.out.println(SEPARATOR);
    }
}
