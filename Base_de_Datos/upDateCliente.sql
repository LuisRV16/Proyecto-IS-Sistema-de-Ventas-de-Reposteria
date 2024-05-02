CREATE PROCEDURE spActualizaCliente (
    @idCliente varchar(15),
    @telefono varchar(15) = NULL,
    @correo varchar(255) = NULL,
    @calle varchar(30) = NULL,
    @numeroInterior varchar(5) = NULL,
    @numeroExterior varchar(6) = NULL,
    @codigoPostal varchar(5) = NULL,
    @colonia varchar(30) = NULL,
    @ciudad varchar(30) = NULL,
    @estado varchar(30) = NULL
)
AS
BEGIN
    SET @telefono = replace(@telefono, '  ', '')
    SET @correo = replace(@correo, '  ', '')
    SET @calle = replace(@calle, '  ', ' ')
    SET @numeroInterior = replace(@numeroInterior, '  ', '')
    SET @numeroExterior = replace(@numeroExterior, '  ', '')
    SET @codigoPostal = replace(@codigoPostal, '  ', '')
    SET @colonia = replace(@colonia, '  ', ' ')
    SET @ciudad = replace(@ciudad, '  ', ' ')
    SET @estado = replace(@estado, '  ', '')
    UPDATE clientes
    SET
        telefono = ISNULL(@telefono, telefono),
        correo = ISNULL(@correo, correo),
        calle = ISNULL(@calle, calle),
        numeroInterior = ISNULL(@numeroInterior, numeroInterior),
        numeroExterior = ISNULL(@numeroExterior, numeroExterior),
        codigoPostal = ISNULL(@codigoPostal, codigoPostal),
        colonia = ISNULL(@colonia, colonia),
        ciudad = ISNULL(@ciudad, ciudad),
        estado = ISNULL(@estado, estado)
    WHERE idCliente = @idCliente

    IF @@ROWCOUNT > 0
    BEGIN
        PRINT 'Datos del cliente actualizados correctamente.'
    END
    ELSE
    BEGIN
        PRINT 'No se encontró ningún cliente con el ID especificado.'
    END
END


drop procedure spActualizaCliente