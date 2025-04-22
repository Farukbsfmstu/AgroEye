package UAV_Drone;

import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class UavInterface {
    // First Uav in linkedlist
   private Uav firstUav;
    // take input from the user   
    private Scanner input;

    // Constructor to initialize the UAV list and Scanner
    public UavInterface() {
        this.firstUav = null ;
        this.input = new Scanner(System.in);
    }

    // Method to run the menu and handle user input
    public void run() {
  try{
        
        while (true) {

            // Display main menu options
            System.out.println("\n1. Add Unmanned Aerial Vehicle (UAV)\n2. Remove Unmanned Aerial Vehicle (UAV)\n3. Add Sensor\n4. Remove Sensor "+
                     "\n5. Query Unmanned Aerial Vehicle(UAV) \n6. List Sensors on UAV\n7. Query Sensor at Unmanned Aerial Vehicle (UAV)\n8. Exit");
            System.out.print("Please Enter your choice: ");
           
               
            int choice = input.nextInt();
            // Consume the newline 
            input.nextLine(); 
            // Switch case to execute the chosen option
            switch (choice) {
                case 1: addUav(); break;
                case 2: removeUav(); break;
                case 3: addSensor(); break;
                case 4: removeSensor(); break;
                case 5: queryBestUav(); break;
                case 6: listSensors(); break;
                case 7: querySensor(); break;
                case 8: 
                    System.out.println("Exiting..."); 
                    return; // Exit the program
                default: 
                    System.out.println("*Invalid choice.Please Try again.");
            }
            }
        }
        catch(Exception e){
                System.out.println("*Invalid choice Please Try again!");  
                input.nextLine();
            }
        run() ;
    }

    // Method to add a new UAV
    private void addUav() {
        // Check if the maximum number of UAVs (4) has been reached

        if(getUavCount()>=4){
            System.out.println("Sorry,Can't add more UAV");
            return ;
        }
        // Prompt the user for UAV details
        System.out.print("Please Enter Uav Name: ");
        String name=input.nextLine();
        
           if (findUavByName(name)!=null) {
            System.out.println("Sorry This Uav name already exist!");
            return ;
        }
        //Get the operational cost and availability
        System.out.print("Please Enter the operational cost: ");
        double cost = input.nextDouble();
        System.out.print("Please Enter the availability (0-5): ");
        int availability = input.nextInt();
        // Consume newline
        input.nextLine(); 
        
        Uav addUav = new Uav(name, cost, availability);
        if(firstUav==null){
           firstUav=addUav;
        }
        else {
            Uav temporary = firstUav;
            while (temporary.getNextUav() != null) {
                temporary = temporary.getNextUav();
            }
            temporary.setNextUav(addUav);
        }
  
        System.out.println("Congrates!! UAV added successfully.");
    }

    // Method to remove an existing UAV by its name
    private void removeUav() {
        System.out.print("Please Enter the UAV name to remove: ");
        String name = input.nextLine();
        
        Uav temporary=firstUav;
        Uav pervious=null;
        
        // Search for the UAV in the list
            while (temporary != null) {
            if (temporary.getName().equals(name)) {
                if (pervious == null) {
                    firstUav = temporary.getNextUav();  // Remove from the beginning
                } else {
                    pervious.setNextUav(temporary.getNextUav());  // Remove from the middle
                }
                System.out.println("UAV removed successfully.");
                return;
            }
            pervious = temporary;
            temporary = temporary.getNextUav();
        }
        // If UAV not found, show error message
        System.out.println("Sorry!UAV not found.");
    }

    // Method to add a sensor to a UAV
    private void addSensor() {
        System.out.print("Enter UAV name: ");
        String name = input.nextLine();
        Uav uav = findUavByName(name);
        
        if (uav == null) { 
            System.out.println("UAV not found.");
            return;
        }

        // Ask for sensor details
        System.out.print("Enter sensor type (temperature, pressure, windspeed, humidity): ");
        String type = input.nextLine().toLowerCase();
        System.out.print("Enter sensor grade (1-5): ");
        int grade = input.nextInt();
        System.out.print("Enter sensor quantity: ");
        int quantity = input.nextInt();
        input.nextLine(); // Consume newline

        // Validate inputs and add the sensor
        Sensor sensor = new Sensor(type, grade, quantity);
        if (!uav.addSensor(sensor)) {
            System.out.println("Unable to add sensor: either UAV already has 3 sensors or this sensor already exists.");
        } else {
            System.out.println("Sensor added successfully.");
        }
    }

    // Method to remove a sensor from a UAV
    private void removeSensor() {
        System.out.print("Enter UAV name: ");
        String name = input.nextLine();
        Uav uav = findUavByName(name);
        
        if (uav == null) {
            System.out.println("UAV not found.");
            return;
        }

        // Ask for sensor type and quantity to remove
        System.out.print("Enter sensor type to remove: ");
        String type = input.nextLine().toLowerCase();
        System.out.print("Enter quantity to remove: ");
        int quantity = input.nextInt();
        input.nextLine(); // Consume newline

        // Validate and remove the sensor
        if (!uav.removeSensor(type, quantity)) {
            System.out.println("Error removing sensor: check sensor type or quantity.");
        } else {
            System.out.println("Sensor removed successfully.");
        }
    }

    // Method to query the best UAV for a task based on sensor requirements
    private void queryBestUav() {
        System.out.print("Enter sensor type: ");
        String type = input.nextLine().toLowerCase();
        System.out.print("Enter required sensor quantity: ");
        int quantity = input.nextInt();
        System.out.print("Enter minimum sensor grade (1-5): ");
        int grade = input.nextInt();
        input.nextLine(); // Consume newline
        
        Uav bestUav = null;
        for (Uav uav= firstUav;uav!=null;uav=uav.getNextUav()) {
            if (uav.hasSensor(type, quantity, grade)) {
                if (bestUav == null || uav.getOperationalCost() < bestUav.getOperationalCost() || 
                    (uav.getOperationalCost() == bestUav.getOperationalCost() && uav.getAvailability() > bestUav.getAvailability())) {
                    bestUav = uav;
                }
            }
        }

        if (bestUav != null) {
            System.out.println("Best UAV for the task: " + bestUav);
        } else {
            System.out.println("No UAV meets the requirements.");
        }
    }

    // Method to list sensors on a given UAV
    private void listSensors() {
        System.out.print("Enter UAV name: ");
        String name = input.nextLine();
        Uav uav = findUavByName(name);
        
        if (uav == null) {
            System.out.println("UAV not found.");
            return;
        }

        uav.listSensors();
    }

    // Method to query sensors of a specific type across all UAVs
    private void querySensor() {
        System.out.print("Enter sensor type: ");
        String type = input.nextLine().toLowerCase();
        
        boolean find = false;
        for (Uav uav= firstUav;uav!=null;uav=uav.getNextUav()) {
            if (uav.hasSensor(type)) {
                System.out.println(type + " sensor is on " + uav.getName());
                find = true;
            }
        }

        if (!find) {
            System.out.println("No UAV has this sensor.");
        }
    }

    // This method find a UAV by name
    private Uav findUavByName(String name) {
       Uav temporary = firstUav;
        while (temporary != null) {
            if (temporary.getName().equals(name)) {
                return temporary;
            }
            temporary = temporary.getNextUav();
        }
        return null;
    }
    //this method count of UAV
    public int getUavCount() {
        int flag = 0;
        Uav temporary = firstUav;
        while (temporary != null) {
            flag++;
            temporary = temporary.getNextUav();
        }
        return flag;
    }

    // Main method 
    public static void main(String[] args) {
        UavInterface ui = new UavInterface();
        ui.run();
    }
}
