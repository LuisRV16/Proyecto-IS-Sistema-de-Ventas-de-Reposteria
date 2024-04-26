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
    @state varchar(30),
	@msg varchar(61) output
AS
BEGIN
    DECLARE @id varchar(15)

	exec getRandomId @length = 15, @randomId = @id output

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
            set @msg = 'Cliente insertado correctamente con ID aleatorio.'
        END
        ELSE
        BEGIN
            set @msg = 'Ha ocurrido un error al insertar el cliente con ID aleatorio.'
        END
    END
    ELSE
    BEGIN
        set @msg = 'El cliente ya existe.'
    END
END