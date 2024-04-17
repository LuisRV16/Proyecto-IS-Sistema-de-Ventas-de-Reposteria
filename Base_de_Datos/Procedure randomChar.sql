-- Definición de la función para generar un carácter aleatorio
create procedure randomChar
as
begin
    declare @numero_aleatorio int
    declare @caracter char
    set @numero_aleatorio = round(rand() * 122, 0)
    while not ((@numero_aleatorio >= 48 and @numero_aleatorio <= 57) or
			   (@numero_aleatorio >= 65 and @numero_aleatorio <= 90) or
			   (@numero_aleatorio >= 97 and @numero_aleatorio <= 122))
    begin
        set @numero_aleatorio = round(rand() * 122, 0)
    end
    set @caracter = char(@numero_aleatorio)
    select @caracter
end