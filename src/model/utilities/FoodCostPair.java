package model.utilities;

import model.domain.IFood;

public class FoodCostPair {

    private double cost;
    private String food;

    public FoodCostPair(IFood food){
        this.cost = food.getCost();
        this.food = food.decorate();
    }

    public FoodCostPair(double cost, String food) {
        this.cost = cost;
        this.food = food;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
