import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class GreetingsClient {

    public static void main (String[] args) {
        if (args.length == 0)
            System.out.println("add 1 param to this command");
        else {
            String message = args[0];
            String jsonMessage = "{\"name\":\"Tim\", \"message\":\"" + message + "\"}\n";
            String host = "localhost";
            int port = 5678;
            try {
                InetAddress address = InetAddress.getByName(host);
                clientSocket = new Socket(address, port);

                OutputStream os = clientSocket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                System.out.println("trying to send message to server: " + jsonMessage);
                bw.write(jsonMessage);
                bw.flush();


                InputStream is = clientSocket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                message = br.readLine();
                message = parseFromJsonString(message);
                System.out.println(message);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            finally {
                try {
                    clientSocket.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String parseFromJsonString(String jsonString) {
        String message = "";
        String[] fields = jsonString.split("\\\"");
        for (int i=1; i<fields.length-2; i+=2) {
            if(fields[i].equals("message")) {
                message = fields[i + 2];
                break;
            }
        }
        return message;
    }

    private static Socket clientSocket;
}
