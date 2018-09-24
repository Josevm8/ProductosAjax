package model.dao;

import dto.Productos;
import java.util.List;

public interface DaoProductos {

    public List<Productos> productosQry();

    public String productosIns(Productos productos);

    public String productosDel(List<Integer> ids);

    public Productos productosGet(Integer idproducto);

    public String productosUpd(Productos productos);
}

