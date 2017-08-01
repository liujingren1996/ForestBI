package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.io.*;
import xml.*;

public final class publish_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


	String toUNICODE(String s)
    {
        if(null==s)
            return null;
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)<=256)
            {
                sb.append("\\u00");
            }
            else
            {
                sb.append("\\u");
            }
            sb.append(Integer.toHexString(s.charAt(i)));
        }
        return sb.toString();
    }
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
      response.setContentType("text/html;charset=ISO-8859-1");
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

String pentahoSolutions = request.getParameter("pentahoSolutions");
String dir = request.getParameter("dir");
String publisPath = pentahoSolutions+dir;
String configUrl = request.getParameter("url");
String publishName = request.getParameter("name");
String publishDesc = request.getParameter("desc");
String id = request.getParameter("id");

String xactionTemplate = request.getParameter("xactionTemplate");
//åå»º xactionæä»¶

String src = xactionTemplate;
	src = new String(src.getBytes("iso8859-1"),"utf-8");
	if(src.indexOf("/")==0){
		src = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+src;
	}
	String xml = "";
	boolean ok = true;
	try{
		xml = XmlUtil.readXmlFromUrl(src);
	}catch(java.io.FileNotFoundException ex){
		src = basePath+src;
		try{
			xml = XmlUtil.readXmlFromUrl(src);
		}catch(java.io.FileNotFoundException exx){
			xml = "<response><error><code>404</code><message>æªæ¾å°éç½®æä»¶</message></error></response>";
			ok = false;
		}
	}
	
	if(ok){
		//System.out.println(xml);
		xml = xml.replace("${configFile}", dir+"/"+publishName+id+".xml");
		XmlUtil.writeXml(xml, publisPath+"/"+publishName+id+".xaction");
	}
	
	//åå»ºå¾è¡¨éç½®æä»¶
	src = configUrl;
	src = new String(src.getBytes("iso8859-1"),"utf-8");
	if(src.indexOf("/")==0){
		src = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+src;
	}
	xml = "";
	ok = true;
	try{
		xml = XmlUtil.readXmlFromUrl(src);
	}catch(java.io.FileNotFoundException ex){
		src = basePath+src;
		try{
			xml = XmlUtil.readXmlFromUrl(src);
		}catch(java.io.FileNotFoundException exx){
			xml = "<response><error><code>404</code><message>æªæ¾å°éç½®æä»¶</message></error></response>";
			ok = false;
		}
	}
	System.out.println(xml);
	
	XmlUtil.writeXml(xml, publisPath+"/"+publishName+id+".xml");
	
	//åå»º propertiesæä»¶
	try{
        FileOutputStream fos = new FileOutputStream(publisPath+"/"+publishName+id+".properties");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write("name=");
        bw.write(toUNICODE(publishName));
        bw.write("\ndescription=");
        bw.write(toUNICODE(publishDesc));
        bw.close();
        fos.close();
    }catch(Exception e){
        e.printStackTrace();
    }

	xml = "<result><code>1</code></result>";
	XmlUtil.printXml(xml,out); 
	

      out.write("\r\n");
      out.write("\r\n");
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
