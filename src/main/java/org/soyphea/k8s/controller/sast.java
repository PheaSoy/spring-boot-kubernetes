public void run(javax.servlet.http.HttpServletRequest request) throws ClassNotFoundException {
    String name = request.getParameter("name");
    if (this.allowed.contains(name)) {
        Class clazz = Class.forName(name);
    }
}
