package robotService.entities.robot;

public class FemaleRobot extends BaseRobot{

    private static final int FEMALE_KILOGRAMS = 7;

    public FemaleRobot(String name, String kind, double price) {
        super(name, kind, FEMALE_KILOGRAMS, price);
    }

    @Override
    public void eating() {

        this.setKilograms(this.getKilograms() + 1);
    }
}
