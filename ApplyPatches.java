import java.io.*;

public class ApplyPatches
{
    public static final String LOG_FILE = "logs/applyPatch.log";
    public static final String MC_VERSION = "1.18.2";

    public static void main(String[] args) {
        var applyPatches = new ApplyPatches();
    }

    ApplyPatches() {
        setUpLog();
    }

    void setUpLog() {
        print("set up log...");
        new File(LOG_FILE).getParentFile().mkdirs();
        try {
            var logOut = new BufferedOutputStream(new FileOutputStream(LOG_FILE));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
                try {
                    logOut.close();
                } catch (IOException ignored) {
                }
            }));

            System.setOut(new PrintStream(new OutputStream()
            {
                OutputStream out = System.out;
                OutputStream file = new FileOutputStream(LOG_FILE);

                @Override
                public void write(int b) throws IOException {
                    out.write(b);
                    file.write(b);
                }
            }));
            System.setErr(new PrintStream(new OutputStream()
            {
                OutputStream out = System.err;
                OutputStream file = new FileOutputStream(LOG_FILE);

                @Override
                public void write(int b) throws IOException {
                    out.write(b);
                    file.write(b);
                }
            }));
        } catch (FileNotFoundException e) {
            System.err.println("can not crete log " + LOG_FILE);
        }
        print("set upd log...");
    }

    void print(String s) {
        System.out.println(s);
    }
}
