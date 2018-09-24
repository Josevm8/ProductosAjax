<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Java en Serio</title>

        <link href="jq/mint-choc/jquery-ui-1.10.3.custom.min.css" type="text/css" rel="stylesheet"/>
        <link href="css/main.css" type="text/css" rel="stylesheet"/>
        <link href="jq/parainfo/crud.css" type="text/css" rel="stylesheet"/>

        <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script> 
        <script src="jq/jquery-2.0.3.min.js" type="text/javascript"></script>
        <script src="jq/mint-choc/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script> 
        <script src="jq/parainfo/crud.js" type="text/javascript"></script>
    </head>
    <body onload="pi_load()">
        <table class="parainfo" style="margin: auto;width: 540px">
            <thead>
                <tr>
                    <td style="padding: 5px">Producto</td>
                    <td>Precio</td>
                    <td>Tipo</td>
                    <th class="crud">
                        <a href="#" class="ins"><span></span></a>
                    </th>
                    <th class="crud">
                        <a href="#" class="del"><span></span></a>
                    </th>
                    <th class="crud">
                        <a href="#" class="upd"><span></span></a>
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="f" items="${prod_qry}">
                    <tr>
                        <td>${f.titulo}</td>
                        <td>${f.precio}</td>
                        <td colspan="2">${f.tipo}</td>
                        <th>
                            <input type="checkbox" name="_del" 
                                   value="${f.idproducto}"/>
                        </th>
                        <th>
                            <input type="radio" name="_upd" 
                                   value="${f.idproducto}"/>
                        </th>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <%-- para mensajes  --%>
        <div id="error" class="ui-state-error ui-corner-all" 
             style="margin: auto;width: 500px;margin-top: 30px">
            <p style="text-align: center;font-weight: bold">${error}</p>
        </div>
        <c:choose>
            <c:when test="${error != null}">
                <script type="text/javascript">
                    $("#error").css("display", "block").fadeIn("slow").delay(3000).fadeOut("slow");
                    ;
                </script>
            </c:when>
            <c:otherwise>
                <script type="text/javascript">
                    $("#error").css("display", "none");
                </script>
            </c:otherwise>
        </c:choose>

        <%-- para INS  --%>
        <div style="display: none;">
            <div id="dins" title="Nuevo registro">
                <form action="Productos" method="post">
                    <input type="hidden" name="accion" value="INS"/>

                    <table>
                        <tr>
                            <td>Producto</td>
                            <td>
                                <input type="text" name="titulo" 
                                       maxlength="200" style="width: 200px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Tipo</td>
                            <td>
                                <select name="tipo" style="width: 205px">
                                    <option value="CAJA">CAJA</option>
                                    <option value="PLASTICO">PLASTICO</option>
                                    <option value="VIDRIO">VIDRIO</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Precio</td>
                            <td>
                                <input type="text" name="precio" maxlength="10" 
                                       style="width: 60px;text-align: right"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <input type="submit" value="Enviar Datos"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <%-- para UPD  --%>
        <div style="display: none">
            <div id="dupd" title="Actualizar datos de registro">
                <form action="Productos" method="post">
                    <input type="hidden" name="accion" value="UPD"/>
                    <input type="hidden" name="idproducto" id="idproducto"/>

                    <table>
                        <tr>
                            <td>Producto</td>
                            <td>
                                <input type="text" name="titulo" id="titulo" 
                                       maxlength="200" style="width: 200px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Tipo</td>
                            <td>
                                <select name="tipo" style="width: 205px" id="tipo">
                                    <option value="CAJA">CAJA</option>
                                    <option value="PLASTICO">PLASTICO</option>
                                    <option value="VIDRIO">VIDRIO</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Precio</td>
                            <td>
                                <input type="text" name="precio" maxlength="10" 
                                       style="width: 60px;text-align: right"
                                       id="precio"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <input type="submit" value="Enviar Datos"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
