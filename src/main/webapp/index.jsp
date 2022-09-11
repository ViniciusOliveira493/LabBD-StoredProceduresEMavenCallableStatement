<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cliente</title>
		<link rel="stylesheet" href="./css/style.css"/>
	</head>
	<body>
		<nav>
		
		</nav>
		<main>
			<div class="container">
				<h1>Clientes</h1>
				<form action="cliente" method="post">
					<div class="form-item">
						CPF:
						<input type="text" name="txtCpf" id="txtCpf" value='<c:out value="${cliente.cpf}"></c:out>'>
					</div>
					<div class="form-item">
						Nome:
						<input type="text" name="txtNome" id="txtNome" value='<c:out value="${cliente.nome}"></c:out>'>
					</div>
					<div class="form-item">
						E-mail:
						<input type="text" name="txtEmail" id="txtEmail" value='<c:out value="${cliente.email}"></c:out>'>
					</div>
					<div class="form-item">
						Limite de Crédito:
						<input type="number" min="0" step="0.01" name="txtLimite" id="txtLimite" value='<c:out value="${cliente.limiteDoCartao}"></c:out>'>
					</div>
					<div class="form-item">
						Data de Nascimento
						<input type="date" name="txtDtNasc" id="txtDtNasc" value='<c:out value="${cliente.dataNascimento}"></c:out>'>
					</div>
					<div class="form-item" id="divBtns">
						<input type="submit" id="btn" name="btn" value="Buscar">
						<input type="submit" id="btn" name="btn" value="Inserir">
						<input type="submit" id="btn" name="btn" value="Atualizar">
						<input type="submit" id="btn" name="btn" value="Excluir">
						<input type="submit" id="btn" name="btn" value="Listar">
					</div>
				</form>
			</div>
			<c:if test="${not empty saida or not empty lista or not empty error}">
				<div class="container">
					<c:if test="${not empty saida}">
						<p><c:out value="${saida}"></c:out></p>
					</c:if>
					<c:if test="${not empty error}">
						<p><c:out value="${error}"></c:out></p>
					</c:if>
					<c:if test="${not empty lista}">
						<table>
							<thead>
								<tr>
									<th>CPF</th>
									<th>Nome</th>
									<th>E-mail</th>
									<th>Limite</th>
									<th>Data nascimento</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${lista}" var="cli">
									<tr>
										<td><c:out value="${cli.cpf}"></c:out></td>
										<td><c:out value="${cli.nome}"></c:out></td>
										<td><c:out value="${cli.email}"></c:out></td>
										<td><c:out value="${cli.limiteDoCartao}"></c:out></td>
										<td><c:out value="${cli.dataNascimento}"></c:out></td>
									</tr>
								</c:forEach>								
							</tbody>
						</table>
					</c:if>
				</div>	
			</c:if>	
		</main>
		<footer>
		
		</footer>
	</body>
</html>