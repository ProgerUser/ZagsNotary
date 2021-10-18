--Класс
select *
  from table(jfx_object.ret_class(upper(&table)))
 order by strnum asc;
--Заполнить класс данными
select * from table(jfx_object.ret_fields(upper(&table)));

--TableView Columns
select * from table(jfx_object.ret_table_col(upper(&table)));

--Вызвать пакет
select *
  from table(jfx_object.ret_callable_statment(package_name_ => 'PM_EMP_PKG',
                                              object_name_  => 'ADD',
                                              TableName     => upper(&table)));

--Создать xml
select * from table(jfx_object.ret_table_xml(upper(&table)));

--Столбцы таблицы 
select * from table(jfx_object.ret_table_cols(upper(&table)));

--Выбор полей xml
select * from table(jfx_object.ret_table_from_xml_fld(upper(&table)));

--Выбор полей xml varchar
select * from table(jfx_object.ret_table_from_xml_char(upper(&table)));

--Для вставки
select * from table(jfx_object.ret_program_insert(upper(&table)));

--Для обновление
select * from table(jfx_object.ret_program_update(upper(&table)));

--Параметры
select * from table(jfx_object.ret_program_param(upper(&table)));
