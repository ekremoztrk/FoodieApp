package model.data_access;

import model.domain.*;
import model.utilities.FoodCostPair;
import model.utilities.ToppingPricePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IOParser {

    protected static Node toRestaurantXMLNode(Restaurant restaurant, Document doc) {
        Element userElem = doc.createElement("User");
        //userElem.setAttribute("id", String.valueOf(user.getId()));
        userElem.setAttribute("id",String.valueOf(restaurant.getId()));
        userElem.appendChild(getNodeElement(doc, "Type", "restaurant"));
        userElem.appendChild(getNodeElement(doc, "Name", restaurant.getName()));
        userElem.appendChild(getNodeElement(doc, "UserName", restaurant.getUsername()));
        userElem.appendChild(getNodeElement(doc, "Password", restaurant.getPassword()));
        userElem.appendChild(getNodeElement(doc, "Address", restaurant.getAddress()));

        Element node = doc.createElement("Orders");
        if(!restaurant.getOrderHistory().isEmpty()){
            for(Order order:restaurant.getOrderHistory()){
                node.appendChild(toOrderXML(order,doc));
            }
        }
        Element menuNode = doc.createElement("Menus");
        if(!restaurant.getMenu().isEmpty()){
            for(Menu menu:restaurant.getMenu()){
                menuNode.appendChild(toMenuXML(menu,doc));
            }
        }
        userElem.appendChild(node);
        userElem.appendChild(menuNode);
        return userElem;
    }
    protected static Node toCustomerXMLNode(Customer customer, Document doc) {
        Element userElem = doc.createElement("User");
        //userElem.setAttribute("id", String.valueOf(user.getId()));
        userElem.setAttribute("id",String.valueOf(customer.getId()));
        userElem.appendChild(getNodeElement(doc, "Type", "customer"));
        userElem.appendChild(getNodeElement(doc, "Name", customer.getName()));
        userElem.appendChild(getNodeElement(doc, "UserName", customer.getUsername()));
        userElem.appendChild(getNodeElement(doc, "Password", customer.getPassword()));
        userElem.appendChild(getNodeElement(doc, "Address", customer.getAddress()));
        Element nodeCurrentOrder = doc.createElement("CurrentOrder");
        if(customer.getCurrentOrder()!=null){
            nodeCurrentOrder.appendChild(toOrderXML(customer.getCurrentOrder(),doc));
        }
        Element node = doc.createElement("Orders");
        if(!customer.getOrderHistory().isEmpty()){
            for(Order order:customer.getOrderHistory()){
                node.appendChild(toOrderXML(order,doc));
            }
        }

        userElem.appendChild(node);
        userElem.appendChild(nodeCurrentOrder);
        return userElem;
    }
    private static Node getNodeElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    private static Node toOrderXML(Order order, Document doc){
        Element orderElem = doc.createElement("Order");
        //userElem.setAttribute("id", String.valueOf(user.getId()));
        orderElem.setAttribute("id",String.valueOf(order.getId()));
        orderElem.appendChild(getNodeElement(doc, "CustomerName", order.getCustomerName()));
        orderElem.appendChild(getNodeElement(doc, "RestaurantName", order.getRestaurantName()));
        orderElem.appendChild(getNodeElement(doc, "Address", order.getAddress()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        if(order.getOrderDate()!=null)
            orderElem.appendChild(getNodeElement(doc, "Date", dateFormat.format(order.getOrderDate())));
        else
            orderElem.appendChild(getNodeElement(doc, "Date","-"));
        Element node = doc.createElement("Foods");
        int foodId = 1;
        if(!order.getItems().isEmpty()){
            for(FoodCostPair food:order.getItems()){
                node.appendChild(toIFoodXML(foodId,food,doc));
                foodId++;
            }
        }
        orderElem.appendChild(node);
        return orderElem;
    }
    private static Node toIFoodXML(int id,FoodCostPair food, Document doc){
        Element foodElem = doc.createElement("Food");
        //userElem.setAttribute("id", String.valueOf(user.getId()));
        foodElem.setAttribute("id",String.valueOf(id));

        foodElem.appendChild(getNodeElement(doc, "Cost", String.valueOf(food.getCost())));
        foodElem.appendChild(getNodeElement(doc, "Food", food.getFood()));
        return foodElem;
    }
    private static Node toMenuXML(Menu menu, Document doc){
        Element menuElem = doc.createElement("Menu");
        menuElem.setAttribute("id", String.valueOf(menu.getId()));
        menuElem.appendChild(getNodeElement(doc, "Name", menu.getName()));
        int elemCounter=0;
        Element itemsNode = doc.createElement("Items");
        if(!menu.getItems().isEmpty()){
            for(Map.Entry<FoodCostPair, List<ToppingPricePair>> item:menu.getItems().entrySet()){
                Element node = doc.createElement("Item");
                node.setAttribute("id",String.valueOf(elemCounter));
                elemCounter++;
                node.appendChild(getNodeElement(doc,"Name",item.getKey().getFood()));
                node.appendChild(getNodeElement(doc,"Cost",String.valueOf(item.getKey().getCost())));
                String ingredients = "";
                for(ToppingPricePair ingredient:item.getValue()){
                    ingredients += ingredient.getTopping() + ":"+ingredient.getCost() + " ";
                }
                if(ingredients.length() > 0) {
                    ingredients = ingredients.substring(0, ingredients.length() - 1);
                }
                node.appendChild(getNodeElement(doc,"Ingredients",ingredients));
                itemsNode.appendChild(node);
            }
            menuElem.appendChild(itemsNode);
        }
        return menuElem;
    }

    protected static List<Menu> nodeToMenus(Node menusNode){
        List<Menu> menuList = new ArrayList<>();
        NodeList nList = menusNode.getChildNodes();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Map<FoodCostPair,List<ToppingPricePair>> menuMap = new HashMap<>();
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.valueOf(eElement.getAttribute("id"));
                String menuName = eElement.getElementsByTagName("Name").item(0).getTextContent();

                Node itemsNode = eElement.getElementsByTagName("Items").item(0);
                if(itemsNode!=null)
                    menuMap = nodeToMenuItems(menuMap,itemsNode);
                Menu menu = new Menu(id,menuName,menuMap);
                menuList.add(menu);
            }
        }
        return menuList;
    }
    private static Map<FoodCostPair,List<ToppingPricePair>> nodeToMenuItems(Map<FoodCostPair,List<ToppingPricePair>> menuMap,Node menuItems){
        NodeList nList = menuItems.getChildNodes();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            List<ToppingPricePair> ingredientsList = new ArrayList<>();

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.valueOf(eElement.getAttribute("id"));
                String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                double cost = Double.valueOf(eElement.getElementsByTagName("Cost").item(0).getTextContent());

                String ingredients = eElement.getElementsByTagName("Ingredients").item(0).getTextContent();
                String[] values = ingredients.split(" ");
                if(values.length > 0) {
                    for (String s : values)
                        try {
                            String[] nameCost = s.split(":");
                            if(nameCost.length>1)
                                ingredientsList.add(new ToppingPricePair(Double.valueOf(nameCost[1]),nameCost[0]));
                        } catch (NumberFormatException e) {
                            ingredientsList = new ArrayList<>();
                        }
                }
                menuMap.put(new FoodCostPair(cost,name),ingredientsList);
            }
        }
        return menuMap;
    }
    protected static  List<Order> nodeToOrders(Node ordersNode) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        NodeList nList = ordersNode.getChildNodes();
        List<Order> orders = new ArrayList<>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.valueOf(eElement.getAttribute("id"));
                String customerName = eElement.getElementsByTagName("CustomerName").item(0).getTextContent();
                String restaurantName = eElement.getElementsByTagName("RestaurantName").item(0).getTextContent();
                String address = eElement.getElementsByTagName("Address").item(0).getTextContent();
                String dateStr = eElement.getElementsByTagName("Date").item(0).getTextContent();
                Date date = null;
                if(!dateStr.equals("-"))
                    date=dateFormat.parse(dateStr);
                Node foodsNode = eElement.getElementsByTagName("Foods").item(0);
                List<FoodCostPair> foodCostPairs = IOParser.nodeToFoods(foodsNode);
                Order order = new Order(id,address,customerName,restaurantName,foodCostPairs,date);
                orders.add(order);
            }
        }
        return orders;
    }
    private static List<FoodCostPair> nodeToFoods(Node foodsNode){
        List<FoodCostPair> foodCostPairs = new ArrayList<>();
        NodeList nList = foodsNode.getChildNodes();
        Map<String,List<String>> menuMap = new HashMap<>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int id = Integer.valueOf(eElement.getAttribute("id"));
                String cost = eElement.getElementsByTagName("Cost").item(0).getTextContent();
                String food = eElement.getElementsByTagName("Food").item(0).getTextContent();
                FoodCostPair foodCostPair = new FoodCostPair(Double.valueOf(cost),food);
                foodCostPairs.add(foodCostPair);
            }
        }
        return foodCostPairs;
    }
}
