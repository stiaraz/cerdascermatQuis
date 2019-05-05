package CerdasCermat;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MultiThreadChatClient implements Runnable {
   
    @FXML private ListView listView; //bind layout biar dibaca kodingan
    @FXML private TextField textField;
  // The client socket
  private static Socket clientSocket = null;
  // The output stream
  private static PrintStream os = null;
  // The input stream
  private static DataInputStream is = null;

  private static BufferedReader inputLine = null;
  private static boolean closed = false;
  
   private ObservableList<String> view = FXCollections.observableArrayList ();
  
  @FXML
  public void send(ActionEvent act) //buat ngeirim chat ke GUI nya
  {
      os.println(textField.getText()); 
      textField.clear();
  }
  
  public MultiThreadChatClient(){    //
  
      new Thread(()->mulai()).start();
  }
  
  
  
  public void mulai(){ //dipindahin di class baru soalnya mainnya pake yg di show.java, sama kalo di main, gabisa akses variable class 
      int portNumber = 2222;
    // The default host.
    String host = "localhost";

//    if (args.length < 2) {
//      System.out
//          .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
//              + "Now using host=" + host + ", portNumber=" + portNumber);
//    } else {
//      host = args[0];
//      portNumber = Integer.valueOf(args[1]).intValue();
//    }

    /*
     * Open a socket on a given host and port. Open input and output streams.
     */
    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }

    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
    if (clientSocket != null && os != null && is != null) {
      try {

        /* Create a thread to read from the server. */
        new Thread(this).start();
        while (!closed) {
//          os.println(inputLine.readLine().trim());
        }
        /*
         * Close the output stream, close the input stream, close the socket.
         */
        os.close();
        is.close();
        clientSocket.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }
  
//  public static void main(String[] args) {
//
//    // The default port.
//    int portNumber = 2222;
//    // The default host.
//    String host = "localhost";
//
//    if (args.length < 2) {
//      System.out
//          .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
//              + "Now using host=" + host + ", portNumber=" + portNumber);
//    } else {
//      host = args[0];
//      portNumber = Integer.valueOf(args[1]).intValue();
//    }
//
//    /*
//     * Open a socket on a given host and port. Open input and output streams.
//     */
//    try {
//      clientSocket = new Socket(host, portNumber);
//      inputLine = new BufferedReader(new InputStreamReader(System.in));
//      os = new PrintStream(clientSocket.getOutputStream());
//      is = new DataInputStream(clientSocket.getInputStream());
//    } catch (UnknownHostException e) {
//      System.err.println("Don't know about host " + host);
//    } catch (IOException e) {
//      System.err.println("Couldn't get I/O for the connection to the host "
//          + host);
//    }
//
//    /*
//     * If everything has been initialized then we want to write some data to the
//     * socket we have opened a connection to on the port portNumber.
//     */
//    if (clientSocket != null && os != null && is != null) {
//      try {
//
//        /* Create a thread to read from the server. */
//        new Thread(new MultiThreadChatClient()).start();
//        while (!closed) {
//          os.println(inputLine.readLine().trim());
//        }
//        /*
//         * Close the output stream, close the input stream, close the socket.
//         */
//        os.close();
//        is.close();
//        clientSocket.close();
//      } catch (IOException e) {
//        System.err.println("IOException:  " + e);
//      }
//    }
//  }

  /*
   * Create a thread to read from the server. (non-Javadoc)
   *
   * @see java.lang.Runnable#run()
   */
  
  private void putMessage(String message)
  {
        view.add(message); 
        listView.setItems(view);
  }
  
  public void run() {
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
    String responseLine;
    try {
      while ((responseLine = is.readLine()) != null) {
//        System.out.println(responseLine);
        final String temp = responseLine;
        Platform.runLater (() -> putMessage (temp));
        
        if (responseLine.indexOf("Bye~~~") != -1)
          break;
      }
      closed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}
