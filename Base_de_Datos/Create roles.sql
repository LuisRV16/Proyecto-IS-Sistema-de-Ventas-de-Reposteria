-- creacion de roles

create role employee
grant select, insert, update on cliente to employee
grant select on producto to employee
grant select on empleado to employee
grant insert on venta to employee
grant insert on ventaProducto to employee
grant exec on getEmployeeByUser to employee
grant exec on randomChar to employee
grant exec on getRandomId to employee
grant exec on addClient to employee
grant exec on updateClient to employee
grant exec on getProduct to employee
grant exec on getClient to employee
grant exec on getClientByName to employee
grant exec on dbo.getIdClientByName to employee
grant exec on dbo.getIdEmployeeByName to employee
grant exec on addSale to employee
grant exec on addSaleProduct to employee

grant exec on searchProduct to employee
grant exec on orderProducts to employee

-----------------------------------------------------

create role manager
grant select on cliente to manager
grant select, insert, update on producto to manager
grant select on venta to manager
grant select on ventaProducto to manager
grant exec on getEmployeeByUser to manager
grant exec on getClient to manager
grant exec on getSales to manager
grant exec on getSalesProducts to manager
grant exec on getProduct to manager
grant exec on getProductByName to manager
grant exec on addProduct to manager
grant exec on updateProduct to manager