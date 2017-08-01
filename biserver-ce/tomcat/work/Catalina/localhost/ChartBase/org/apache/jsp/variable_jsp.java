package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import xml.XmlUtil;

public final class variable_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;


	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	String xml = "\n<item name=\"变量\" type=\"catalog\">\n<item name=\"系统变量\" type=\"systemCatalog\">\n";
	
	Date data = new Date();
	Date yesterday = new Date(System.currentTimeMillis() - 24*60*60*1000);
	
	//Properties p = new Properties();
	//p.put("YEAR",Calendar.getInstance().YEAR+"");
	/*
	p.put("DATE",data.toLocaleString().split(" ")[0]);
	p.put("DATE_TIME",data.toLocaleString());
	p.put("YEAR",(data.getYear()+1900)+"");
	p.put("MONTH",(data.getMonth()+1)+"");
	p.put("DAY_OF_MONTH",data.getDate()+"");
	p.put("YESTERDAY",yesterday.toLocaleString().split(" ")[0]);
	p.put("YESTERDAY_MONTH",(yesterday.getMonth()+1)+"");
	
	Enumeration<Object> e = p.keys();
	while(e.hasMoreElements()){
		String key = e.nextElement().toString();
		String val = p.getProperty(key);
		xml += "<item name=\""+key+"\" value=\""+val+"\"/>\n";
	}
	*/
	
	xml += "<item name=\"DATE\" type=\"String\" value=\""+data.toLocaleString().split(" ")[0]+"\"/>\n";
	xml += "<item name=\"DATE_TIME\" type=\"String\" value=\""+data.toLocaleString()+"\"/>\n";
	xml += "<item name=\"YEAR\" type=\"Number\" value=\""+(data.getYear()+1900)+"\"/>\n";
	xml += "<item name=\"MONTH\" type=\"Number\" value=\""+(data.getMonth()+1)+"\"/>\n";
	xml += "<item name=\"DAY_OF_MONTH\" type=\"Number\" value=\""+data.getDate()+"\"/>\n";
	xml += "<item name=\"DAY_OF_WEEK\" type=\"Number\" value=\""+data.getDay()+"\"/>\n";
	xml += "<item name=\"QUARTER\" type=\"Number\" value=\""+((data.getMonth()/3+1))+"\"/>\n";
	xml += "<item name=\"YESTERDAY\" type=\"String\" value=\""+yesterday.toLocaleString().split(" ")[0]+"\"/>\n";
	xml += "<item name=\"YESTERDAY_YEAR\" type=\"Number\" value=\""+(yesterday.getYear()+1900)+"\"/>\n";
	xml += "<item name=\"YESTERDAY_MONTH\" type=\"Number\" value=\""+(yesterday.getMonth()+1)+"\"/>\n";
	xml += "<item name=\"YESTERDAY_QUARTER\" type=\"Number\" value=\""+((yesterday.getMonth()/3+1))+"\"/>\n";
	xml += "</item>\n";
	xml += "<item name=\"全局变量\" type=\"globalCatalog\"/>";
	xml += "<item name=\"图表变量\" type=\"chartCatalog\"/>";
	xml += "</item>\n";
	response.setContentType("text/xml");
	XmlUtil.printXml(xml,out);

      out.write('\r');
      out.write('\n');
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
