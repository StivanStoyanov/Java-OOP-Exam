package robotService.core;

import robotService.common.ConstantMessages;
import robotService.common.ExceptionMessages;
import robotService.entities.robot.FemaleRobot;
import robotService.entities.robot.MaleRobot;
import robotService.entities.robot.Robot;
import robotService.entities.services.MainService;
import robotService.entities.services.SecondaryService;
import robotService.entities.services.Service;
import robotService.entities.supplements.MetalArmor;
import robotService.entities.supplements.PlasticArmor;
import robotService.entities.supplements.Supplement;
import robotService.repositories.SupplementRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerImpl implements Controller{

    private SupplementRepository supplements;
    private Map<String, Service> services;

    public ControllerImpl() {
        this.supplements = new SupplementRepository();
        this.services = new LinkedHashMap<>();
    }

    @Override
    public String addService(String type, String name) {
        Service service;
        if (type.equals("MainService")){

            service = new MainService(name);
        } else if (type.equals("SecondaryService")){

            service = new SecondaryService(name);
        }else {
            throw new NullPointerException(ExceptionMessages.INVALID_SERVICE_TYPE);
        }

        services.put(name, service);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SERVICE_TYPE, type);
    }

    @Override
    public String addSupplement(String type) {
        Supplement supplement;
        if (type.equals("PlasticArmor")){

            supplement = new PlasticArmor();
        }else if (type.equals("MetalArmor")){

            supplement = new MetalArmor();
        }else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_SUPPLEMENT_TYPE);
        }
        supplements.addSupplement(supplement);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForService(String serviceName, String supplementType) {
        Supplement supplement = supplements.findFirst(supplementType);

        if (supplement == null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_SUPPLEMENT_FOUND, supplementType));
        }
        services.get(serviceName).addSupplement(supplement);
        supplements.removeSupplement(supplement);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_IN_SERVICE, supplementType, serviceName);
    }

    @Override
    public String addRobot(String serviceName, String robotType, String robotName, String robotKind, double price) {

        Robot robot;
        if (robotType.equals("MaleRobot")){

            robot = new MaleRobot(robotName, robotKind, price);
        } else if (robotType.equals("FemaleRobot")){

            robot = new FemaleRobot(robotName, robotKind, price);
        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_ROBOT_TYPE);
        }

        if (serviceName.equals("MainService") && robot.getClass().getSimpleName().equals("MaleRobot")){
            services.get(serviceName).addRobot(robot);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_ROBOT_IN_SERVICE, robotType, serviceName);
        } else if (serviceName.equals("SecondaryService") && robot.getClass().getSimpleName().equals("FemaleRobot")){
            services.get(serviceName).addRobot(robot);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_ROBOT_IN_SERVICE, robotType, serviceName);
        } else {
            return String.format(ConstantMessages.UNSUITABLE_SERVICE);
        }
    }

    @Override
    public String feedingRobot(String serviceName) {
        services.get(serviceName).feeding();
        return String.format(ConstantMessages.FEEDING_ROBOT, services.get(serviceName).getRobots().size());
    }

    @Override
    public String sumOfAll(String serviceName) {

        double sumOfRobots = services.get(serviceName).getRobots().stream().mapToDouble(Robot::getPrice).sum();
        double sumOfSupplements = services.get(serviceName).getSupplements().stream().mapToDouble(Supplement::getPrice).sum();

        double totalSum = sumOfRobots + sumOfSupplements;
        return String.format(ConstantMessages.VALUE_SERVICE, serviceName, totalSum);
    }

    @Override
    public String getStatistics() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, Service> entry : services.entrySet()) {
            stringBuilder.append(entry.getValue().getStatistics());
            stringBuilder.append(System.lineSeparator());
        }


        return stringBuilder.toString().trim();
    }
}
