/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.79
 * Generated at: 2023-09-06 10:18:48 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.account;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import bank_project.myapp.vo.Account;
import bank_project.myapp.dao.AccountDao;
import javax.servlet.ServletContext;

public final class add_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


    public String generateAccountNumber(ServletContext application) {
        try {
            String maxAccNum = ((AccountDao) application.getAttribute("accountDao")).findMaxAccNum();

            if (maxAccNum == null) {
                return "11100001";
            } else {
                int numberPart = Integer.parseInt(maxAccNum.substring(3));
                return "111" + String.format("%05d", numberPart + 1);
            }
        } catch (Exception e) {
            throw new RuntimeException("새 계좌를 발급할 수 없습니다.", e);
        }
    }

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("jar:file:/Users/woon/.gradle/caches/modules-2/files-2.1/jstl/jstl/1.2/74aca283cd4f4b4f3e425f5820cda58f44409547/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("file:/Users/woon/.gradle/caches/modules-2/files-2.1/jstl/jstl/1.2/74aca283cd4f4b4f3e425f5820cda58f44409547/jstl-1.2.jar", Long.valueOf(1692863839065L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("bank_project.myapp.vo.Account");
    _jspx_imports_classes.add("bank_project.myapp.dao.AccountDao");
    _jspx_imports_classes.add("javax.servlet.ServletContext");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      bank_project.myapp.dao.AccountDao accountDao = null;
      synchronized (application) {
        accountDao = (bank_project.myapp.dao.AccountDao) _jspx_page_context.getAttribute("accountDao", javax.servlet.jsp.PageContext.APPLICATION_SCOPE);
        if (accountDao == null){
          throw new java.lang.InstantiationException("bean accountDao not found within scope");
        }
      }
      org.apache.ibatis.session.SqlSessionFactory sqlSessionFactory = null;
      synchronized (application) {
        sqlSessionFactory = (org.apache.ibatis.session.SqlSessionFactory) _jspx_page_context.getAttribute("sqlSessionFactory", javax.servlet.jsp.PageContext.APPLICATION_SCOPE);
        if (sqlSessionFactory == null){
          throw new java.lang.InstantiationException("bean sqlSessionFactory not found within scope");
        }
      }
      bank_project.myapp.vo.Customer loginUser = null;
      synchronized (session) {
        loginUser = (bank_project.myapp.vo.Customer) _jspx_page_context.getAttribute("loginUser", javax.servlet.jsp.PageContext.SESSION_SCOPE);
        if (loginUser == null){
          loginUser = new bank_project.myapp.vo.Customer();
          _jspx_page_context.setAttribute("loginUser", loginUser, javax.servlet.jsp.PageContext.SESSION_SCOPE);
        }
      }

    request.setAttribute("refresh", "1;url=list");

    if (loginUser == null) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }

    Account account = new Account(); // 입력데이터 배열로 저장
    account.setOwner(loginUser);
    account.setAccNum(generateAccountNumber(application)); // Here we generate a new account number.
    account.setPassword(request.getParameter("password"));
    account.setBalance(0);

    accountDao.insert(account);
    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("list.jsp");

    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
