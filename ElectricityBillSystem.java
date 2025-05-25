import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Customer class to represent customer data
class Customer implements Serializable {
    private int customerId;
    private String name;
    private String address;
    private String phone;
    private int prevReading;
    private int currReading;
    private double ratePerUnit;
    
    // Constructor
    public Customer(int customerId, String name, String address, String phone, 
                   int prevReading, int currReading, double ratePerUnit) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.prevReading = prevReading;
        this.currReading = currReading;
        this.ratePerUnit = ratePerUnit;
    }
    
    // Getters and Setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public int getPrevReading() { return prevReading; }
    public void setPrevReading(int prevReading) { this.prevReading = prevReading; }
    
    public int getCurrReading() { return currReading; }
    public void setCurrReading(int currReading) { this.currReading = currReading; }
    
    public double getRatePerUnit() { return ratePerUnit; }
    public void setRatePerUnit(double ratePerUnit) { this.ratePerUnit = ratePerUnit; }
    
    @Override
    public String toString() {
        return String.format("%-5d %-20s %-30s %-15s %-8d %-8d %.2f", 
                           customerId, name, address, phone, prevReading, currReading, ratePerUnit);
    }
}

// Bill class to represent bill data
class Bill implements Serializable {
    private int billId;
    private int customerId;
    private String customerName;
    private int unitsConsumed;
    private double amount;
    private String date;
    
    // Constructor
    public Bill(int billId, int customerId, String customerName, 
                int unitsConsumed, double amount, String date) {
        this.billId = billId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.unitsConsumed = unitsConsumed;
        this.amount = amount;
        this.date = date;
    }
    
    // Getters and Setters
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public int getUnitsConsumed() { return unitsConsumed; }
    public void setUnitsConsumed(int unitsConsumed) { this.unitsConsumed = unitsConsumed; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    @Override
    public String toString() {
        return String.format("%-8d %-8d %-20s %-12s %-8d Rs.%.2f", 
                           billId, customerId, customerName, date, unitsConsumed, amount);
    }
}

// Main application class
public class ElectricityBillSystem {
    private static final String CUSTOMERS_FILE = "customers.dat";
    private static final String BILLS_FILE = "bills.dat";
    private static final String ADMIN_USERNAME = "govind";
    private static final String ADMIN_PASSWORD = "123";
    
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();
    
    public static void main(String[] args) {
        ElectricityBillSystem system = new ElectricityBillSystem();
        system.mainMenu();
    }
    
    public void mainMenu() {
        while (true) {
            clearScreen();
            System.out.println("===============================================================");
            System.out.println("           ELECTRICITY BILL GENERATION SYSTEM                 ");
            System.out.println("===============================================================\n");
            System.out.println("                        MAIN MENU\n");
            System.out.println("\t\t1. View All Customers");
            System.out.println("\t\t2. Generate Bill");
            System.out.println("\t\t3. View All Bills");
            System.out.println("\t\t4. Admin Panel");
            System.out.println("\t\t5. Exit");
            System.out.print("\n\t\tEnter Your Choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        displayCustomers();
                        break;
                    case 2:
                        generateBill();
                        break;
                    case 3:
                        viewBills();
                        break;
                    case 4:
                        adminLogin();
                        break;
                    case 5:
                        System.out.println("\nThank you for using Electricity Bill System!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\nInvalid option! Please try again.");
                        pauseScreen();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input! Please enter a number.");
                scanner.nextLine(); // clear invalid input
                pauseScreen();
            }
        }
    }
    
    private void adminLogin() {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("                        ADMIN PANEL                           ");
        System.out.println("===============================================================\n");
        System.out.println("PLEASE LOGIN\n");
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            adminPanel();
        } else {
            System.out.println("\nInvalid Username/Password!");
            pauseScreen();
        }
    }
    
    private void adminPanel() {
        while (true) {
            clearScreen();
            System.out.println("===============================================================");
            System.out.println("                        ADMIN PANEL                           ");
            System.out.println("===============================================================\n");
            System.out.println("\t\t1. Add New Customer");
            System.out.println("\t\t2. Modify Customer Details");
            System.out.println("\t\t3. Remove Customer");
            System.out.println("\t\t4. View All Customers");
            System.out.println("\t\t5. Return to Main Menu");
            System.out.print("\n\t\tEnter Your Choice: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        modifyCustomer();
                        break;
                    case 3:
                        deleteCustomer();
                        break;
                    case 4:
                        displayCustomers();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("\nInvalid option! Please try again.");
                        pauseScreen();
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input! Please enter a number.");
                scanner.nextLine();
                pauseScreen();
            }
        }
    }
    
    private void addCustomer() {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("                      ADD NEW CUSTOMER                        ");
        System.out.println("===============================================================\n");
        
        try {
            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            // Check if customer ID already exists
            if (findCustomer(customerId) != null) {
                System.out.println("\nCustomer ID already exists!");
                pauseScreen();
                return;
            }
            
            System.out.print("Enter Customer Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();
            
            System.out.print("Enter Previous Meter Reading: ");
            int prevReading = scanner.nextInt();
            
            System.out.print("Enter Current Meter Reading: ");
            int currReading = scanner.nextInt();
            
            System.out.print("Enter Rate per Unit (Rs.): ");
            double ratePerUnit = scanner.nextDouble();
            
            Customer customer = new Customer(customerId, name, address, phone, 
                                           prevReading, currReading, ratePerUnit);
            
            saveCustomer(customer);
            System.out.println("\nCUSTOMER ADDED SUCCESSFULLY!");
            
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input! Please enter correct data types.");
            scanner.nextLine();
        }
        
        pauseScreen();
    }
    
    private void generateBill() {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("                      GENERATE BILL                           ");
        System.out.println("===============================================================\n");
        
        try {
            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();
            
            Customer customer = findCustomer(customerId);
            
            if (customer != null) {
                // Calculate bill
                int unitsConsumed = customer.getCurrReading() - customer.getPrevReading();
                double amount = calculateBillAmount(unitsConsumed, customer.getRatePerUnit());
                
                // Generate bill ID and date
                int billId = random.nextInt(9000) + 1000;
                String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                
                Bill bill = new Bill(billId, customer.getCustomerId(), customer.getName(), 
                                   unitsConsumed, amount, date);
                
                saveBill(bill);
                displayBillDetails(customer, bill);
                
            } else {
                System.out.println("\nCustomer not found!");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input! Please enter a valid customer ID.");
            scanner.nextLine();
        }
        
        pauseScreen();
    }
    
    private double calculateBillAmount(int units, double baseRate) {
        double amount = 0;
        
        if (units <= 100) {
            amount = units * baseRate;
        } else if (units <= 300) {
            amount = (100 * baseRate) + ((units - 100) * (baseRate + 2));
        } else {
            amount = (100 * baseRate) + (200 * (baseRate + 2)) + 
                    ((units - 300) * (baseRate + 5));
        }
        
        return amount;
    }
    
    private void displayBillDetails(Customer customer, Bill bill) {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("                      ELECTRICITY BILL                        ");
        System.out.println("===============================================================\n");
        System.out.println("Bill ID          : " + bill.getBillId());
        System.out.println("Date             : " + bill.getDate());
        System.out.println("Customer ID      : " + customer.getCustomerId());
        System.out.println("Customer Name    : " + customer.getName());
        System.out.println("Address          : " + customer.getAddress());
        System.out.println("Phone            : " + customer.getPhone());
        System.out.println("---------------------------------------------------------------");
        System.out.println("Previous Reading : " + customer.getPrevReading() + " units");
        System.out.println("Current Reading  : " + customer.getCurrReading() + " units");
        System.out.println("Units Consumed   : " + bill.getUnitsConsumed() + " units");
        System.out.printf("Rate per Unit    : Rs. %.2f\n", customer.getRatePerUnit());
        System.out.println("---------------------------------------------------------------");
        System.out.printf("Total Amount     : Rs. %.2f\n", bill.getAmount());
        System.out.println("===============================================================");
        System.out.println("\nBILL GENERATED SUCCESSFULLY!");
        pauseScreen();
    }
    
    private void displayCustomers() {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("                      CUSTOMER LIST                           ");
        System.out.println("===============================================================\n");
        
        List<Customer> customers = loadCustomers();
        
        if (customers.isEmpty()) {
            System.out.println("No customers found!");
        } else {
            System.out.printf("%-5s %-20s %-30s %-15s %-8s %-8s %-5s\n", 
                           "ID", "NAME", "ADDRESS", "PHONE", "PREV", "CURR", "RATE");
            System.out.println("---------------------------------------------------------------");
            
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
        
        pauseScreen();
    }
    
    private void viewBills() {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("                        ALL BILLS                             ");
        System.out.println("===============================================================\n");
        
        List<Bill> bills = loadBills();
        
        if (bills.isEmpty()) {
            System.out.println("No bills found!");
        } else {
            System.out.printf("%-8s %-8s %-20s %-12s %-8s %-10s\n", 
                           "BILL_ID", "CUST_ID", "NAME", "DATE", "UNITS", "AMOUNT");
            System.out.println("---------------------------------------------------------------");
            
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        }
        
        pauseScreen();
    }
    
    private void modifyCustomer() {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("                    MODIFY CUSTOMER DETAILS                   ");
        System.out.println("===============================================================\n");
        
        try {
            System.out.print("Enter Customer ID to modify: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            Customer customer = findCustomer(customerId);
            
            if (customer != null) {
                System.out.println("\nCurrent Details:");
                System.out.println("Name: " + customer.getName());
                System.out.println("Address: " + customer.getAddress());
                System.out.println("Phone: " + customer.getPhone());
                System.out.println("Previous Reading: " + customer.getPrevReading());
                System.out.println("Current Reading: " + customer.getCurrReading());
                System.out.println("Rate per Unit: " + customer.getRatePerUnit());
                
                System.out.println("\nEnter New Details:");
                System.out.print("Enter Customer Name: ");
                customer.setName(scanner.nextLine());
                
                System.out.print("Enter Address: ");
                customer.setAddress(scanner.nextLine());
                
                System.out.print("Enter Phone Number: ");
                customer.setPhone(scanner.nextLine());
                
                System.out.print("Enter Previous Meter Reading: ");
                customer.setPrevReading(scanner.nextInt());
                
                System.out.print("Enter Current Meter Reading: ");
                customer.setCurrReading(scanner.nextInt());
                
                System.out.print("Enter Rate per Unit (Rs.): ");
                customer.setRatePerUnit(scanner.nextDouble());
                
                updateCustomer(customer);
                System.out.println("\nCUSTOMER DETAILS UPDATED SUCCESSFULLY!");
                
            } else {
                System.out.println("\nCustomer not found!");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid input! Please enter correct data types.");
            scanner.nextLine();
        }
        
        pauseScreen();
    }
    
    private void deleteCustomer() {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("                      DELETE CUSTOMER                         ");
        System.out.println("===============================================================\n");
        
        try {
            System.out.print("Enter Customer ID to delete: ");
            int customerId = scanner.nextInt();
            
            Customer customer = findCustomer(customerId);
            
            if (customer != null) {
                removeCustomer(customerId);
                System.out.println("Customer '" + customer.getName() + "' deleted successfully!");
            } else {
                System.out.println("Customer not found!");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid customer ID.");
            scanner.nextLine();
        }
        
        pauseScreen();
    }
    
    // File handling methods
    private void saveCustomer(Customer customer) {
        List<Customer> customers = loadCustomers();
        customers.add(customer);
        saveCustomersToFile(customers);
    }
    
    private void saveBill(Bill bill) {
        List<Bill> bills = loadBills();
        bills.add(bill);
        saveBillsToFile(bills);
    }
    
    private Customer findCustomer(int customerId) {
        List<Customer> customers = loadCustomers();
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }
    
    private void updateCustomer(Customer updatedCustomer) {
        List<Customer> customers = loadCustomers();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId() == updatedCustomer.getCustomerId()) {
                customers.set(i, updatedCustomer);
                break;
            }
        }
        saveCustomersToFile(customers);
    }
    
    private void removeCustomer(int customerId) {
        List<Customer> customers = loadCustomers();
        customers.removeIf(customer -> customer.getCustomerId() == customerId);
        saveCustomersToFile(customers);
    }
    
    @SuppressWarnings("unchecked")
    private List<Customer> loadCustomers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CUSTOMERS_FILE))) {
            return (List<Customer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<Bill> loadBills() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BILLS_FILE))) {
            return (List<Bill>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
    
    private void saveCustomersToFile(List<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CUSTOMERS_FILE))) {
            oos.writeObject(customers);
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }
    
    private void saveBillsToFile(List<Bill> bills) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BILLS_FILE))) {
            oos.writeObject(bills);
        } catch (IOException e) {
            System.out.println("Error saving bills: " + e.getMessage());
        }
    }
    
    // Utility methods
    private void clearScreen() {
        // Clear screen for different operating systems
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            // If clearing fails, just print newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    private void pauseScreen() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}