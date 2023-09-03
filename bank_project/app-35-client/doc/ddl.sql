create table myapp_board(
  board_no int not null,
  title varchar(255) not null,
  content text null,
  writer varchar(20) not null,
  password varchar(100) null,
  view_count int default 0,
  created_date datetime default now()
);

alter table myapp_board
  add constraint primary key (board_no),
  modify column board_no int not null auto_increment;
  
create table myapp_account(
  account_no int not null,
  name varchar(20) not null,
  account_number varchar(50) not null,
  password varchar(100) not null,
  bank_name varchar(20) not null,
  balance int not null
);

alter table myapp_account
  add constraint primary key (account_no),
  modify column account_no int not null auto_increment;
  
create table myapp_customer(
  customer_no int not null,
  name varchar(20) not null,
  email varchar(50) not null,
  password varchar(50) not null,
  phone_number varchar(50) not null,
  created_date datetime default now()
);

alter table myapp_customer
  add constraint primary key (customer_no),
  modify column customer_no int not null auto_increment;

create table ts_history(
  ts_no int not null,
  name varchar(20) not null,
  account_number varchar(50) not null,
  bank_name varchar(20) not null,
  balance int not null,
  deposit int not null,
  withraw int not null,
  created_date datetime default now()
);

alter table ts_history
  add constraint primary key (ts_no),
  modify column ts_no int not null auto_increment;
