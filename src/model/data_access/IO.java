package model.data_access;

import model.domain.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.utilities.FoodCostPair;
import model.utilities.ToppingPricePair;
import org.w3c.dom.*;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IO {


    public void outputUsers(List<User> userList){
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElementNS("CENG431", "Users");
            doc.appendChild(rootElement);
            for (User user : userList)
                if(user instanceof Restaurant)
                    rootElement.appendChild(IOParser.toRestaurantXMLNode((Restaurant) user,doc));
                else if(user instanceof Customer)
                    rootElement.appendChild(IOParser.toCustomerXMLNode((Customer) user,doc));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File("users.xml"));
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> inputUsers(){
        List<User> users = new ArrayList<>();
        try {
            File inputFile = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            // doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("User");

            Node nNode = nList.item(0);
            int length = nNode.getChildNodes().getLength();
            for (int i = 0; i < length; i++) {
                if (nNode != null && nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int id = Integer.valueOf(eElement.getAttribute("id"));
                    String type = eElement.getElementsByTagName("Type").item(0).getTextContent();
                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    String userName = eElement.getElementsByTagName("UserName").item(0).getTextContent();
                    String password = eElement.getElementsByTagName("Password").item(0).getTextContent();
                    String address = eElement.getElementsByTagName("Address").item(0).getTextContent();
                    Node ordersNode = eElement.getElementsByTagName("Orders").item(0);
                    List<Order> orders = IOParser.nodeToOrders(ordersNode);
                    if(type.equalsIgnoreCase("restaurant")){
                        Node menusNode = eElement.getElementsByTagName("Menus").item(0);

                        List<Menu> menus = IOParser.nodeToMenus(menusNode);
                        User restaurant = new Restaurant(id,name,userName,password,address,orders,menus);
                        users.add(restaurant);
                    }
                    else if(type.equalsIgnoreCase("customer")){
                        Node currentOrderNode = eElement.getElementsByTagName("CurrentOrder").item(0);
                        List<Order> currentOrderList = IOParser.nodeToOrders(currentOrderNode);
                        Order currentOrder = null;
                        if(!currentOrderList.isEmpty())
                            currentOrder = currentOrderList.get(0);
                        User customer = new Customer(id,name,userName,password,address,orders);
                        ((Customer)customer).setCurrentOrder(currentOrder);
                        users.add(customer);
                    }


                    nNode = nNode.getNextSibling().getNextSibling();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

}
