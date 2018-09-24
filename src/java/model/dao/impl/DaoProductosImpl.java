package model.dao.impl;

import dto.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.dao.DaoProductos;
import parainfo.sql.ConectaDb;

public class DaoProductosImpl implements DaoProductos {

    private ConectaDb sql;

    public DaoProductosImpl() {
        this.sql = new ConectaDb();
    }

    @Override
    public List<Productos> productosQry() {
        List<Productos> list = new ArrayList<Productos>();
        String s = "SELECT idproducto,titulo,tipo,precio FROM productos ORDER BY titulo";
        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                PreparedStatement ps = cn.prepareStatement(s);
                ResultSet rs = ps.executeQuery(s);
                while (rs.next()) {
                    Productos p = new Productos();
                    p.setIdproducto(rs.getInt(1));
                    p.setTitulo(rs.getString(2));
                    p.setTipo(rs.getString(3));
                    p.setPrecio(rs.getDouble(4));
                    list.add(p);
                }

            } catch (SQLException e) {
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                }
            }
        }
        return list;
    }

    @Override
    public String productosIns(Productos productos) {
        String result = null;
        String s = "INSERT INTO productos(titulo, tipo, precio) "
                + "VALUES(?, ?, ?)";

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                PreparedStatement ps = cn.prepareStatement(s);
                ps.setString(1, productos.getTitulo());
                ps.setString(2, productos.getTipo());
                ps.setDouble(3, productos.getPrecio());

                int ctos = ps.executeUpdate();
                if (ctos == 0) {
                    result = "0 filas afectadas";
                }

            } catch (SQLException e) {
                result = e.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                    result = e.getMessage();
                }
            }
        }

        return result;
    }

    @Override
    public String productosDel(List<Integer> ids) {
        String result = null;
        String s = "DELETE FROM productos WHERE idproducto=?";

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                PreparedStatement ps = cn.prepareStatement(s);
                for (Integer ix : ids) {
                    ps.setInt(1, ix);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                result = e.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                    result = e.getMessage();
                }
            }
        }

        return result;
    }

    @Override
    public Productos productosGet(Integer idproducto) {
        Productos productos = null;
        String s = "SELECT "
                + "idproducto,"
                + "titulo,"
                + "tipo,"
                + "precio "
                + "FROM productos "
                + "WHERE idproducto=?";

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                PreparedStatement ps = cn.prepareStatement(s);
                ps.setInt(1, idproducto);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    productos = new Productos();

                    productos.setIdproducto(rs.getInt(1));
                    productos.setTitulo(rs.getString(2));
                    productos.setTipo(rs.getString(3));
                    productos.setPrecio(rs.getDouble(4));
                }

            } catch (SQLException e) {
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                }
            }
        }

        return productos;
    }

    @Override
    public String productosUpd(Productos productos) {
        String result = null;
        String s = "UPDATE productos SET "
                + "titulo=?,"
                + "tipo=?,"
                + "precio=? "
                + "WHERE idproducto=?";

        Connection cn = sql.getConnection();
        if (cn != null) {
            try {
                PreparedStatement ps = cn.prepareStatement(s);
                ps.setString(1, productos.getTitulo());
                ps.setString(2, productos.getTipo());
                ps.setDouble(3, productos.getPrecio());
                ps.setInt(4, productos.getIdproducto());

                int ctos = ps.executeUpdate();
                if (ctos == 0) {
                    result = "0 filas afectadas";
                }

            } catch (SQLException e) {
                result = e.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e) {
                    result = e.getMessage();
                }
            }
        }

        return result;
    }
}
