package UAV_Drone;
public class Sensor {
    
    //Declare necessary local variable
    private String type;
    private int grade;
    private int quantity;
    private Sensor nextSensor;
    
    //declare constructor initialize local variable
    public Sensor(String type, int grade, int quantity) {
        if (!type.equalsIgnoreCase("temperature") && !type.equalsIgnoreCase("pressure") &&
            !type.equalsIgnoreCase("windspeed")  &&  !type.equalsIgnoreCase("humidity")) {
            throw new IllegalArgumentException("Invalid sensor type.");
        }
        if (grade < 1 || grade > 5) {
            throw new IllegalArgumentException("Grade must be between 1 and 5.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.type = type;
        this.grade = grade;
        this.quantity = quantity;
        this.nextSensor=null;
    }

    //Getter Method
    public String getType() {
        return type; 
    }
    public int getGrade() { 
        return grade; 
    }
    public int getQuantity() {
        return quantity;
    }
    public Sensor getNextSensor(){
        return nextSensor ;
    }
    public void setNextSensor(Sensor next){
        this.nextSensor=next ;
    }
    // method of quantity
    public void addQuantity(int n) { 
         if (n > 0) {
            this.quantity += n;
        }
    }
    public boolean removeQuantity(int n) {
        if (n > 0 && n <= this.quantity) {
            this.quantity -= n;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "type"+type + " sensor, Grade " + grade + ", Quantity " + quantity;
    }
}