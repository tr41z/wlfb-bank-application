package Client;

interface Client {
    void add_money(int account, Float value);
    void subtract_money(int account, Float value);
    void transfer_money(int first_account, int second_account, Float value);
    boolean establish_connection();
}