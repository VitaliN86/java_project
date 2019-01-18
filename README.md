# Project for my testing in Java-Incubator company
To start the project one should have postgresql installed with java_incubator database created there and table named url_info created also.  
Login should be 'postgres' and pass should be 'postgres'.  
To create a table one can use this script:  
``create table public.url_info (  
	id serial primary key,  
	url varchar(1000) not null,  
	date date,  
	status int  
);``  
Project has a commandline-based interface.  
One should pass one argument to the program: check date in the format yyyy-MM-dd.  
For example,  
``java_incubator 2019-01-09``