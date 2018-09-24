package web.servlet;

import dto.Productos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dao.DaoProductos;
import model.dao.impl.DaoProductosImpl;

@WebServlet(name = "ServletProductos", urlPatterns = {"/Productos"})
public class ServletProductos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String result = null;
        String error = null;
        //
        DaoProductos daoProductos = new DaoProductosImpl();

        if (accion == null) {
            error = "Solicitud no recibida";

        } else if (accion.equals("QRY")) {
            error = query(request, daoProductos); //dev error = null

        } else if (accion.equals("INS")) {
            Productos productos = new Productos();
            error = valida(request, productos);

            if (error == null) {
                error = daoProductos.productosIns(productos);
            }

            if (error == null) {
                error = query(request, daoProductos);
            }

        } 
        else if (accion.equals("DEL")) {
            String ids = request.getParameter("ids");
            String[] id = null;
            if (ids != null) {
                id = ids.split(",");
            } else {
                error = "No ha llegado idproducto(s)";
            }

            List<Integer> list = new ArrayList<Integer>();
            if (error == null) {
                for (String ix : id) {
                    try {
                        Integer idproducto = Integer.valueOf(ix);
                        list.add(idproducto);

                    } catch (NumberFormatException e) {
                        error = "Valor errado de idproducto";
                        break;
                    }
                }
            }

            if (error == null) {
                error = daoProductos.productosDel(list);
            }

            if (error == null) {
                error = query(request, daoProductos);
            }

        } 
        
        else if (accion.equals("GET")) {
            String id = request.getParameter("id");
            Integer idx = null;
            try {
                idx = Integer.valueOf(id);
            } catch (NumberFormatException e) {
                error = "Valor errado de idproducto";
            }

            if (error == null) {
                Productos productos = daoProductos.productosGet(idx);
                if (productos != null) {
                    result = productos.getIdproducto() + "+++"
                            + productos.getTitulo() + "+++"
                            + productos.getTipo() + "+++"
                            + productos.getPrecio();
                }
            }

        } else if (accion.equals("UPD")) {
            Productos productos = new Productos();
            error = valida(request, productos);

            if (error == null) {
                error = daoProductos.productosUpd(productos);
            }

            if (error == null) {
                error = query(request, daoProductos);
            }

        } 
        else {
            error = "Solicitud no reconocida";
        }

        // para manejar errores
        if (error != null) {
            request.getSession().setAttribute("error", error);
        } else {
            request.getSession().removeAttribute("error");
        }

        // resultado de solicitud ajax
        if (result != null) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.close();

        } else {
            response.sendRedirect("productos.jsp");
        }
    }

    // apoyo
    private String query(HttpServletRequest request, DaoProductos dao) {
        String error = null;

        List<Productos> list = dao.productosQry();

        if (list != null) {
            request.getSession().setAttribute("prod_qry", list);
        } else {
            error = "Sin acceso a Base de Datos";
        }

        return error;
    }

    private String valida(HttpServletRequest request, Productos p) {
        String error = null;

        String idproducto = request.getParameter("idproducto");
        String titulo = request.getParameter("titulo");
        String tipo = request.getParameter("tipo");
        String precio = request.getParameter("precio");

        Integer idproductox = null;
        if (idproducto != null) {
            try {
                idproductox = Integer.valueOf(idproducto);
            } catch (NumberFormatException e) {
                error = "Valor errado de idproducto";
            }
        }

        if (error == null) {
            if ((titulo == null) || (titulo.trim().length() == 0)) {
                error = "Debe ingresar T&iacute;tulo de Producto";
            }
        }

        if (error == null) {
            if ((tipo == null) || (tipo.trim().length() == 0)) {
                error = "Debe ingresar Tipo de Producto";
            }
        }

        Double preciox = null;
        if (error == null) {
            try {
                preciox = Double.valueOf(precio);
            } catch (NumberFormatException e) {
                error = "Error en ingreso de Precio de Producto";
            }
        }

        if (error == null) {
            p.setIdproducto(idproductox);
            p.setTitulo(titulo);
            p.setTipo(tipo);
            p.setPrecio(preciox);
        }

        return error;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
