CREATE PROCEDURE addClient
    @name varchar(40),
    @lastName1 varchar(20),
    @lastName2 varchar(20),
    @phone varchar(15),
    @email varchar(255),
    @street varchar(30),
    @interiorNumber varchar(5),
    @outdoorNumber varchar(6),
    @postalCode varchar(5),
    @colony varchar(30),
    @city varchar(30),
    @state varchar(30)
AS
BEGIN
    DECLARE @id varchar(15)

	create table #tempRandomId (randomId varchar(15))
	
	insert into  #tempRandomId (randomId) exec getRandomId

	select top 1 @id = randomId from #tempRandomId

	IF NOT EXISTS (
        SELECT @id
        FROM clientes
        WHERE nombre = @name
        AND apellido1 = @lastName1
        AND apellido2 = @lastName2
    )
	BEGIN
        -- Insertar el nuevo cliente con el ID generado
        INSERT INTO clientes (idCliente, nombre, apellido1, apellido2, telefono, correo, calle, numeroInterior, numeroExterior, codigoPostal, colonia, ciudad, estado)
        VALUES (@id, @name, @lastName1, @lastName2, @phone, @email, @street, @interiorNumber, @outdoorNumber, @postalCode, @colony, @city, @state)

        IF @@ERROR = 0
        BEGIN
            PRINT 'Cliente insertado correctamente con ID aleatorio.'
        END
        ELSE
        BEGIN
            PRINT 'Ha ocurrido un error al insertar el cliente con ID aleatorio.'
        END
    END
    ELSE
    BEGIN
        PRINT 'El cliente ya existe.'
    END
END