package org.esmerilprogramming.overtown.http;

import io.undertow.io.Sender;
import io.undertow.server.HttpServerExchange;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 02/03/15.
 */
public class DefaultMethodNotAllowedPage implements ErrorHandler {
  @Override
  public void handleError(OvertownRequest request) {
    HttpServerExchange exchange = request.getExchange();
    exchange.setResponseCode(StatusError.METHOD_NOT_ALLOWED.getCode());
    StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    sb.append("<head>");
    sb.append("<title>405 Method Not Allowed</title>");
    sb.append("</head>");
    sb.append("<body>");
    sb.append("<h1>405 - Method Not Allowed</h1>");
    sb.append("<p>Request method '" + exchange.getRequestMethod().toString().toUpperCase() + "' is not allowed for this path</p>");
    sb.append("</body>");
    sb.append("</html>");

    Sender rs = exchange.getResponseSender();
    rs.send( sb.toString() );
    rs.close();
  }
}
