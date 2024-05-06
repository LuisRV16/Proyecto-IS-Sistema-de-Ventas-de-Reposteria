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
			set @msg = 'Ingrese el nombre correcto.' + CHAR(13) + CHAR(10) + 'El nombre debe contener m�s de una letra.' + CHAR(13) + CHAR(10) + 'El nombre no debe contener n�meros.'
			set @band = 0
		end
	else if @lastName1 = '' or replace(@lastName1, ' ', '') = ''
		begin
			set @msg = 'Ingrese el apellido del cliente en el campo de texto.'
			set @band = 0
		end
	else if len(@lastName1) = 1 or @lastName1 like '%[^A-Za-z ]%'
		begin
			set @msg = 'Ingrese el Apellido Paterno correcto.' + CHAR(13) + CHAR(10) + 'El Apellido Paterno debe contener m�s de una letra.' + CHAR(13) + CHAR(10) + 'El Apellido Paterno no debe contener n�meros.'
			set @band = 0
		end
	else if @lastName2 = '' or replace(@lastName2, ' ', '') = ''
		begin
			set @lastName2 = null
		end
	else if @lastName2 is not null and (len(@lastName2) = 1 or @lastName2 like '%[^A-Za-z ]%')
		begin
			set @msg = 'Ingrese el Apellido Materno correcto.' + CHAR(13) + CHAR(10) + 'El Apellido Materno debe contener m�s de una letra.' + CHAR(13) + CHAR(10) + 'El Apellido Materno no debe contener n�meros.'
			set @band = 0
		end
	else if @rfc = ''
		begin
			set @msg = 'Ingrese el RFC con homoclave del cliente.'
			set @band = 0
		end
	else if len(@rfc) < 13 or @rfc collate Latin1_General_BIN like '%[^A-Z0-9]%'
		begin
			set @msg = 'Ingrese el RFC correcto.\nEl RFC debe contener 13 caracteres.' + CHAR(13) + CHAR(10) + 'El RFC solo debe contener letras may�sculas y n�meros.'
			set @band = 0
		end
	else if substring(@rfc, 0, 5) collate Latin1_General_BIN like '%[^A-Z]%'
		begin
			set @msg = 'Ingrese el RFC correcto.' + CHAR(13) + CHAR(10) + 'Los primeros 4 caracteres del RFC deben ser letras.'
			set @band = 0
		end
	else if substring(@rfc, 5, 6) like '%[^0-9]%'
		begin
			set @msg = 'Ingrese el RFC correcto.' + CHAR(13) + CHAR(10) + 'Los caracteres despu�s de los 4 primeros y antes de la homoclave deben ser n�meros, referentes a la fecha de nacimiento.'
			set @band = 0
		end
	else if @phone = ''
		begin
			set @msg = 'Ingrese el n�mero de tel�fono en el campo de texto.'
			set @band = 0
		end
	else if len(@phone) < 10 or @phone like '%[^0-9]%'
		begin
			set @msg = 'Ingrese el n�mero de tel�fono correcto.' + CHAR(13) + CHAR(10) + 'El numero de telefono se compone de 10 n�meros.' + CHAR(13) + CHAR(10) + 'No se permiten car�cteres distintos a d�gitos.'
			set @band = 0
		end
	else if @street = '' or replace(@street, ' ', '') = ''
		begin
			set @msg = 'Ingrese la calle perteneciente a la direcci�n del cliente.'
			set @band = 0
		end
	else if len(@street) < 1 or @street like '%[^A-Za-z0-9 ]%'
		begin
			set @msg = 'Ingrese una calle v�lida.'
			set @band = 0
		end
	else if @interiorNumber = ''
		begin
			set @interiorNumber = 'S/N'
			set @band = 0
		end
	else if @interiorNumber like '%[^A-Z0-9]%'
		begin
			set @msg = 'Ingrese un numero interior correcto.' + CHAR(13) + CHAR(10) + 'El n�mero interior puede estar compuesto por n�meros y letras'
			set @band = 0
		end
	else if @outdoorNumber = ''
		begin
			set @msg = 'Ingrese el numero exterior perteneciente a la direcci�n del cliente.'
			set @band = 0
		end
	else if @outdoorNumber like '%[^A-Z0-9]%'
		begin
			set @msg = 'Ingrese un numero interior correcto.' + CHAR(13) + CHAR(10) + 'El n�mero exterior puede estar compuesto por n�meros y letras'
			set @band = 0
		end
	else if len(@postalCode) < 5 or @postalCode like '%[^0-9]%'
		begin
			set @msg = 'Ingrese el C�digo postal correcto.' + CHAR(13) + CHAR(10) + 'El C�digo postal se compone de 5 n�meros.' + CHAR(13) + CHAR(10) + 'No se permiten car�cteres distintos a d�gitos.'
			set @band = 0
		end
	else if @colony = '' or replace(@colony, ' ', '') = ''
		begin
			set @msg = 'Ingrese la colonia perteneciente a la direcci�n del cliente.'
			set @band = 0
		end
	else if @colony like '%[^A-Z0-9 ]%'
		begin
			set @msg = 'Ingrese una colonia v�lida.'
			set @band = 0
		end
	else if @city = ''
		begin
			set @msg = 'Ingrese la ciudad perteneciente a la direcci�n del cliente.'
			set @band = 0
		end
	else if @state = ''
		begin
			set @msg = 'Ingrese el estado perteneciente a la direcci�n del cliente.'
			set @band = 0
		end
	else if @email = ''
		begin
			set @msg = 'Ingrese el correo electr�nico en el campo de texto.'
			set @band = 0
		end
	else if charindex(' ', @email) > 0
		begin
			set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico no debe contener espacios en blanco.'
			set @band = 0
		end
	else if left(@email, 1) like '.'
		begin
			set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico no debe inicar con .'
			set @band = 0
		end
	else if left(@email, 1) like '@'
		begin
			set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico no debe inicar con @'
			set @band = 0
		end
	else if right(@email, 1) like '.'
		begin
			set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico no debe terminar con .'
			set @band = 0
		end
	else if right(@email, 1) like '@'
		begin
			set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico no debe terminar con @'
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
					set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico no debe contener una @ solamente.'
					set @band = 0
				end
			else if @contador = 0
				begin
					set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico debe contener una @.'
					set @band = 0
				end

			if @band = 1
				begin
					if  substring(@email, charindex('@', @email) - 1, 1) = '.'
						begin
							set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico no debe tener un . antes del @'
							set @band = 0
						end
					else if substring(@email, charindex('@', @email) + 1, 1) = '.'
						begin
							set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico no debe tener un . despu�s del @'
							set @band = 0
						end
					else if charindex('.', substring(@email, charindex('@', @email) + 1, len(@email))) = 0
						begin
							set @msg = 'Ingrese el correo electr�nico correctamente.' + CHAR(13) + CHAR(10) + 'El correo electr�nico debe contener la parte del dominio.'
							set @band = 0
						end
				end

			if @band = 1
				begin
					set @posicion = 1

					while @posicion <= len(@email)
						begin
							set @caracter = substring(@email, @posicion, 1)

							if @caracter in ('[', ']', '!', '#', '$', '%', '&', '\', '*', '+', '=', '?', '^', '|', '{', '}', char(34), '�', '�', '�', ';', ':', '/', '<', '>', '(', ')')
								begin
									set @msg = 'Car�cter inv�lido en el RFC.' + CHAR(13) + CHAR(10) + 'Ingrese el RFC correctamente.'
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