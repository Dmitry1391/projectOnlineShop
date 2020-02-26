package controllers;

import dao.exceptions.DAOException;
import models.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
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
 * This class to work with ProductController.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
@WebServlet("/ProductController")
public class ProductController extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(ProductController.class);

    private static final long serialVersionUID = 1L;

    private static final String KEY_PRODUCT_ID = "id";
    private static final String KEY_PRODUCT_NAME = "name";
    private static final String KEY_PRODUCT_PRICE = "price";

    private ProductService productService;

    // Writing in JSONArray for response
    private JSONArray productListToArray(List<Product> products) {

        JSONArray array = new JSONArray();

        for (Product product : products) {
            JSONObject JSONProduct = new JSONObject();
            JSONProduct.put(KEY_PRODUCT_ID, product.getId());
            JSONProduct.put(KEY_PRODUCT_NAME, product.getName());
            JSONProduct.put(KEY_PRODUCT_PRICE, product.getPrice());
            array.put(JSONProduct);
        }
        return array;
    }

    @Override
    public void init() {
        productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String getProductByID = "getProductByID";
        String create = "create";
        String update = "updateProduct";
        String delete = "delete";
        String getAll = "getAllProducts";

        Product product;

        List<String> queries = HttpQueryUtils.getListQuery(req);

        String commandQuery = queries.get(0);
        String nameQuery = queries.get(1);

        // Get product by id in DB
        if (getProductByID.equals(commandQuery)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {

                try {
                    product = productService.getProductByID(Integer.parseInt(nameQuery));
                    List<Product> products = new ArrayList<Product>();
                    if (product != null) {
                        products.add(product);
                        resp.setStatus(200);
                        resp.setContentType("application/json");
                        resp.getWriter().write(productListToArray(products).toString());
                        logger.info("Successful, in method: getProductByID, into the class: ProductController!");
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getProductByID, into the class: ProductController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Get all products in DB
        if (getAll.equals(commandQuery)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    List<Product> products = productService.getAll();

                    if (!products.isEmpty()) {
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        resp.getWriter().write(productListToArray(products).toString());
                        logger.info("Successful, in method: getAll, into the class: ProductController!");
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getAll, into the class: ProductController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Create product in DB
        if (create.equals(commandQuery)) {

            String priceQuery = queries.get(2);

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);
            System.out.println("queryPrice = " + priceQuery);

            if ((nameQuery != null && !nameQuery.isEmpty()) && (priceQuery != null && !priceQuery.isEmpty())) {
                try {
                    productService.create(new Product(nameQuery, Double.valueOf(priceQuery)));
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: create, into the class: ProductController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: create, into the class: ProductController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Update product in DB
        if (update.equals(commandQuery)) {

            String queryId = queries.get(1);
            String queryNewName = queries.get(2);
            String queryNewPrice = queries.get(3);

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("queryId = " + queryId);
            System.out.println("queryNewName = " + queryNewName);
            System.out.println("queryNewPrice = " + queryNewPrice);

            if ((queryId != null && !queryId.isEmpty()) && (queryNewName != null && !queryNewName.isEmpty()) && (queryNewPrice != null && !queryNewPrice.isEmpty())) {
                try {
                    product = productService.getProductByID(Integer.parseInt(queryId));
                    productService.update(product, new Product(Integer.parseInt(queryId), queryNewName, Double.parseDouble(queryNewPrice)));
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: update, into the class: ProductController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: update, into the class: ProductController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Delete product in DB
        if (delete.equals(commandQuery)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQueryId = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    product = productService.getProductByID(Integer.parseInt(nameQuery));
                    productService.delete(product);
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: delete, into the class: ProductController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: delete, into the class: ProductController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }
    }
}

