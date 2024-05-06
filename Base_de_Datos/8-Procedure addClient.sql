create procedure addClient
    @name varchar(40),
    @lastName1 varchar(20),
    @lastName2 varchar(20),
	@rfc varchar(13),
    @phone varchar(15),
    @email varchar(255),
    @street varchar(30),
    @interiorNumber varchar(5),
    @outdoorNumber varchar(6),
    @postalCode varchar(5),
    @colony varchar(30),
    @city varchar(30),
    @state varchar(30),
	@msg varchar(200) output
as
begin
    declare @id varchar(15)
	declare @band bit = 1

	if @name = '' or replace(@name, ' ', '') = ''
		begin
			set @msg = 'Ingrese el nombre del cliente en el campo de texto.'
			set @band = 0
		end
	else if len(@name) = 1 or @name like '%[^A-Za-z ]%'
		begin
			set @msg = 'Ingrese el nombre correcto.' + CHAR(13) + CHAR(10) + 'El nombre debe contener más de una letra.' + CHAR(13) + CHAR(10) + 'El nombre no debe contener números.'
			set @band = 0
		end
	else if @lastName1 = '' or replace(@lastName1, ' ', '') = ''
		begin
			set @msg = 'Ingrese el apellido del cliente en el campo de texto.'
			set @band = 0
		end
	else if len(@lastName1) = 1 or @lastName1 like '%[^A-Za-z ]%'
		begin
			set @msg = 'Ingrese el Apellido Paterno correcto.' + CHAR(13) + CHAR(10) + 'El Apellido Paterno debe contener más de una letra.' + CHAR(13) + CHAR(10) + 'El Apellido Paterno no debe contener números.'
			set @band = 0
		end
	else if @lastName2 = '' or replace(@lastName2, ' ', '') = ''
		begin
			set @lastName2 = null
		end
	else if @lastName2 is not null and (len(@lastName2) = 1 or @lastName2 like '%[^A-Za-z ]%')
		begin
			set @msg = 'Ingrese el Apellido Materno correcto.' + CHAR(13) + CHAR(10) + 'El Apellido Materno debe contener más de una letra.' + CHAR(13) + CHAR(10) + 'El Apellido Materno no debe contener números.'
			set @band = 0
		end
	else if @rfc = ''
		begin
			set @msg = 'Ingrese el RFC con homoclave del cliente.'
			set @band = 0
		end
	else if len(@rfc) < 13 or @rfc collate Latin1_General_BIN like '%[^A-Z0-9]%'
		begin
			set @msg = 'Ingrese el RFC correcto.\nEl RFC debe contener 13 caracteres.' + CHAR(13) + CHAR(10) + 'El RFC solo debe contener letras mayúsculas y números.'
			set @band = 0
		end
	else if substring(@rfc, 0, 5) collate Latin1_General_BIN like '%[^A-Z]%'
		begin
			set @msg = 'Ingrese el RFC correcto.' + CHAR(13) + CHAR(10) + 'Los primeros 4 caracteres del RFC deben ser letras.'
			set @band = 0
		end
	else if substring(@rfc, 5, 6) like '%[^0-9]%'
		begin
			set @msg = 'Ingrese el RFC correcto.' + CHAR(13) + CHAR(10) + 'Los caracteres después de los 4 primeros y antes de la homoclave deben ser números, referentes a la fecha de nacimiento.'
			set @band = 0
		end
	else if @phone = ''
		begin
			set @msg = 'Ingrese el número de teléfono en el campo de texto.'
			set @band = 0
		end
	else if len(@phone) < 10 or @phone like '%[^0-9]%'
		begin
			set @msg = 'Ingrese el número de teléfono correcto.' + CHAR(13) + CHAR(10) + 'El numero de telefono se compone de 10 números.' + CHAR(13) + CHAR(10) + 'No se permiten carácteres distintos a dígitos.'
			set @band = 0
		end
	else if @street = '' or replace(@street, ' ', '') = ''
		begin
			set @msg = 'Ingrese la calle perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if len(@street) < 1 or @street like '%[^A-Za-z0-9 ]%'
		begin
			set @msg = 'Ingrese una calle válida.'
			set @band = 0
		end
	else if @interiorNumber = ''
		begin
			set @interiorNumber = 'S/N'
			set @band = 0
		end
	else if @interiorNumber like '%[^A-Z0-9]%'
		begin
			set @msg = 'Ingrese un numero interior correcto.' + CHAR(13) + CHAR(10) + 'El número interior puede estar compuesto por números y letras'
			set @band = 0
		end
	else if @outdoorNumber = ''
		begin
			set @msg = 'Ingrese el numero exterior perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if @outdoorNumber like '%[^A-Z0-9]%'
		begin
			set @msg = 'Ingrese un numero interior correcto.' + CHAR(13) + CHAR(10) + 'El número exterior puede estar compuesto por números y letras'
			set @band = 0
		end
	else if len(@postalCode) < 5 or @postalCode like '%[^0-9]%'
		begin
			set @msg = 'Ingrese el Código postal correcto.' + CHAR(13) + CHAR(10) + 'El Código postal se compone de 5 números.' + CHAR(13) + CHAR(10) + 'No se permiten carácteres distintos a dígitos.'
			set @band = 0
		end
	else if @colony = '' or replace(@colony, ' ', '') = ''
		begin
			set @msg = 'Ingrese la colonia perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if @colony like '%[^A-Z0-9 ]%'
		begin
			set @msg = 'Ingrese una colonia válida.'
			set @band = 0
		end
	else if @city = ''
		begin
			set @msg = 'Ingrese la ciudad perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if @state = ''
		begin
			set @msg = 'Ingrese el estado perteneciente a la dirección del cliente.'
			set @band = 0
		end
	else if @email = ''
		begin
			set @msg = 'Ingrese el correo electrónico en el campo de texto.'
			set @band = 0
		end
	else if charindex(' ', @email) > 0
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico no debe contener espacios en blanco.'
			set @band = 0
		end
	else if left(@email, 1) like '.'
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico no debe inicar con .'
			set @band = 0
		end
	else if left(@email, 1) like '@'
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico no debe inicar con @'
			set @band = 0
		end
	else if right(@email, 1) like '.'
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico no debe terminar con .'
			set @band = 0
		end
	else if right(@email, 1) like '@'
		begin
			set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico no debe terminar con @'
			set @band = 0
		end
	else
		begin
			declare @contador int = 0
			declare @posicion int = 1
			declare @caracter varchar(1)

			while @posicion <= len(@email)
				begin
					set @caracter = substring(@email, @posicion, 1)

					if @caracter = '@'
					begin
						set @contador = @contador + 1
					end

					set @posicion = @posicion + 1

				end

			if @contador > 1
				begin
					set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico no debe contener una @ solamente.'
					set @band = 0
				end
			else if @contador = 0
				begin
					set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico debe contener una @.'
					set @band = 0
				end

			if @band = 1
				begin
					if  substring(@email, charindex('@', @email) - 1, 1) = '.'
						begin
							set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico no debe tener un . antes del @'
							set @band = 0
						end
					else if substring(@email, charindex('@', @email) + 1, 1) = '.'
						begin
							set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico no debe tener un . después del @'
							set @band = 0
						end
					else if charindex('.', substring(@email, charindex('@', @email) + 1, len(@email))) = 0
						begin
							set @msg = 'Ingrese el correo electrónico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electrónico debe contener la parte del dominio.'
							set @band = 0
						end
				end

			if @band = 1
				begin
					set @posicion = 1

					while @posicion <= len(@email)
						begin
							set @caracter = substring(@email, @posicion, 1)

							if @caracter in ('[', ']', '!', '#', '$', '%', '&', '\', '*', '+', '=', '?', '^', '|', '{', '}', char(34), '¿', '¡', '°', ';', ':', '/', '<', '>', '(', ')')
								begin
									set @msg = 'Carácter inválido en el RFC.' + CHAR(13) + CHAR(10) + 'Ingrese el RFC correctamente.'
									set @band = 0
									break
								end

							set @posicion = @posicion + 1
						end
				end
		end

	if @band = 1
	begin
		if not exists (
			select * from cliente where rfc = @rfc
		)
			begin
				insert into cliente (rfc, nombre, apellido1, apellido2, telefono, correo, calle, numeroInterior,
									 numeroExterior, codigoPostal, colonia, ciudad, estado)
				values (@rfc, @name, @lastName1, @lastName2, @phone, @email, @street, @interiorNumber, @outdoorNumber, @postalCode, @colony,
						@city, @state)

				if @@ERROR = 0
					set @msg = 'Cliente insertado correctamente.'
				else
					set @msg = 'Ha ocurrido un error al insertar el cliente.'
			end
		else
			begin
				set @msg = 'El cliente ya existe.'
			end
	end
end