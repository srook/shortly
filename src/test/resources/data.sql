drop sequence hibernate_sequence if exists;
create sequence hibernate_sequence start with 10 increment by 1;

delete from links;
delete from accounts;

insert into accounts (id, username, encrypted_password) values (1, 'shortly', '$2a$11$XL7tPfQpW4Hmr9buUsTSN.09I.qcj9GEayENXdKTv9H8BtiGcDFZq');

insert into links (id, url, key, account_id, redirect_code, visit_count)
values
	(1, 'http://shortly.org/test/controller', 'testshort', 1, 302, 0),
	(2, 'http://shortly.org/test/controller/some/longer/path', 'testlong', 1, 301, 1);
