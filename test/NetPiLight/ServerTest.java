package NetPiLight;

import Jimbo.Boards.com.pimoroni.Blinkt;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JTextArea;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Joshua Alexander
 */
public class ServerTest {

    private Socket s;
    private Server server;
    private JTextArea serverTextArea;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    private final int PORT = 9001;
    private final ExecutorService pool = Executors.newSingleThreadExecutor();

    public ServerTest() {
    }

    @Before
    public void setUp() throws IOException {
        serverTextArea = new JTextArea();
        server = new Server(new Blinkt(true), PORT, serverTextArea);

        pool.execute(() -> {
            server.start();
        });

        s = new Socket("127.0.0.1", PORT);
        dataIn = new DataInputStream(s.getInputStream());
        dataOut = new DataOutputStream(s.getOutputStream());
    }

    @After
    public void tearDown() throws IOException {
        server.finalize();
        pool.shutdown();
    }

    /**
     * Test of start method, of class Server.
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testStart() throws IOException, InterruptedException {
        String actualMessage;

        actualMessage = dataIn.readUTF();
        Assert.assertEquals("Server: Connected", actualMessage);

        String expected = "Incoming Connection from: /127.0.0.1";
        boolean isExpected = serverTextArea.getText().contains(expected);
        Assert.assertTrue(isExpected);

        s.close();
    }

    /**
     * Test of sending commands, of class Server.
     */
    @Test
    public void testValidCommands() throws IOException, InterruptedException {
        // array of commands, array of responses from commands in jtextarea
        System.out.println("testValidCommands");
        dataOut.writeUTF("pulse-red");
        Thread.sleep(1000);
        System.out.print(serverTextArea.getText());
    }

    /**
     * Test of finalize method, of class Server.
     */
    @Test
    public void testInvalidCommands() {
        // test an unknow command
    }

}
