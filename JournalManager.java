import java.io.*;
import java.util.*;
import java.net.*;

public class JournalManager 
{
    String baseDirectory;

    public JournalManager(String basedir) 
    {
        baseDirectory = basedir;

        File dir = new File(basedir);
        if (dir.exists() == false)
        {
            dir.mkdir();
        }
    }
    public static void createUser(String username) 
    {
        
        File dir = new File(username);
        if (dir.exists() == false)
        {
            dir.mkdir();
        }
    }
	
    public static String readFile(String fromClient, String date)
    {
    	File journalEntry;
    	String result;
    	
        journalEntry = new File(fromClient + "/" 
                +date+".txt");
        if (journalEntry.exists() == false)
        {
            return "Requested journal entry not found!";
        } 
        else
        {                        
            try
            {
                Scanner in = new Scanner(journalEntry);
                result = in.nextLine();
                while(in.hasNextLine())
                {
                	result += "\n" + in.nextLine();
                }
                in.close();
            }
            catch(FileNotFoundException fnf)
            {
                result = "Requested journal entry not found";
            }
        }
        return result;
    }

	public static void main(String args[]) throws IOException
	{
	    DatagramSocket serverSocket = new DatagramSocket(9890);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
	    int flag = 0;
	    int flag2 = 0;
            while(true)
	    {
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);

		  byte[] data = new byte[receivePacket.getLength()];
		  System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0, receivePacket.getLength());
		  InetAddress IPAddress = receivePacket.getAddress();
                  int port = receivePacket.getPort();
		  String username = new String(data);
		  System.out.println("Request for username:"+username+" received");
		  try
		  {
		    Scanner txtscan = new Scanner(new File("UserList.txt"));
		    while(txtscan.hasNextLine())
		    {
		      String str = txtscan.nextLine();
		      if(str.equals(username) == true)
		      {
			  String sentence = "Username exists.\nEnter Month, Day, Year (mm-dd-yyyy)";
			  sendData = sentence.getBytes();						
			  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			  serverSocket.send(sendPacket);
			  flag = 1;
			  break;
		      }
		    }
		  }catch(Exception E)
		  {
		  }
		  if(flag != 1)
		  {
		    //Create directory in the name of user 
		    createUser(username);
		    FileWriter userList = new FileWriter("UserList.txt", true);
		    
		    Formatter out = new Formatter(userList);
		    out.format("%s\n",username);
		    out.close();
		    String sentence = "Username does not exist. New user created.\nEnter Month, Day, Year (mm-dd-yyyy) ";
		    sendData = sentence.getBytes();
		    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		    serverSocket.send(sendPacket);
		  }
		  receivePacket = new DatagramPacket(receiveData, receiveData.length);
		  serverSocket.receive(receivePacket);
		  data = new byte[receivePacket.getLength()];
		  System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0, receivePacket.getLength());
		  String date = new String(data);

		  //Search for existing journal for given date
		  try
		  {
		    Scanner jrnscn = new Scanner(new File(username+"/JournalList.txt"));
		    while(jrnscn.hasNextLine())
		    {
		      String str = jrnscn.nextLine();
		      System.out.println("Excelsior!!\n");
		      if(str.equals(date) == true)
		      {
			  String prev_entry=readFile(username,date);
			  String sentence = "Journal Exists\nCurrent entry for date:"+date+"\n"+prev_entry+"\nPlease send updates necessary for journal\n";
			  sendData = sentence.getBytes();						
			  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			  serverSocket.send(sendPacket);
			  flag2 = 1;
			  break;
		      }
		    }
		  }catch(Exception E)
		  {
		  }
		  if(flag2!=1)
		  {
		    
		    FileWriter journalList = new FileWriter(username+"/JournalList.txt", true);		    
		    Formatter out2 = new Formatter(journalList);
		    out2.format("%s\n",date);
		    out2.close();
		    String sentence = "Journal for "+date+" does not exist. New Journal created.\nPlease send journal entry ";
		    sendData = sentence.getBytes();
		    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		    serverSocket.send(sendPacket);
		  }
		  flag2=0;
		  
		  receivePacket = new DatagramPacket(receiveData, receiveData.length);
		  serverSocket.receive(receivePacket);
		
		  data = new byte[receivePacket.getLength()];
		  System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0, receivePacket.getLength());
		  String jentry = new String(data);
		  FileWriter journaldate = new FileWriter(username+"/"+date+".txt", true);		    
		  Formatter out3 = new Formatter(journaldate);
		  out3.format("%s\n",jentry);
		  out3.close();
		  String msg = "Journal Updated\n";
		  sendData = msg.getBytes();						
		  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		  serverSocket.send(sendPacket);
		  flag = 0;
               }
      }
}
