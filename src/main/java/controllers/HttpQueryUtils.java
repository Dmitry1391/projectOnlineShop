package controllers;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * This class to work with HttpQueryUtils.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
public class HttpQueryUtils {

    static List<String> getListQuery(HttpServletRequest req) throws UnsupportedEncodingException {
        String queryStr = URLDecoder.decode(req.getQueryString(),"UTF-8");
        List<String> query = new ArrayList<String>();
        if (queryStr == null || queryStr.isEmpty()) {
            query.add("");
            query.add("");
            return query;
        }

        String[] queryPairsStr = queryStr.split("&");
        for (String str : queryPairsStr) {
            String[] queryMas = str.split("=");
            if (queryMas.length >= 2) {
                query.add(queryMas[1]);
            } else {
                query.add("");
            }
        }
        return query;
    }
}
