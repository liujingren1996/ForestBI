package org.apache.jsp.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.pentaho.platform.util.messages.LocaleHelper;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.web.jsp.messages.Messages;
import java.util.List;

public final class InitFailure_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
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
      response.setContentType("text/html;");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;


	response.setCharacterEncoding(LocaleHelper.getSystemEncoding());
    response.setHeader("Pragma", "no-cache"); // Set standard HTTP/1.0 no-cache header.
    response.setHeader("Cache-Control", "no-store, no-cache, private, must-revalidate, max-stale=0" );
    response.setHeader("Expires", "0");
  	List initializationErrorMessages = PentahoSystem.getInitializationFailureMessages();

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("  <title>Error Initializing Pentaho</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body bgcolor=\"white\" dir=\"");
      out.print( LocaleHelper.getTextDirection() );
      out.write("\">\r\n");
      out.write("\r\n");
      out.write("  <h2>Pentaho Initialization Exception</h2>\r\n");
      out.write("  <br />\r\n");
      out.write("  <div style='border:2px solid #cccccc'>\r\n");
      out.write("    <table width='100%' border='0'>\r\n");
      out.write("      <tr><td><b>");
      out.print(Messages.getString("InitFailure.USER_ERRORS_DETECTED"));
      out.write("</b></td></tr>\r\n");

  for (int i=0; i<initializationErrorMessages.size(); i++) {

      out.write("\r\n");
      out.write("    <tr><td>");
      out.print(initializationErrorMessages.get(i));
      out.write("</td></tr>\r\n");

  } // end for loop

      out.write("\r\n");
      out.write("    </table>\r\n");
      out.write("    <br />\r\n");
      out.write("      ");
      out.print( Messages.getString("InitFailure.USER_SEE_SERVER_CONSOLE") );
      out.write("\r\n");
      out.write("  </div>\r\n");
      out.write(" </body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
