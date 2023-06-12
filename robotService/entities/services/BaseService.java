package robotService.entities.services;

import robotService.common.ConstantMessages;
import robotService.common.ExceptionMessages;
import robotService.entities.robot.Robot;
import robotService.entities.supplements.Supplement;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseService implements Service{

    private String name;
    private int capacity;
    private Collection<Supplement> supplements;
    private Collection<Robot> robots;

    public BaseService(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.supplements = new ArrayList<>();
        this.robots = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {

        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.SERVICE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<Robot> getRobots() {
        return this.robots;
    }

    @Override
    public Collection<Supplement> getSupplements() {
        return this.supplements;
    }

    @Override
    public void addRobot(Robot robot) {

        if (robots.size() < capacity){
            throw new IllegalArgumentException(ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_ROBOT);
        } else {
            robots.add(robot);
        }
    }

    @Override
    public void removeRobot(Robot robot) {

        robots.remove(robot);
    }

    @Override
    public void addSupplement(Supplement supplement) {

        supplements.add(supplement);
    }

    @Override
    public void feeding() {

        for (Robot robot : robots) {
            robot.eating();
        }
    }

    @Override
    public int sumHardness() {

        int sum = 0;
        for (Supplement supplement : supplements) {
            int supplementHardness = supplement.getHardness();
            sum = sum + supplementHardness;
        }
        return sum;
    }

    @Override
    public String getStatistics() {

        StringBuilder sb = new StringBuilder();

        sb.append(getName() + " " + getName().getClass().getSimpleName());
        sb.append(System.lineSeparator());
        sb.append("Robots:");
        if (robots.isEmpty()){
            sb.append(" none");
        } else {
            for (Robot robot : robots) {
                sb.append(" " + robot.getName());
            }
        }
        sb.append(System.lineSeparator());
        sb.append("Supplements: " + supplements.size() + " Hardness: " + this.sumHardness());

        return sb.toString().trim();
    }
}
