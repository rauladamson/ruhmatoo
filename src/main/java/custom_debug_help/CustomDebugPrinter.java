package custom_debug_help;

public class CustomDebugPrinter {
    private static int debugMessageLevel = 0;

    public static int getDebugMessageLevel() {
        return debugMessageLevel;
    }

    public static void setDebugMessageLevel(int debugMessageLevel) {
        CustomDebugPrinter.debugMessageLevel = debugMessageLevel;
    }

    public static void dbgMsg(String message, int level) {
        if (debugMessageLevel >= level) {
            System.out.println(message);
        }
    }
    public static void dbgMsgLvl1(String message) {
        dbgMsg(message, 1);
    }
}
