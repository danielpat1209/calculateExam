public class Person {

    private int line;
    private String id;
    private String firstName;
    private String lastName;
    private int birth;
    private String gender;
    private String cityLiving;
    private double purchase;

    public Person(int line, String id, String firstName, String lastName, int birth, String gender, String cityLiving, double purchase) {
        this.line = line;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.gender = gender;
        this.cityLiving = cityLiving;
        this.purchase = purchase;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCityLiving() {
        return cityLiving;
    }

    public void setCityLiving(String cityLiving) {
        this.cityLiving = cityLiving;
    }

    public double getPurchase() {
        return purchase;
    }

    public void setPurchase(double purchase) {
        this.purchase = purchase;
    }

    // מספר שורה -
    //תעודת זהות -
   // שם פרטי -
    //שם משפחה -
   // שנת לידה -
    // זכר/נקבה -
   // עיר מגורים -
   // סכום הרכישה.

}
