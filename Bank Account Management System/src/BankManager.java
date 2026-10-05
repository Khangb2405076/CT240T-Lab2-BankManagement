import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class BankManager {
    Map<String, BankAccount> accountNumber = new HashMap<>();

    public void addAccount(BankAccount acc){
        if(acc == null){
            System.out.println("/nDu lieu khong duoc null");
        }
        if(accountNumber.containsKey(acc.getAccountNumber())){
            System.out.println("/nMa tai khoan " + acc.getAccountNumber() + "da ton tai");
        }
        int oldSize = accountNumber.size();

        // --- THỰC THI ---
        accountNumber.put(acc.getAccountNumber(), acc);
    }

    public BankAccount findAccount(String acc){
        if (acc == null || acc.isEmpty()) {
            System.out.println("Mã tài khoản tìm kiếm không hợp lệ.");
        }
        BankAccount account = accountNumber.get(acc);
        if (account == null) {
            System.out.println("/nKhông tìm thấy tài khoản với mã: " + accountNumber);
        }
        return account;

    }
    public void transferMoney(String fromAcc, String toAcc, double amount) throws BankAccount.InsufficientBalanceException, BankAccount.InvalidAmountException {

        if (fromAcc == null || toAcc == null) {
            System.out.println("/nMã tài khoản không được null.");
        }
        if (fromAcc.equals(toAcc)) {
            System.out.println("Tài khoản nguồn và tài khoản đích không được trùng nhau.");
        }
        if (amount <= 0) {
            System.out.println("Số tiền chuyển phải lớn hơn 0.");
        }

        BankAccount fromAccount = findAccount(fromAcc);
        BankAccount toAccount = findAccount(toAcc);

        double oldFromBalance = fromAccount.getBalance();
        double oldToBalance = toAccount.getBalance();

        fromAccount.withdraw(amount);
        try {
            // Nạp tiền vào tài khoản đích
            toAccount.deposit(amount);
        } catch (Exception e) {
            // Cơ chế Rollback (Hoàn tiền) nếu nạp tiền thất bại
            fromAccount.deposit(amount);
            throw e;
        }

    }

    public double calculateTotalBalance(List<? extends BankAccount> accounts){
        double total = 0.0;
        for(BankAccount acc: accounts){
            total += acc.getBalance();
        }
        return total;
    }
}
