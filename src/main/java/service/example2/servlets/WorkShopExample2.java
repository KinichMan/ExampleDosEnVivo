/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.example2.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.example2.actions.WorkShopExample2Actions;
import service.example2.util.InboundXML;

/**
 *
 * @author umansilla
 */
public class WorkShopExample2 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("WORKS");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAccessControlHeaders(response);
        try {
            switch (request.getParameter("example2")) {
                case "one":
                    new WorkShopExample2Actions(response, request).analizarYResponderNivelUnoIVRExampleB();
                    break;
                case "two":
                    new WorkShopExample2Actions(response, request).analizarYResponderNivelDosIVRExampleB();
                    break;
                case "three":
                    new WorkShopExample2Actions(response, request).analizarYResponderNivelTresIVRExampleB();
                    break;
                default:
                    response.getWriter().println(InboundXML.setRedirectInboundXML(InboundXML.EXAMPLE2_ERROR_CON_CAUSA, "EL NIVEL ENVIADO NO EXISTE"));
            }
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
            response.getWriter().println(InboundXML.setRedirectInboundXML(InboundXML.EXAMPLE2_ERROR_CON_CAUSA, "ERROR EN EL SERVIDOR"));
        }
    }

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
        response.setHeader("Content-Type", "application/xml; charset=utf-8");
    }
}
