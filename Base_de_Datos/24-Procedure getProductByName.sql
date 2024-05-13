create procedure getProductByName
	@name varchar(40)
as
begin
	select * from producto where nombre = @name
end