package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastPublisher {
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
    
    public void setSocket(int socknum) throws IOException {
		this.socket = new MulticastSocket(socknum);
	}
    
    public void setGroup(String IP) throws UnknownHostException {
		this.group = InetAddress.getByName(IP);
	}
 
    public void multicast(String multicastMessage) throws IOException {
        buf = multicastMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, socket.getLocalPort());
        socket.send(packet);
    }
    public static void main(String[] args) throws IOException {
		MulticastPublisher multicastPublisher = new MulticastPublisher();
		multicastPublisher.setSocket(4467);
		multicastPublisher.setGroup("230.0.0.0");
		multicastPublisher.multicast("Hey dude");
		
	}
}