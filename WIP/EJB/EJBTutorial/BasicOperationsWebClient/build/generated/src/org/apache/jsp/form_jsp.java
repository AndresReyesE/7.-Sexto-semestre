package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class form_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <title>Calculator</title>\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("    <body bgcolor=\"lightgreen\">\n");
      out.write("        <h1>Basic Operations</h1>\n");
      out.write("        <hr>\n");
      out.write("\n");
      out.write("        <form action=\"Result.jsp\" method=\"POST\">\n");
      out.write("            <p>Enter first value:\n");
      out.write("                <input type=\"text\" name=\"num1\" size=\"25\"></p>\n");
      out.write("            <br>\n");
      out.write("            <p>Enter second value:\n");
      out.write("                <input type=\"text\" name=\"num2\" size=\"25\"></p>\n");
      out.write("            <br>\n");
      out.write("\n");
      out.write("            <b>Select your choice:</b><br>\n");
      out.write("            <input type=\"radio\" name=\"group1\" value =\"add\">Addition<br>\n");
      out.write("            <input type=\"radio\" name=\"group1\" value =\"sub\">Subtraction<br>\n");
      out.write("            <input type=\"radio\" name=\"group1\" value =\"multi\">Multiplication<br>\n");
      out.write("            <input type=\"radio\" name=\"group1\" value =\"div\">Division<br>\n");
      out.write("            <p>\n");
      out.write("                <input type=\"submit\" value=\"Submit\">\n");
      out.write("                <input type=\"reset\" value=\"Reset\"></p>\n");
      out.write("\n");
      out.write("        </form>\n");
      out.write("    </body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
