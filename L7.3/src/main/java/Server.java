import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws InterruptedException {
        Thread serverThread = new Thread(new SimpleDistributedEcho(5050));
        serverThread.start();
        log.info("Server started");
        serverThread.join();
    }
}

class SimpleDistributedEcho implements Runnable {
    private static final Logger log = Logger.getLogger(SimpleDistributedEcho.class.getName());

    private final ExecutorService workersPool = Executors.newCachedThreadPool();
    final int PORT;

    public SimpleDistributedEcho(int PORT) {
        this.PORT = PORT;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                workersPool.execute(new EchoWorker(serverSocket.accept()));
            }
        } catch (IOException e) {
            log.severe(e.getLocalizedMessage());
        }
        workersPool.shutdown();
    }
}

class EchoWorker implements Runnable {
    private static final Logger log = Logger.getLogger(EchoWorker.class.getName());
    private final Socket socket;

    public EchoWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
            while (true) {
                int data = in.read();
                if (data == -1){
                    break;
                }
                out.write(data);
            }
            out.flush();
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
        try {
            socket.close();
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }
}