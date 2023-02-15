
drop table member;

create table member(
	id varchar(50) primary key check(id not in('')),
	pwd varchar(50) not null check(pwd not in('')),
	name varchar(50) not null check(name not in('')),
	email varchar(50) unique check(email not in('')),
	auth int
);

select id
from member
where id = 'abc';

select count(*)
from member
where id='abc';

select * from member;

delete from member
where id = '';