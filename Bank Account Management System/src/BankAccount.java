public abstract class BankAccount {
    private String accountNumber;
    private String holderName;
    private double balance;

    public BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public abstract void withdraw(double amount)
        throws InsufficientBalanceException, InvalidAmountException;

    public void deposit(double amount)
            throws InvalidAmountException {

    }

    public class InsufficientBalanceException extends Exception {
        public InsufficientBalanceException(String message) { super(message);}
    }


    public class InvalidAmountException extends Exception {
        public InvalidAmountException(String message) { super(message);}
    }
}

