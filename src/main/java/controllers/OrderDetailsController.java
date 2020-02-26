package controllers;

import dao.exceptions.DAOException;
import models.OrderDetails;
import models.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import services.OrderDetailsService;
import services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class to work with OrderDetailsController.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
@WebServlet("/OrderDetailsController")
public class OrderDetailsController extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(OrderDetailsController.class);

    private static final long serialVersionUID = 1L;

    private static final String KEY_ORDER_DETAILS_ID = "orderDetailsId";
    private static final String KEY_ORDER_ID = "orderId";
    private static final String KEY_PRODUCT_ID = "productId";
    private static final String KEY_PRODUCT_NAME = "productName";
    private static final String KEY_PRODUCT_COUNT = "productCount";
    private static final String KEY_PRODUCT_COST = "productCost";


    private OrderDetailsService orderDetailsService;
    private ProductService productService;

    // Writing in JSONArray for response
    private JSONArray orderDetailsListToArray(List<FullOrderDetails> orderDetails) {

        JSONArray array = new JSONArray();

        for (FullOrderDetails orderDetailsElement : orderDetails) {
            JSONObject JSONClient = new JSONObject();
            JSONClient.put(KEY_ORDER_DETAILS_ID, orderDetailsElement.detailsId);
            JSONClient.put(KEY_ORDER_ID, orderDetailsElement.orderId);
            JSONClient.put(KEY_PRODUCT_ID, orderDetailsElement.productId);
            JSONClient.put(KEY_PRODUCT_NAME, orderDetailsElement.name);
            JSONClient.put(KEY_PRODUCT_COUNT, orderDetailsElement.productCount);
            JSONClient.put(KEY_PRODUCT_COST, (orderDetailsElement.price * orderDetailsElement.productCount));
            array.put(JSONClient);
        }
        return array;
    }

    @Override
    public void init() {
        orderDetailsService = new OrderDetailsService();
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String getOrderDetailsByOrderId = "getOrderDetailsByOrderId";

        List<String> queries = HttpQueryUtils.getListQuery(req);
        String commandQuery = queries.get(0);
        String nameQuery = queries.get(1);

        // Get OrderDetails By OrderId in DB
        if (commandQuery.equals(getOrderDetailsByOrderId)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {

                    List<OrderDetails> orderDetailsList = orderDetailsService.getByOrderId(Integer.parseInt(nameQuery));
                    List<FullOrderDetails> fullOrderDetails = createFullOrderDetailsList(orderDetailsList);

                    if (!fullOrderDetails.isEmpty()) {

                        for (FullOrderDetails element : fullOrderDetails) {
                            System.out.println(element.toString());
                        }
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        resp.getWriter().write(orderDetailsListToArray(fullOrderDetails).toString());
                        logger.info("Successful, in method: getOrderDetailsByOrderId, into the class: OrderDetailsController!");
                    } else {
                        resp.setStatus(404);
                    }

                } catch (DAOException e) {
                    logger.error("Error, in method: getOrderDetailsByOrderId, into the class: OrderDetailsController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }
    }

    // Create full OrderDetails list for response
    private List<FullOrderDetails> createFullOrderDetailsList(List<OrderDetails> orderDetailsList) {
        List<FullOrderDetails> fullOrderDetailsList = new ArrayList<FullOrderDetails>();
        List<Integer> productIds = new ArrayList<Integer>();
        for (OrderDetails orderDetails : orderDetailsList) {
            if (!productIds.contains(orderDetails.getProductId())) {

                productIds.add(orderDetails.getProductId()); // add ids into fullDetail list

                FullOrderDetails fullOrderDetails = new FullOrderDetails();
                fullOrderDetails.detailsId = orderDetails.getDetailsId();
                fullOrderDetails.orderId = orderDetails.getOrderId();
                fullOrderDetails.productId = orderDetails.getProductId();
                try {
                    Product product = productService.getProductByID(orderDetails.getProductId());
                    fullOrderDetails.name = product.getName();
                    fullOrderDetails.price = product.getPrice();
                } catch (DAOException e) {
                    logger.error("Error, in method: createFullOrderDetailsList, into the class: OrderDetailsController! \n" + e);
                    fullOrderDetails.name = "unknown"; //default name
                    fullOrderDetails.price = 0.0; //default price
                }
                fullOrderDetailsList.add(fullOrderDetails);
            } else {
                for (FullOrderDetails fullOrderDetails : fullOrderDetailsList) {
                    if (fullOrderDetails.productId.equals(orderDetails.getProductId())) {
                        fullOrderDetails.productCount++;
                    }
                }
            }
        }
        return fullOrderDetailsList;
    }

    // Class for response full OrderDetails
    class FullOrderDetails {
        Integer detailsId;
        Integer orderId;
        Integer productId;
        String name;
        Double price;
        Integer productCount = 1;
    }
}


