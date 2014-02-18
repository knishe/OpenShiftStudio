CREATE TABLE IF NOT EXISTS Reputation (
	id int unsigned not null auto_increment,
	site varchar(600) not null,
	reputation varchar(600),
	gold varchar(300) ,
	silver varchar(100),
	bronze varchar(100),
	create_by varchar(45),
	create_at datetime not null,
	update_by varchar(45),
	update_at datetime not null,
	primary key (id)
);