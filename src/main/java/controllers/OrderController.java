package controllers;

import dao.exceptions.DAOException;
import models.Client;
import models.Order;
import models.OrderDetails;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import services.ClientService;
import services.OrderDetailsService;
import services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class to work with OrderController.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
@WebServlet("/OrderController")
public class OrderController extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(OrderController.class);

    private static final long serialVersionUID = 1L;

    private static final String KEY_ORDER_ID = "orderId";
    private static final String KEY_CLIENT_ID = "clientId";
    private static final String KEY_ORDER_STATUS = "orderStatus";
    private static final String KEY_ORDER_COST = "totalCost";

    private OrderService orderService;
    private ClientService clientService;
    private OrderDetailsService orderDetailsService;

    // Writing in JSONArray for response
    private JSONArray orderListToArray(List<Order> orders) {

        JSONArray array = new JSONArray();

        for (Order order : orders) {
            JSONObject JSONClient = new JSONObject();
            JSONClient.put(KEY_ORDER_ID, order.getOrderId());
            JSONClient.put(KEY_CLIENT_ID, order.getClientId());
            JSONClient.put(KEY_ORDER_STATUS, order.getOrderStatus());
            JSONClient.put(KEY_ORDER_COST, order.getTotalCost());
            array.put(JSONClient);
        }
        return array;
    }

    @Override
    public void init() {
        clientService = new ClientService();
        orderService = new OrderService();
        orderDetailsService = new OrderDetailsService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String getAllOrders = "getAllOrders";
        String deleteOrder = "deleteOrder";
        String updateOrderByID = "updateOrderByID";
        String createOrder = "createOrder";
        String getOrderByID = "getOrderByID";
        String getAllByOrderStatus = "getAllByOrderStatus";
        String getAllOrdersByClient = "getAllOrdersByClient";
        String getAllOrdersByClientID = "getAllOrdersByClientID";

        List<String> queries = HttpQueryUtils.getListQuery(req);
        String commandQuery = queries.get(0);
        String nameQuery = queries.get(1);

        Order order;
        Client client;

        //  Get all orders by client in DB
        if (commandQuery.equals(getAllOrdersByClient)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    client = clientService.getByLogin(nameQuery);
                    List<Order> orders = orderService.getAllByClient(client.getClientId());

                    if (!orders.isEmpty()) {
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        resp.getWriter().write(orderListToArray(orders).toString());
                        logger.info("Successful, in method: getAllOrdersByClient, into the class: OrderController!");
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getAllOrdersByClient, into the class: OrderController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Update Order By ID in DB
        if (commandQuery.equals(updateOrderByID)) {

            String queryNewStatus = queries.get(2);

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("orderId = " + nameQuery);
            System.out.println("queryNewStatus = " + queryNewStatus);

            if ((nameQuery != null && !nameQuery.isEmpty()) && (queryNewStatus != null && !queryNewStatus.isEmpty())) {
                try {
                    order = orderService.getOrderByID(Integer.parseInt(nameQuery));
                    orderService.update(order, new Order(order.getOrderId(), order.getClientId(), queryNewStatus, order.getTotalCost()));
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: updateOrderByID, into the class: OrderController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: updateOrderByID, into the class: OrderController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        //  Get Order By orderID in DB
        if (commandQuery.equals(getOrderByID)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("orderId = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    order = orderService.getOrderByID(Integer.parseInt(nameQuery));
                    List<Order> orders = new ArrayList<Order>();
                    if (order != null) {
                        orders.add(order);
                        resp.setStatus(200);
                        resp.setContentType("application/json");
                        resp.getWriter().write(orderListToArray(orders).toString());
                        logger.info("Successful, in method: getOrderByID, into the class: OrderController!");
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getOrderByID, into the class: OrderController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        //  Get Order By clientId in DB
        if (commandQuery.equals(getAllOrdersByClientID)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("clientId = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    List<Order> orders = orderService.getAllByClient(Integer.parseInt(nameQuery));
                    if (!orders.isEmpty()) {
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        logger.info("Successful, in method: getAllOrdersByClientID, into the class: OrderController!");
                        resp.getWriter().write(orderListToArray(orders).toString());
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getAllOrdersByClientID, into the class: OrderController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        //  Get all Orders in DB
        if (commandQuery.equals(getAllOrders)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    List<Order> orders = orderService.getAll();
                    if (!orders.isEmpty()) {
                        logger.info("Successful, in method: getAllOrders, into the class: OrderController!");
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        resp.getWriter().write(orderListToArray(orders).toString());
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getAllOrders, into the class: OrderController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }


        //  Get all Orders by status in DB
        if (commandQuery.equals(getAllByOrderStatus)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("status = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    List<Order> orders = orderService.getAllByOrderStatus(nameQuery);
                    if (!orders.isEmpty()) {
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        resp.getWriter().write(orderListToArray(orders).toString());
                        logger.info("Successful, in method: getAllByOrderStatus, into the class: OrderController!");
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getAllByOrderStatus, into the class: OrderController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Delete Order in DB
        if (commandQuery.equals(deleteOrder)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("orderId = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    order = orderService.getOrderByID(Integer.parseInt(nameQuery));
                    orderService.delete(order);
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: deleteOrder, into the class: OrderController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: deleteOrder, into the class: OrderController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Create order in DB
        if (commandQuery.equals(createOrder)) {

            String queryStatus = queries.get(2);
            double queryTotalCost = Double.parseDouble(queries.get(3));

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("clientLogin = " + nameQuery);
            System.out.println("queryStatus = " + queryStatus);
            System.out.println("queryTotalCost = " + queryTotalCost);

            //Парсим JSON приходящий из body
            Map<String, Object> counts;

            StringBuffer jsonBody = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null) {
                    jsonBody.append(line);
                }
            } catch (Exception e) {
                logger.error("Error, in method: createOrder, into the class: OrderController! \n" + e);
            }
            //

            if ((nameQuery != null && !nameQuery.isEmpty()) && (queryStatus != null && !queryStatus.isEmpty()) && (queryTotalCost != 0)) {
                try {
                    int clientID = clientService.getByLogin(nameQuery).getClientId();
                    orderService.create(new Order(clientID, queryStatus, queryTotalCost));

                    //Получение ID только что созданного заказа
                    List<Order> orderList = orderService.getAllByClient(clientID);
                    int lastOrderID = 0;
                    for (Order el : orderList) {
                        if (el.getOrderId() > lastOrderID) {
                            lastOrderID = el.getOrderId();
                        }
                    }
                    System.out.println("lastOrderID = " + lastOrderID);
                    //

                    if (!jsonBody.toString().isEmpty()) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonBody.toString());
                            JSONObject counterStr = new JSONObject(jsonObject.getJSONObject("cart").toString());
                            counts = counterStr.toMap();
                            System.out.println("JSON = " + jsonBody.toString());

                            // Создание деталей заказа в БД
                            for (Map.Entry<String, Object> entry : counts.entrySet()) {
                                int productID = Integer.parseInt(entry.getKey());
                                int countProduct = (Integer) entry.getValue();
                                System.out.println("productID = " + productID + "  countProduct = " + countProduct);
                                for (int i = 0; i < countProduct; i++) {
                                    orderDetailsService.create(new OrderDetails(lastOrderID, productID));
                                }
                            }
                            //

                        } catch (JSONException e) {
                            logger.error("Error parsing JSON request string, in method: createOrder, into the class: OrderController! \n" + e);
                        }
                    }
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: createOrder, into the class: OrderController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: createOrder, into the class: OrderController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }
    }
}
