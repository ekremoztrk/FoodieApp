package model.utilities;

import model.domain.IFood;

public class ToppingPricePair {
    private double cost;
    private String topping;

    public ToppingPricePair(IFood food){
        this.cost = food.getCost();
        this.topping = food.decorate();
    }

    public ToppingPricePair(double cost, String food) {
        this.cost = cost;
        this.topping = food;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String food) {
        this.topping = food;
    }
}
