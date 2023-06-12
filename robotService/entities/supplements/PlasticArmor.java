package robotService.entities.supplements;

public class PlasticArmor extends BaseSupplement{

    private static final int PLASTIC_ARMOR_HARDNESS = 1;
    private static final double PLASTIC_ARMOR_PRICE = 10;

    public PlasticArmor() {
        super(PLASTIC_ARMOR_HARDNESS, PLASTIC_ARMOR_PRICE);
    }
}
