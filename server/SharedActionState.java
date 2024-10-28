package server;

public class SharedActionState {
	private boolean accessing = false; // true if a thread has a lock, false otherwise
	int threadsWaiting = 0; // number of waiting writers

	private double accountBalance;

	// Constructor
	SharedActionState(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	// Attempt to acquire a lock
	public synchronized void acquireLock() throws InterruptedException {
		Thread me = Thread.currentThread(); // Get a reference to the current thread
		System.out.println(me.getName() + " is attempting to acquire a lock!");
		++threadsWaiting;

		while (accessing) { // While someone else is accessing or threadsWaiting > 0
			System.out.println(me.getName() + " waiting to get a lock as someone else is accessing...");
			// Wait for the lock to be released - see releaseLock() below
			wait();
		}

		// Nobody has a lock, so acquire it
		--threadsWaiting;
		accessing = true;
		System.out.println(me.getName() + " got a lock!");
	}

	// Releases a lock when a thread is finished
	public synchronized void releaseLock() {
		// Release the lock and notify all waiting threads
		accessing = false;
		notifyAll();
		Thread me = Thread.currentThread(); // Get a reference to the current thread
		System.out.println(me.getName() + " released a lock!");
	}

	/* The processInput method */
	public synchronized String processInput(String myThreadName, String theInput, String ammountToWithdraw) {
		System.out.println(myThreadName + " received " + theInput);
		String theOutput = null;

		// Check what the client said
		if (theInput.equalsIgnoreCase("1")) {
			// Correct request
			switch (myThreadName) {
				case "CLIENT1":
					theOutput = String.format("Account balance: %.2f units", accountBalance);
					break;

				case "CLIENT2":
					theOutput = String.format("Account balance: %.2f units", accountBalance);
					break;

				case "CLIENT3":
					theOutput = String.format("Account balance: %.2f units", accountBalance);
					break;

				default:
					System.out.println("Error - thread call not recognized.");
			}
		} else if (theInput.equalsIgnoreCase("2")) {
			int amount = Integer.valueOf(theInput);
			switch (myThreadName) {
				case "CLIENT1":
					theOutput = String.format("Account balance: %.2f units", accountBalance = accountBalance - amount);
				default:
					System.out.println("The input is not a number");
			}
		}
		else { // Incorrect request
			theOutput = myThreadName + " received incorrect request - only understand \"Do my action!\"";
		}

		// Return the output message to the ActionServer
		System.out.println(theOutput);
		return theOutput;
	}
}
