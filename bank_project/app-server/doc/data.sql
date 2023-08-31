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
insert into bank_board(board_no, title, content, writer, category)
  values(11, '제목1', '내용', 1, 1);
insert into bank_board(board_no, title, content, writer, category)
  values(12, '제목2', '내용', 1, 1);
insert into bank_board(board_no, title, content, writer, category)
  values(13, '제목3', '내용', 3, 1);
insert into bank_board(board_no, title, content, writer, category)
  values(14, '제목4', '내용', 4, 1);
insert into bank_board(board_no, title, content, writer, category)
  values(15, '제목5', '내용', 5, 2);
insert into bank_board(board_no, title, content, writer, category)
  values(16, '제목6', '내용', 5, 2);
insert into bank_board(board_no, title, content, writer, category)
  values(17, '제목7', '내용', 5, 2);
  
-- board_category 테이블 예제 데이터
insert into board_category(board_category_no, name) values(1, '공지사항');
insert into board_category(board_category_no, name) values(2, '고객VOC');
  
-- myapp_board 테이블 예제 데이터
