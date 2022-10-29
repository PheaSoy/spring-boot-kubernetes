public void run(javax.servlet.http.HttpServletRequest request) throws ClassNotFoundException {
    String sast = request.getParameter("sast");
    if (this.allowed.contains(sast)) {
        Class clazz = Class.forName(sast);
    }
}
