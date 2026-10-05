public abstract class SavingAccount extends BankAccount{
    private double interestRate;
    private static final double MIN_BALANCE = 50000.0;

    public SavingAccount(String accountNumber, String holderName, double balance, double interestRate) {
        super(accountNumber, holderName, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) throws InvalidAmountException{
        if (interestRate < 0) {
            throw new InvalidAmountException("Lãi suất không được nhỏ hơn 0");
        }
        this.interestRate = interestRate;
    }

    public boolean Invariant(){
        return this.getBalance() >= MIN_BALANCE;
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException, InvalidAmountException {
        if (amount <= 0){
            throw new InvalidAmountException("So tien rut phai lon hon 0");
        }

        if(this.getBalance() < amount){
            throw new InsufficientBalanceException("So du cua ban khong du");
        }

        if(!Invariant()){
            throw new InsufficientBalanceException("Không thể rút. Tài khoản phải duy trì số dư tối thiểu: " + MIN_BALANCE);
        }
        double Balance_new = this.getBalance() - amount;
        this.setBalance(Balance_new);
    }
}
