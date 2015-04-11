package org.esmerilprogramming.overtown.management;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.esmerilprogramming.overtown.annotation.BeforeTranslate;
import org.esmerilprogramming.overtown.annotation.Controller;
import org.esmerilprogramming.overtown.annotation.Converter;
import org.esmerilprogramming.overtown.annotation.Page;
import org.esmerilprogramming.overtown.annotation.path.Get;
import org.esmerilprogramming.overtown.annotation.path.Post;
import org.esmerilprogramming.overtown.http.OvertownRequest;
import org.esmerilprogramming.overtown.http.OvertownSession;
import org.esmerilprogramming.overtown.http.JsonResponse;
import org.esmerilprogramming.overtown.management.converters.DateConverter;
import org.esmerilprogramming.overtown.management.converters.ServerStatusConverter;
import org.esmerilprogramming.overtown.management.model.ServerStatus;
import org.esmerilprogramming.overtown.view.ViewAttributes;

import java.util.Date;

@Controller(path = "/management")
public class ManagementPage {

  public ManagementPage() {}

  @BeforeTranslate
  public void doSomething( OvertownRequest request ) {
    System.out.println("SOMETHING BEFORE TRANSLATE");
    request.addAttribute("name", "Efraim Gentil");
  }

  @Get(value = "" , template = "home.ftl")
  public void index() {

  }
  @Get(value = "error" , template = "home.ftl")
  public void index(OvertownRequest request) {
    int i = 20/0;
  }
  
  @Page(value = "ws" , responseTemplate = "form.ftl")
  public void wsTeste() {
    
  }
  
  @Page("teste")
  public void teste(String nomeDaString, OvertownRequest request) {
    System.out.println(nomeDaString);
  }

  @Page("testeInteger")
  public void teste(Integer id, OvertownRequest request) {
    System.out.println("Teste integer");
    System.out.println(id);
  }

  @Page("testeDouble")
  public void teste(Double val) {
    System.out.println("Teste double");
    System.out.println(val);
  }

  @Page("testeLong")
  public void teste(Long val) {
    System.out.println("Teste Long");
    System.out.println(val);
    throw new RuntimeException();
  }

  @Page("teste2")
  public void teste2(HttpServerExchange exchange, ServerStatus serverStatus) {
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

    System.out.println(serverStatus);

    exchange.getResponseSender().send(
        "<form method='post' >"
            + "<input name='serverStatus.host' />"
            + "<input name='serverStatus.port' />"
                + "<input name='serverStatus.id' />"
                + "<button type='submit'>Submit</button>"
        + "</form>");
  }

  @Page("teste3")
  public void teste3(HttpServerExchange exchange, ServerStatus server) {
    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

    System.out.println(server);

    exchange.getResponseSender().send(
        "<form method='post' >" + "<input name='serverStatus.host' />"
            + "<input name='serverStatus.port' />" + "<button type='submit'>Submit</button>"
            + "</form>");
  }
  
  @Page("teste4")
  public void teste3(Integer x , HttpServerExchange exchange) {
    System.out.println( "Teste: " + x);
    exchange.getResponseSender().send("This is your x: " + x );
  }

  @Post(value = "testeTemplate", template = "methodNotAllowed.ftl")
  //@Page(value = "testeTemplate", responseTemplate = "teste.ftl")
  public void testeTemplate(OvertownRequest request) {
//    request.addAttribute("name", "Efraim Gentil");
    OvertownSession session = request.getSession();
    System.out.println( session.getAttribute("nome") );
    
  }
  
  @Page(value = "nameInSession")
  public void testeTemplate(OvertownRequest request , String nome) {
    OvertownSession session = request.getSession();
    if(session.getAttribute("nome") == null ){  
      session.setAttribute("nome", nome );
      session.setAttribute("int", 10 );
    }else{
      System.out.println( session.getAttribute("nome") );
    }
  }
  
  @Page(value = "logout")
  public void logout(OvertownRequest request , String nome) {
    OvertownSession session = request.getSession();
    session.destroy();
  }
  
  
  @Page(value = "newSession")
  public void testeTemplateT(OvertownRequest request , String nome) {
    OvertownSession session = request.createSession();
    session.setAttribute("nome", "TESTE HIHE HE" );
    session.setAttribute("int", 9999 );
  }
  
  @Page(value = "testeTemplate2", responseTemplate = "teste.ftl")
  public void testeTemplate(ViewAttributes attributes) {
    attributes.add("name", "Efraim Gentil");
  }

  @Page("testePrimitivo")
  public void testePrimitivo(int i, long l) {
    System.out.println(i);
    System.out.println(l);
  }
  
  @Page("dateConvert")
  public void dateConvert(@Converter(DateConverter.class) Date dataPagina){
    System.out.println( dataPagina );
  }
  
  @Page("objectConvert")
  public void objectConvert(@Converter(ServerStatusConverter.class) ServerStatus serverStatus ){
    System.out.println( serverStatus );
  }
  
  @Converter(value = ServerStatusConverter.class)
  @Page("objectConvert2")
  public void objectConvert2(ServerStatus serverStatus ){
    System.out.println( serverStatus );
  }
  
  @Page("testeResponse")
  public void dateConvert(HttpServerExchange exchange){
//    Response r = new Response(exchange) {
//      @Override
//      protected void sendAsResponse(ByteBuffer buffer) {
//        // TODO Auto-generated method stub
//      }
//    };
//    System.out.println(" LOL ");
//    r.sendRedirect("http://127.0.0.1:8080/management/testeTemplate");
  }
  
  @Page("notFound")
  public void asdasda(HttpServerExchange exchange){
//    Response r = new Response(exchange) {
//      @Override
//      protected void sendAsResponse(ByteBuffer buffer) {
//        // TODO Auto-generated method stub
//      }
//    };
//    r.sendError(StatusError.NOT_FOUND);
  }
  
  @Page("json")
  public void respondJson(OvertownRequest request){
    JsonResponse jsonResponse = request.getJsonResponse();
    jsonResponse.setCharset("UTF-8");
    jsonResponse.sendAsResponse("{ \"name\" : \"Efraim Gentil\" , \"blah\" : \"çãoéàè\" }");
  }
  
  @Page("jso2")
  public void respondJson(JsonResponse response){
    response.sendAsResponse("{ \"name\" : \"Efraim Gentil\" , \"blah\" : \"çãoéàè\" }");
  }
  
  @Page("json3")
  public void respondJson3(JsonResponse response){
    response.addAttribute("name", "Efraim Gentil");
    response.addAttribute("age", 26);
    response.addAttribute("blah", "çãoéàè");
  }

}

