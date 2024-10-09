package Server;

public class Account {
    private String accountName;
    private int moneyAmount = 1000;

    // Constructor
    public Account(String accountName) {
        this.accountName = accountName;
    }

    // Getter for accountName
    public String getAccountName() {
        return accountName;
    }

    // Getter for moneyAmount
    public int getBalance() {
        return moneyAmount;
    }

    // Setter for moneyAmount
    public void setBalance(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
