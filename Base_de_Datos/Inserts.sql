
use sistemaReposteria

-- insercion de prueba para JDBC
insert into almacen (idMaterial, nombre, cantidad) values ('asggh4ca34gas45', 'Huevos', 2)
select * from almacen
GRANT SELECT ON sistemaReposteria.almacen TO userprueba;
-- insercion de prueba para JDBC

SELECT ASCII('A') AS Valor_ASCII;

SELECT CHAR(65) AS Caracter_ASCII;

DECLARE @numero_aleatorio INT;
SET @numero_aleatorio = ROUND(RAND() * 122, 0);
if ((@numero_aleatorio >= 48 and @numero_aleatorio <= 57) or (@numero_aleatorio >= 65 and @numero_aleatorio <= 122))
	select @numero_aleatorio
-- cambiar el if por un while o algo