package Domain.Utils;

public class Console {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_TITLE = ANSI_BOLD + "\u001B[3m";

    public static void EmitError(String mensagem) {
        System.out.println(ANSI_RED + ANSI_BOLD + mensagem + ANSI_RESET);
    }

    public static void EmitSuccess(String mensagem) {
        System.out.println(ANSI_GREEN + ANSI_BOLD + mensagem + ANSI_RESET);
    }

    public static void EmitTitle(String mensagem) {
        System.out.println(ANSI_TITLE + "========== " + mensagem + " ==========" +ANSI_RESET);
    }
}
