package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MulticastReceiver {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    InetAddress group;
    Peer peer;
    
    public void setSocket(int socknum) throws IOException {
		this.socket = new MulticastSocket(socknum);
	}
    
    public void setGroup(String IP) throws UnknownHostException {
		this.group = InetAddress.getByName(IP);
	}
    
    public void setPeer(Peer peer) {
		this.peer = peer;
	}
    
    public void run(){
        try {
			socket.joinGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			socket.setLoopbackMode(false);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
        while (true) {
	        DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        String received = new String(packet.getData(), 0, packet.getLength());
	        peer.updateChat(received);
	        if(peer == null)
	        	break;
        }
        try {
			socket.leaveGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
        socket.close();
    }
    public static void main(String[] args) throws IOException {
    	MulticastReceiver multicastReceiver = new MulticastReceiver();
    	multicastReceiver.setGroup("230.0.0.0");
    	multicastReceiver.setSocket(4467);
    	multicastReceiver.run();
	}
}
