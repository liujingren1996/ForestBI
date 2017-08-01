package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import java.util.*;
import xml.XmlUtil;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public final class query_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

	public Connection getJndiConnection(Context ctx,String jndiName) throws NamingException,SQLException{
		Connection conn=null;
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/"+jndiName);
		conn = ds.getConnection();
		return conn;
	}
	
	public Connection getPentahoConnection(HttpServletRequest request,String pentahoJNDIServlet,String jndi) throws Exception{
		//Document doc = XmlUtil.getDocumentFromUri(pentahoJNDIServlet+"?action=get&jndi="+jndi);
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String src = pentahoJNDIServlet;
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
		
		Document doc = XmlUtil.getNewDoc(xml);
		
		NodeList nl = doc.getElementsByTagName("jndi");

		Connection conn=null;
		if(nl.getLength()>0){
			Node n = nl.item(0);
			//*
			NamedNodeMap nnm = n.getAttributes();
			String driver = nnm.getNamedItem("driverClassName").getNodeValue();
			String url = nnm.getNamedItem("url").getNodeValue();
			String username = nnm.getNamedItem("username").getNodeValue();
			String password = nnm.getNamedItem("password").getNodeValue();
			//*/
			/*
			NodeList dc = doc.getElementsByTagName("driverClassName");
			System.out.println(dc.item(0).getNodeType()+dc.item(0).getNodeValue());
			String driver = doc.getElementsByTagName("driverClassName").item(0).getNodeValue().trim();
			String url = doc.getElementsByTagName("url").item(0).getNodeValue().trim();
			String username = doc.getElementsByTagName("username").item(0).getNodeValue().trim();
			String password = doc.getElementsByTagName("password").item(0).getNodeValue().trim();
			*/
			Class.forName(driver);
      		conn = DriverManager.getConnection(url, username, password);
		}
		return conn;
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
      response.setContentType("text/html;charset=utf-8");
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String type = request.getParameter("type");
String jndi = request.getParameter("jndi");

String sql = new String(request.getParameter("sql").getBytes("iso8859-1"),"utf-8");
String f = request.getParameter("f");
String pentahoJNDIURL = request.getParameter("pentahoJNDIURL");


if(type==null)
	System.out.println("DataSource type is undefined.");

Connection conn=null;
StringBuffer sb = new StringBuffer();
try {
	Context ctx = new InitialContext();
	if(type.equalsIgnoreCase("JNDI")){
		conn = getJndiConnection(ctx,jndi);
	}
	else if(type.equalsIgnoreCase("pentaho")){
		conn = getPentahoConnection(request,pentahoJNDIURL,jndi);
	}
	Statement st = conn.createStatement();
	ResultSet rs = st.executeQuery(sql);
	ResultSetMetaData rsmd = rs.getMetaData(); 
	
	sb.append("{\"metadata\":[");
	for(int i=0;i<rsmd.getColumnCount();i++){
		String column = rsmd.getColumnLabel(i+1);
		int ct = rsmd.getColumnType(i+1);
		int lt = rsmd.getColumnDisplaySize(i+1);
		String dataType = "string";
		if(ct==12){
			dataType = "string";
		}else
			dataType = "double";
		if(i!=0)
			sb.append(",");
		sb.append("{\"name\":\""+column+"\",\"type\":\"xs:"+dataType+"\",\"length\":\""+lt+"\"}");
	}
	sb.append("],\"data\":[");
	
	int m = 0;
	
	while(rs.next()){
		if(m!=0)
			sb.append(",");
		m++;
		sb.append("{");
		for(int i=0;i<rsmd.getColumnCount();i++){
			String column = rsmd.getColumnLabel(i+1);
			String value = rs.getString(i+1);
			if(i!=0)
				sb.append(",");
			sb.append("\""+column+"\":\""+value+"\"");
		}
		sb.append("}");
	}
	sb.append("]}");
   	ctx.close();
} catch(Exception e){
	System.out.println(e.getMessage()+"\n"+sql);
	e.printStackTrace();
	sb.delete(0,sb.length());
	sb.append(XmlUtil.toJSON("<response><error><code>500</code><message>"+e.getMessage()+"</message></error></response>"));
} finally{
	if(conn!=null){
		try {
			conn.close();
		} catch(SQLException e){ }
	}
}
response.setContentType("text/plain");
//System.out.println(sb.toString());
out.print(sb.toString());

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
