create procedure getRandomId
	@length int
as
begin
	declare @id varchar(20) = ''
	declare @counter int = 1
	declare @character char = ''

	create table #tempRandomCharResult (charResult char)

	while @counter <= @length
	begin
		insert into #tempRandomCharResult (charResult) exec randomChar

		select top 1 @character = charResult from #tempRandomCharResult
		set @id = @id + @character
		delete from #tempRandomCharResult
		set @counter = @counter + 1
	end
	drop table #tempRandomCharResult
	select @id as randomId
end
