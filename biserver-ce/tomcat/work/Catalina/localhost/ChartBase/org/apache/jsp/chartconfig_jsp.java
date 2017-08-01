package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;
import java.util.*;
import xml.XmlUtil;
import org.w3c.dom.*;
import org.json.*;

public final class chartconfig_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("\r\n");
      out.write('\r');
      out.write('\n');

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

/*
System.out.println("The Path1 is "+request.getContextPath());
System.out.println("The Path2 is "+request.getRealPath(request.getRequestURI()));
System.out.println("The Path3 is "+new java.io.File(application.getRealPath(request.getRequestURI())).getParent());
System.out.println("The Path4 is "+request.getServletPath());
System.out.println("The Path5 is "+new java.io.File(request.getRealPath(request.getServletPath())).getParent());
System.out.println("The Path6 is "+application.getRealPath(request.getServletPath()));

System.out.println(basePath); 
//*/
/*
String age=request.getParameter("age");

*/
response.setContentType("text/xml");
String action = request.getParameter("action");

//保存图表配置参数
if(action.equalsIgnoreCase("save")){
	String file = request.getParameter("file");
	file = new String(file.getBytes("iso8859-1"),"utf-8");
	String fs = java.io.File.separator;
	String xml = XmlUtil.readXmlFromRequestBody(request);
	String xmlPath = new java.io.File(request.getRealPath(request.getServletPath())).getParent().toString()+fs+file;
	//XmlUtil.writeXml(xml,xmlPath);
	Document doc = XmlUtil.getXmlDocument(xml);
	XmlUtil.save(doc,xmlPath);
	xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	xml += "<response><returnCode>0</returnCode></response>";
	XmlUtil.printXml(xml,out); 
}

else if(action.equalsIgnoreCase("load")){
	String src = request.getParameter("src");
	src = new String(src.getBytes("iso8859-1"),"utf-8");
	if(src.indexOf("/")==0){
		src = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+src;
	}
	String xml = "";
	try{
		xml = XmlUtil.readXmlFromUrl(src);
	}catch(java.io.FileNotFoundException ex){
		src = basePath+src;
		try{
			xml = XmlUtil.readXmlFromUrl(src);
		}catch(java.io.FileNotFoundException exx){
			xml = "<response><error><code>404</code><message>未找到配置文件</message></error></response>";
		}
	}
	String f = request.getParameter("f");
	if(f==null||f.equals("")) f="xml";
	if(f.equalsIgnoreCase("xml"))
		XmlUtil.printXml(xml,out);
	else if(f.equalsIgnoreCase("json")){
		response.setContentType("text/plain");
		//String json = XML.toJSONObject(xml).toString();
		//System.out.println(json);
		String step = (request.getParameter("p")!=null && request.getParameter("p").equalsIgnoreCase("true"))?"    ":null;
		String json = XmlUtil.toJSON(xml,step);
		//System.out.println(json);
		out.print(json);
	}
}

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
