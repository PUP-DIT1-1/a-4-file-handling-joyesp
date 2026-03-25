import java.io.*;
import java.util.*;

class Record {
    int id;
    String name;

    Record(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return id + "," + name;
    }
}

public class Main {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<Record> records = new ArrayList<>();
    static final String FILE_NAME = "records.txt";

    public static void main(String[] args) {
        loadFromFile();

        int choice = 0;

        do {
            try {
                System.out.println(" MENU ");
                System.out.println("1. Add Record");
                System.out.println("2. View Records");
                System.out.println("3. Update Record");
                System.out.println("4. Delete Record");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                choice = scan.nextInt();

                switch (choice) {
                    case 1: addRecord(); break;
                    case 2: viewRecords(); break;
                    case 3: updateRecord(); break;
                    case 4: deleteRecord(); break;
                    case 5: saveToFile(); System.out.println("Exiting..."); break;
                    default: System.out.println("Invalid choice!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numbers only.");
                scan.nextLine(); // clear buffer
            }

        } while (choice != 5);
    }

    
    static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                file.createNewFile();
                System.out.println("New file created.");
                return;
            }

            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                try {
                    String[] data = line.split(",");
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];

                    records.add(new Record(id, name));
                } catch (Exception e) {
                    System.out.println("Skipping corrupted data.");
                }
            }

            fileReader.close();
            System.out.println("Records loaded successfully!");

        } catch (IOException e) {
            System.out.println("Error handling file.");
        }
    }

    
    static void saveToFile() {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);

            for (Record r : records) {
                writer.write(r.toString() + "\n");
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    
    static void addRecord() {
        try {
            System.out.print("Enter ID: ");
            int id = scan.nextInt();
            scan.nextLine();

            System.out.print("Enter Name: ");
            String name = scan.nextLine();

            records.add(new Record(id, name));
            saveToFile();

            System.out.println("Record added!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! ID must be a number.");
            scan.nextLine();
        }
    }

    
    static void viewRecords() {
        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (int i = 0; i < records.size(); i++) {
            System.out.println(i + ": ID=" + records.get(i).id + ", Name=" + records.get(i).name);
        }
    }

    
    static void updateRecord() {
        viewRecords();

        try {
            System.out.print("Enter index to update: ");
            int index = scan.nextInt();
            scan.nextLine();

            if (index < 0 || index >= records.size()) {
                System.out.println("Invalid index!");
                return;
            }

            System.out.print("Enter new name: ");
            String newName = scan.nextLine();

            records.get(index).name = newName;
            saveToFile();

            System.out.println("Record updated!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scan.nextLine();
        }
    }

    
    static void deleteRecord() {
        viewRecords();

        try {
            System.out.print("Enter index to delete: ");
            int index = scan.nextInt();

            if (index < 0 || index >= records.size()) {
                System.out.println("Invalid index!");
                return;
            }

            records.remove(index);
            saveToFile();

            System.out.println("Record deleted!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scan.nextLine();
        }
    }
}