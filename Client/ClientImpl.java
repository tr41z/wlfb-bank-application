package Client;

public class ClientImpl implements Client {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public void add_money(int account, Float value) {}
    public void subtract_money(int account, Float value) {}
    public void transfer_money(int first_account, int second_account, Float value) {}
    public boolean establish_connection() { return true; }
}
