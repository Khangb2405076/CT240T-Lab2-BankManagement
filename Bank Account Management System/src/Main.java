import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final BankManager bankManager = new BankManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            try {
                showMenu();
                int choice = readIntInput("Lựa chọn của bạn (1-7): ");

                switch (choice) {
                    case 1:
                        handleCreateAccount();
                        break;
                    case 2:
                        handleFindAccount();
                        break;
                    case 3:
                        handleDeposit();
                        break;
                    case 4:
                        handleWithdraw();
                        break;
                    case 5:
                        handleTransfer();
                        break;
                    case 6:
                        handleShowTotalBalance();
                        break;
                    case 7:
                        System.out.println("\nCảm ơn bạn đã sử dụng dịch vụ ngân hàng. Tạm biệt!");
                        running = false;
                        break;
                    default:
                        System.out.println("❌ Lỗi: Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 7.");
                }
            } catch (Exception e) {
                // Bẫy ngoại lệ cấp cao nhất để đảm bảo chương trình không bao giờ bị crash đột ngột
                System.out.println("❌ Lỗi hệ thống không xác định: " + e.getMessage());
            }

            if (running) {
                System.out.println("\n--------------------------------------------------");
            }
        }
        scanner.close();
    }

    // =========================================================================
    // HÀM HIỂN THỊ MENU
    // =========================================================================
    private static void showMenu() {
        System.out.println("\n=== HỆ THỐNG QUẢN LÝ TÀI KHOẢN NGÂN HÀNG ===");
        System.out.println("1. Thêm tài khoản tiết kiệm mới");
        System.out.println("2. Tìm kiếm / Xem thông tin tài khoản");
        System.out.println("3. Nạp tiền vào tài khoản");
        System.out.println("4. Rút tiền từ tài khoản");
        System.out.println("5. Chuyển tiền giữa 2 tài khoản");
        System.out.println("6. Xem tổng số dư toàn hệ thống");
        System.out.println("7. Thoát chương trình");
    }

    // =========================================================================
    // CÁC HÀM XỬ LÝ CHỨC NĂNG (BẪY NGOẠI LỆ NGHIỆP VỤ)
    // =========================================================================

    private static void handleCreateAccount() {
        System.out.println("\n--- 1. THÊM TÀI KHOẢN TIẾT KIỆM NGUYÊN TẮC ---");
        try {
            String accNum = readStringInput("Nhập số tài khoản: ");
            String holderName = readStringInput("Nhập tên chủ tài khoản: ");
            double balance = readDoubleInput("Nhập số dư ban đầu (tối thiểu 50,000 VNĐ): ");
            double interestRate = readDoubleInput("Nhập lãi suất (%/năm, ví dụ 5.5): ");

            // Tạo tài khoản tiết kiệm cụ thể
            SavingAccount acc = new ConcreteSavingAccount(accNum, holderName, balance, interestRate);
            bankManager.addAccount(acc);

            System.out.println("✅ Thêm tài khoản thành công!");

        } catch (BankAccount.InvalidAmountException e) {
            System.out.println("⚠️ Lỗi số tiền: " + e.getMessage());
        } catch (BankAccount.InsufficientBalanceException e) {
            System.out.println("⚠️ Lỗi số dư: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Lỗi dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Không thể tạo tài khoản: " + e.getMessage());
        }
    }

    private static void handleFindAccount() {
        System.out.println("\n--- 2. TÌM KIẾM TÀI KHOẢN ---");
        String accNum = readStringInput("Nhập số tài khoản cần tìm: ");

        BankAccount acc = bankManager.findAccount(accNum);
        if (acc != null) {
            System.out.println("🔍 Thông tin tài khoản:");
            System.out.println(" - Số TK: " + acc.getAccountNumber());
            System.out.println(" - Chủ TK: " + acc.getHolderName());
            System.out.println(" - Số dư: " + String.format("%,.2f", acc.getBalance()) + " VNĐ");
        }
    }

    private static void handleDeposit() {
        System.out.println("\n--- 3. NẠP TIỀN ---");
        try {
            String accNum = readStringInput("Nhập số tài khoản: ");
            BankAccount acc = bankManager.findAccount(accNum);

            if (acc == null) return;

            double amount = readDoubleInput("Nhập số tiền muốn nạp: ");
            acc.deposit(amount);

            System.out.println("✅ Nạp tiền thành công! Số dư mới: " + String.format("%,.2f", acc.getBalance()) + " VNĐ");

        } catch (BankAccount.InvalidAmountException e) {
            System.out.println("⚠️ Lỗi giao dịch: " + e.getMessage());
        }
    }

    private static void handleWithdraw() {
        System.out.println("\n--- 4. RÚT TIỀN ---");
        try {
            String accNum = readStringInput("Nhập số tài khoản: ");
            BankAccount acc = bankManager.findAccount(accNum);

            if (acc == null) return;

            double amount = readDoubleInput("Nhập số tiền muốn rút: ");
            acc.withdraw(amount);

            System.out.println("✅ Rút tiền thành công! Số dư mới: " + String.format("%,.2f", acc.getBalance()) + " VNĐ");

        } catch (BankAccount.InvalidAmountException e) {
            System.out.println("⚠️ Lỗi nhập số tiền: " + e.getMessage());
        } catch (BankAccount.InsufficientBalanceException e) {
            System.out.println("⚠️ Rút tiền thất bại: " + e.getMessage());
        }
    }

    private static void handleTransfer() {
        System.out.println("\n--- 5. CHUYỂN TIỀN ---");
        try {
            String fromAcc = readStringInput("Nhập số tài khoản nguồn (người chuyển): ");
            String toAcc = readStringInput("Nhập số tài khoản đích (người nhận): ");
            double amount = readDoubleInput("Nhập số tiền cần chuyển: ");

            bankManager.transferMoney(fromAcc, toAcc, amount);
            System.out.println("✅ Chuyển tiền thành công!");

        } catch (BankAccount.InvalidAmountException e) {
            System.out.println("⚠️ Lỗi giao dịch: " + e.getMessage());
        } catch (BankAccount.InsufficientBalanceException e) {
            System.out.println("⚠️️ Lỗi số dư: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Yêu cầu không hợp lệ: " + e.getMessage());
        }
    }

    private static void handleShowTotalBalance() {
        System.out.println("\n--- 6. TỔNG SỐ DƯ HỆ THỐNG ---");
        // Giả lập danh sách tài khoản hiện tại từ manager
        ArrayList<BankAccount> list = new ArrayList<>();
        // Lấy danh sách hoặc tính toán tổng
        double total = bankManager.calculateTotalBalance(list);
        System.out.println("💰 Tổng số dư của tất cả tài khoản: " + String.format("%,.2f", total) + " VNĐ");
    }

    // =========================================================================
    // HÀM BỖ TRỢ ĐỌC DỮ LIỆU AN TOÀN (BẪY NGOẠI LỆ NHẬP LIỆU SCANNER)
    // =========================================================================

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                scanner.nextLine(); // Đọc bỏ ký tự xuống dòng '\n'
                return input;
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Lỗi nhập liệu: Vui lòng nhập một số nguyên hợp lệ!");
                scanner.nextLine(); // Xóa bộ nhớ đệm bị lỗi
            }
        }
    }

    private static double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                scanner.nextLine(); // Đọc bỏ ký tự xuống dòng '\n'
                return input;
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Lỗi nhập liệu: Vui lòng nhập số thực hợp lệ (VD: 50000 hoặc 5.5)!");
                scanner.nextLine(); // Xóa bộ nhớ đệm bị lỗi
            }
        }
    }

    private static String readStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("⚠️ Dữ liệu nhập không được để trống!");
        }
    }
}

// =========================================================================
// LỚP THỰC THI THỰC TẾ CHO SAVINGACCOUNT (Cụ thể hóa để khởi tạo đối tượng)
// =========================================================================
class ConcreteSavingAccount extends SavingAccount {
    public ConcreteSavingAccount(String accountNumber, String holderName, double balance, double interestRate)
            throws InvalidAmountException, InsufficientBalanceException {
        super(accountNumber, holderName, balance, interestRate);
    }
}