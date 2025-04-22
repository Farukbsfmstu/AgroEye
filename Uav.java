package UAV_Drone;

import UAV_Drone.Sensor;
import java.util.HashMap;
import java.util.Map;

//This class is called POJO class 
public class Uav {
    //Declare necessary local variable
    private String name;
    private double operationalCost;
    private int availability;
    private  Sensor firstSensor;
    private Uav nextUav ;

    //declare constructor initialize local variable
    public Uav(String name, double operationalCost, int availability) {
        if (operationalCost <= 0) {
            throw new IllegalArgumentException("Operational cost must be positive.");
        }
        if (availability < 0 || availability > 5) {
            throw new IllegalArgumentException("Availability must be between 0 and 5.");
        }
        this.name = name;
        this.operationalCost = operationalCost;
        this.availability = availability;
        this.firstSensor =null;
    }
    //getter method
    public String getName() {
        return name; 
    }
    
    public double getOperationalCost() { 
        return operationalCost; 
    }
    
    public int getAvailability() {
        return availability; 
    }
    public Uav getNextUav() {
        return nextUav;
    }
    public void setNextUav(Uav nextUav) {
        this.nextUav = nextUav;
    }
    
    // Method to add a sensor
    public boolean addSensor(Sensor sensor) {
        if (firstSensor==null) {
            firstSensor = sensor;
        }
         else {
            Sensor temprary = firstSensor;
            int flag = 0;
            while (temprary != null) {
                flag++;
                temprary = temprary.getNextSensor();
            }
            if (flag >= 3) {
                return false;  // UAV can only have up to 3 sensors
            }
            temprary = firstSensor;
            while (temprary.getNextSensor() != null) {
                temprary = temprary.getNextSensor();
            }
            temprary.setNextSensor(sensor);  // Add sensor at the end of the list
        }
        return true;   
    }
    
    //Method to remove a sensor
    public boolean removeSensor(String type, int quantity) {
         Sensor temporary = firstSensor;
        Sensor previous = null;

        while (temporary != null) {
            if (temporary.getType().equals(type)) {
                if (temporary.getQuantity() >= quantity) {
                    temporary.removeQuantity(quantity);
                    if (temporary.getQuantity() == 0) {
                        if (previous == null) {
                            // Remove from the beginning
                            firstSensor = temporary.getNextSensor();  
                        } else {
                             // Remove from the middle
                            previous.setNextSensor(temporary.getNextSensor()); 
                        }
                    }
                    return true;
                }
            }
            previous = temporary;
            temporary = temporary.getNextSensor();
        }
        return false;
    }
    
        //Method to check if the UAV has a specific sensor type with certain quantity and grade
    public boolean hasSensor(String type) {
         Sensor temprary = firstSensor;
        while (temprary != null) {
            if (temprary.getType().equals(type)) {
                return true;  
            }
            temprary = temprary.getNextSensor();
        }
        return false;  
    }
    
    //check for a sensor with a specific quantity and grade
     public boolean hasSensor(String type, int requiredQuantity, int requiredGrade) {
        Sensor temp = firstSensor;
        while (temp != null) {
            if (temp.getType().equals(type) && temp.getQuantity() >= requiredQuantity && temp.getGrade() <= requiredGrade) {
                return true; 
            }
            temp = temp.getNextSensor();
        }
        return false; 
    }
              
    //List of all sensors
    public void listSensors() {
     Sensor temporary = firstSensor;
        if (temporary == null) {
            System.out.println("No sensors on " + name);
        } else {
            while (temporary != null) {
                System.out.println(temporary);
                temporary = temporary.getNextSensor();
            }
}
}

    @Override
    public String toString() {
        return "UAV: " + name + ", Cost: " + operationalCost + ", Availability: " + availability;
    }
}