create procedure getRandomId
	@length int,
	@randomId varchar(20) output
as
begin
	declare @id varchar(20) = ''
	declare @counter int = 1
	declare @character char

	while @counter <= @length
	begin	
		exec randomChar @char = @character output
		set @id = @id + @character
		set @counter = @counter + 1
	end

	set @randomId = @id
end