package server;

public class SharedActionState {
	private double[] accounts; // array to hold the account balances
	private boolean accessing = false; // true a thread has a lock, false otherwise
	int threadsWaiting = 0; // number of waiting writers

	public SharedActionState(double[] initialBalances) {
		accounts = initialBalances;
	}

	public synchronized String processInput(String input) {
		String[] parts = input.split(": ");
		String clientID = parts[0];
		String command = parts[1];

		String[] commandParts = command.split(" ");
		String action = commandParts[0].toLowerCase();

		int accountIndex = getAccountIndex(clientID);
		String response;

		switch (action) {
			case "add":
				double amountToAdd = Double.parseDouble(commandParts[1]);
				accounts[accountIndex] += amountToAdd;
				response = clientID + ": Added " + amountToAdd + ". New balance: " + accounts[accountIndex];
				break;
			case "subtract":
				double amountToSubtract = Double.parseDouble(commandParts[1]);
				accounts[accountIndex] -= amountToSubtract;
				response = clientID + ": Subtracted " + amountToSubtract + ". New balance: " + accounts[accountIndex];
				break;
			case "transfer":
				int targetAccountIndex = getAccountIndex(commandParts[1]);
				double amountToTransfer = Double.parseDouble(commandParts[2]);
				if (accounts[accountIndex] >= amountToTransfer) {
					accounts[accountIndex] -= amountToTransfer;
					accounts[targetAccountIndex] += amountToTransfer;
					response = clientID + ": Transferred " + amountToTransfer + " to " + commandParts[1]
							+ ". New balance: " + accounts[accountIndex];
				} else {
					response = clientID + ": Insufficient funds for transfer.";
				}
				break;
			case "view":
				response = clientID + ": Your current balance is " + accounts[accountIndex];
				break;
			case "exit":
				response = clientID + ": exited.";
				return response;
			default:
				response = clientID + ": Unknown command.";
		}
		return response;
	}

	private int getAccountIndex(String clientID) {
		switch (clientID) {
			case "CLIENT1":
				return 0; // account 1
			case "CLIENT2":
				return 1; // account 2
			case "CLIENT3":
				return 2; // account 3
			default:
				throw new IllegalArgumentException("Unknown client ID: " + clientID);
		}
	}

	public synchronized void acquireLock() throws InterruptedException {
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName() + " is attempting to acquire a lock!");
		++threadsWaiting;
		while (accessing) { // while someone else is accessing or threadsWaiting > 0
			System.out.println(me.getName() + " waiting to get a lock as someone else is accessing...");
			// Wait for the lock to be released - see releaseLock() below
			wait();
		}
		// Nobody has got a lock so get one
		--threadsWaiting;
		accessing = true;
		System.out.println(me.getName() + " got a lock!");
	}

	// Releases a lock to when a thread is finished
	public synchronized void releaseLock() {
		// Release the lock and tell everyone
		accessing = false;
		notifyAll();
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName() + " released a lock!");
	}
}
