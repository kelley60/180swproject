/**
 * Project 6
 * @author kelley60
 * @author rpoonaw
 */

import edu.purdue.cs.cs180.channel.*;
import java.util.ArrayList;

/**
 * A sample server application. Both server and clients must implement the
 * MessageListener interface.
 */
class Server implements MessageListener {
	/**
	 * Creates a server channel on the current machine and assigns port 8888 to
	 * it.
	 */
	TCPChannel channel = null;

	ArrayList<Integer> volunteerQueue = new ArrayList<Integer>();
	ArrayList<String> requesterQueue = new ArrayList<String>();
	ArrayList<Integer> requesterID = new ArrayList<Integer>();

	/**
	 * Constructor.
	 */
	public Server(TCPChannel c) {
		// inform the channel that when new messages are received forward them
		// to the current server object.
		this.channel = c;
		channel.setMessageListener(this);
	}

	@Override
	public void messageReceived(String message, int clientID) {
		System.out.println(message);
		// simple reply that message received, send it to the same client it
		// came from.

		if (message.contains("REQUEST")) {

			requesterID.add(clientID);
			message = message.substring(8, message.length());
			requesterQueue.add(message);

		}

		else {

			volunteerQueue.add(clientID);

		}

		if (volunteerQueue.size() > 0 && requesterQueue.size() > 0) {

			try {
				channel.sendMessage("LOCATION " + requesterQueue.get(0),
						(volunteerQueue.get(0)));

			} catch (ChannelException e) {
				e.printStackTrace();
			}

			try {
				channel.sendMessage("VOLUNTEER " + volunteerQueue.get(0),
						(requesterID.get(0)));

			} catch (ChannelException e) {
				e.printStackTrace();
			}

			volunteerQueue.remove(0);
			requesterID.remove(0);
			requesterQueue.remove(0);

		}

	}

	public static void main(String[] args) {
		Server myServer = new Server(new TCPChannel(Integer.parseInt(args[0])));
	}
}
