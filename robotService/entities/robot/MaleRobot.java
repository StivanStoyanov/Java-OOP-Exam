package robotService.entities.robot;

public class MaleRobot extends BaseRobot{

    private static final int MALE_KILOGRAMS = 9;

    public MaleRobot(String name, String kind, double price) {
        super(name, kind, MALE_KILOGRAMS, price);
    }

    @Override
    public void eating() {

        this.setKilograms(this.getKilograms() + 3);
    }
}
