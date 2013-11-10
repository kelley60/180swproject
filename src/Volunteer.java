import java.util.Scanner;
import edu.purdue.cs.cs180.channel.*;

public class Volunteer implements MessageListener {
	public static int identification;
	TCPChannel channel = null;

	public void sendRequest() {
		System.out.println("Press ENTER when ready:");

		Scanner input = new Scanner(System.in);

		if (input.hasNextLine()) {

			String mssg = "VOLUNTEER " + identification;
			sendMessage(mssg);
		}

	}

	public Volunteer(Channel c) {

		this.channel = (TCPChannel) c;
		this.channel.setMessageListener(this);
		identification = channel.getID();
		sendRequest();
	}

	@Override
	public void messageReceived(String message, int channelID) {
		System.out.println("Proceed to "
				+ message.substring(9, message.length()));
		sendRequest();

	}

	public void sendMessage(String message) {
		try {
			channel.sendMessage(message);

			System.out.println("Waiting for assignment...");
		} catch (ChannelException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ChannelException {

		try {
			Volunteer volunteer1 = new Volunteer(new TCPChannel(args[0],
					Integer.parseInt(args[1])));
		} catch (ChannelException e) {
			e.printStackTrace();
		}

	}

}
