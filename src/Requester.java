import java.util.Scanner;

import edu.purdue.cs.cs180.channel.*;

public class Requester implements MessageListener {
	Scanner input = new Scanner(System.in);
	static String result = "";
	static int in = 0;
	TCPChannel channel = null;

	public Requester(Channel c) {

		channel = (TCPChannel) c;
		channel.setMessageListener(this);
		sendRequest();
	}

	public void sendRequest() {

		result = "";
		in = 0;

		System.out.println("1. CL50 - Class of 1950 Lecture Hall");
		System.out.println("2. EE - Electrical Engineering Building");
		System.out.println("3. LWSN - Lawson Computer Science Building");
		System.out.println("4. PMU - Purdue Memorial Union");
		System.out.println("5. PUSH - Purdue University Student Health Center");
		System.out.print("Enter your location (1-5): ");

		try {

			in = Integer.parseInt(input.nextLine());

			if (in != 1 && in != 2 && in != 3 && in != 4 && in != 5) {

				System.out.println("Invalid input. Please try again.");
				sendRequest();
			}

		} catch (NumberFormatException e) {
			System.out.println("Invalid number format. Please try again.");
			sendRequest();
		}

		if (in == 1) {

			result = result + "CL50";
		}

		else if (in == 2) {

			result = result + "EE";
		}

		else if (in == 3) {

			result = result + "LWSN";
		}

		else if (in == 4) {

			result = result + "PMU";
		}

		else if (in == 5) {

			result = result + "PUSH";
		}

		result = "REQUEST " + result;

		try {
			if (result.contains("R")) {
				channel.sendMessage(result);

				in = 0;
				result = "";

				System.out.println("Waiting for volunteer...");
			}
		} catch (ChannelException e) {
			e.printStackTrace();
		}

	}

	public void messageReceived(String message, int channelID) {

		System.out.println("Volunteer " + message.substring(10)
				+ " assigned and will arrive shortly.");
		sendRequest();
	}

	public void sendMessage(String message) {
		try {
			channel.sendMessage(message);
		} catch (ChannelException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws ChannelException {

		Requester requester1 = new Requester(new TCPChannel(args[0],
				Integer.parseInt(args[1])));

	}

}
