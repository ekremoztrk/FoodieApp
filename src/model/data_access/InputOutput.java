//package model.data_access;
//
//import model.domain.*;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import model.utilities.FoodCostPair;
//import org.w3c.dom.*;
//import java.io.File;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class InputOutput {
//
//
//    public void outputUsers(List<User> userList){
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder;
//        try {
//            dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.newDocument();
//            Element rootElement = doc.createElementNS("CENG431", "Users");
//            doc.appendChild(rootElement);
//            for (User user : userList)
//                if(user instanceof Restaurant)
//                    rootElement.appendChild(IOParser.toRestaurantXMLNode((Restaurant) user,doc));
//                else if(user instanceof Customer)
//                    rootElement.appendChild(IOParser.toCustomerXMLNode((Customer) user,doc));
//
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            DOMSource source = new DOMSource(doc);
//
//            StreamResult file = new StreamResult(new File("users.xml"));
//            transformer.transform(source, file);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<User> inputUsers(){
//        List<User> users = new ArrayList<>();
//        try {
//            File inputFile = new File("users.xml");
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(inputFile);
//            doc.getDocumentElement().normalize();
//            // doc.getDocumentElement().getNodeName());
//            NodeList nList = doc.getElementsByTagName("User");
//
//            Node nNode = nList.item(0);
//            int length = nNode.getChildNodes().getLength();
//            for (int i = 0; i < length; i++) {
//                if (nNode != null && nNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element eElement = (Element) nNode;
//                    int id = Integer.valueOf(eElement.getAttribute("id"));
//                    String type = eElement.getElementsByTagName("Type").item(0).getTextContent();
//                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
//                    String userName = eElement.getElementsByTagName("UserName").item(0).getTextContent();
//                    String password = eElement.getElementsByTagName("Password").item(0).getTextContent();
//                    String address = eElement.getElementsByTagName("Address").item(0).getTextContent();
//                    Node ordersNode = eElement.getElementsByTagName("Orders").item(0);
//                    List<Order> orders = nodeToOrders(ordersNode);
//                    if(type.equalsIgnoreCase("restaurant")){
//                        Node menusNode = eElement.getElementsByTagName("Menus").item(0);
//
//                        List<Menu> menus = nodeToMenus(menusNode);
//                        User restaurant = new Restaurant(id,name,userName,password,address,orders,menus);
//                        users.add(restaurant);
//                    }
//                    else if(type.equalsIgnoreCase("customer")){
//                        Node currentOrderNode = eElement.getElementsByTagName("CurrentOrder").item(0);
//                        List<Order> currentOrderList = nodeToOrders(currentOrderNode);
//                        Order currentOrder = null;
//                        if(!currentOrderList.isEmpty())
//                            currentOrder = currentOrderList.get(0);
//                        User customer = new Customer(id,name,userName,password,address,orders);
//                        ((Customer)customer).setCurrentOrder(currentOrder);
//                        users.add(customer);
//                    }
//
//
//                    nNode = nNode.getNextSibling().getNextSibling();
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return users;
//    }
//    private List<Menu> nodeToMenus(Node menusNode){
//        List<Menu> menuList = new ArrayList<>();
//        NodeList nList = menusNode.getChildNodes();
//        Map<String,List<String>> menuMap = new HashMap<>();
//        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//                int id = Integer.valueOf(eElement.getAttribute("id"));
//                String menuName = eElement.getElementsByTagName("Name").item(0).getTextContent();
//
//                Node itemsNode = eElement.getElementsByTagName("Items").item(0);
//                menuMap = nodeToMenuItems(menuMap,itemsNode);
//                Menu menu = new Menu(id,menuName,menuMap);
//                menuList.add(menu);
//            }
//        }
//        return menuList;
//    }
//    private Map<String,List<String>> nodeToMenuItems(Map<String,List<String>> menuMap,Node menuItems){
//        NodeList nList = menuItems.getChildNodes();
//        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//            List<String> ingredientsList = new ArrayList<>();
//
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//                int id = Integer.valueOf(eElement.getAttribute("id"));
//                String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
//                String ingredients = eElement.getElementsByTagName("Ingredients").item(0).getTextContent();
//                String[] values = ingredients.split(" ");
//                if(values.length > 0) {
//                    for (String s : values)
//                        try {
//                            ingredientsList.add(s);
//                        } catch (NumberFormatException e) {
//                            ingredientsList = new ArrayList<>();
//                        }
//                }
//                menuMap.put(name,ingredientsList);
//            }
//        }
//        return menuMap;
//    }
//    private  List<Order> nodeToOrders(Node ordersNode) throws ParseException {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        NodeList nList = ordersNode.getChildNodes();
//        List<Order> orders = new ArrayList<>();
//        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//                int id = Integer.valueOf(eElement.getAttribute("id"));
//                String customerName = eElement.getElementsByTagName("CustomerName").item(0).getTextContent();
//                String restaurantName = eElement.getElementsByTagName("RestaurantName").item(0).getTextContent();
//                String address = eElement.getElementsByTagName("Address").item(0).getTextContent();
//                String dateStr = eElement.getElementsByTagName("Date").item(0).getTextContent();
//                Date date = null;
//                if(!dateStr.equals("-"))
//                    date=dateFormat.parse(dateStr);
//                Node foodsNode = eElement.getElementsByTagName("Foods").item(0);
//                List<FoodCostPair> foodCostPairs = nodeToFoods(foodsNode);
//                Order order = new Order(id,address,customerName,restaurantName,foodCostPairs,date);
//                orders.add(order);
//            }
//        }
//        return orders;
//    }
//    private List<FoodCostPair> nodeToFoods(Node foodsNode){
//        List<FoodCostPair> foodCostPairs = new ArrayList<>();
//        NodeList nList = foodsNode.getChildNodes();
//        Map<String,List<String>> menuMap = new HashMap<>();
//        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//                int id = Integer.valueOf(eElement.getAttribute("id"));
//                String cost = eElement.getElementsByTagName("Cost").item(0).getTextContent();
//                String food = eElement.getElementsByTagName("Food").item(0).getTextContent();
//                FoodCostPair foodCostPair = new FoodCostPair(Double.valueOf(cost),food);
//                foodCostPairs.add(foodCostPair);
//            }
//        }
//        return foodCostPairs;
//    }
//
//
//}
