-- account테스트데이터

insert into myapp_account(account_no, name, account_number, password, bank_name, balance) 
  values(1, '손흥민', '10001234',sha1('1111'), '신한은행', 20000);
insert into myapp_account(account_no, name, account_number, password, bank_name, balance) 
  values(2, '이강인', '20001234',sha1('1111'), '국민은행', 300000);
insert into myapp_account(account_no, name, account_number, password, bank_name, balance) 
  values(3, '조규성', '50001234',sha1('1111'), '농협은행', 500000);
insert into myapp_account(account_no, name, account_number, password, bank_name, balance) 
  values(4, '김민재', '30001111',sha1('1111'), '하나은행', 2000000);
insert into myapp_account(account_no, name, account_number, password, bank_name, balance) 
  values(5, '황의조', '10001212',sha1('1111'), '신한은행', 3000000);
insert into myapp_account(account_no, name, account_number, password, bank_name, balance) 
  values(6, '황희찬', '20001111',sha1('1111'), '국민은행', 1000000);
  
-- board 테스트 데이터
insert into myapp_board(board_no, title, content, writer, password)
  values (11, '제목1', '내용1', '손흥민', sha1('1111'));
insert into myapp_board(board_no, title, content, writer, password)
  values (12, '제목2', '내용2', '이강인', sha1('1111'));
insert into myapp_board(board_no, title, content, writer, password)
  values (13, '제목3', '내용3', '김민재', sha1('1111'));
insert into myapp_board(board_no, title, content, writer, password)
  values (14, '제목4', '내용4', '황의조', sha1('1111'));
insert into myapp_board(board_no, title, content, writer, password)
  values (15, '제목5', '내용5', '황희찬', sha1('1111'));
insert into myapp_board(board_no, title, content, writer, password)
  values (16, '제목6', '내용6', '조규성', sha1('1111'));
  
-- customer 데이터
insert into myapp_customer(customer_no, name, email, password, phone_number)
  values (1, '이강인', 'psg@test.com', sha1('1111'), '01011112222');
insert into myapp_customer(customer_no, name, email, password, phone_number)
  values (2, '손흥민', 'tot@test.com', sha1('1111'), '01022223333');
insert into myapp_customer(customer_no, name, email, password, phone_number)
  values (3, '김민재', 'byr@test.com', sha1('1111'), '01033331111');