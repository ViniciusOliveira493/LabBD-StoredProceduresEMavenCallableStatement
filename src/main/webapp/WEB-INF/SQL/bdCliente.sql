CREATE DATABASE	bdClientes
GO
USE bdClientes
CREATE	TABLE tbCliente(
	cpf				CHAR(11)		NOT NULL
	, nome			VARCHAR(100)	NOT NULL
	, email			VARCHAR(200)	NOT NULL
	, limiteCredito	DECIMAL(7,2)	NOT NULL
	, dtNascimento	DATE			NOT NULL
	PRIMARY KEY(cpf)
);

--========================================================================================================
CREATE PROCEDURE sp_calcularDigito(@digitoVerificador INT, @cpf CHAR(11), @digito INT OUTPUT)
AS
DECLARE @pos INT, @multiplicar INT,@resultado INT;
DECLARE @div FLOAT, @mod FLOAT;
SET @pos= 1
IF(@digitoVerificador = 1)
BEGIN
	SET @multiplicar = 10
END

IF(@digitoVerificador != 1)
BEGIN
	SET @multiplicar = 11
END

SET @resultado = 0
WHILE(@multiplicar > 1)
BEGIN
	SET @resultado = (CAST(SUBSTRING(@cpf, @pos,1) AS INT) * @multiplicar) +@resultado
	SET @pos = @pos+1
	SET @multiplicar = @multiplicar-1
END
SET @div = @resultado / 11
SET @mod = @resultado % 11 

IF(@mod < 2)
BEGIN
	SET @digito = 0
END

IF(@mod > 1)
BEGIN
	SET @digito = 11-@mod
END

--=====================
CREATE PROCEDURE sp_validarCPF(@cpf CHAR(11), @valido BIT OUTPUT)
AS
SET @valido = 0
IF(@cpf != '11111111111' AND @cpf != '22222222222' AND @cpf != '33333333333'
	AND @cpf != '44444444444' AND @cpf != '55555555555' AND @cpf != '66666666666'
		AND @cpf != '77777777777' AND @cpf != '88888888888' AND @cpf != '99999999999'
			AND @cpf != '00000000000' )
BEGIN
	DECLARE @cpf2 VARCHAR(11)
	DECLARE @digito INT
	SET @digito = 0
	SET @cpf2  = SUBSTRING(@cpf,1,9)
	--Calculando primeiro digito verificador
	EXEC sp_calcularDigito 1,@cpf2, @digito OUTPUT
	SET @cpf2 = @cpf2 + CAST(@digito AS CHAR(1))
	--Calculando segundo digito verificador
	EXEC sp_calcularDigito 2,@cpf2, @digito OUTPUT
	SET @cpf2 = @cpf2 + CAST(@digito AS CHAR(1))

	IF(@cpf = @cpf2)
	BEGIN
		SET @valido = 1
	END
END
--=========================== PROCEDURE CLIENTE ====================================
CREATE PROCEDURE sp_cliente(@op CHAR,@cpf CHAR(11),@nome VARCHAR(100),@email VARCHAR(200),@limiteCredito DECIMAL(7,2),@dtNasc DATE,@out VARCHAR(50) OUTPUT)
AS
	IF(UPPER(@OP) = 'I')
	BEGIN
		DECLARE @valido BIT
		EXEC sp_validarCPF @cpf,@valido OUTPUT
		IF(@valido = 1)
		BEGIN
			INSERT INTO tbCliente(cpf,nome,email,limiteCredito,dtNascimento)
				VALUES
					(@cpf,@nome,@email,@limiteCredito,@dtNasc)
			SET @out = 'Cliente cadastrado com sucesso'
		END
		IF(@valido != 1)
		BEGIN
			RAISERROR('O CPF digitado é inválido',16,1)
		END
	END
	IF(UPPER(@OP) = 'U')
	BEGIN
		UPDATE tbCliente
		SET 
			nome = @nome
			, email = @email
			, limiteCredito = @limiteCredito
			, dtNascimento = @dtNasc
		WHERE
			cpf = @cpf
		SET @out = 'Os Dados do cliente foram atualizados com sucesso'
	END
	IF(UPPER(@OP) = 'D')
	BEGIN
		DELETE FROM tbCliente
		WHERE
			cpf = @cpf
		SET @out = 'Os Dados do cliente foram apagados com sucesso'
	END
	IF(UPPER(@OP) = 'D' AND UPPER(@OP) = 'U' AND UPPER(@OP) = 'I')
	BEGIN
		RAISERROR('Opção Inválida',16,1)
	END
--==================================== CLIENTES ====================================
DECLARE @out VARCHAR(50)
exec sp_cliente 'i','62327601827','Ester Natália Isabelle das Neves','ester.natalia.dasneves@eton.com.br',5000,'1977-07-13', @out OUTPUT
exec sp_cliente 'i','26336852695','Daniela Flávia Caroline Castro','daniela-castro71@itatiaia.net',6000,'1954-04-07', @out OUTPUT
exec sp_cliente 'i','53860347586','Yago Marcos Drumond','yago-drumond@dprf.gov.br',4000,'2003-05-21', @out OUTPUT
--exec sp_cliente 'i','53860347516','Yago Marcos Drumond','yago-drumond@dprf.gov.br',4000,'2003-05-21', @out OUTPUT
print @out