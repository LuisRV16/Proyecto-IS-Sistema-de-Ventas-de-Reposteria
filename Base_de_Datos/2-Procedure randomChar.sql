-- Definición de la función para generar un carácter aleatorio
create procedure randomChar
	@char char output
as
begin
    declare @random_number int

    set @random_number = round(rand() * 122, 0)
    while not ((@random_number >= 48 and @random_number <= 57) or
			   (@random_number >= 65 and @random_number <= 90) or
			   (@random_number >= 97 and @random_number <= 122))
		begin
			set @random_number = round(rand() * 122, 0)
		end

    set @char = char(@random_number)

end