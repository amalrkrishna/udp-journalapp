import java.io.*;
import java.net.*;

class JournalClient
{
   public static void main(String args[]) throws Exception
   {
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
	  
      System.out.println("Enter your Username");
      String msg1 = inFromUser.readLine();
      sendData = msg1.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9890);
      clientSocket.send(sendPacket);

      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String msg2 = new String(receivePacket.getData());
      System.out.println(msg2);

      String  msg3 = inFromUser.readLine();
      sendData = msg3.getBytes();
      sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9890);
      clientSocket.send(sendPacket);

      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String msg4 = new String(receivePacket.getData());
      System.out.println(msg4);

      String msg5 = inFromUser.readLine();
      sendData = msg5.getBytes();
      sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9890);
      clientSocket.send(sendPacket);
      receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      
      byte[] data = new byte[receivePacket.getLength()];
      System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0, receivePacket.getLength());
      String msg6 = new String(data);
      System.out.println(msg6);

      clientSocket.close();
   }
}
