<%@page import="entidade.Produtos"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/css.css" rel="stylesheet" />
</head>
<body>
	<form action="controllerProduto" id="formProduto"
		enctype="application/x-www-form-urlencoded" method="post">



		<div style="width: 100%">

			<div style="float: left; width: 45%">
				<table>

					<tr>
						<td>
						<input type="hidden" type="text" style="width: 80px;" id="id" name="id"
							value="<c:out value="${produto.id}" />">
						</td>
					</tr>

					<tr>
						<td>Produto</td>
					</tr>
					<tr>
						<td>
						<input type="text" style="width: 200px;" id="produto"
							name="produto" value="<c:out value="${produto.produto}" />">
						</td>
					</tr>


				</table>
				<br /> <br /> <br /> <br />
				  <input type="submit" onclick="document.getElementById('formProduto').action = 'controllerProduto?acao=salvar'" value="Salvar"> 
					<input type="submit"  onclick="document.getElementById('formProduto').action = 'controllerProduto?acao=deletar&id=${produto.id}'" value="Limpar">
					<input type="submit"  onclick="document.getElementById('formProduto').action = 'controllerProduto?acao=todos'" value="Todos">
			</div>


		</div>

	</form>

	<br/>
	<br/>

<form method="get" action="controllerProduto">


<div align="left" style="width: 100%;">
	<table align="left" style="widows: 100%">
		<c:forEach items='${listadeprodutos}' var='produto'>
			<tr>
				<td style="width: 70px;" align="left"><c:out value="${produto.id}" /></td>
				<td style="width: 200px" align="left"><c:out value="${produto.produto}" /></td>

				<td align="right" style="width: 150px"><a
					href="controllerProduto?acao=editar&codigoProduto=<c:out value="${produto.id}"/>">Editar</a>
				</td>
				<td align="right" style="width: 150px"><a
					href="controllerProduto?acao=deletar&codigoProduto=<c:out value="${produto.id}"/>">Excluir</a>
				</td>


			</tr>
			<br />
		</c:forEach>

	</table>

</div>
</form>


</body>

</html>
