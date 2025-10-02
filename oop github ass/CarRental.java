import java.util.*;

// Car Class
class Car {
    private String carId;
    private String model;
    private double dailyRate;
    private boolean available;

    public Car(String carId, String model, double dailyRate) {
        this.carId = carId;
        this.model = model;
        this.dailyRate = dailyRate;
        this.available = true;
    }

    public String getCarId() { return carId; }
    public String getModel() { return model; }
    public double getDailyRate() { return dailyRate; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }
    public void setModel(String model) { this.model = model; }
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }

    @Override
    public String toString() {
        return carId + " - " + model + " ($" + dailyRate + "/day) " +
               (available ? "[Available]" : "[Rented]");
    }
}

// Customer Class
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return customerId + " - " + name;
    }
}

// Rental Class
class Rental {
    private Car car;
    private Customer customer;
    private int days;
    private double totalCost;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.totalCost = days * car.getDailyRate();
    }

    public Car getCar() { return car; }
    public Customer getCustomer() { return customer; }

    @Override
    public String toString() {
        return customer.getName() + " rented " + car.getModel() +
               " for " + days + " days. Total = $" + totalCost;
    }
}

// Rental Agency Class
class RentalAgency {
    List<Car> cars = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
    List<Rental> rentals = new ArrayList<>();
    private int carCounter = 1;
    private int customerCounter = 1;

    // Auto ID generators
    private String generateCarId() { return "C" + (carCounter++); }
    private String generateCustomerId() { return "CU" + (customerCounter++); }

    // Car management
    public void addCar(String model, double dailyRate) {
        cars.add(new Car(generateCarId(), model, dailyRate));
        System.out.println("Car added.");
    }

    public void editCar(int index, String model, double dailyRate) {
        Car car = cars.get(index);
        car.setModel(model);
        car.setDailyRate(dailyRate);
        System.out.println("Car updated.");
    }

    public void deleteCar(int index) {
        cars.remove(index);
        System.out.println("Car deleted.");
    }

    public void listCars() {
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
            return;
        }
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ". " + cars.get(i));
        }
    }

    // Customer management
    public void addCustomer(String name) {
        customers.add(new Customer(generateCustomerId(), name));
        System.out.println("Customer added.");
    }

    public void editCustomer(int index, String name) {
        customers.get(index).setName(name);
        System.out.println("Customer updated.");
    }

    public void deleteCustomer(int index) {
        customers.remove(index);
        System.out.println("Customer deleted.");
    }

    public void listCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i));
        }
    }

    // Rent car
    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.setAvailable(false);
            rentals.add(new Rental(car, customer, days));
            System.out.println("Rental successful!");
        } else {
            System.out.println("Car not available.");
        }
    }

    // Return car
    public void returnCar(Car car) {
        car.setAvailable(true);
        System.out.println("Car returned.");
    }

    // Show all rentals
    public void showTransactions() {
        if (rentals.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Rental r : rentals) {
            System.out.println(r);
        }
    }

    // Show rented cars
    public void viewRentedCars() {
        boolean any = false;
        for (Car c : cars) {
            if (!c.isAvailable()) {
                System.out.println(c);
                any = true;
            }
        }
        if (!any) {
            System.out.println("No cars currently rented.");
        }
    }
}

// Main Class
public class CarRental {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RentalAgency agency = new RentalAgency();

        // Preloaded sample data
        agency.addCar("Toyota Corolla", 50);
        agency.addCar("Honda Civic", 60);
        agency.addCustomer("Alice");
        agency.addCustomer("Bob");

        while (true) {
            System.out.println("\n=== Car Rental System ===");
            System.out.println("1. Manage Cars");
            System.out.println("2. Manage Customers");
            System.out.println("3. Rent a Car");
            System.out.println("4. Return a Car");
            System.out.println("5. Show Transactions");
            System.out.println("6. View Rented Cars");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n--- Manage Cars ---");
                    System.out.println("1. Add Car");
                    System.out.println("2. Edit Car");
                    System.out.println("3. Delete Car");
                    System.out.println("4. List Cars");
                    int cChoice = sc.nextInt(); sc.nextLine();
                    switch (cChoice) {
                        case 1 -> {
                            System.out.print("Enter Model: ");
                            String model = sc.nextLine();
                            System.out.print("Enter Daily Rate: ");
                            double rate = sc.nextDouble();
                            agency.addCar(model, rate);
                        }
                        case 2 -> {
                            agency.listCars();
                            System.out.print("Select Car (number): ");
                            int idx = sc.nextInt() - 1; sc.nextLine();
                            System.out.print("Enter new Model: ");
                            String model = sc.nextLine();
                            System.out.print("Enter new Daily Rate: ");
                            double rate = sc.nextDouble();
                            agency.editCar(idx, model, rate);
                        }
                        case 3 -> {
                            agency.listCars();
                            System.out.print("Select Car (number): ");
                            int idx = sc.nextInt() - 1;
                            agency.deleteCar(idx);
                        }
                        case 4 -> agency.listCars();
                    }
                }
                case 2 -> {
                    System.out.println("\n--- Manage Customers ---");
                    System.out.println("1. Add Customer");
                    System.out.println("2. Edit Customer");
                    System.out.println("3. Delete Customer");
                    System.out.println("4. List Customers");
                    int cuChoice = sc.nextInt(); sc.nextLine();
                    switch (cuChoice) {
                        case 1 -> {
                            System.out.print("Enter Name: ");
                            String name = sc.nextLine();
                            agency.addCustomer(name);
                        }
                        case 2 -> {
                            agency.listCustomers();
                            System.out.print("Select Customer (number): ");
                            int idx = sc.nextInt() - 1; sc.nextLine();
                            System.out.print("Enter new Name: ");
                            String name = sc.nextLine();
                            agency.editCustomer(idx, name);
                        }
                        case 3 -> {
                            agency.listCustomers();
                            System.out.print("Select Customer (number): ");
                            int idx = sc.nextInt() - 1;
                            agency.deleteCustomer(idx);
                        }
                        case 4 -> agency.listCustomers();
                    }
                }
                case 3 -> {
                    System.out.println("\nAvailable Cars:");
                    List<Car> availableCars = new ArrayList<>();
                    int index = 1;
                    for (Car c : agency.cars) {
                        if (c.isAvailable()) {
                            System.out.println(index + ". " + c);
                            availableCars.add(c);
                            index++;
                        }
                    }
                    if (availableCars.isEmpty()) {
                        System.out.println("No cars available.");
                        break;
                    }

                    System.out.println("\nRegistered Customers:");
                    List<Customer> customerList = new ArrayList<>();
                    index = 1;
                    for (Customer cust : agency.customers) {
                        System.out.println(index + ". " + cust);
                        customerList.add(cust);
                        index++;
                    }
                    if (customerList.isEmpty()) {
                        System.out.println("No customers available.");
                        break;
                    }

                    System.out.print("Select Car (number): ");
                    int carChoice = sc.nextInt();
                    System.out.print("Select Customer (number): ");
                    int custChoice = sc.nextInt();
                    System.out.print("Enter number of days: ");
                    int days = sc.nextInt();

                    if (carChoice > 0 && carChoice <= availableCars.size()
                        && custChoice > 0 && custChoice <= customerList.size()) {
                        Car selectedCar = availableCars.get(carChoice - 1);
                        Customer selectedCust = customerList.get(custChoice - 1);
                        agency.rentCar(selectedCar, selectedCust, days);
                    } else {
                        System.out.println("Invalid selection.");
                    }
                }
                case 4 -> {
                    agency.listCars();
                    System.out.print("Select Car to return (number): ");
                    int idx = sc.nextInt() - 1;
                    if (idx >= 0 && idx < agency.cars.size()) {
                        agency.returnCar(agency.cars.get(idx));
                    }
                }
                case 5 -> agency.showTransactions();
                case 6 -> agency.viewRentedCars();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
