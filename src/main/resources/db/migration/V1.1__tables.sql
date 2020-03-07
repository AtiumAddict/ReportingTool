create TABLE employees(
	id bigint NOT NULL,
	title varchar(50),
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    username varchar(50) NOT NULL UNIQUE,
    email varchar(100) NOT NULL UNIQUE,
    gender varchar(50),
    comments varchar(1000),
PRIMARY KEY (id ASC));

create sequence employee_id_seq start with 1000 increment by 50;

create TABLE reports(
	id bigint NOT NULL,
	employee_id bigint NOT NULL,
	title varchar(200) NOT NULL,
    description varchar(1000),
    priority varchar(50) NOT NULL,
    created_on datetime2 NOT NULL,
    edited_on datetime2 NOT NULL,
PRIMARY KEY (id ASC));

create sequence report_id_seq start with 1000 increment by 50;
