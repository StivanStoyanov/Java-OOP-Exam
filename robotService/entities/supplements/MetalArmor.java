package robotService.entities.supplements;

public class MetalArmor extends BaseSupplement{

    private static final int METAL_ARMOR_HARDNESS = 5;
    private static final double METAL_ARMOR_PRICE = 15;

    public MetalArmor() {
        super(METAL_ARMOR_HARDNESS, METAL_ARMOR_PRICE);
    }
}
