package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import sun.awt.image.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.text.*;
import java.util.*;
import java.io.*;
import xml.XmlUtil;
import org.w3c.dom.*;
import org.json.*;

public final class fileinfo_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


	String toBase64(RenderedImage img){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		String icontxt = "";
		try{
			ImageIO.write(img,"png",bos);
			byte[] bytes = bos.toByteArray();
			BASE64Encoder be = new BASE64Encoder();
			icontxt = be.encode(bytes);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}finally{
			try{
				bos.close();
			}catch(IOException ioe){
				;
			}
		}
		return icontxt;			
	}
	
	String getFileInfo(File f,final String filter){
		String xml = "";
		FileSystemView fsv=new JFileChooser().getFileSystemView();
		
		if(f.isDirectory()){
			xml+="<dir name=\""+f.getName()+"\">";
			
			//java.io.FileFilter filter = new java.io.FileFilter();
			
			File[] fs = null;
			if(filter.equalsIgnoreCase("*.*")){
				fs = f.listFiles();
			}else{
				fs = f.listFiles(new java.io.FileFilter(){
					public boolean accept(File pathname) {
						String suffix = filter.substring(filter.lastIndexOf(".")+1).toLowerCase();
						String fn = pathname.getName().toLowerCase();
						return(pathname.isFile() && (fn.lastIndexOf(suffix)== fn.length()-suffix.length()));
					}
				});
			}
			
			for(int i=0;i<fs.length;i++){
				if(fs[i].isDirectory()) continue;
				String fname = fs[i].getName();
				String icontxt = "";
				Icon icon=fsv.getSystemIcon(fs[i]);
				ImageIcon imgicon = (ImageIcon)icon;
				
				if(!(imgicon.getImage() instanceof sun.awt.image.ToolkitImage)){
					RenderedImage img = (RenderedImage)imgicon.getImage();
					icontxt = toBase64(img);
				}else{
					sun.awt.image.ToolkitImage timg = (sun.awt.image.ToolkitImage)imgicon.getImage() ;
					BufferedImage img = timg.getBufferedImage();
					icontxt = toBase64(img);
					//System.out.println("icon faile: "+f.getName()+"\t"+timg);
				}
				//BufferedImage img = (BufferedImage)(imgicon.getImage());
				//IntegerInterleavedRaster raster = (IntegerInterleavedRaster)(img.getRaster());
				//System.out.println(raster);
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				String date = df.format(new Date(fs[i].lastModified()));
				String type = fsv.getSystemTypeDescription(fs[i]);
				xml+="<file name=\""+fname+"\" lastModified=\""+ date +"\" type=\""+type+"\" icon=\""+icontxt+"\"/>";
			}
			xml+="</dir>";
		}
		return xml;
	}

	String getDirInfo(File f){
		String xml = "";
		if(!f.exists()){
			xml+="<dir name=\""+f.getName()+"\" exists=\"false\" />";
		}
		else if(f.isDirectory()){
			xml+="<dir name=\""+f.getName()+"\" >";
			File[] fs = f.listFiles();
			for(int i=0;i<fs.length;i++){
				xml += getDirInfo(fs[i]);
			}
			xml+="</dir>";
		}
		return xml;
	}
	
	String getTree(File f){
		StringBuilder xml = new StringBuilder();
		_getTree(f,xml);
		return xml.toString();
	}
	
	void _getTree(File f,StringBuilder xml){
		String fn = f.getName();
		String lb = fn;
		if(fn.lastIndexOf(".")!=-1)
			lb = fn.substring(0,fn.lastIndexOf("."));
		if(lb.length()==0)
			lb = fn.substring(1);
		
		xml.append("<file name=\""+fn+"\" label=\""+lb+"\" isdir=\""+f.isDirectory()+"\">");
		if(f.isDirectory()){
			File[] files = f.listFiles();
			for(int i=0;i<files.length;i++){
				File nf = files[i];
				if(nf.isDirectory())
					_getTree(nf,xml);
			}
			for(int i=0;i<files.length;i++){
				File nf = files[i];
				if(!nf.isDirectory())
					_getTree(nf,xml);
			}
		}
		xml.append("</file>");
	}
	
	void deleteFile(File f){
		try{
			if(f.exists()){
				File[] fs = f.listFiles();
				if(fs!=null){
					for(int i=fs.length-1;i>-1;i--){
						if(fs[i].isFile())
							fs[i].delete();
					}
				}
				fs = f.listFiles();
				if(fs!=null){
					for(int i=fs.length-1;i>-1;i--){
						if(fs[i].isDirectory())
							deleteFile(fs[i]);
					}
				}
			}
			f.delete();
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;



	String action = request.getParameter("action");
	String type = request.getParameter("type");
	String path = request.getParameter("path");
	String name = request.getParameter("name");
	String filter = request.getParameter("filter");
	
	if(name!=null)
		name = new String(name.getBytes("iso8859-1"),"utf-8");
	if(path!=null)
		path = new String(path.getBytes("iso8859-1"),"utf-8");
	if(filter!=null)
		filter = new String(filter.getBytes("iso8859-1"),"utf-8");
	
		
		
	
	if(path==null) path="";
	if(type==null) type="dir";
	
	String fs = File.separator;
	if(action.equals("list")){
		path = path.replace("/",fs);
		path = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path;
		//System.out.println("list: "+path);
		File f = new File(path);
		String xml = "";
		if(type.equals("dir"))
			xml = getDirInfo(f);
		else if(type.equals("tree"))
			xml = getTree(f);
		else
			xml = getFileInfo(f,filter);
		//System.out.println(xml);
		response.setContentType("text/xml");
		XmlUtil.printXml(xml,out);
	}
	else if(action.equals("create") && type.equals("dir")){
		path = path.replace("/",fs);
		String name2 = name;
		if(path.lastIndexOf(fs)!=path.length()-1){
			name2 = fs+name;
		}
		path = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+name2;
		File f = new File(path);
		boolean ok = false;
		String xml = "";
		if(!f.exists()){
			ok = f.mkdir();
			if(ok)
				xml = "<dir name=\""+name+"\" status=\"created\" code=\"0\" />";
			else
				xml = "<dir name=\""+name+"\" status=\"create failed\" code=\"1\" />";
		}
		else if(f.isDirectory())
			xml = "<dir name=\""+name+"\" status=\"directory exists\" code=\"2\" />";
		else
			xml = "<dir name=\""+name+"\" status=\"file exists\" code=\"3\" />";
			
		response.setContentType("text/xml");
		XmlUtil.printXml(xml,out);
	}
	
	else if(action.equals("rename") && type.equals("dir")){
		path = path.replace("/",fs);
		String oldName = request.getParameter("oldName");;
		if(oldName!=null)
			oldName = new String(oldName.getBytes("iso8859-1"),"utf-8");
		String newName = request.getParameter("newName");;
		if(newName!=null)
			newName = new String(newName.getBytes("iso8859-1"),"utf-8");
		//String oldName2 = oldName;

		
		String path1 = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+oldName;
		String path2 = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+newName;
		
		if(path.lastIndexOf(fs)!=path.length()-1){
			path1 = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+fs+oldName;
			path2 = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+fs+newName;
		}
		
		
		File f = new File(path1);
		File f2 = new File(path2);
		boolean ok = false;
		String xml = "";
		boolean err = false;
		if(!f.exists()){ //更名目录不存在  
			xml = "<dir name=\""+name+"\" status=\"directory not exists\" code=\"1\" />";
			err = true;
		}
		else if(!f.isDirectory()){ //更名目录不是目录
			xml = "<dir name=\""+name+"\" status=\"is not a directory\" code=\"2\" />";
			err = true;
		}
		else if(f2.exists()&&f2.isDirectory()){//目标目录已存在
			//&& !f.getName().equalsIgnoreCase(f2.getName())
			String f2name = f2.getName();
			File[] list = f2.getParentFile().listFiles();
			for(int i=0;i<list.length;i++){
				File lf = list[i];
				if(lf.isDirectory() && lf.getName().equals(f2.getName())){
					xml = "<dir name=\""+name+"\" status=\"target directory "+f2name+" already exists\" code=\"3\" />";
					err = true;
					break;
				}
			}
		}
		if(!err){
			ok = f.renameTo(f2);
			if(ok){
				xml = "<dir name=\""+name+"\" status=\"directory renamed\" code=\"0\" />";
			}else{
				xml = "<dir name=\""+name+"\" status=\"directory renamed failed\" code=\"4\" />";
			}
		}
		response.setContentType("text/xml");
		XmlUtil.printXml(xml,out);
	}
	
	else if(action.equals("rename") && type.equals("file")){
		path = path.replace("/",fs);
		String oldName = request.getParameter("oldName");;
		if(oldName!=null)
			oldName = new String(oldName.getBytes("iso8859-1"),"utf-8");
		String newName = request.getParameter("newName");;
		if(newName!=null)
			newName = new String(newName.getBytes("iso8859-1"),"utf-8");
		//String oldName2 = oldName;

		
		String path1 = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+oldName;
		String path2 = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+newName;
		
		if(path.lastIndexOf(fs)!=path.length()-1){
			path1 = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+fs+oldName;
			path2 = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+fs+newName;
		}
		
		
		File f = new File(path1);
		File f2 = new File(path2);
		boolean ok = false;
		String xml = "";
		boolean err = false;
		if(!f.exists()){ //更名文件不存在
			xml = "<file name=\""+name+"\" status=\"file not exists\" code=\"1\" />";
			err = true;
		}
		else if(!f.isFile()){ //更名文件不是文件
			xml = "<file name=\""+name+"\" status=\"is not a file\" code=\"2\" />";
			err = true;
		}
		else if(f2.exists()&&f2.isFile()){//目标文件已存在
			//&& !f.getName().equalsIgnoreCase(f2.getName())
			String f2name = f2.getName();
			File[] list = f2.getParentFile().listFiles();
			for(int i=0;i<list.length;i++){
				File lf = list[i];
				if(lf.isFile() && lf.getName().equals(f2.getName())){
					xml = "<file name=\""+name+"\" status=\"target file "+f2name+" already exists\" code=\"3\" />";
					err = true;
					break;
				}
			}
		}
		if(!err){
			ok = f.renameTo(f2);
			if(ok){
				xml = "<file name=\""+name+"\" status=\"file renamed\" code=\"0\" />";
			}else{
				xml = "<file name=\""+name+"\" status=\"file renamed failed\" code=\"4\" />";
			}
		}
		response.setContentType("text/xml");
		XmlUtil.printXml(xml,out);
	}
	else if(action.equals("delete")){
		path = path.replace("/",fs);
		if(type.equals("file")){
			String name2 = name;
			if(path.lastIndexOf(fs)!=path.length()-1){
				name2 = fs+name;
			}
			path = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path+name2;
		}else{
			path = new java.io.File(request.getRealPath(request.getServletPath())).getParent()+fs+path;
		}
		File f = new File(path);
		boolean ok = false;
		String xml = "";
		if(f.exists()){
			deleteFile(f);
			xml = "<file status=\"deleted\" code=\"0\" />";
		}
		else
			xml = "<file status=\"file not exists\" code=\"1\" />";
			
		response.setContentType("text/xml");
		XmlUtil.printXml(xml,out);
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
